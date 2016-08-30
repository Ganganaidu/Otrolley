
package com.thisisswitch.otrolly.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Vehicletype {

    @SerializedName("dimensions")
    @Expose
    private List<Dimension> dimensions = new ArrayList<Dimension>();

    @SerializedName("load_capacity")
    @Expose
    private String loadCapacity;

    @SerializedName("marker_img")
    @Expose
    private String markerImg;

    @SerializedName("tab_offline_img")
    @Expose
    private String tabOfflineImg;

    @SerializedName("tab_online_img")
    @Expose
    private String tabOnlineImg;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("add_cost_per_min")
    @Expose
    private double addCostPerMin;

    @SerializedName("base_km")
    @Expose
    private double baseKm;

    @SerializedName("base_min")
    @Expose
    private double baseMin;

    @SerializedName("min_cost")
    @Expose
    private double minCost;

    @SerializedName("region_id")
    @Expose
    private String regionId;

    @SerializedName("add_cost_per_kilometer")
    @Expose
    private double addCostPerKilometer;

    @SerializedName("id")
    @Expose
    private String id;

    /**
     * @return The dimensions
     */
    public List<Dimension> getDimensions() {
        return dimensions;
    }

    /**
     * @param dimensions The dimensions
     */
    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * @return The loadCapacity
     */
    public String getLoadCapacity() {
        return loadCapacity;
    }

    /**
     * @param loadCapacity The load_capacity
     */
    public void setLoadCapacity(String loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    /**
     * @return The markerImg
     */
    public String getMarkerImg() {
        return markerImg;
    }

    /**
     * @param markerImg The marker_img
     */
    public void setMarkerImg(String markerImg) {
        this.markerImg = markerImg;
    }

    /**
     * @return The tabOfflineImg
     */
    public String getTabOfflineImg() {
        return tabOfflineImg;
    }

    /**
     * @param tabOfflineImg The tab_offline_img
     */
    public void setTabOfflineImg(String tabOfflineImg) {
        this.tabOfflineImg = tabOfflineImg;
    }

    /**
     * @return The tabOnlineImg
     */
    public String getTabOnlineImg() {
        return tabOnlineImg;
    }

    /**
     * @param tabOnlineImg The tab_online_img
     */
    public void setTabOnlineImg(String tabOnlineImg) {
        this.tabOnlineImg = tabOnlineImg;
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
    public double getAddCostPerMin() {
        return addCostPerMin;
    }

    /**
     * @param addCostPerMin The add_cost_per_min
     */
    public void setAddCostPerMin(int addCostPerMin) {
        this.addCostPerMin = addCostPerMin;
    }

    /**
     * @return The baseKm
     */
    public double getBaseKm() {
        return baseKm;
    }

    /**
     * @param baseKm The base_km
     */
    public void setBaseKm(int baseKm) {
        this.baseKm = baseKm;
    }

    /**
     * @return The baseMin
     */
    public double getBaseMin() {
        return baseMin;
    }

    /**
     * @param baseMin The base_min
     */
    public void setBaseMin(int baseMin) {
        this.baseMin = baseMin;
    }

    /**
     * @return The minCost
     */
    public double getMinCost() {
        return minCost;
    }

    /**
     * @param minCost The min_cost
     */
    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    /**
     * @return The regionId
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * @param regionId The region_id
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    /**
     * @return The addCostPerKilometer
     */
    public double getAddCostPerKilometer() {
        return addCostPerKilometer;
    }

    /**
     * @param addCostPerKilometer The add_cost_per_kilometer
     */
    public void setAddCostPerKilometer(int addCostPerKilometer) {
        this.addCostPerKilometer = addCostPerKilometer;
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

}
