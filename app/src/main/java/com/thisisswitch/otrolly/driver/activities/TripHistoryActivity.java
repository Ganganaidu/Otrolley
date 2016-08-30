package com.thisisswitch.otrolly.driver.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.thisisswitch.otrolly.driver.BaseActivity;
import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.adapters.TripHistoryAdapter;
import com.thisisswitch.otrolly.driver.listeners.RequestListener;
import com.thisisswitch.otrolly.driver.models.TripHistoryModel;
import com.thisisswitch.otrolly.driver.network.RestAPIRequest;
import com.thisisswitch.otrolly.driver.network.RestRequestInterface;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;

public class TripHistoryActivity extends BaseActivity {

    @Bind(R.id.trackHistoryRecycle)
    RecyclerView trackHistoryRecycle;

    @Bind(R.id.trip_id_textview)
    TextView tripIdTextview;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    // private List<FeedItem> feedsList;
    private TripHistoryAdapter adapter;
    private static final String TAG = "TripHistoryFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setUpToolBar();

        trackHistoryRecycle = (RecyclerView) findViewById(R.id.trackHistoryRecycle);
        trackHistoryRecycle.setLayoutManager(new LinearLayoutManager(this));

        String accessToken = AppPreferences.getInstance().getAccessToken();
        RestRequestInterface myLogin = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<List<TripHistoryModel>> call = myLogin.getTripHistory(accessToken, AppPreferences.getInstance().getUserId());
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<List<TripHistoryModel>>() {
            @Override
            public void onResponse(List<TripHistoryModel> response) {
                Log.e(TAG, "response:" + response);
                if (response != null) {
                    trackHistoryRecycle.setAdapter(new TripHistoryAdapter(TripHistoryActivity.this, response));
                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                Log.e(TAG, "errorMsg:" + errorMsg);
            }
        });

    }


    private void setUpToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.nav_item_trip_history));

        //updating status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.main));
        }

        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

