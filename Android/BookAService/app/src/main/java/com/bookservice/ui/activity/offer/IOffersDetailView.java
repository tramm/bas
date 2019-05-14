package com.bookservice.ui.activity.offer;

import com.bookservice.data.model.user.UserReponse;
import com.bookservice.data.model.vehicle.VehicleResponse;

public interface IOffersDetailView {

    public void onSuccessBooking();
    public void onSuccessVehicleList(VehicleResponse vehicleResponse);
    public void onSuccessUserList(UserReponse userReponse);

    public void onError(String message);

}
