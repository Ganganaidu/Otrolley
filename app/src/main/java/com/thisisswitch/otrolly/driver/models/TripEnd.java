package com.thisisswitch.otrolly.driver.models;

import com.akexorcist.googledirection.model.Direction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Office on 5/1/2016.
 */
public class TripEnd {

    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("route_path")
    @Expose
    private String routePath;
    @SerializedName("total_distance")
    @Expose
    private String totalDistance;
    @SerializedName("total_time")
    @Expose
    private String totalTime;
    @SerializedName("base_min")
    @Expose
    private String baseMin;
    @SerializedName("fare_cost")
    @Expose
    private String fareCost;
    @SerializedName("transit_time")
    @Expose
    private String transitTime;
    @SerializedName("transit_time_cost")
    @Expose
    private String transitTimeCost;
    @SerializedName("net")
    @Expose
    private String net;


    /**
     * @return The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * @param rating The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The routePath
     */
    public String getRoutePath() {
        return routePath;
    }

    /**
     * @param routePath The route_path
     */
    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getBaseMin() {
        return baseMin;
    }

    public void setBaseMin(String baseMin) {
        this.baseMin = baseMin;
    }

    public String getFareCost() {
        return fareCost;
    }

    public void setFareCost(String fareCost) {
        this.fareCost = fareCost;
    }

    public String getTransitTime() {
        return transitTime;
    }

    public void setTransitTime(String transitTime) {
        this.transitTime = transitTime;
    }

    public String getTransitTimeCost() {
        return transitTimeCost;
    }

    public void setTransitTimeCost(String transitTimeCost) {
        this.transitTimeCost = transitTimeCost;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }
}
