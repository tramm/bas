
package com.bookservice.data.model.vehicle;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleResponse {

    @SerializedName("userVehicles")
    @Expose
    private List<UserVehicle> userVehicles = null;

    public List<UserVehicle> getUserVehicles() {
        return userVehicles;
    }

    public void setUserVehicles(List<UserVehicle> userVehicles) {
        this.userVehicles = userVehicles;
    }

}
