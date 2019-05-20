package com.bookservice.ui.activity.login.pinconfirmation;

import android.app.Activity;
import android.text.TextUtils;

import com.bookservice.data.model.login.LoginResponse;
import com.bookservice.network.AsyncTaskConnection;
import com.bookservice.network.IConnectionListener;
import com.bookservice.network.Result;
import com.bookservice.utils.ConnectionUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bookservice.constants.Constants.MOBILE;
import static com.bookservice.constants.Constants.PIN;
import static com.bookservice.constants.Constants.POST;
import static com.bookservice.constants.Constants.RE_PIN;
import static com.bookservice.constants.ConstantsUrl.CONFIRM_PIN;


public class PinConfirmPresenter implements IPinConfirmPresenter {

    IPinConfirmView view;

    public PinConfirmPresenter(IPinConfirmView iPinConfirmView) {
        view = iPinConfirmView;
    }

    @Override
    public void confirmPin(String pin, String confirmPin, Activity activity, String mobile) {
        if (TextUtils.isEmpty(pin)) {
            view.onError("Pin field cannot be empty");
        } else if (TextUtils.isEmpty(confirmPin)) {
            view.onError("Confirm pin field cannot be empty");
        } else if (pin.length() != 4) {
            view.onError("Pin length should be 4 digits");
        } else if (confirmPin.length() != 4) {
            view.onError("Confirm Pin length should be 4 digits");
        } else if (!confirmPin.equalsIgnoreCase(pin)) {
            view.onError("Pin and confirm pin does not match");
        } else {
            if (ConnectionUtils.isConnectedNetwork(activity)) {
                String json = "";
                JSONObject postDataParams = new JSONObject();
                try {
                    postDataParams.put(PIN, pin);
                    postDataParams.put(RE_PIN, confirmPin);
                    postDataParams.put(MOBILE, mobile);
                    json = postDataParams.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(CONFIRM_PIN, activity, json, POST, new IConnectionListener() {
                    @Override
                    public void onSuccess(Result result) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(result.getResponse());
                            Gson gson = new Gson();
                            LoginResponse loginResponse = gson.fromJson(jsonObject.toString(), LoginResponse.class);
                            view.onSuccess(loginResponse);
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

                    }
                });
                asyncTaskConnection.execute();

            } else {
                view.onError("No Active Network please connect to internet.");
            }
        }
    }
}
