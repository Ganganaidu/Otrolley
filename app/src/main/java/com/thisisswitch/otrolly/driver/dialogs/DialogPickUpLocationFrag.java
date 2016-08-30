package com.thisisswitch.otrolly.driver.dialogs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.google.android.gms.maps.model.LatLng;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.events.PickUpAtLocEvent;
import com.thisisswitch.otrolly.driver.events.TripStatusCodes;
import com.thisisswitch.otrolly.driver.listeners.OnDirectionCallback;
import com.thisisswitch.otrolly.driver.listeners.RequestListener;
import com.thisisswitch.otrolly.driver.models.TripRequest;
import com.thisisswitch.otrolly.driver.models.TrpStatusUpdate;
import com.thisisswitch.otrolly.driver.network.RestAPIRequest;
import com.thisisswitch.otrolly.driver.network.RestRequestInterface;
import com.thisisswitch.otrolly.driver.network.SocketSession;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;
import com.thisisswitch.otrolly.driver.utils.Utils;

import retrofit2.Call;

/**
 * A custom DialogFragment that is positioned above given "source" component.
 *
 * @author Gangadhar, http://stackoverflow.com/a/20419231/56285
 */
public class DialogPickUpLocationFrag implements View.OnClickListener {

    ProgressBar progressBar4;
    TextView pickipAddrsTextView;
    TextView reachPicLocTextView;
    TextView pickupDurationTextView;
    Button navigateToPickupLocBtn;
    Button startToPicBtn;
    Button refreshBtn;
    LinearLayout displayBtnLayout;

    TripRequest tripRequest;
    Context context;
    public DialogPlus dialog;

    public static boolean isTripAccepted;

    public DialogPickUpLocationFrag(Context mContext) {
        this.context = mContext;
    }

    public void setTripRequest(TripRequest tripRequest) {
        this.tripRequest = tripRequest;
    }

    public void displayDialog() {
        dialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_at_pick_loc))
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .setCancelable(false)
                .create();
        dialog.show();

        View view = dialog.getHolderView();

        progressBar4 = (ProgressBar) view.findViewById(R.id.progressBar4);
        pickipAddrsTextView = (TextView) view.findViewById(R.id.pickip_addrs_textView);
        reachPicLocTextView = (TextView) view.findViewById(R.id.reach_pic_loc);
        pickupDurationTextView = (TextView) view.findViewById(R.id.pickup_duration_textView);
        navigateToPickupLocBtn = (Button) view.findViewById(R.id.navigate_to_pickup_loc_btn);
        startToPicBtn = (Button) view.findViewById(R.id.start_to_pic_btn);
        refreshBtn = (Button) view.findViewById(R.id.refresh_btn);
        displayBtnLayout = (LinearLayout) view.findViewById(R.id.display_btn_layout);

        startToPicBtn.setOnClickListener(this);
        navigateToPickupLocBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);

        displayAddressDetails();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.navigate_to_pickup_loc_btn:
                navigateToPickUp();
                break;
            case R.id.start_to_pic_btn:
                isTripAccepted = true;
                MyApplication.getInstance().showProgress(context, "updating status");
                sendTripStatus(TripStatusCodes.reachedPickupLocation);
                break;
            case R.id.refresh_btn:
                displayAddressDetails();
                break;
        }
    }


    public void displayAddressDetails() {

        displayBtnLayout.setVisibility(View.GONE);
        progressBar4.setVisibility(View.VISIBLE);

        if (tripRequest != null) {
            Double dLat = Double.parseDouble(tripRequest.getPickupAddress().getGeo().getLat());
            Double dLng = Double.parseDouble(tripRequest.getPickupAddress().getGeo().getLng());

            Double sLat = Double.parseDouble(AppPreferences.getInstance().getLat());
            Double sLng = Double.parseDouble(AppPreferences.getInstance().getLng());

            getDirectionDetails(context, new LatLng(sLat, sLng), new LatLng(dLat, dLng));
        }
    }

    private void navigateToPickUp() {
        String sLat = AppPreferences.getInstance().getLat();
        String sLng = AppPreferences.getInstance().getLng();

        String dLat = tripRequest.getPickupAddress().getGeo().getLat();
        String dLng = tripRequest.getPickupAddress().getGeo().getLng();

        directionMapIntent(sLat, sLng, dLat, dLng);
    }

    private void directionMapIntent(String sLat, String sLng, String dLat, String dLng) {
        String navigationUrlOfMap = "http://maps.google.com/maps?saddr=" + sLat + "," + sLng + " &daddr=" + dLat + "," + dLng;
        Intent navigationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(navigationUrlOfMap));
        context.startActivity(navigationIntent);
    }

    private void sendTripStatus(int status) {
        TrpStatusUpdate trpStatusUpdate = new TrpStatusUpdate();
        trpStatusUpdate.setStatus(status);

        RestRequestInterface tripDetails = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<TrpStatusUpdate> call = tripDetails.sendTripStatus(AppPreferences.getInstance().getAccessToken(), trpStatusUpdate, AppPreferences.getInstance().getTripId());
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<TrpStatusUpdate>() {
            @Override
            public void onResponse(final TrpStatusUpdate response) {
                MyApplication.getInstance().hideProgress();
                if (response != null) {
                    //update the customer
                    SocketSession.getInstance().updateTripStatus(AppPreferences.getInstance().getUserId(), AppPreferences.getInstance().getTripId(),
                            TripStatusCodes.reachedPickupLocation);

                    MyApplication.getBus().post(new PickUpAtLocEvent());

                    //
                    AppPreferences.getInstance().saveStartPicUpstate(false);
                    AppPreferences.getInstance().saveFirstDropLocState(true);

                    dialog.dismiss();
                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                MyApplication.getInstance().hideProgress();
            }
        });
    }

    public void getDirectionDetails(final Context context, LatLng pickLatLng, LatLng dropLatLng) {

        RestAPIRequest.getInstance().getDirectionGoogleAPI(context.getResources().getString(R.string.google_server_key),
                pickLatLng, dropLatLng, new OnDirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        String status = direction.getStatus();
                        if (status.equals(RequestResult.OK)) {

                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);

                            double distance = Double.parseDouble(leg.getDistance().getValue().trim()); // KM
                            double duration = Double.parseDouble(leg.getDuration().getValue().trim()); //min

                            distance = Math.round(Utils.calculateKilometers(distance));
                            duration = Math.round(Utils.secondsToMin(duration));

                            if (distance > 2) {
                                refreshBtn.setVisibility(View.VISIBLE);
                                startToPicBtn.setEnabled(false);
                                startToPicBtn.setBackgroundColor(context.getResources().getColor(R.color.gray_light_dark));
                            } else {
                                refreshBtn.setVisibility(View.GONE);
                                reachPicLocTextView.setVisibility(View.GONE);
                                startToPicBtn.setEnabled(true);
                                startToPicBtn.setBackgroundColor(context.getResources().getColor(R.color.bg));
                            }

                            pickipAddrsTextView.setText(context.getResources().getString(R.string.pick_loc_dis_km).replace("^", String.valueOf(distance)));
                            pickupDurationTextView.setText(context.getResources().getString(R.string.pick_loc_duration).replace("^", String.valueOf(duration)));

                            displayBtnLayout.setVisibility(View.VISIBLE);
                            progressBar4.setVisibility(View.GONE);

                            MyApplication.getInstance().hideProgress();
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        MyApplication.getInstance().hideProgress();
                        Log.e("grid", "onDirectionFailure: " + t.getMessage());
                    }
                }
        );
    }
}
