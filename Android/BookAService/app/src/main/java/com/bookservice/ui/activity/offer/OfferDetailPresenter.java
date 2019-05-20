package com.bookservice.ui.activity.offer;

import android.app.Activity;
import android.text.TextUtils;

import com.bookservice.data.model.user.UserReponse;
import com.bookservice.data.model.vehicle.VehicleResponse;
import com.bookservice.network.AsyncTaskConnection;
import com.bookservice.network.IConnectionListener;
import com.bookservice.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bookservice.constants.Constants.DATE_OF_SERVICE;
import static com.bookservice.constants.Constants.GET;
import static com.bookservice.constants.Constants.OFFERS_ID;
import static com.bookservice.constants.Constants.PARTNER_ID;
import static com.bookservice.constants.Constants.POST;
import static com.bookservice.constants.Constants.VEHICLE_ID;
import static com.bookservice.constants.ConstantsUrl.BOOK_OFFER;
import static com.bookservice.constants.ConstantsUrl.USERS;
import static com.bookservice.constants.ConstantsUrl.VEHICLES;

public class OfferDetailPresenter implements IOfferDetailPresenter {

    IOffersDetailView view;

    public OfferDetailPresenter(IOffersDetailView offersDetailView) {
        view = offersDetailView;
    }

    @Override
    public void bookOffer(Activity activity, String offerId, String userId, String vehicleId, String dateOfService) {
        if (TextUtils.isEmpty(userId)) {
            view.onError("Please select customer detail");
        } else if (TextUtils.isEmpty(vehicleId)) {
            view.onError("Please select vehicle detail");
        } else if (dateOfService.equalsIgnoreCase("Select Date") || TextUtils.isEmpty(dateOfService)) {
            view.onError("Please select date of service");
        } else {
            String json = "";
            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put(VEHICLE_ID, vehicleId);
                postDataParams.put(PARTNER_ID, userId);
                postDataParams.put(OFFERS_ID, offerId);
                postDataParams.put(DATE_OF_SERVICE, dateOfService);

                json = postDataParams.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(BOOK_OFFER, activity, json,
                    POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    view.onSuccessBooking();
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
    public void getUsersList(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(USERS, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    UserReponse userReponse = gson.fromJson(jsonObject.toString(), UserReponse.class);
                    view.onSuccessUserList(userReponse);
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
