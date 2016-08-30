
package com.thisisswitch.otrolly.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vehicledata implements Serializable{

    @SerializedName("registrationNo")
    @Expose
    private String registrationNo;
    @SerializedName("vehicletype_id")
    @Expose
    private String vehicletypeId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vehicletype")
    @Expose
    private Vehicletype vehicletype;

    /**
     * 
     * @return
     *     The registrationNo
     */
    public String getRegistrationNo() {
        return registrationNo;
    }

    /**
     * 
     * @param registrationNo
     *     The registrationNo
     */
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    /**
     * 
     * @return
     *     The vehicletypeId
     */
    public String getVehicletypeId() {
        return vehicletypeId;
    }

    /**
     * 
     * @param vehicletypeId
     *     The vehicletype_id
     */
    public void setVehicletypeId(String vehicletypeId) {
        this.vehicletypeId = vehicletypeId;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The vehicletype
     */
    public Vehicletype getVehicletype() {
        return vehicletype;
    }

    /**
     * 
     * @param vehicletype
     *     The vehicletype
     */
    public void setVehicletype(Vehicletype vehicletype) {
        this.vehicletype = vehicletype;
    }

}
