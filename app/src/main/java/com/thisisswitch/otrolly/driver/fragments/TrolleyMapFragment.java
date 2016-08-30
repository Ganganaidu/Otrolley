package com.thisisswitch.otrolly.driver.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.nkzawa.emitter.Emitter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.adapters.DropLocationPagerAdapter;
import com.thisisswitch.otrolly.driver.dialogs.DialogEndTripFragment;
import com.thisisswitch.otrolly.driver.dialogs.DialogOtpFragment;
import com.thisisswitch.otrolly.driver.dialogs.DialogPickUpLocationFrag;
import com.thisisswitch.otrolly.driver.events.DropLocStatusCodes;
import com.thisisswitch.otrolly.driver.events.DropLocUpdateEvent;
import com.thisisswitch.otrolly.driver.events.OtpCheckEvent;
import com.thisisswitch.otrolly.driver.events.PickUpAtLocEvent;
import com.thisisswitch.otrolly.driver.events.TripRequestMessageEvent;
import com.thisisswitch.otrolly.driver.events.TripStatusCodes;
import com.thisisswitch.otrolly.driver.events.UpdateTripStatusEvent;
import com.thisisswitch.otrolly.driver.models.DropLocation;
import com.thisisswitch.otrolly.driver.models.TripRequest;
import com.thisisswitch.otrolly.driver.network.OTrolleyAPIRequest;
import com.thisisswitch.otrolly.driver.network.SocketSession;
import com.thisisswitch.otrolly.driver.permissions.AndroidPermissions;
import com.thisisswitch.otrolly.driver.service.LocationService;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;
import com.thisisswitch.otrolly.driver.utils.GoogleMapDirections;
import com.thisisswitch.otrolly.driver.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import me.relex.circleindicator.CircleIndicator;

public class TrolleyMapFragment extends Fragment implements
        OnMapReadyCallback, LocationService.Callbacks {

    private static final String TAG = "TrollyMapFragment";

    @Bind(R.id.map)
    MapView mMapView;
    @Bind(R.id.showIncomeLayout)
    RelativeLayout showIncomeLayout;
    @Bind(R.id.trip_status_textView)
    TextView tripStatusTextView;
    @Bind(R.id.trip_count_textView)
    TextView tripCountTextView;
    @Bind(R.id.trip_user_textView)
    TextView tripUserTextView;
    @Bind(R.id.trip_pnumber_textView)
    TextView tripPnumberTextView;
    @Bind(R.id.trip_address_textView)
    TextView tripAddressTextView;
    @Bind(R.id.trip_time_textView)
    TextView tripTimeTextView;
    @Bind(R.id.down_arrow_imageView)
    ImageView downArrowImageView;
    @Bind(R.id.trip_viewpager)
    ViewPager tripViewpager;
    @Bind(R.id.indicator)
    CircleIndicator indicator;
    @Bind(R.id.bottom_trip_accept_lyt)
    CardView bottomTripAcceptLyt;
    @Bind(R.id.trip_request_lyt)
    RelativeLayout tripRequestLyt;
    @Bind(R.id.trip_first_dropLocation_view)
    CardView tripFirstDropLocationView;
    @Bind(R.id.trip_dropLocation_listlayout)
    RelativeLayout tripDropLocationListLayout;
    @Bind(R.id.pic_trip_status_textView)
    TextView picTripStatusTextView;
    @Bind(R.id.pic_trip_count_textView)
    TextView picTripCountTextView;
    @Bind(R.id.pic_trip_user_textView)
    TextView picTripUserTextView;
    @Bind(R.id.pic_trip_pnumber_textView)
    TextView picTripPnumberTextView;
    @Bind(R.id.pic_trip_address_textView)
    TextView picTripAddressTextView;
    @Bind(R.id.pic_trip_time_textView)
    TextView picTripTimeTextView;
    @Bind(R.id.accept_trip_request)
    Button acceptTripRequest;
    @Bind(R.id.reject_trip_request)
    Button rejectTripRequest;
    @Bind(R.id.noDropLocationsMsg)
    CardView noDropLocationsMsg;
    @Bind(R.id.trip_view_pager_layout)
    CardView tripViewPagerLayout;
    @Bind(R.id.trip_start_status_textview)
    TextView trip_start_status_textview;
    @Bind(R.id.init_trip_layout)
    LinearLayout init_trip_layout;
    @Bind(R.id.navigate_to_pickup_loc_btn)
    Button navigateToPickupLocBtn;
    @Bind(R.id.start_init_trip_btn)
    Button startInitTripBtn;
    @Bind(R.id.count_layout)
    RelativeLayout countLayout;
    @Bind(R.id.showIncomeImage)
    ImageView showIncomeImage;
    @Bind(R.id.showIncomeText)
    TextView showIncomeText;
    @Bind(R.id.gps_imagebtn)
    ImageButton gpsImageBtn;
    @Bind(R.id.offlineOnlineToggle)
    ToggleButton offlineOnlineToggle;
    @Bind(R.id.toggle_bg_layout)
    RelativeLayout toggleBgLayout;
    @Bind(R.id.refreshTrip_btn)
    Button refreshTrip_btn;

    LocationService myService;
    Intent serviceIntent;

    Bundle mBundle;
    GoogleMap mGoogleMap;
    //GPSTracker gpsTracker;
    TripRequest tripRequest;
    DropLocationPagerAdapter adapter;
    DialogPickUpLocationFrag dropDialog;

    private boolean isMapUpdated = false;
    private Marker locationMarker, dropMarker;

    LatLng dropLocationLatLng;
    ArrayList<LatLng> markerPoints;

    int tripPos = 1;

    public TrolleyMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;

        serviceIntent = new Intent(getActivity(), LocationService.class);
        // Initializing array List
        markerPoints = new ArrayList<>();

        //Log.d(TAG, "acccessToken:" + AppPreferences.getInstance().getAccessToken());

        SocketSession.getSession().on(AppPreferences.getInstance().getUserId(), onNewMessage);
        SocketSession.getSession().connect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);

        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.onCreate(mBundle);
        mMapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateTripStatus();
        setOfflineOnlineToggle();
    }

    private void updateTripStatus() {
        //Log.d(TAG, "trackState:" + AppPreferences.getInstance().getTrackingState());

        if (AppPreferences.getInstance().getTrackingState()) {
            SocketSession.isTrackingStarted = true;
            showIncomeReqLayout(View.GONE);
        } else {
            SocketSession.isTrackingStarted = false;
            showIncomeReqLayout(View.VISIBLE);
        }
    }

    @Subscribe
    public void onOtpChecked(OtpCheckEvent otpCheckEvent) {
        waitTripLoading();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (myService != null && myService.gpsTracker != null && myService.gpsTracker.checkLocationState()) {
            myService.gpsTracker.startLocationUpdates();

            setMap(googleMap);
            LatLng latLang = new LatLng(myService.gpsTracker.getLatitude(), myService.gpsTracker.getLongitude());
            //First time if you don't have latitude and longitude user default address
            if (myService.gpsTracker.getLatitude() == 0) {
                latLang = new LatLng(17.3700, 78.4800);
                myService.gpsTracker.setDefaultAddress("Hyderabad, Telangana");
            }
            updateMapWithLocationFirstTime(latLang, 0);
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've binded to LocalService, cast the IBinder and get LocalService instance
            LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
            myService = binder.getServiceInstance(); //Get instance of your service!

            updateLocationPermission();

            resumeTrip();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            //updating map
            if (!isMapUpdated) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                updateMapWithLocationFirstTime(latLng, 0);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.e(TAG,"isMyServiceRunning:"+ Utils.isMyServiceRunning(getActivity(),LocationService.class));
        getActivity().startService(serviceIntent); //Starting the service
        getActivity().bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE); //Binding to the service!
        if (myService != null) {
            myService.registerClient(this); //Activity register in the service as client for callabcks!
            updateLocationPermission();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves so that we can provide the initial value.
        MyApplication.getBus().register(this);

        if (mMapView != null) {
            mMapView.onResume();
        }

        updateLocationPermission();

        if (dropDialog != null && dropDialog.dialog != null && dropDialog.dialog.isShowing()) {
            dropDialog.displayAddressDetails();
        }
    }

    public void resumeTrip() {
        tripRequest = AppPreferences.getInstance().getTripRequest();

        if (AppPreferences.getInstance().getStartPicupState()) {

            showIncomeReqLayout(View.GONE);
            SocketSession.isTrackingStarted = true;

            if (myService != null && myService.gpsTracker != null) {
                showPickUpNavDialog();
            }
        } else if (AppPreferences.getInstance().getFirstDropLocState()) {

            showIncomeReqLayout(View.GONE);
            SocketSession.isTrackingStarted = true;
            SocketSession.isTripStarted = true;

            displayDropLocations(new PickUpAtLocEvent());

        } else if (tripRequest != null) {
            acceptRequest(tripRequest);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //gpsTracker.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
        // Always unregister when an object no longer should be on the bus.
        MyApplication.getBus().unregister(this);
    }

    @Override
    public void onDestroy() {
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        getActivity().unbindService(mConnection);
        getActivity().stopService(serviceIntent);
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        // gpsTracker.onStop();
        //getActivity().unbindService(mConnection);
        // getActivity().stopService(serviceIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void updateLocationPermission() {
        if (!AndroidPermissions.getInstance().checkLocationPermission(getActivity())) {
            AndroidPermissions.getInstance().displayLocationPermissionAlert(getActivity());
            return;
        }

        if (myService != null && myService.gpsTracker != null && myService.gpsTracker.checkLocationState()) {
            myService.gpsTracker.startLocationUpdates();

            if (myService != null && myService.gpsTracker != null) {
                myService.registerClient(this); //Activity register in the service as client for callabcks!

                if (myService.gpsTracker != null) {
                    myService.gpsTracker.onResume();
                    myService.gpsTracker.startLocationUpdates();
                }
            }

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            if (myService != null && myService.gpsTracker != null) {
                showSettingsAlert();
            }
        }
    }

    //this will call when you get new message from socket
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Make the thread wait half a second. If you want...
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // here you check the value of getActivity() and break up if needed
            if (getActivity() == null)
                return;

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.d(TAG, "data:" + data);
                    if (data != null) {
                        //when first time trip starts
                        if (!SocketSession.isTripStarted) {
                            TripRequestMessageEvent tripRequestEvent = new TripRequestMessageEvent();
                            tripRequestEvent.tripRequestJson = data;
                            MyApplication.getBus().post(tripRequestEvent);
                        } else if (DialogPickUpLocationFrag.isTripAccepted) {
                            //when drop location added after trip has started
                            DialogPickUpLocationFrag.isTripAccepted = false;
                            OTrolleyAPIRequest.getInstance().requestForUpdatedDropLoc(getActivity());
                        }
                    }
                }
            });
        }
    };

    //set all map related details here
    private void setMap(GoogleMap mGoogleMap) {

        if (mGoogleMap != null) {
            mGoogleMap.clear();

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            /*Map configurations*/
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
            mGoogleMap.getUiSettings().setTiltGesturesEnabled(true);
            mGoogleMap.getUiSettings().setCompassEnabled(false);
            mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);
            mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        }
    }

    @OnLongClick({R.id.showIncomeLayout, R.id.showIncome})
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.showIncomeImage:
                Utils.setVibrator(getActivity(), 100);
                startRequestingDriverLoc(true);
                break;
            case R.id.showIncomeText:
                Utils.setVibrator(getActivity(), 100);
                startRequestingDriverLoc(true);
                break;
            case R.id.showIncomeLayout:
                Utils.setVibrator(getActivity(), 100);
                startRequestingDriverLoc(true);
                break;
            case R.id.showIncome:
                Utils.setVibrator(getActivity(), 100);
                startRequestingDriverLoc(true);
                break;
        }
        return true;
    }

    //TODO
    private void setOfflineOnlineToggle() {
        offlineOnlineToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    startRequestingDriverLoc(false);
                } else {
                    // The toggle is disabled
                    stopRequestingDriverLoc();
                }
            }
        });
    }

    @OnClick({R.id.offlineOnlineToggle, R.id.refreshTrip_btn,
            R.id.accept_trip_request, R.id.reject_trip_request,
            R.id.navigate_to_pickup_loc_btn, R.id.start_init_trip_btn, R.id.gps_imagebtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gps_imagebtn:
                if (myService != null && myService.gpsTracker != null) {
                    LatLng latLng = new LatLng(myService.gpsTracker.getLatitude(), myService.gpsTracker.getLongitude());
                    updateMapWithLocationFirstTime(latLng, 0);
                }
                break;
            case R.id.reject_trip_request:

                SocketSession.isTripStarted = false;
                tripRequestLyt.setVisibility(View.GONE);
                SocketSession.getInstance().rejectTrip(AppPreferences.getInstance().getUserId(),
                        AppPreferences.getInstance().getTripId());

                break;
            case R.id.start_init_trip_btn:
                OTrolleyAPIRequest.getInstance().sendTripStatus(getActivity(), TripStatusCodes.EnrouteToPickupLocation);

                break;
            case R.id.refreshTrip_btn:
                OTrolleyAPIRequest.getInstance().requestForUpdatedDropLoc(getActivity());

                break;
            case R.id.accept_trip_request:

                SocketSession.isTrackingStarted = true;
                SocketSession.isTripStarted = true;

                String btnText = acceptTripRequest.getText().toString().trim();
                if (btnText.equalsIgnoreCase(getMyString(R.string.no_drop_loc_req))) {
                    MyApplication.getInstance().showOkAlert(getString(R.string.no_droploc_msg), getActivity());

                } else if (btnText.equalsIgnoreCase(getMyString(R.string.reached_dest))) {

                    endDropLocTrip();

                } else if (btnText.contains(getMyString(R.string.start_to_dest_com))) {
                    //update map here..with new current and drop location
                    startTripTowardsDropLoc();

                } else if (btnText.equalsIgnoreCase(getMyString(R.string.unload_trip))) {

                    unloadTrip();
                } else if (btnText.equalsIgnoreCase(getMyString(R.string.submit_trip))) {

                    showEndTripDialog(tripRequest);
                }
                break;
        }
    }

    private void showIncomeReqLayout(int visible) {
        showIncomeLayout.setVisibility(visible);
    }

    private void startTripTowardsDropLoc() {
        //update map here..with new current and drop location
        acceptTripRequest.setText(getMyString(R.string.reached_dest));
        tripViewPagerLayout.setVisibility(View.GONE);

        if (tripRequest != null && tripRequest.getDroplocations() != null && tripRequest.getDroplocations().size() > 0) {
            double lat = Double.parseDouble(tripRequest.getDroplocations().get(0).getGeo().getLat());
            double lng = Double.parseDouble(tripRequest.getDroplocations().get(0).getGeo().getLng());

            //update maps with current and destination loc
            dropLocationLatLng = new LatLng(lat, lng);
            updateMapWithLocationFirstTime(dropLocationLatLng, 1);

            MyApplication.getInstance().showProgress(getActivity(), getMyString(R.string.loading_wait));
            sendDropEndRequest("1001", false);

            //get map directions
            if (myService != null || myService.gpsTracker != null) {
                LatLng latLng = new LatLng(myService.gpsTracker.getLatitude(), myService.gpsTracker.getLongitude());
                GoogleMapDirections.loadTripDirections(getActivity(), latLng, dropLocationLatLng, mGoogleMap);
            }
            isMapUpdated = false; //update driver current location with pick up marker
        }
    }

    private void waitTripLoading() {
        if (tripRequest != null && tripRequest.getDroplocations() != null && tripRequest.getDroplocations().size() == 1) {
            acceptTripRequest.setText(getMyString(R.string.submit_trip));
            trip_start_status_textview.setText(getMyString(R.string.complete_trip));
        } else {
            acceptTripRequest.setText(getMyString(R.string.unload_trip));
            trip_start_status_textview.setText(getMyString(R.string.start_next_trip));
        }
    }


    private void unloadTrip() {
        if (tripRequest.getDroplocations() != null || tripRequest.getDroplocations().size() > 1) {
            tripRequest.getDroplocations().remove(0);
            tripPos = tripPos + 1;

            trip_start_status_textview.setText(getMyString(R.string.next_trip));
            acceptTripRequest.setText(getMyString(R.string.start_to_dest).replace("^", "D" + tripPos));
            tripViewPagerLayout.setVisibility(View.VISIBLE);

            //update with modified trips
            saveTripRequestResponse(tripRequest);

            //update view pager adapter
            setAdapter();
        } else {
            showEndTripDialog(tripRequest);
        }
    }


    private void endDropLocTrip() {
        sendDropEndRequest("1008", true);
        showOtpDialog();
    }

    private void startRequestingDriverLoc(boolean isVibrateNeeded) {
        gpsImageBtn.setVisibility(View.VISIBLE);
        offlineOnlineToggle.setChecked(true);
        toggleBgLayout.setVisibility(View.VISIBLE);
        toggleBgLayout.setBackgroundResource(R.drawable.round);

        showIncomeReqLayout(View.GONE);

        AppPreferences.getInstance().saveTrackingState(true);
        SocketSession.isTrackingStarted = true;

        if (isVibrateNeeded) {
            //wait for one sec and then cancel vibrator
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Utils.cancelVibrate();
        }
    }

    private void stopRequestingDriverLoc() {
        gpsImageBtn.setVisibility(View.GONE);
        offlineOnlineToggle.setChecked(false);
        toggleBgLayout.setVisibility(View.GONE);
        toggleBgLayout.setBackgroundResource(R.drawable.round1);

        showIncomeReqLayout(View.VISIBLE);

        AppPreferences.getInstance().saveTrackingState(false);
        SocketSession.isTrackingStarted = false;
    }

    //when driver accept the trip
    public void acceptRequest(TripRequest tripRequest) {
        this.tripRequest = tripRequest;

        showIncomeReqLayout(View.GONE);

        tripRequestLyt.setVisibility(View.VISIBLE);
        init_trip_layout.setVisibility(View.VISIBLE);
        tripFirstDropLocationView.setVisibility(View.VISIBLE);

        tripDropLocationListLayout.setVisibility(View.GONE);
        navigateToPickupLocBtn.setVisibility(View.GONE);
        countLayout.setVisibility(View.GONE);

        displayTripOwnerDetails();
    }

    private void displayTripOwnerDetails() {
        //Log.d("tripRequest14", "" + tripRequest);
        tripCountTextView.setVisibility(View.GONE);
        tripStatusTextView.setText(getMyString(R.string.trip_req_from));
        tripTimeTextView.setVisibility(View.GONE);

        if (tripRequest.getUser() != null) {
            tripUserTextView.setText(tripRequest.getUser().getFullName());
            tripPnumberTextView.setText(tripRequest.getUser().getUsername());
            tripAddressTextView.setText(tripRequest.getPickupAddress().getAddress());
        }
    }

    @Subscribe
    public void displayDropLocations(PickUpAtLocEvent event) {
        displayNumberOfDropLocations();
        updateTripDetails();
    }

    private void updateTripDetails() {
        if (tripRequest != null && tripRequest.getDroplocations().size() > 0) {
            displayNumberOfDropLocations();
        } else {
            OTrolleyAPIRequest.getInstance().requestForUpdatedDropLoc(getActivity());
            updateNoDropsState();
        }
    }

    private void displayNumberOfDropLocations() {
        MyApplication.getInstance().hideProgress();
        //accept_trip_request
        refreshTrip_btn.setVisibility(View.GONE);
        acceptTripRequest.setBackgroundColor(getResources().getColor(R.color.bg));
        acceptTripRequest.setEnabled(true);
        tripFirstDropLocationView.setVisibility(View.GONE);
        tripRequestLyt.setVisibility(View.VISIBLE);
        tripDropLocationListLayout.setVisibility(View.VISIBLE);

        setPickUpRequestData(tripRequest);
        setAdapter();
    }

    private void setAdapter() {
        if (tripRequest.getDroplocations() != null && tripRequest.getDroplocations().size() > 0) {
            adapter = new DropLocationPagerAdapter(getActivity(), tripRequest.getDroplocations());
            tripViewpager.setAdapter(adapter);
            indicator.setViewPager(tripViewpager);
        }
    }

    private void setPickUpRequestData(TripRequest tripRequest) {
        picTripCountTextView.setVisibility(View.GONE);
        picTripStatusTextView.setText(getMyString(R.string.pic_up_loc));

        //picTripTimeTextView.setText("ETA : " + tripRequest.getDroplocations().get(0).getRecivedTime());
        if (tripRequest.getDroplocations() != null && tripRequest.getDroplocations().size() > 0) {
            picTripUserTextView.setText(tripRequest.getDroplocations().get(0).getReciverName());
            picTripPnumberTextView.setText(tripRequest.getDroplocations().get(0).getReciverPhone());
            picTripAddressTextView.setText(tripRequest.getDroplocations().get(0).getAddress());
        }
    }

    private void showEndTripDialog(TripRequest tripRequest) {
        //Log.d("at end Trip dia", "" + tripRequest);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DialogEndTripFragment endTripDialog = new DialogEndTripFragment();
        endTripDialog.setEndTripTransaction(tripRequest);
        endTripDialog.show(fm, "DialogEndTripFragment");
    }

    //display promo code dialog
    private void showOtpDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DialogOtpFragment otpDialog = new DialogOtpFragment();
        otpDialog.show(fm, "DialogOtpFragment");
        otpDialog.setTripOtpNumber(tripRequest);
    }


    //display promo code dialog
    private void showPickUpNavDialog() {
        dropDialog = new DialogPickUpLocationFrag(getActivity());
        dropDialog.setTripRequest(tripRequest);
        dropDialog.displayDialog();
    }

    //first time when fragment opened we will call this and update map with current location and marker
    private void updateMapWithLocationFirstTime(LatLng latLang, int position) {
        if (myService.gpsTracker.getLatitude() != 0) {
            isMapUpdated = true;
            if (mGoogleMap != null) {
                //latLngArrayList.add(0, latLang);//default pick up location
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLang, 15);
                mGoogleMap.animateCamera(cameraUpdate);

                if (position == 0) {
                    updatePickUPMarker(latLang);
                } else {
                    updateDropMarker(latLang);
                }
            }
        } else {
            isMapUpdated = false;
        }
    }

    private void updatePickUPMarker(LatLng latLng) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable._0001_location);
        if (locationMarker != null) {
            locationMarker.remove();
        }
        //default pickup location marker options
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(icon)
                .draggable(true);
        locationMarker = mGoogleMap.addMarker(markerOptions);
        locationMarker.setDraggable(false);
    }

    private void updateDropMarker(LatLng latLng) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable._0000_flag);
        if (dropMarker != null) {
            dropMarker.remove();
        }
        //default pickup location marker options
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(icon)
                .draggable(true);
        dropMarker = mGoogleMap.addMarker(markerOptions);
        dropMarker.setDraggable(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case AndroidPermissions.REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateLocationPermission();
                } else {
                    AndroidPermissions.getInstance().displayAlert(getActivity(), AndroidPermissions.REQUEST_LOCATION);
                }
                break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public String getMyString(int id) {
        return getResources().getString(id);
    }

    public void sendDropEndRequest(String status, final boolean isProgress) {
        if (tripRequest != null && tripRequest.getDroplocations() != null) {
            final DropLocation dropLocation = tripRequest.getDroplocations().get(0);
            dropLocation.setStatus(status);

            if (isProgress) {
                MyApplication.getInstance().showProgress(getActivity(), getMyString(R.string.loading_wait));
            }
            //update status when driver reached the pick up location
            //update the driver status
            SocketSession.getInstance().dropLocationStatus(AppPreferences.getInstance().getTripId(), dropLocation.getId(),
                    DropLocStatusCodes.startToWardsTrip);

            OTrolleyAPIRequest.getInstance().updateDropLocStatus(dropLocation);
        }
    }

    @Subscribe
    public void updateTripStatus(UpdateTripStatusEvent event) {
        if (myService.gpsTracker != null) {
            AppPreferences.getInstance().saveStartPicUpstate(true);
            showPickUpNavDialog();
        }
    }

    @Subscribe
    public void updateNewDropLocation(DropLocUpdateEvent event) {
        tripRequest = event.tripRequest;
        if (tripRequest != null && isDropLocationAdded()) { //display drop location
            saveTripRequestResponse(tripRequest);

            SocketSession.isTripStarted = true;
            acceptTripRequest.setText(getMyString(R.string.start_to_dest).replace("^", "D" + tripPos));
            trip_start_status_textview.setText(getString(R.string.trip_started));

            displayNumberOfDropLocations();
        } else { //if no drop location display msg to user
            updateNoDropsState();
        }
    }

    //will use this when app clear from stack while trip is in process
    private void saveTripRequestResponse(TripRequest response) {
        Gson gson = new Gson();
        String json = gson.toJson(response); // saving trip response for later use
        AppPreferences.getInstance().saveTripRequestResponse(json);
    }

    private void updateNoDropsState() {
        refreshTrip_btn.setVisibility(View.VISIBLE);
        acceptTripRequest.setText(getString(R.string.no_drop_loc_req));
        trip_start_status_textview.setText(getString(R.string.please_wait_for));
        acceptTripRequest.setBackgroundColor(getResources().getColor(R.color.gray_light_dark));
    }

    //check drop location status
    private boolean isDropLocationAdded() {
        return (tripRequest.getDroplocations() != null &&
                tripRequest.getDroplocations().size() > 0 &&
                tripRequest.getDropLocationsAdded());
    }

    /**
     * Function to show settings alert dialog
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        //Setting Dialog Title
        alertDialog.setTitle(R.string.GPSAlertDialogTitle);

        //Setting Dialog Message
        alertDialog.setMessage(R.string.GPSAlertDialogMessage);

        alertDialog.setCancelable(false);

        //On Pressing Setting button
        alertDialog.setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getActivity().startActivity(intent);

                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
