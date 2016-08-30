package com.thisisswitch.otrolly.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TripHistoryModel implements Serializable {

    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("status")
    @Expose
    private String status;
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
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("add_cost_per_min")
    @Expose
    private String addCostPerMin;
    @SerializedName("base_km")
    @Expose
    private String baseKm;
    @SerializedName("min_cost")
    @Expose
    private String minCost;
    @SerializedName("add_cost_per_kilometer")
    @Expose
    private String addCostPerKilometer;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("pickup_address")
    @Expose
    private PickupAddress pickupAddress;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("_droplocations")
    @Expose
    private List<DropLocation> droplocations = new ArrayList<>();
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("route_path")
    @Expose
    private String routePath;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;

    /**
     * @return The driverId
     */
    public String getDriverId() {
        return driverId;
    }

    /**
     * @param driverId The driver_id
     */
    public void setDriverId(String driverId) {
        this.driverId = driverId;
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
     * @return The totalDistance
     */
    public String getTotalDistance() {
        return totalDistance;
    }

    /**
     * @param totalDistance The total_distance
     */
    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    /**
     * @return The totalTime
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     * @param totalTime The total_time
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * @return The baseMin
     */
    public String getBaseMin() {
        return baseMin;
    }

    /**
     * @param baseMin The base_min
     */
    public void setBaseMin(String baseMin) {
        this.baseMin = baseMin;
    }

    /**
     * @return The fareCost
     */
    public String getFareCost() {
        return fareCost;
    }

    /**
     * @param fareCost The fare_cost
     */
    public void setFareCost(String fareCost) {
        this.fareCost = fareCost;
    }

    /**
     * @return The transitTime
     */
    public String getTransitTime() {
        return transitTime;
    }

    /**
     * @param transitTime The transit_time
     */
    public void setTransitTime(String transitTime) {
        this.transitTime = transitTime;
    }

    /**
     * @return The transitTimeCost
     */
    public String getTransitTimeCost() {
        return transitTimeCost;
    }

    /**
     * @param transitTimeCost The transit_time_cost
     */
    public void setTransitTimeCost(String transitTimeCost) {
        this.transitTimeCost = transitTimeCost;
    }

    /**
     * @return The net
     */
    public String getNet() {
        return net;
    }

    /**
     * @param net The net
     */
    public void setNet(String net) {
        this.net = net;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The addCostPerMin
     */
    public String getAddCostPerMin() {
        return addCostPerMin;
    }

    /**
     * @param addCostPerMin The add_cost_per_min
     */
    public void setAddCostPerMin(String addCostPerMin) {
        this.addCostPerMin = addCostPerMin;
    }

    /**
     * @return The baseKm
     */
    public String getBaseKm() {
        return baseKm;
    }

    /**
     * @param baseKm The base_km
     */
    public void setBaseKm(String baseKm) {
        this.baseKm = baseKm;
    }

    /**
     * @return The minCost
     */
    public String getMinCost() {
        return minCost;
    }

    /**
     * @param minCost The min_cost
     */
    public void setMinCost(String minCost) {
        this.minCost = minCost;
    }

    /**
     * @return The addCostPerKilometer
     */
    public String getAddCostPerKilometer() {
        return addCostPerKilometer;
    }

    /**
     * @param addCostPerKilometer The add_cost_per_kilometer
     */
    public void setAddCostPerKilometer(String addCostPerKilometer) {
        this.addCostPerKilometer = addCostPerKilometer;
    }

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The pickupAddress
     */
    public PickupAddress getPickupAddress() {
        return pickupAddress;
    }

    /**
     * @param pickupAddress The pickup_address
     */
    public void setPickupAddress(PickupAddress pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The droplocations
     */
    public List<DropLocation> getDroplocations() {
        return droplocations;
    }

    /**
     * @param droplocations The _droplocations
     */
    public void setDroplocations(List<DropLocation> droplocations) {
        this.droplocations = droplocations;
    }

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

    /**
     * @return The createdOn
     */
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn The created_on
     */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return The updatedOn
     */
    public String getUpdatedOn() {
        return updatedOn;
    }

    /**
     * @param updatedOn The updated_on
     */
    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

}
