package com.bookservice.ui.activity.vehicle;

import android.app.Activity;
import android.text.TextUtils;

import com.bookservice.data.model.brands.BrandsResponse;
import com.bookservice.data.model.brands.ModelResponse;
import com.bookservice.data.model.vehicle.VehicleRequest;
import com.bookservice.data.model.vehicle.VehicleResponse;
import com.bookservice.network.AsyncTaskConnection;
import com.bookservice.network.IConnectionListener;
import com.bookservice.network.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bookservice.constants.Constants.BRAND_ID;
import static com.bookservice.constants.Constants.GET;
import static com.bookservice.constants.Constants.POST;
import static com.bookservice.constants.Constants.VEHICLE_ID;
import static com.bookservice.constants.ConstantsUrl.ADD_VEHICLES;
import static com.bookservice.constants.ConstantsUrl.BRANDS;
import static com.bookservice.constants.ConstantsUrl.DELETE_VEHICLES;
import static com.bookservice.constants.ConstantsUrl.MODELS;
import static com.bookservice.constants.ConstantsUrl.UPDATE_VEHICLES;
import static com.bookservice.constants.ConstantsUrl.VEHICLES;

public class VehiclePresenter implements IVehiclePresenter {

    IVehicleView view;

    public VehiclePresenter(IVehicleView iVehicleView) {
        view = iVehicleView;
    }

    @Override
    public void getVehiclesList(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(VEHICLES, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    VehicleResponse vehicleResponse = gson.fromJson(jsonObject.toString(), VehicleResponse.class);
                    view.onSuccessVehicleList(vehicleResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }

            @Override
            public void onNetworkFail(String message) {
                view.onError(message);
            }
        });
        asyncTaskConnection.execute();
    }

    @Override
    public void addVehicle(Activity activity, String strBrand, String strModel, String strRegNo, String strYear, String strType) {

        if (TextUtils.isEmpty(strBrand)) {
            view.onError("Please enter brand from dropdown");
        } else if (TextUtils.isEmpty(strModel)) {
            view.onError("Please enter model from dropdown");
        } else if (TextUtils.isEmpty(strRegNo)) {
            view.onError("Please enter registration number ");
        } else if (TextUtils.isEmpty(strYear)) {
            view.onError("Please enter year ");
        } else {

            VehicleRequest vehicleRequest = new VehicleRequest();
            vehicleRequest.setManufacturer(strBrand);
            vehicleRequest.setModel(strModel);
            vehicleRequest.setRegistrationNumber(strRegNo);
            vehicleRequest.setYear(strYear);
            vehicleRequest.setTag(strType);

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(vehicleRequest);

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(ADD_VEHICLES, activity,
                    json, POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    view.onSuccessCrudVehicle("Added successfully");
                }

                @Override
                public void onFail(Result result) {
                    view.onError("Something went wrong, Please try after sometime");
                }

                @Override
                public void onNetworkFail(String message) {
                    view.onError(message);
                }
            });
            asyncTaskConnection.execute();
        }
    }

    @Override
    public void updateVehicle(Activity activity, String strBrand, String strModel,
                              String strRegNo, String strYear, String id, String strType) {

        if (TextUtils.isEmpty(strBrand)) {
            view.onError("Please enter brand from dropdown ");
        } else if (TextUtils.isEmpty(strModel)) {
            view.onError("Please enter model from dropdown");
        } else if (TextUtils.isEmpty(strRegNo)) {
            view.onError("Please enter registration number ");
        } else if (TextUtils.isEmpty(strYear)) {
            view.onError("Please enter year ");
        } else {

            VehicleRequest vehicleRequest = new VehicleRequest();
            vehicleRequest.setManufacturer(strBrand);
            vehicleRequest.setModel(strModel);
            vehicleRequest.setRegistrationNumber(strRegNo);
            vehicleRequest.setYear(strYear);
            vehicleRequest.setTag(strType);
            vehicleRequest.set_id(id);

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(vehicleRequest);

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(UPDATE_VEHICLES, activity,
                    json, POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    view.onSuccessCrudVehicle("Updated successfully");
                }

                @Override
                public void onFail(Result result) {
                    view.onError("Something went wrong, Please try after sometime");
                }

                @Override
                public void onNetworkFail(String message) {
                    view.onError(message);
                }
            });
            asyncTaskConnection.execute();
        }
    }

    @Override
    public void deleteVehicle(Activity activity, String id) {
        String json = "";

        org.json.JSONArray jsonArray = new org.json.JSONArray();
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put(VEHICLE_ID, id);
            jsonArray.put(postDataParams);
            json = jsonArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(DELETE_VEHICLES, activity, json, POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                view.onSuccessCrudVehicle("Deleted successfully");

            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }

            @Override
            public void onNetworkFail(String message) {
                view.onError(message);
            }
        });
        asyncTaskConnection.execute();
    }

    @Override
    public void getBrands(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(BRANDS, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    BrandsResponse brandsResponse = gson.fromJson(jsonObject.toString(), BrandsResponse.class);
                    view.onSuccessBrandsList(brandsResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }

            @Override
            public void onNetworkFail(String message) {
                view.onError(message);
            }
        });
        asyncTaskConnection.execute();
    }

    @Override
    public void getModels(Activity activity, String brandId) {
        String json = "";
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put(BRAND_ID, brandId);
            json = postDataParams.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(MODELS, activity, json, POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    ModelResponse modelResponse = gson.fromJson(jsonObject.toString(), ModelResponse.class);
                    view.onSuccessModelList(modelResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }

            @Override
            public void onNetworkFail(String message) {
                view.onError(message);
            }
        });
        asyncTaskConnection.execute();
    }


}
