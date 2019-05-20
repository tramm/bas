package com.bookservice.ui.activity.vehicle;

import android.app.Activity;

public interface IVehiclePresenter {

    public void getVehiclesList(Activity activity);

    public void addVehicle(Activity activity, String strBrand, String strModel, String strRegNo, String strYear, String strType);

    public void updateVehicle(Activity activity, String strBrand, String strModel, String strRegNo, String strYear, String id, String strType);

    public void deleteVehicle(Activity activity, String id);

    void getBrands(Activity activity);

    void getModels(Activity activity, String brandId);
}
