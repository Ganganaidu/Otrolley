package com.thisisswitch.otrolly.driver.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.maps.android.PolyUtil;
import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.events.TripEndEvent;
import com.thisisswitch.otrolly.driver.listeners.RequestListener;
import com.thisisswitch.otrolly.driver.models.CouponDetails;
import com.thisisswitch.otrolly.driver.models.TripEnd;
import com.thisisswitch.otrolly.driver.models.TripRequest;
import com.thisisswitch.otrolly.driver.models.Vehicletype;
import com.thisisswitch.otrolly.driver.network.RestAPIRequest;
import com.thisisswitch.otrolly.driver.network.RestRequestInterface;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Office on 4/30/2016.
 */
public class DialogEndTripFragment extends DialogFragment {

    private static final String TAG = "DialogEndTripFragment";

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.total_bill_textView)
    TextView totalBillTextView;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.submit_rating_button)
    Button submitRatingButton;
    @Bind(R.id.trip_details_layout)
    LinearLayout tripDetailsLayout;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.loading_layout)
    RelativeLayout loadingLayout;
    @Bind(R.id.progressBar3)
    ProgressBar progressBar3;
    @Bind(R.id.trip_coupon_text)
    TextView tripCouponText;

    private TripRequest tripRequest;
    private TripEnd tripEnd;
    private double netFare;

    public DialogEndTripFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tripEnd = new TripEnd();

        Log.e(TAG, "onCreate: " + AppPreferences.getInstance().getRoutePath());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_end_trip, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);

        setRatingBar();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        calculateTripDetails(0);
        if (tripRequest != null && tripRequest.getCouponId() != null && tripRequest.getCouponId().length() > 0) {
            validateTripCoupon(tripRequest.getCouponId(), String.valueOf(netFare));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.submit_rating_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_rating_button:

                //encode latlng to route path and then send it to server
                String routePath = PolyUtil.encode(AppPreferences.getInstance().getRoutePath());

                tripEnd.setRoutePath(routePath);
                tripEnd.setStatus("1009");

                sendTripDetails(AppPreferences.getInstance().getTripId());

                break;
        }
    }

    private void calculateTripDetails(double couponDiscountAmount) {
        //take default values if we dnt have configured data
        double minFare = 0;
        double add_cost_per_kilometer = 0;
        double min_cost = 0;
        double base_km = 0;
        double total_distance = 0;
        double transit_time = 0;
        double base_min = 0;
        double total_time = 0;
        double transit_time_cost = 0;
        double addCostPerMin = 0;
        double fare_cost = 0;

        Vehicletype vehicletype = AppPreferences.getInstance().getVehicleData();
        if (vehicletype != null) {
            base_min = vehicletype.getBaseMin();
            addCostPerMin = vehicletype.getAddCostPerMin();
            base_km = vehicletype.getBaseKm();
            min_cost = vehicletype.getMinCost();
            add_cost_per_kilometer = vehicletype.getAddCostPerKilometer();
        }

        total_distance = AppPreferences.getInstance().getTotalDistance();
        transit_time = AppPreferences.getInstance().getTotalDuration();

        if (transit_time > base_min) {
            total_time = transit_time - base_min;
        } else {
            total_time = 0;
        }

        transit_time_cost = addCostPerMin * total_time;
        if (total_distance > base_km) {
            fare_cost = ((total_distance - base_km) * add_cost_per_kilometer) + min_cost;
        } else {
            fare_cost = min_cost;
        }

        //"net" = fare_cost + transit_time_cost - couponDiscountAmount
        //deduct discountAmount if we have from coupon
        netFare = fare_cost + transit_time_cost - couponDiscountAmount;

        //display coupon details
        if (couponDiscountAmount > 0) {
            String tripMSg;
            if (netFare > couponDiscountAmount) {
                tripMSg = getString(R.string.discount_text).replace("^", String.valueOf(couponDiscountAmount));
                tripCouponText.setVisibility(View.VISIBLE);
                tripCouponText.setText(tripMSg);
            } else {
                tripMSg = getString(R.string.discount_text).replace("^", String.valueOf(netFare));
                tripCouponText.setVisibility(View.VISIBLE);
                tripCouponText.setText(tripMSg);
            }
        }

        //if fare is less than zero ...put normal min fare
        if (netFare < minFare) {
            netFare = minFare;
        }

        tripEnd.setBaseMin(String.valueOf(minFare));
        tripEnd.setTotalDistance(String.valueOf(total_distance));
        tripEnd.setTotalTime(String.valueOf(transit_time));
        tripEnd.setFareCost(String.valueOf(fare_cost));
        tripEnd.setNet(String.valueOf(netFare));
        tripEnd.setTransitTime(String.valueOf(transit_time));

        totalBillTextView.setText(getString(R.string.total_bill_is).replace("^", "" + netFare));
    }

    private void setRatingBar() {
        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tripEnd.setRating(String.valueOf(rating));
            }
        });
    }

    public void setEndTripTransaction(TripRequest tripRequestModel) {
        this.tripRequest = tripRequestModel;
    }

    private void sendTripDetails(final String tripId) {
        RestRequestInterface tripDetails = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<TripEnd> call = tripDetails.sendTripDetails(AppPreferences.getInstance().getAccessToken(), tripEnd, tripId);
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<TripEnd>() {
            @Override
            public void onResponse(final TripEnd response) {
                MyApplication.getInstance().hideProgress();
                if (response != null) {
                    MyApplication.getBus().post(new TripEndEvent());
                    dismiss();
                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                MyApplication.getInstance().hideProgress();
            }
        });
    }

    private void validateTripCoupon(String couponCode, final String netFares) {
        MyApplication.getInstance().showProgress(getActivity(), "Checking coupon please wait..");

        RestRequestInterface tripDetails = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<CouponDetails> call = tripDetails.validateCoupon(AppPreferences.getInstance().getAccessToken(), couponCode, netFares,
                AppPreferences.getInstance().getUserId());
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<CouponDetails>() {
            @Override
            public void onResponse(final CouponDetails response) {
                MyApplication.getInstance().hideProgress();
                //Log.d(TAG, "response:" + response);
                try {
                    if (response != null) {
                        calculateTripDetails(response.getDiscountAmount());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                MyApplication.getInstance().hideProgress();
                MyApplication.getInstance().showToast(getString(R.string.invalid_coupon));
            }
        });
    }
}
