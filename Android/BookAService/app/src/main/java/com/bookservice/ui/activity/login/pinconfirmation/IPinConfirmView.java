package com.bookservice.ui.activity.login.pinconfirmation;

import com.bookservice.data.model.login.LoginResponse;

public interface IPinConfirmView {

    public void onSuccess(LoginResponse result);

    public void onError(String result);


}
