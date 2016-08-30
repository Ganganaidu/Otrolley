package com.thisisswitch.otrolly.driver.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.models.DropLocation;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Office on 4/24/2016.
 */
public class DropLocationPagerAdapter extends PagerAdapter {

    private static final String TAG = "DropPagerAdapter";

    private int totalSize;
    private Context mContext;
    private List<DropLocation> dropLocations;


    public DropLocationPagerAdapter(Context context, List<DropLocation> dropLocList) {
        mContext = context;
        this.dropLocations = dropLocList;
        totalSize = dropLocations.size();
    }

    @Override
    public int getCount() {
        return totalSize;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.trip_request_item_layout, collection, false);
        ViewHolder viewHolder = new ViewHolder(layout);
        collection.addView(layout);

        int numberOfStops = position + 1;
        viewHolder.tripStatusTextView.setText("Stop No " + numberOfStops);
        viewHolder.tripCountTextView.setText(String.valueOf(numberOfStops) + "/" + String.valueOf(totalSize));
        viewHolder.tripUserTextView.setText(dropLocations.get(position).getReciverName());
        viewHolder.tripPnumberTextView.setText(dropLocations.get(position).getReciverPhone());
        viewHolder.tripAddressTextView.setText(dropLocations.get(position).getAddress());
        //  viewHolder.tripTimeTextView.setText(dropLocations.get(position).getRecivedTime());

        return layout;
    }

    public void removeItem() {
        notifyDataSetChanged();
    }

    public class ViewHolder {

        TextView tripStatusTextView;
        TextView tripCountTextView;
        TextView tripUserTextView;
        TextView tripPnumberTextView;
        TextView tripAddressTextView;
        TextView tripTimeTextView;

        ViewHolder(View view) {
            tripStatusTextView = ButterKnife.findById(view, R.id.trip_status_textView);
            tripCountTextView = ButterKnife.findById(view, R.id.trip_count_textView);
            tripUserTextView = ButterKnife.findById(view, R.id.trip_user_textView);
            tripPnumberTextView = ButterKnife.findById(view, R.id.trip_pnumber_textView);
            tripAddressTextView = ButterKnife.findById(view, R.id.trip_address_textView);
            tripTimeTextView = ButterKnife.findById(view, R.id.trip_time_textView);
        }
    }
}
