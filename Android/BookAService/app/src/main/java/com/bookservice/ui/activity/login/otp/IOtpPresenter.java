package com.bookservice.ui.activity.login.otp;

import android.app.Activity;

public interface IOtpPresenter {

    public void verifyOtp(String otp, Activity activity, String secret);

}
