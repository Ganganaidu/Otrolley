package com.thisisswitch.otrolly.driver.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thisisswitch.otrolly.driver.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    protected ImageView imageView;
    protected TextView driver_nameID;
    protected TextView vehicle_numberID;
    protected TextView paymentID;
    protected LinearLayout track_history_item_layout;

    public CustomViewHolder(View view) {
        super(view);
//        this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
        this.driver_nameID = (TextView) view.findViewById(R.id.driver_nameID);
        this.vehicle_numberID = (TextView) view.findViewById(R.id.vehicle_numberID);
        this.paymentID = (TextView) view.findViewById(R.id.paymentID);
        this.track_history_item_layout = (LinearLayout) view.findViewById(R.id.track_history_item_layout);
    }
}
