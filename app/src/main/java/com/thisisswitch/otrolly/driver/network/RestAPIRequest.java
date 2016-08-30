package com.thisisswitch.otrolly.driver.network;

import android.util.Log;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.google.android.gms.maps.model.LatLng;
import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.events.SessionExpiredEvent;
import com.thisisswitch.otrolly.driver.listeners.OnDirectionCallback;
import com.thisisswitch.otrolly.driver.listeners.RequestListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ganga on 3/17/2016.
 */
public class RestAPIRequest {
    private static final String TAG = "RestAPIRequest";

    public static final String BASE_URL = "http://52.77.201.220:3004/api/";
    public static final String BASE_URL_IP = "http://52.77.201.220:3004";

    private static Retrofit retrofit;
    private static RestAPIRequest instance;

    public static RestAPIRequest getInstance() {
        if (instance == null) {
            instance = new RestAPIRequest();
        }
        return instance;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /*Generic method for doing all API requests in the application*/
    public <T> void doRequest(Call<T> call, final RequestListener<T> requestListener) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                Log.d(TAG, "response code:" + response.code());
                Log.d(TAG, "response message:" + response.message());

                //Log.e(TAG, "response:" + response.body());
                if (response.code() == 200 || response.code() == 204) {
                    requestListener.onResponse(response.body());
                    //Log.d(TAG, "response:" + response.body());
                    //Log.e(TAG, "response:" + response.body());
                } else {
                    requestListener.onDisplayError(response.message());
                    if (response.message().equalsIgnoreCase("unauthorized")) {
                        MyApplication.getBus().post(new SessionExpiredEvent());
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.d(TAG, "errorMsg:" + t.getMessage());
                requestListener.onDisplayError(t.getMessage());
                if (t.getMessage().equalsIgnoreCase("unauthorized")) {
                    MyApplication.getBus().post(new SessionExpiredEvent());
                }
            }
        });
    }


    public void getDirectionGoogleAPI(String apiKey, LatLng origin, LatLng destination, final OnDirectionCallback callback) {
        // LatLng origin = new LatLng(37.7849569, -122.4068855);
        // LatLng destination = new LatLng(37.7814432, -122.4460177);
        GoogleDirection.withServerKey(apiKey)
                .from(origin)
                .to(destination)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        // Do something here
                        callback.onDirectionSuccess(direction, rawBody);
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something here
                        callback.onDirectionFailure(t);
                    }
                });
    }
}
