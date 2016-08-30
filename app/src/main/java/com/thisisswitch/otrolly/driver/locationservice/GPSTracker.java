package com.thisisswitch.otrolly.driver.locationservice;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.thisisswitch.otrolly.driver.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Office on 3/14/2016.
 */
public class GPSTracker implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = "GPSTracker";

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 20 * 1000; // 1*1000(1 second)
    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 2000;

    public double latitude;
    public double longitude;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;
    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;
    // flag for GPS Status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // How many Geocoder should return our GPSTracker
    int geocoderMaxResults = 1;
    List<Address> addresses;
    String addressLine = "";
    //location listener for updating location
    UpdateLocationListener locationListener;
    private Context mContext;

    public GPSTracker(Context context) {
        mContext = context;
        buildGoogleApiClient(context);
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient(Context mContext) {
        Log.i(TAG, "Building GoogleApiClient");

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        createLocationRequest();
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);// 10 seconds, in milliseconds

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);// 1 second, in milliseconds

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    //TODO

    /**
     * Requests location updates from the FusedLocationApi.
     */
    public void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            //LocationServices.FusedLocationApi.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, this);
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mCurrentLocation == null) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            //Update location
            updateGPSCoordinates();
        }

        // If the user presses the Start Updates button before GoogleApiClient connects, we set
        // mRequestingLocationUpdates to true (see startUpdatesButtonHandler()). Here, we check
        // the value of mRequestingLocationUpdates and if it is true, we start location updates.
        startLocationUpdates();
    }

    /**
     * Callback that fires when the location changes.
     */
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        //UPdate location
        updateGPSCoordinates();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    public void onStart() {
        mGoogleApiClient.connect();
    }

    public void onResume() {
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.

        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    public void onPause() {
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
    }

    /**
     * Update GPSTracker latitude and longitude
     */
    public void updateGPSCoordinates() {
        if (mCurrentLocation != null) {
            locationListener.onLocationChanged(mCurrentLocation);
            latitude = mCurrentLocation.getLatitude();
            longitude = mCurrentLocation.getLongitude();
        }
    }

    /**
     * GPSTracker latitude getter and setter
     *
     * @return latitude
     */
    public double getLatitude() {
        if (mCurrentLocation != null) {
            latitude = mCurrentLocation.getLatitude();
        }

        return latitude;
    }

    /**
     * GPSTracker longitude getter and setter
     *
     * @return
     */
    public double getLongitude() {
        if (mCurrentLocation != null) {
            longitude = mCurrentLocation.getLongitude();
        }

        return longitude;
    }

    LocationManager locationManager;

    /**
     * Try to get my current location by GPS or Network Provider
     */
    public boolean checkLocationState() {

        try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            //getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            // Try to get location if you GPS Service is enabled
            if (isGPSEnabled) {
                return true;
            } else if (isNetworkEnabled) { // Try to get location if you Network Service is enabled
                return true;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            Log.e(TAG, "Impossible to connect to LocationManager", e);
        }
        return false;
    }

    /**
     * Function to show settings alert dialog
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        //Setting Dialog Title
        alertDialog.setTitle(R.string.GPSAlertDialogTitle);

        //Setting Dialog Message
        alertDialog.setMessage(R.string.GPSAlertDialogMessage);

        //On Pressing Setting button
        alertDialog.setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        //On pressing cancel button
        alertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void reverseGeocode(Context context, final OnAddressChangeListener listener) {
        final Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        if (mCurrentLocation != null) {
            new AsyncTask<String, Integer, List<Address>>() {
                @Override
                protected List<Address> doInBackground(String... params) {
                    addresses = new ArrayList<>();
                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, geocoderMaxResults);
                    } catch (Exception e) {
                        Log.w(getClass().toString(), e);
                    }
                    return addresses;
                }

                @Override
                protected void onPostExecute(List<Address> res) {
                    listener.updateAddress();
                }
            }.execute();
        }
    }

    public void reverseGeocodeUsingLatlng(final LatLng latLng, Context context, final OnAddressChangeListener listener) {
        final Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        if (mCurrentLocation != null) {
            new AsyncTask<String, Integer, List<Address>>() {
                @Override
                protected List<Address> doInBackground(String... params) {
                    addresses = new ArrayList<>();
                    try {
                        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, geocoderMaxResults);
                    } catch (Exception e) {
                        Log.w(getClass().toString(), e);
                    }
                    return addresses;
                }

                @Override
                protected void onPostExecute(List<Address> res) {
                    listener.updateAddress();
                }
            }.execute();
        }
    }

    /**
     * Try to get AddressLine
     *
     * @return null or addressLine
     */
    public String getAddressLine(Context context) {
        //List<Address> addresses = getGeocoderAddress(context);
        //reverseGeocode(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            addressLine = address.getAddressLine(0);

            return addressLine;
        } else {
            return null;
        }
    }

    public void setDefaultAddress(String address) {
        this.addressLine = address;
    }

    /**
     * Try to get Locality
     *
     * @return null or locality
     */
    public String getLocality(Context context) {
        //List<Address> addresses = getGeocoderAddress(context);
        //reverseGeocode(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String locality = address.getLocality();

            return locality;
        } else {
            return null;
        }
    }

    /**
     * Try to get Postal Code
     *
     * @return null or postalCode
     */
    public String getPostalCode(Context context) {
        // List<Address> addresses = getGeocoderAddress(context);
        //reverseGeocode(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String postalCode = address.getPostalCode();

            return postalCode;
        } else {
            return null;
        }
    }

    /**
     * Try to get CountryName
     *
     * @return null or postalCode
     */
    public String getCountryName(Context context) {
        // List<Address> addresses = getGeocoderAddress(context);
        //reverseGeocode(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            return address.getCountryName();
        } else {
            return null;
        }
    }

    public void setLocationListener(UpdateLocationListener listener) {
        this.locationListener = listener;
    }

    public interface UpdateLocationListener {
        void onLocationChanged(Location location);
    }

    public interface OnAddressChangeListener {
        void updateAddress();
    }
}

