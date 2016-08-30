package com.thisisswitch.otrolly.driver.activities;

import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.fragments.SignInFragment;
import com.thisisswitch.otrolly.driver.fragments.SignupFragment;

/**
 * @author Raja Gopal Reddy P
 */
public class AccountActivity extends AppCompatActivity implements SignInFragment.OnFragmentInteractionListener {

    private Toolbar mToolbar;
    private int viewPage = 0, currentViewPage;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        viewPage = getIntent().getExtras().getInt("viewPage");

        findViewById(R.id.progressLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account, menu);
        this.menu = menu;
        displayView(viewPage);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.exit2, R.anim.enter2);
        finish();
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new SignInFragment();
                title = getString(R.string.signin);
                break;
            case 1:
                fragment = new SignupFragment();
                title = getString(R.string.signup);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (currentViewPage == 1)
                    fragment.setEnterTransition(new Slide(Gravity.LEFT));
                else
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
            } else {
                if (currentViewPage == 1) {
                    fragmentTransaction.setCustomAnimations(R.anim.exit2, R.anim.enter2);
                } else {
                    fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit);
                }
            }
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

        }
        getSupportActionBar().setTitle(title);
        currentViewPage = position;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
