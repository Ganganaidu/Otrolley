package com.thisisswitch.otrolly.driver.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.activities.TripHistoryItemDetailsActivity;
import com.thisisswitch.otrolly.driver.models.TripHistoryModel;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;
import com.thisisswitch.otrolly.driver.utils.Utils;

import java.util.List;

/**
 * Created by syarlagadda on 4/17/16.
 */

public class TripHistoryAdapter extends RecyclerView.Adapter<TripHistoryViewholder> {
    private List<TripHistoryModel> tripHistoryModelList;
    private Context mContext;
    String vehicleId;

    public TripHistoryAdapter(Context context, List<TripHistoryModel> tripHistoryModelList) {
        this.tripHistoryModelList = tripHistoryModelList;
        this.mContext = context;

        vehicleId = AppPreferences.getInstance().getVehicleNumber();
    }


    @Override
    public TripHistoryViewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

        return new TripHistoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(TripHistoryViewholder customViewHolder, int position) {
        final TripHistoryModel tripHistoryModel = tripHistoryModelList.get(position);

        //Setting text view title
        customViewHolder.booking_trip_id.setText("Booking ID: " + tripHistoryModel.getId());
        customViewHolder.driver_nameID.setText(tripHistoryModel.getUserId());

        if (tripHistoryModel.getCreatedOn() != null) {
            customViewHolder.booking_trip_date.setText(Utils.convertDateTssZ(tripHistoryModel.getCreatedOn()));
        } else {
            customViewHolder.booking_trip_date.setText("");
        }
        customViewHolder.vehicle_numberID.setText(vehicleId);
        customViewHolder.netPriceTextView.setText(tripHistoryModel.getNet());

        customViewHolder.track_history_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TripHistoryItemDetailsActivity.class);
                intent.putExtra("TripHistoryMOdel", tripHistoryModel);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != tripHistoryModelList ? tripHistoryModelList.size() : 0);
    }
}