package com.thisisswitch.otrolly.driver.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.events.DriverLogOutEvent;
import com.thisisswitch.otrolly.driver.events.DriverLoginEvent;
import com.thisisswitch.otrolly.driver.events.DropLocUpdateEvent;
import com.thisisswitch.otrolly.driver.events.InitialTripRequestEvent;
import com.thisisswitch.otrolly.driver.events.TripStatusCodes;
import com.thisisswitch.otrolly.driver.events.UpdateTripStatusEvent;
import com.thisisswitch.otrolly.driver.listeners.RequestListener;
import com.thisisswitch.otrolly.driver.models.DropLocation;
import com.thisisswitch.otrolly.driver.models.TripRequest;
import com.thisisswitch.otrolly.driver.models.TrpStatusUpdate;
import com.thisisswitch.otrolly.driver.models.User;
import com.thisisswitch.otrolly.driver.models.UserProfile;
import com.thisisswitch.otrolly.driver.models.Vehicletype;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;

import retrofit2.Call;

/**
 * Created by gkondati on 7/6/2016.
 */
public class OTrolleyAPIRequest {
    private static final String TAG = "OtrolleyAPIRequest";

    private static OTrolleyAPIRequest _instance;

    private OTrolleyAPIRequest() {

    }

    public static OTrolleyAPIRequest getInstance() {
        if (_instance == null) {
            _instance = new OTrolleyAPIRequest();
        }
        return _instance;
    }

    public void requestForUpdatedDropLoc(Context context) {
        MyApplication.getInstance().showProgress(context, "updating tirp details");

        RestRequestInterface tripDetails = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<TripRequest> call = tripDetails.getTripDetails(AppPreferences.getInstance().getAccessToken(), AppPreferences.getInstance().getTripId(), "");
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<TripRequest>() {
            @Override
            public void onResponse(final TripRequest response) {
                MyApplication.getInstance().hideProgress();

                DropLocUpdateEvent dropLocUpdateEvent = new DropLocUpdateEvent();
                dropLocUpdateEvent.tripRequest = response;
                MyApplication.getBus().post(dropLocUpdateEvent);
            }

            @Override
            public void onDisplayError(String errorMsg) {
                MyApplication.getInstance().hideProgress();
                Log.e(TAG, "errorMsg:" + errorMsg);
            }
        });
    }

    public void updateDropLocStatus(DropLocation dropLocation) {
        RestRequestInterface dropLocReq = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<DropLocation> call = dropLocReq.sendDropEndStatus(AppPreferences.getInstance().getAccessToken(),
                dropLocation, AppPreferences.getInstance().getTripId(), dropLocation.getId());
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<DropLocation>() {
            @Override
            public void onResponse(DropLocation response) {
                //Log.e(TAG, "response:" + response);
                MyApplication.getInstance().hideProgress();
            }

            @Override
            public void onDisplayError(String errorMsg) {
                MyApplication.getInstance().hideProgress();
                //Log.e(TAG, "errorMsg:" + errorMsg);
            }
        });
    }

    //1010
    public void sendTripStatus(Context context, int status) {
        MyApplication.getInstance().showProgress(context, "updating status");

        TrpStatusUpdate trpStatusUpdate = new TrpStatusUpdate();
        trpStatusUpdate.setStatus(status);

        RestRequestInterface tripDetails = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<TrpStatusUpdate> call = tripDetails.sendTripStatus(AppPreferences.getInstance().getAccessToken(), trpStatusUpdate, AppPreferences.getInstance().getTripId());
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<TrpStatusUpdate>() {
            @Override
            public void onResponse(final TrpStatusUpdate response) {
                MyApplication.getInstance().hideProgress();
                if (response != null) {
                    SocketSession.getInstance().updateTripStatus(AppPreferences.getInstance().getUserId(), AppPreferences.getInstance().getTripId(),
                            TripStatusCodes.EnrouteToPickupLocation);

                    UpdateTripStatusEvent updateTripStatusEvent = new UpdateTripStatusEvent();
                    updateTripStatusEvent.trpStatusUpdate = response;
                    MyApplication.getBus().post(updateTripStatusEvent);
                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                MyApplication.getInstance().hideProgress();
                Log.e(TAG, "errorMsg:" + errorMsg);
            }
        });
    }

    public void getTripDetails(final String tripId) {
        final InitialTripRequestEvent initialTripRequestEvent = new InitialTripRequestEvent();

        RestRequestInterface tripDetails = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<TripRequest> call = tripDetails.getTripDetails(AppPreferences.getInstance().getAccessToken(), tripId, "");
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<TripRequest>() {
            @Override
            public void onResponse(final TripRequest response) {
                initialTripRequestEvent.tripRequest = response;
                initialTripRequestEvent.tripId = tripId;
                MyApplication.getBus().post(initialTripRequestEvent);
            }

            @Override
            public void onDisplayError(String errorMsg) {
                //hideProgress();
                initialTripRequestEvent.tripRequest = null;
                Log.e(TAG, "errorMsg:" + errorMsg);
            }
        });
    }

    /*Sign in details
    * @link SignInFragment*/
    public void loginUser(final Context context, String phone, String password, final String registrationNumber) {
        AppPreferences.getInstance().saveVehicleNumber(registrationNumber);

        RestRequestInterface myLogin = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<User> call = myLogin.loginUser(phone, password, registrationNumber);
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<User>() {
            @Override
            public void onResponse(User response) {
                //MyApplication.getInstance().hideProgress();
                //Log.e(TAG, "response:" + response);
                if (response != null) {
                    AppPreferences.getInstance().saveTrackingState(false);

                    AppPreferences.getInstance().saveSignInState(true);

                    AppPreferences.getInstance().saveAccessToken(response.getId());
                    AppPreferences.getInstance().saveUserId(response.getUserId());

                    Gson gson = new Gson();
                    String json = gson.toJson(response); // myObject - instance of MyObject
                    AppPreferences.getInstance().saveUserResponse(json);

                    //Log.d(TAG, "loginButton onResponse: ");
                    loadVehicleDetails(context, registrationNumber);
                } else {
                    MyApplication.getInstance().hideProgress();
                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                MyApplication.getInstance().hideProgress();
                //Log.e(TAG, "errorMsg:" + errorMsg);
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadVehicleDetails(final Context context, String vehicleNumber) {
        //Log.d(TAG, "loadVehicleDetails: ");
        RestRequestInterface myLogin = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);

        Call<Vehicletype> call = myLogin.getVehicleDetails(AppPreferences.getInstance().getAccessToken(), vehicleNumber);
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<Vehicletype>() {
            @Override
            public void onResponse(Vehicletype response) {
                //Log.e(TAG, "response:" + response);
                if (response != null) {

                    Gson gson = new Gson();
                    String json = gson.toJson(response); // myObject - instance of MyObject
                    AppPreferences.getInstance().saveVehicleTypeResponse(json);

                    loadDriverDetails(context);
                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                MyApplication.getInstance().hideProgress();

                Log.e(TAG, "errorMsg:" + errorMsg);
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDriverDetails(final Context context) {
        // Log.d(TAG, "loadDriverDetails: ");
        final DriverLoginEvent driverLoginEvent = new DriverLoginEvent();

        RestRequestInterface myLogin = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);

        Call<UserProfile> call = myLogin.getDriverDetails(AppPreferences.getInstance().getAccessToken(), AppPreferences.getInstance().getUserId());
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<UserProfile>() {
            @Override
            public void onResponse(UserProfile response) {
                MyApplication.getInstance().hideProgress();
                //Log.e(TAG, "response:" + response);
                if (response != null) {

                    Gson gson = new Gson();
                    String json = gson.toJson(response); // myObject - instance of MyObject
                    AppPreferences.getInstance().saveUserResponse(json);

                    driverLoginEvent.success = true;
                    MyApplication.getBus().post(driverLoginEvent);
                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                MyApplication.getInstance().hideProgress();

                driverLoginEvent.success = false;
                MyApplication.getBus().post(driverLoginEvent);

                Log.e(TAG, "errorMsg:" + errorMsg);
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logOutUser() {
        final DriverLogOutEvent logOutEvent = new DriverLogOutEvent();

        RestRequestInterface tripDetails = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<String> call = tripDetails.logOutUser(AppPreferences.getInstance().getAccessToken());
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<String>() {
            @Override
            public void onResponse(final String response) {
                logOutEvent.logoutUser = true;
                MyApplication.getBus().post(logOutEvent);
            }

            @Override
            public void onDisplayError(String errorMsg) {
                logOutEvent.logoutUser = true;
                MyApplication.getBus().post(logOutEvent);
            }
        });
    }
}
