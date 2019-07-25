
package com.bookservice.data.model.vehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VehicleResponse implements Serializable {

    @SerializedName("userActiveVehicles")
    @Expose
    private List<UserVehicle> userVehicles = null;

    public List<UserVehicle> getUserVehicles() {
        return userVehicles;
    }

    public void setUserVehicles(List<UserVehicle> userVehicles) {
        this.userVehicles = userVehicles;
    }

}
