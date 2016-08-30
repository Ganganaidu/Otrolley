
package com.thisisswitch.otrolly.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class PickupAddress implements Serializable {

    @SerializedName("geo")
    @Expose
    private Geo geo;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("address")
    @Expose
    private String address;

    /**
     * @return The geo
     */
    public Geo getGeo() {
        return geo;
    }

    /**
     * @param geo The geo
     */
    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    /**
     * @return The area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area The area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

}
