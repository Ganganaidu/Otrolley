
package com.thisisswitch.otrolly.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;
    @SerializedName("newUser")
    @Expose
    private Boolean newUser;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("_savedAddresses")
    @Expose
    private List<Object> savedAddresses = new ArrayList<Object>();
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;

    @SerializedName("user")
    @Expose
    private UserProfile user;
    @SerializedName("vehicledata")
    @Expose
    private Vehicledata vehicledata;

    /**
     * @return The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return The img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img The img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The deviceToken
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * @param deviceToken The deviceToken
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    /**
     * @return The newUser
     */
    public Boolean getNewUser() {
        return newUser;
    }

    /**
     * @param newUser The newUser
     */
    public void setNewUser(Boolean newUser) {
        this.newUser = newUser;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The savedAddresses
     */
    public List<Object> getSavedAddresses() {
        return savedAddresses;
    }

    /**
     * @param savedAddresses The _savedAddresses
     */
    public void setSavedAddresses(List<Object> savedAddresses) {
        this.savedAddresses = savedAddresses;
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
     * @return The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The user
     */
    public UserProfile getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(UserProfile user) {
        this.user = user;
    }

    /**
     * @return The vehicledata
     */
    public Vehicledata getVehicledata() {
        return vehicledata;
    }

    /**
     * @param vehicledata The vehicledata
     */
    public void setVehicledata(Vehicledata vehicledata) {
        this.vehicledata = vehicledata;
    }

}
