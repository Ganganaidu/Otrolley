package com.thisisswitch.otrolly.driver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import com.thisisswitch.otrolly.driver.BaseActivity;
import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.dialogs.DialogTripAcceptFragment;
import com.thisisswitch.otrolly.driver.events.DriverLogOutEvent;
import com.thisisswitch.otrolly.driver.events.InitialTripRequestEvent;
import com.thisisswitch.otrolly.driver.events.SessionExpiredEvent;
import com.thisisswitch.otrolly.driver.events.TripAcceptEvent;
import com.thisisswitch.otrolly.driver.events.TripEndEvent;
import com.thisisswitch.otrolly.driver.events.TripRequestMessageEvent;
import com.thisisswitch.otrolly.driver.events.TripStatusCodes;
import com.thisisswitch.otrolly.driver.fragments.DrawerFragment;
import com.thisisswitch.otrolly.driver.fragments.TrolleyMapFragment;
import com.thisisswitch.otrolly.driver.models.TripEnd;
import com.thisisswitch.otrolly.driver.models.TripRequest;
import com.thisisswitch.otrolly.driver.network.OTrolleyAPIRequest;
import com.thisisswitch.otrolly.driver.network.SocketSession;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;

import org.json.JSONObject;

/**
 * @author Gangadhar
 */
public class MainActivity extends BaseActivity
        implements DrawerFragment.FragmentDrawerListener {

    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DrawerFragment drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        displayView(0);

        //Log.e(TAG, "accesToken:" + AppPreferences.getInstance().getAccessToken());
        TripEnd tripEnd = new TripEnd();
        MyApplication.getInstance().setTripEndRequest(tripEnd);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //display trip id
    public void showTripRequestDialog(JSONObject object) {
        Log.e(TAG, "data:" + object);
        FragmentManager fm = getSupportFragmentManager();
        DialogTripAcceptFragment tripAcceptDialog = new DialogTripAcceptFragment();
        tripAcceptDialog.show(fm, "DialogTripAcceptFragment");

        tripAcceptDialog.setTripJsonResponse(object);
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        if (!SocketSession.isTripStarted) {
            displayView(position);
        } else {
            Toast.makeText(this, getString(R.string.complete_trip_msg), Toast.LENGTH_SHORT).show();
        }
    }

    FragmentTransaction fragmentTransaction;

    private void displayView(int position) {
        switch (position) {
            case 0:
                getSupportActionBar().setTitle(R.string.nav_item_home);
                loadTripMapFragment();
                break;
            case 1:
                Intent intent = new Intent(this, TripHistoryActivity.class);
                startActivity(intent);
                break;
            case 2://Track

                break;
            case 3://logout
                showProgress(this, "Pleas wait...");
                OTrolleyAPIRequest.getInstance().logOutUser();
                break;
        }
    }


    private void loadTripMapFragment() {
        TrolleyMapFragment mainFragment = new TrolleyMapFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, mainFragment, "TrolleyMapFragment");
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (View.VISIBLE == findViewById(R.id.showIncomeLayout).getVisibility()) {
            findViewById(R.id.showIncomeLayout).setVisibility(View.GONE);
            return;
        }
        moveTaskToBack(true);
    }

    @Subscribe
    public void onTripRequest(TripRequestMessageEvent event) {
        showTripRequestDialog(event.tripRequestJson);
    }

    @Subscribe
    public void onTripEnd(TripEndEvent event) {
        Log.d(TAG, "tripEnd");

        SocketSession.getInstance().tripEndStatus(AppPreferences.getInstance().getUserId(), AppPreferences.getInstance().getTripId());
        SocketSession.isTripStarted = false;

        AppPreferences.getInstance().clearTripPref();

        //refresh trip page
        loadTripMapFragment();
    }

    @Subscribe
    public void onTripAccepted(TripAcceptEvent tripAcceptEvent) {
        showProgress(MainActivity.this, "Loading trip details please wait....");
        OTrolleyAPIRequest.getInstance().getTripDetails(tripAcceptEvent.tripId);
    }

    @Subscribe
    public void onUserSessionExpired(SessionExpiredEvent sessionExpiredEvent) {
        logOutUser();
    }

    @Subscribe
    public void getTripDetails(InitialTripRequestEvent event) {
        hideProgress();
        TripRequest response = event.tripRequest;
        String tripId = event.tripId;
        if (response != null && response.getStatus() == 1001) {
            SocketSession.isTripStarted = true;

            //will use this when app clear from stack while trip is in process
            Gson gson = new Gson();
            String json = gson.toJson(response); // saving trip response for later use
            AppPreferences.getInstance().saveTripRequestResponse(json);

            //notify socket with trip acceptance
            SocketSession.getInstance().onAcceptingTripRequest(tripId, AppPreferences.getInstance().getUserId());
            SocketSession.getInstance().updateTripStatus(AppPreferences.getInstance().getUserId(), tripId, TripStatusCodes.requestAccepted);

            //save customer user id
            AppPreferences.getInstance().saveCustomerUserId(response.getUserId());
            if (response.getDroplocations() == null) {// removed check as null bcoz ite will not give null values
                showToast(getString(R.string.no_drop_loc));
            } else {
                displayTrips(response);
            }
        } else {
            Toast.makeText(MainActivity.this, "Too late, this trip has been accepted by your friend", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayTrips(TripRequest response) {
        //send response to fragment to display pickup and drop details
        TrolleyMapFragment fragment = (TrolleyMapFragment) getSupportFragmentManager().findFragmentByTag("TrolleyMapFragment");
        if (fragment != null) {
            fragment.acceptRequest(response);
        }
    }

    @Subscribe
    public void logoutUser(DriverLogOutEvent event) {
        hideProgress();
        if (event.logoutUser) {
            logOutUser();
        }
    }

    private void logOutUser() {
        hideProgress();

        AppPreferences.getInstance().deletePref();
        MyApplication.getInstance().clearAllData();

        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        intent.putExtra("viewPage", 0);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Register ourselves so that we can provide the initial value.
        MyApplication.getBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getBus().unregister(this);
        AppPreferences.getInstance().saveTrackingState(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppPreferences.getInstance().saveTrackingState(false);
    }
}
