package com.thisisswitch.otrolly.driver.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.models.TripHistoryModel;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TripHistoryItemDetailsActivity extends AppCompatActivity {

    @Bind(R.id.track_item_payment)
    TextView trackItemPayment;
    @Bind(R.id.track_item_distance)
    TextView trackItemDistance;
    @Bind(R.id.track_item_drive_time)
    TextView trackItemDriveTime;
    @Bind(R.id.track_item_wait_time)
    TextView trackItemWaitTime;
    @Bind(R.id.track_item_vehicle_type)
    TextView trackItemVehicleType;
    @Bind(R.id.track_item_stop_numbers)
    TextView trackItemStopNumbers;
    @Bind(R.id.track_item_pickup)
    TextView trackItemPickup;
    @Bind(R.id.track_item_stops)
    TextView trackItemStops;
    @Bind(R.id.track_item_start_time)
    TextView trackItemStartTime;
    @Bind(R.id.track_item_end_time)
    TextView trackItemEndTime;
    @Bind(R.id.track_history_item_layout)
    LinearLayout trackHistoryItemLayout;
    @Bind(R.id.trip_id_textview)
    TextView tripIdTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    TripHistoryModel tripHistoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history_item_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setUpToolBar();

        tripHistoryModel = (TripHistoryModel) getIntent().getSerializableExtra("TripHistoryMOdel");

        if (tripHistoryModel != null) {

            tripIdTextView.setText(getString(R.string.id_trip).replace("^", tripHistoryModel.getId()));
            trackItemPayment.setText(getString(R.string.rs_trip).replace("^", tripHistoryModel.getNet()));

            trackItemDistance.setText(String.valueOf(tripHistoryModel.getTotalDistance()));
            trackItemDriveTime.setText(String.valueOf(tripHistoryModel.getTotalTime()));
            trackItemWaitTime.setText(String.valueOf(tripHistoryModel.getTransitTime()));
            trackItemVehicleType.setText(String.valueOf(tripHistoryModel.getType()));
            if (tripHistoryModel.getDroplocations() != null) {
                int dropLocSize = tripHistoryModel.getDroplocations().size();
                trackItemStopNumbers.setText(String.valueOf(tripHistoryModel.getDroplocations().size()));
                StringBuilder dropLocNamesBuffer = new StringBuilder();
                for (int i = 0; i < dropLocSize; i++) {
                    dropLocNamesBuffer.append(tripHistoryModel.getDroplocations().get(0).getAddress());
                }
                trackItemStops.setText(dropLocNamesBuffer.toString());
            }
            if (tripHistoryModel.getPickupAddress() != null) {
                trackItemPickup.setText(String.valueOf(tripHistoryModel.getPickupAddress().getAddress()));
            }

            trackItemStartTime.setText(String.valueOf(""));
            trackItemEndTime.setText(String.valueOf(""));
        }
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
}
