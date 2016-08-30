
package com.thisisswitch.otrolly.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DropLocation implements Serializable {

    @Expose
    private String goodscategoryId;
    @SerializedName("invoice_number")
    @Expose
    private String invoiceNumber;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("plot_number")
    @Expose
    private String plotNumber;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("reachedTime")
    @Expose
    private String reachedTime;
    @SerializedName("bypassTime")
    @Expose
    private String bypassTime;
    @SerializedName("verifiedTime")
    @Expose
    private String verifiedTime;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("geo")
    @Expose
    private Geo geo;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("reciver_phone")
    @Expose
    private String reciverPhone;
    @SerializedName("reciver_name")
    @Expose
    private String reciverName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("area")
    @Expose
    private String area;

    /**
     * @return The goodscategoryId
     */
    public String getGoodscategoryId() {
        return goodscategoryId;
    }

    /**
     * @param goodscategoryId The goodscategory_id
     */
    public void setGoodscategoryId(String goodscategoryId) {
        this.goodscategoryId = goodscategoryId;
    }

    /**
     * @return The invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * @param invoiceNumber The invoice_number
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * @return The locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * @param locationName The location_name
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * @return The plotNumber
     */
    public String getPlotNumber() {
        return plotNumber;
    }

    /**
     * @param plotNumber The plot_number
     */
    public void setPlotNumber(String plotNumber) {
        this.plotNumber = plotNumber;
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
     * @return The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime The startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return The reachedTime
     */
    public String getReachedTime() {
        return reachedTime;
    }

    /**
     * @param reachedTime The reachedTime
     */
    public void setReachedTime(String reachedTime) {
        this.reachedTime = reachedTime;
    }

    /**
     * @return The bypassTime
     */
    public String getBypassTime() {
        return bypassTime;
    }

    /**
     * @param bypassTime The bypassTime
     */
    public void setBypassTime(String bypassTime) {
        this.bypassTime = bypassTime;
    }

    /**
     * @return The verifiedTime
     */
    public String getVerifiedTime() {
        return verifiedTime;
    }

    /**
     * @param verifiedTime The verifiedTime
     */
    public void setVerifiedTime(String verifiedTime) {
        this.verifiedTime = verifiedTime;
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
     * @return The otp
     */
    public String getOtp() {
        return otp;
    }

    /**
     * @param otp The otp
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }

    /**
     * @return The reciverPhone
     */
    public String getReciverPhone() {
        return reciverPhone;
    }

    /**
     * @param reciverPhone The reciver_phone
     */
    public void setReciverPhone(String reciverPhone) {
        this.reciverPhone = reciverPhone;
    }

    /**
     * @return The reciverName
     */
    public String getReciverName() {
        return reciverName;
    }

    /**
     * @param reciverName The reciver_name
     */
    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
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

}
