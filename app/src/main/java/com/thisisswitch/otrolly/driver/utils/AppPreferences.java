package com.thisisswitch.otrolly.driver.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.models.TripRequest;
import com.thisisswitch.otrolly.driver.models.UserProfile;
import com.thisisswitch.otrolly.driver.models.Vehicletype;

/**
 * Saving data across the application
 */
public class AppPreferences {

    private static final String APP_SHARED_PREFS = "com.snappit.utils";
    private SharedPreferences appSharedPrefs;
    private Editor prefsEditor;

    private static AppPreferences instance = null;

    public static AppPreferences getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return new AppPreferences(MyApplication.getInstance());
        }
    }

    /**
     * Saving data in shared preferences which will store life time of Application
     */
    public AppPreferences(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    /*
     *  Delete the all the preferences
     */
    public void deletePref() {
        this.prefsEditor.clear();
        this.prefsEditor.commit();
    }

    /**
     * Save user response
     */
    public void saveUserResponse(String value) {
        prefsEditor.putString("userResponse", value);
        prefsEditor.commit();
    }

    public String getUserResponse() {
        return appSharedPrefs.getString("userResponse", "");
    }

    public UserProfile getUser() {
        Gson gson = new Gson();
        String json = getUserResponse();
        return gson.fromJson(json, UserProfile.class);
    }

    /**
     * Save trip response
     */
    public void saveTripRequestResponse(String value) {
        prefsEditor.putString("tripResponse", value);
        prefsEditor.commit();
    }

    public String getTripRequestResponse() {
        return appSharedPrefs.getString("tripResponse", "");
    }

    public TripRequest getTripRequest() {
        Gson gson = new Gson();
        String json = getTripRequestResponse();
        return gson.fromJson(json, TripRequest.class);
    }

    /**
     * Save vehicle data
     */
    public void saveVehicleTypeResponse(String value) {
        prefsEditor.putString("vehicleType", value);
        prefsEditor.commit();
    }

    public String getVehicleType() {
        return appSharedPrefs.getString("vehicleType", "");
    }

    public Vehicletype getVehicleData() {
        Gson gson = new Gson();
        String json = getVehicleType();
        return gson.fromJson(json, Vehicletype.class);
    }


    public void saveLat(String latitude) {
        prefsEditor.putString("latitude", latitude);
        prefsEditor.commit();
    }

    public String getLat() {
        return appSharedPrefs.getString("latitude", "");
    }

    public void saveLng(String longitude) {
        prefsEditor.putString("longitude", longitude);
        prefsEditor.commit();
    }

    public String getLng() {
        return appSharedPrefs.getString("longitude", "");
    }


    /**
     * Save user ID
     */
    public void saveUserId(String userId) {
        prefsEditor.putString("userId", userId);
        prefsEditor.commit();
    }

    public String getUserId() {
        return appSharedPrefs.getString("userId", "");
    }

    /**
     * Save customer user ID
     */
    public void saveCustomerUserId(String userId) {
        prefsEditor.putString("saveCustomerUserId", userId);
        prefsEditor.commit();
    }

    public String getCustomerUserId() {
        return appSharedPrefs.getString("saveCustomerUserId", "");
    }

    /**
     * Save user ID
     */
    public void saveSignInState(boolean state) {
        prefsEditor.putBoolean("saveSignInState", state);
        prefsEditor.commit();
    }

    public boolean getSignInState() {
        return appSharedPrefs.getBoolean("saveSignInState", false);
    }

    /**
     * use trip pic up state when user clear the app from the stack while trip is active
     */
    public void saveStartPicUpstate(boolean state) {
        prefsEditor.putBoolean("saveStartPicUpsate", state);
        prefsEditor.commit();
    }

    public boolean getStartPicupState() {
        return appSharedPrefs.getBoolean("saveStartPicUpsate", false);
    }

    /**
     * use drop loc state when user clear the app from the stack while trip is active
     */
    public void saveFirstDropLocState(boolean state) {
        prefsEditor.putBoolean("saveFirstDropLocState", state);
        prefsEditor.commit();
    }

    public boolean getFirstDropLocState() {
        return appSharedPrefs.getBoolean("saveFirstDropLocState", false);
    }

    /**
     * Save access token
     */
    public void saveAccessToken(String state) {
        prefsEditor.putString("getAccessToken", state);
        prefsEditor.commit();
    }

    public String getAccessToken() {
        return appSharedPrefs.getString("getAccessToken", "");
    }

    /**
     * Save trip id
     */
    public void saveTripId(String state) {
        prefsEditor.putString("saveTripId", state);
        prefsEditor.commit();
    }

    public String getTripId() {
        return appSharedPrefs.getString("saveTripId", "");
    }

    /**
     * Save trip id
     */
    public void saveVehicleNumber(String state) {
        prefsEditor.putString("saveVehicleNumber", state);
        prefsEditor.commit();
    }

    public String getVehicleNumber() {
        return appSharedPrefs.getString("saveVehicleNumber", "");
    }

    /**
     * Save tracking state
     */
    public void saveTrackingState(boolean state) {
        prefsEditor.putBoolean("saveTrackingState", state);
        prefsEditor.commit();
    }

    public boolean getTrackingState() {
        return appSharedPrefs.getBoolean("saveTrackingState", false);
    }

    /**
     * Save total distance
     */
    public void saveTotalDistance(float val) {
        prefsEditor.putFloat("saveTotalDistance", val);
        prefsEditor.commit();
    }

    public float getTotalDistance() {
        return appSharedPrefs.getFloat("saveTotalDistance", 0);
    }

    /**
     * Save total duration
     */
    public void saveTotalDuration(float val) {
        prefsEditor.putFloat("TotalDuration", val);
        prefsEditor.commit();
    }

    public float getTotalDuration() {
        return appSharedPrefs.getFloat("TotalDuration", 0);
    }

    public void clearTripPref() {
        saveFirstDropLocState(false);
        saveStartPicUpstate(false);
        saveTrackingState(false);

        saveTotalDistance(0);
        saveTotalDuration(0);
        saveTripRequestResponse("");
    }
}