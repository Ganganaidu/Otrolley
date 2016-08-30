
package com.thisisswitch.otrolly.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TripRequest {

    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("total_distance")
    @Expose
    private Float totalDistance;
    @SerializedName("total_time")
    @Expose
    private Float totalTime;
    @SerializedName("base_min")
    @Expose
    private Float baseMin;
    @SerializedName("fare_cost")
    @Expose
    private Float fareCost;
    @SerializedName("transit_time")
    @Expose
    private Float transitTime;
    @SerializedName("transit_time_cost")
    @Expose
    private Float transitTimeCost;
    @SerializedName("net")
    @Expose
    private Float net;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("coupon_id")
    @Expose
    private String couponId;
    @SerializedName("add_cost_per_min")
    @Expose
    private Float addCostPerMin;
    @SerializedName("base_km")
    @Expose
    private Float baseKm;
    @SerializedName("min_cost")
    @Expose
    private Float minCost;
    @SerializedName("add_cost_per_kilometer")
    @Expose
    private Float addCostPerKilometer;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("pickup_address")
    @Expose
    private PickupAddress pickupAddress;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("_droplocations")
    @Expose
    private List<DropLocation> droplocations = new ArrayList<>();
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("dropLocationsAdded")
    @Expose
    private Boolean dropLocationsAdded;

    public Boolean getDropLocationsAdded() {
        return dropLocationsAdded;
    }

    public void setDropLocationsAdded(Boolean dropLocationsAdded) {
        this.dropLocationsAdded = dropLocationsAdded;
    }

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
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The totalDistance
     */
    public Float getTotalDistance() {
        return totalDistance;
    }

    /**
     * @param totalDistance The total_distance
     */
    public void setTotalDistance(Float totalDistance) {
        this.totalDistance = totalDistance;
    }

    /**
     * @return The totalTime
     */
    public Float getTotalTime() {
        return totalTime;
    }

    /**
     * @param totalTime The total_time
     */
    public void setTotalTime(Float totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * @return The baseMin
     */
    public Float getBaseMin() {
        return baseMin;
    }

    /**
     * @param baseMin The base_min
     */
    public void setBaseMin(Float baseMin) {
        this.baseMin = baseMin;
    }

    /**
     * @return The fareCost
     */
    public Float getFareCost() {
        return fareCost;
    }

    /**
     * @param fareCost The fare_cost
     */
    public void setFareCost(Float fareCost) {
        this.fareCost = fareCost;
    }

    /**
     * @return The transitTime
     */
    public Float getTransitTime() {
        return transitTime;
    }

    /**
     * @param transitTime The transit_time
     */
    public void setTransitTime(Float transitTime) {
        this.transitTime = transitTime;
    }

    /**
     * @return The transitTimeCost
     */
    public Float getTransitTimeCost() {
        return transitTimeCost;
    }

    /**
     * @param transitTimeCost The transit_time_cost
     */
    public void setTransitTimeCost(Float transitTimeCost) {
        this.transitTimeCost = transitTimeCost;
    }

    /**
     * @return The net
     */
    public Float getNet() {
        return net;
    }

    /**
     * @param net The net
     */
    public void setNet(Float net) {
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
     * @return The couponId
     */
    public String getCouponId() {
        return couponId;
    }

    /**
     * @param couponId The coupon_id
     */
    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    /**
     * @return The addCostPerMin
     */
    public Float getAddCostPerMin() {
        return addCostPerMin;
    }

    /**
     * @param addCostPerMin The add_cost_per_min
     */
    public void setAddCostPerMin(Float addCostPerMin) {
        this.addCostPerMin = addCostPerMin;
    }

    /**
     * @return The baseKm
     */
    public Float getBaseKm() {
        return baseKm;
    }

    /**
     * @param baseKm The base_km
     */
    public void setBaseKm(Float baseKm) {
        this.baseKm = baseKm;
    }

    /**
     * @return The minCost
     */
    public Float getMinCost() {
        return minCost;
    }

    /**
     * @param minCost The min_cost
     */
    public void setMinCost(Float minCost) {
        this.minCost = minCost;
    }

    /**
     * @return The addCostPerKilometer
     */
    public Float getAddCostPerKilometer() {
        return addCostPerKilometer;
    }

    /**
     * @param addCostPerKilometer The add_cost_per_kilometer
     */
    public void setAddCostPerKilometer(Float addCostPerKilometer) {
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

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUserId(User user) {
        this.user = user;
    }
}
