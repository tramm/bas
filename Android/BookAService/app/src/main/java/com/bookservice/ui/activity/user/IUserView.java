package com.bookservice.ui.activity.user;

import com.bookservice.data.model.user.UserReponse;

public interface IUserView {

    public void onSuccessUserList(UserReponse vehicleResponse);
    public void onSuccessCrudUser(String message);
    public void onError(String message);


}
