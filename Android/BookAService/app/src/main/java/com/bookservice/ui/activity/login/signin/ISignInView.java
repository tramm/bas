package com.bookservice.ui.activity.login.signin;

import com.bookservice.data.model.login.LoginResponse;
import com.bookservice.data.model.verify.VerifyResponse;

public interface ISignInView {

    public void onSuccess(VerifyResponse result);
    public void onSuccessLogin(LoginResponse result);



    public void onError(String result);

}
