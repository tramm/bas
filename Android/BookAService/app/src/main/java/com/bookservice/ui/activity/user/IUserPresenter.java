package com.bookservice.ui.activity.user;

import android.app.Activity;

public interface IUserPresenter {

    public void getUsersList(Activity activity);

    public void addUser(Activity activity, String strName, String strMobileNo, String strEmail, String strTag);

    public void updateUser(Activity activity, String StrName, String strMobileNo, String strEmail, String strTag, String id);

    public void deleteUser(Activity activity, String id);

}
