
package com.thisisswitch.otrolly.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Dimension {

    @SerializedName("dimensions")
    @Expose
    private String dimensions;

    /**
     * @return The dimensions
     */
    public String getDimensions() {
        return dimensions;
    }

    /**
     * @param dimensions The dimensions
     */
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
}
