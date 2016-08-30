package com.thisisswitch.otrolly.driver.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.fragments.WalletFragment;

/**
 *
 * @author Raja Gopal Reddy P
 */
public class OtherActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private int viewPage = 0, currentViewPage;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPage = getIntent().getExtras().getInt("viewPage");


        findViewById(R.id.progressLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        displayView(viewPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account, menu);
        this.menu = menu;
//        displayView(viewPage);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
//
//        if (id == R.id.action) {
//            displayView(currentViewPage == 0 ? 1 : 0);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 1:

                title = getString(R.string.nav_item_trip_history);
                break;
            case 2:
                fragment = new WalletFragment();
                title = getString(R.string.nav_item_wallet);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

        }
        getSupportActionBar().setTitle(title);
        currentViewPage = position;
    }

}
