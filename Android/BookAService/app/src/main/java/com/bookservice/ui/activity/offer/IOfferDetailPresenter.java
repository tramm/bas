package com.bookservice.ui.activity.offer;

import android.app.Activity;

public interface IOfferDetailPresenter {

    public void bookOffer(Activity activity, String offerId, String userId, String vehicleId, String dateOfService);

    public void getVehiclesList(Activity activity);

    public void getUsersList(Activity activity);
}
