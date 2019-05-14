package com.bookservice.ui.activity.login.otp;

import android.app.Activity;
import android.text.TextUtils;

import com.bookservice.network.AsyncTaskConnection;
import com.bookservice.network.IConnectionListener;
import com.bookservice.network.Result;
import com.bookservice.utils.ConnectionUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bookservice.constants.Constants.OTP_NUMBER;
import static com.bookservice.constants.Constants.POST;
import static com.bookservice.constants.Constants.SECRET;
import static com.bookservice.constants.ConstantsUrl.OTP_VERIFY;


public class OtpPresenter implements IOtpPresenter {

    IOtpView view;

    public OtpPresenter(IOtpView iOtpView) {
        view = iOtpView;
    }


    @Override
    public void verifyOtp(String otp, Activity activity, String secret) {
        if (TextUtils.isEmpty(otp)) {
            view.onError("Otp field cannot be empty");
        } else {
            if (ConnectionUtils.isConnectedNetwork(activity)) {
                String json = "";
                JSONObject postDataParams = new JSONObject();
                try {
                    postDataParams.put(OTP_NUMBER, otp);
                    postDataParams.put(SECRET, secret);
                    json = postDataParams.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(OTP_VERIFY, activity, json, POST, new IConnectionListener() {
                    @Override
                    public void onSuccess(Result result) {
                        view.onSuccess(result.getResponse());
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

            } else {
                view.onError("No Active Network please connect to internet.");
            }
        }
    }
}
