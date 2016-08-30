package com.thisisswitch.otrolly.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponDetails {

    @SerializedName("coupon_id")
    @Expose
    private String couponId;
    @SerializedName("percentage")
    @Expose
    private double percentage;
    @SerializedName("discountAmount")
    @Expose
    private double discountAmount;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("maxAmount")
    @Expose
    private double maxAmount;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;
    @SerializedName("quantity")
    @Expose
    private double quantity;

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
     * @return The percentage
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * @param percentage The percentage
     */
    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    /**
     * @return The discountAmount
     */
    public double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * @param discountAmount The discountAmount
     */
    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * @return The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
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
     * @return The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return The maxAmount
     */
    public double getMaxAmount() {
        return maxAmount;
    }

    /**
     * @param maxAmount The maxAmount
     */
    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }

    /**
     * @return The code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return The expiryDate
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate The expiryDate
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return The quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity The quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
