package com.bookservice.ui.activity.user;

import android.app.Activity;
import android.text.TextUtils;

import com.bookservice.data.model.user.User;
import com.bookservice.data.model.user.UserReponse;
import com.bookservice.network.AsyncTaskConnection;
import com.bookservice.network.IConnectionListener;
import com.bookservice.network.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bookservice.constants.Constants.GET;
import static com.bookservice.constants.Constants.POST;
import static com.bookservice.constants.ConstantsUrl.ADD_USERS;
import static com.bookservice.constants.ConstantsUrl.DELETE_USERS;
import static com.bookservice.constants.ConstantsUrl.UPDATE_USERS;
import static com.bookservice.constants.ConstantsUrl.USERS;

public class UserPresenter implements IUserPresenter {

    IUserView view;

    public UserPresenter(IUserView iUserView) {
        view = iUserView;
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

    @Override
    public void addUser(Activity activity, String strName, String strMobileNo, String strEmail,
                        String strTag) {
        if (TextUtils.isEmpty(strName)) {
            view.onError("Name cannot be empty");
        } else if (TextUtils.isEmpty(strMobileNo)) {
            view.onError("Mobile number field cannot be empty");
        } else if (strMobileNo.length() != 10) {
            view.onError("Please enter a valid 10 digit mobile number");
        } else if (TextUtils.isEmpty(strEmail)) {
            view.onError("Email field cannot be empty");
        } else if (TextUtils.isEmpty(strTag)) {
            view.onError("Please select a tag, self or others");
        } else {
            User user = new User();
            user.setEmail(strEmail);
            user.setMobile(strMobileNo);
            user.setName(strName);
            user.setTag(strTag);

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(user);

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(ADD_USERS, activity, json,
                    POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    view.onSuccessCrudUser("Added successfully");
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
    public void updateUser(Activity activity, String strName, String strMobileNo, String strEmail, String strTag, String id) {
        if (TextUtils.isEmpty(strName)) {
            view.onError("Name cannot be empty");
        } else if (TextUtils.isEmpty(strMobileNo)) {
            view.onError("Mobile number field cannot be empty");
        } else if (strMobileNo.length() != 10) {
            view.onError("Please enter a valid 10 digit mobile number");
        } else if (TextUtils.isEmpty(strEmail)) {
            view.onError("Email field cannot be empty");
        } else if (TextUtils.isEmpty(strTag)) {
            view.onError("Please select a tag, self or others");
        } else {
            User user = new User();
            user.setEmail(strEmail);
            user.setMobile(strMobileNo);
            user.setName(strName);
            user.setTag(strTag);
            user.setIdRequest(id);

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(user);

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(UPDATE_USERS, activity, json,
                    POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    view.onSuccessCrudUser("Updated successfully");
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
    public void deleteUser(Activity activity, String id) {
        User user = new User();
        user.setIdRequest(id);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(user);

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(DELETE_USERS, activity, json,
                POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                view.onSuccessCrudUser("Deleted successfully");
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
