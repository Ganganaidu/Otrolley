package com.thisisswitch.otrolly.driver.network;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.thisisswitch.otrolly.driver.events.TripStatusCodes;

import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by Office on 4/12/2016.
 */
public class SocketSession {

    public static boolean isTrackingStarted;
    public static boolean isTripStarted;
    private static Socket mSocket;
    private static SocketSession instance;

    public static Socket getSession() {
        if (mSocket == null) {
            try {
                mSocket = IO.socket(RestAPIRequest.BASE_URL_IP);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return mSocket;
    }

    public SocketSession() {
        getSession();
    }

    public static SocketSession getInstance() {
        if (instance == null) {
            instance = new SocketSession();
        }
        return instance;
    }

    public void startSendingDriverLocation(String id, String lat, String lng) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("lat", lat);
            json.put("lng", lng);
        } catch (Exception ex) {
        }
        mSocket.emit("dl", json);
    }

    public void driverOffLine(String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("status", "offline");
        } catch (Exception ex) {
        }
        mSocket.emit("dl", json);
    }

    public void onReceiveTripRequest(String tripId, String areaName) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", tripId);
            json.put("area", areaName);
        } catch (Exception ex) {
        }
        mSocket.emit("DriverID", json);
    }

    //On Accepting a Trip
    public void onAcceptingTripRequest(String tripId, String driverId) {
        JSONObject json = new JSONObject();
        try {
            json.put("tid", tripId);
            json.put("did", driverId);
        } catch (Exception ex) {
        }
        mSocket.emit("ta", json);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", driverId);
            jsonObject.put("status", "1002");
        } catch (Exception ex) {
        }
        mSocket.emit("dl", jsonObject);
    }

    public void trackDuringTrip(String driverId, String tripId, String lat, String lng) {
        JSONObject json = new JSONObject();
        try {
            json.put("did", driverId);
            json.put("tid", tripId);
            json.put("lat", lat);
            json.put("lng", lng);
        } catch (Exception ex) {
        }
        mSocket.emit(tripId, json);
    }

    public void dropLocationStatus(String tripId, String dropLocId, int status) {
        JSONObject json = new JSONObject();
        try {
            json.put("tid", tripId);
            json.put("droplocationId", dropLocId);
            json.put("status", status);
        } catch (Exception ex) {
        }
        mSocket.emit(tripId, json);
    }

    public void tripEndStatus(String driverId, String tripId) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", driverId);
            json.put("status", TripStatusCodes.complete);
        } catch (Exception ex) {
        }
        mSocket.emit(tripId, json);
    }

    public void updateTripStatus(String driverId, String tripId, int statusCode) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", driverId);
            json.put("tid", tripId);
            json.put("status", statusCode);
        } catch (Exception ex) {
        }
        mSocket.emit(tripId, json);
    }

    public void rejectTrip(String driverId, String tripId) {
        JSONObject json = new JSONObject();
        try {
            json.put("did", driverId);
            json.put("tid", tripId);
        } catch (Exception ex) {
        }
        mSocket.emit("tj", json);
    }
}
