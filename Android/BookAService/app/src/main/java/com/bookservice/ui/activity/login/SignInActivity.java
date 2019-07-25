package com.bookservice.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bookservice.R;
import com.bookservice.data.model.login.LoginResponse;
import com.bookservice.data.model.verify.VerifyResponse;
import com.bookservice.preference.BsPreference;
import com.bookservice.ui.activity.home.HomeActivity;
import com.bookservice.ui.activity.login.signin.ISignInView;
import com.bookservice.ui.activity.login.signin.SignInPresenter;
import com.bookservice.ui.base.BsBaseActivity;
import com.bookservice.utils.view.BsToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bookservice.constants.Constants.KEY_TOKEN;
import static com.bookservice.constants.Constants.KEY_USER_MOBILE;
import static com.bookservice.constants.Constants.MOBILE;
import static com.bookservice.constants.Constants.SECRET;

public class SignInActivity extends BsBaseActivity implements ISignInView {

    @BindView(R.id.et_mobile_no)
    EditText etMobileNo;
    @BindView(R.id.et_pin)
    EditText etPin;
    @BindView(R.id.ll_pin)
    LinearLayout llPin;
    @BindView(R.id.btn_sign_in)
    Button btnSubmit;

    SignInPresenter signInPresenter;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        showFullScreen();
        activity = SignInActivity.this;
        signInPresenter = new SignInPresenter(this);
    }

    @OnClick(R.id.btn_sign_in) //ButterKnife uses.
    public void launchHomePage() {
      /*  Intent intent = new Intent(this, OtpActivity.class);
        startActivity(intent);*/
        String strMobileNo = etMobileNo.getText().toString();
        if (llPin.getVisibility() == View.VISIBLE) {
            String strPin = etPin.getText().toString();
            signInPresenter.loginAttempt(strMobileNo, strPin, activity);
        } else {
            signInPresenter.verifyNumber(strMobileNo, activity);
        }
    }

    @Override
    public void onSuccess(VerifyResponse verifyResponse) {
        if (verifyResponse.getAuth()) {
            llPin.setVisibility(View.VISIBLE);
            btnSubmit.setText("LOGIN");
        } else {
            onError("Mobile Number not registered, please register");
            String strMobileNo = etMobileNo.getText().toString();
            Intent intent = new Intent(this, OtpActivity.class);
            intent.putExtra(MOBILE, strMobileNo);
            intent.putExtra(SECRET, verifyResponse.getKey());
            startActivity(intent);
        }
    }

    @Override
    public void onSuccessLogin(LoginResponse result) {
        if (result.getAuth()) {
            String strMobileNo = etMobileNo.getText().toString();
            BsPreference.setString(KEY_USER_MOBILE, strMobileNo);
            BsPreference.setString(KEY_TOKEN, result.getToken());
            BsToast.showLong(activity, "Logged in Successfully");
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    @Override
    public void onError(String message) {
        BsToast.showLong(activity, message);
    }

}
