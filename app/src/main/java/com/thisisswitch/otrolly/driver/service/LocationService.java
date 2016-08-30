package com.thisisswitch.otrolly.driver.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.locationservice.GPSTracker;
import com.thisisswitch.otrolly.driver.network.SocketSession;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;

import java.util.ArrayList;

/**
 * Created by Office on 6/2/2016.
 */
public class LocationService extends IntentService implements GPSTracker.UpdateLocationListener {

    private static final String TAG = "LocationService";

    Callbacks callbacks;
    public GPSTracker gpsTracker;
    private final IBinder mBinder = new LocalBinder();


    public LocationService() {
        super("LocationService");

    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LocationService(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //returns the instance of the service
    public class LocalBinder extends Binder {
        public LocationService getServiceInstance() {
            return LocationService.this;
        }
    }

    //Here Activity register to the service as Callbacks client
    public void registerClient(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    //callbacks interface for communication with service clients!
    public interface Callbacks {
        void onLocationChanged(Location location);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        gpsTracker = new GPSTracker(this);
        gpsTracker.setLocationListener(this);
        gpsTracker.onStart();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (callbacks != null) {
                //update activity
                callbacks.onLocationChanged(location);
            }

            if (SocketSession.isTrackingStarted) {
                Log.d(TAG, "userId:" + AppPreferences.getInstance().getUserId());
                Log.d(TAG, "isTripStarted:" + SocketSession.isTripStarted);

                AppPreferences.getInstance().saveLat(String.valueOf(location.getLatitude()));
                AppPreferences.getInstance().saveLng(String.valueOf(location.getLongitude()));

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MyApplication.getInstance().setCurrentLatLng(latLng);

                Log.d(TAG, "latLng:" + latLng.toString());

                if (SocketSession.isTripStarted) {
                    try {

//                        ArrayList<LatLng> arrayList = AppPreferences.getInstance().getRoutePath();
//                        arrayList.add(latLng);
//                        AppPreferences.getInstance().saveRoutePath(arrayList);

                        SocketSession.getInstance().trackDuringTrip(AppPreferences.getInstance().getUserId(),
                                AppPreferences.getInstance().getTripId(), String.valueOf(location.getLatitude()),
                                String.valueOf(location.getLongitude()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    SocketSession.getInstance().startSendingDriverLocation(AppPreferences.getInstance().getUserId(),
                            String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (gpsTracker != null) {
            gpsTracker.onPause();
            gpsTracker.onStop();
        }
    }
}
