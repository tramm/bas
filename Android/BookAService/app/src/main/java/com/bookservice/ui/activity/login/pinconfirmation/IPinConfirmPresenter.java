package com.bookservice.ui.activity.login.pinconfirmation;

import android.app.Activity;

public interface IPinConfirmPresenter {

    public void confirmPin(String pin, String confirmPin, Activity activity, String mobile);

}
