package com.bookservice.ui.activity.vehicle;

import com.bookservice.data.model.brands.BrandsResponse;
import com.bookservice.data.model.brands.ModelResponse;
import com.bookservice.data.model.vehicle.VehicleResponse;

public interface IVehicleView {

    public void onSuccessVehicleList(VehicleResponse vehicleResponse);
    public void onSuccessBrandsList(BrandsResponse brandsResponse);
    public void onSuccessModelList(ModelResponse list);
    public void onSuccessCrudVehicle(String message);
    public void onError(String message);


}
