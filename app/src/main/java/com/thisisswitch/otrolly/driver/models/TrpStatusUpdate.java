package com.thisisswitch.otrolly.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TrpStatusUpdate {

    @SerializedName("status")
    @Expose
    private Integer status;

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
}