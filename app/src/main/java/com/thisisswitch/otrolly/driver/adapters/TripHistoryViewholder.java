package com.thisisswitch.otrolly.driver.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thisisswitch.otrolly.driver.R;

public class TripHistoryViewholder extends RecyclerView.ViewHolder {
    protected ImageView imageView;
    protected TextView driver_nameID;
    protected TextView booking_trip_id;
    protected TextView booking_trip_date;
    protected TextView vehicle_numberID;
    protected TextView netPriceTextView;
    protected LinearLayout track_history_item_layout;

    public TripHistoryViewholder(View view) {
        super(view);
//        this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
        this.driver_nameID = (TextView) view.findViewById(R.id.driver_nameID);
        this.booking_trip_id = (TextView) view.findViewById(R.id.booking_trip_id);
        this.booking_trip_date = (TextView) view.findViewById(R.id.booking_trip_date);
        this.vehicle_numberID = (TextView) view.findViewById(R.id.vehicle_numberID);
        this.netPriceTextView = (TextView) view.findViewById(R.id.paymentID);
        this.track_history_item_layout = (LinearLayout) view.findViewById(R.id.track_history_item_layout);
    }
}
