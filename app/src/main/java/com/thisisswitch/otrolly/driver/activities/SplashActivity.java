package com.thisisswitch.otrolly.driver.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;

/**
 * @author Raja Gopal Reddy P
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!AppPreferences.getInstance().getSignInState()) {
                    intent = new Intent(SplashActivity.this, AccountActivity.class);
                    intent.putExtra("viewPage", 0);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
