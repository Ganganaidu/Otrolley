package com.thisisswitch.otrolly.driver;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.otto.Bus;
import com.thisisswitch.otrolly.driver.models.TripEnd;

import java.util.ArrayList;

public class MyApplication extends Application {

    //private static final String TAG = "MyApplication";
    public static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication instance = null;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    private LatLng currentLatLng;;
    private TripEnd tripEnd;

    private String lat, lng;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    private static final Bus BUS = new Bus();

    public static Bus getBus() {
        return BUS;
    }

    private static ProgressDialog pd;

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showProgress(Context mContext, String message) {
        pd = new ProgressDialog(mContext);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    public void hideProgress() {
        if (pd != null) {
            pd.dismiss();
        }
    }

    //alert for leave album
    public void showOkAlert(String msg, Context context) {
        //final exit of application
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);

        builder.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void setCurrentLatLng(LatLng geoPoint) {
        this.currentLatLng = geoPoint;
    }

    public LatLng getCurrentLatLng() {
        return currentLatLng;
    }

    public void setTripEndRequest(TripEnd tripEnd) {
        this.tripEnd = tripEnd;
    }

    public TripEnd getTripEndRequest() {
        return tripEnd;
    }

    private ArrayList<LatLng> routPathList = new ArrayList<>();

    public ArrayList<LatLng> getRoutePath() {
        return routPathList;
    }

    public void setRoutePath(ArrayList<LatLng> direction) {
        this.routPathList = direction;
    }

    public void saveRouteLatitudes(String lat) {
        this.lat = lat;
    }

    public String getRouteLat() {
        return lat;
    }

    public void saveRouteLng(String lng) {
        this.lng = lng;
    }

    public String getRouteLng() {
        return lng;
    }

    public void clearAllData() {

        tripEnd = null;

        lat = "";
        lng = "";
        routPathList = new ArrayList<>();
    }
}
