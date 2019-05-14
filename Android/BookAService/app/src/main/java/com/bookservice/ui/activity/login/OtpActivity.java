package com.bookservice.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.bookservice.R;
import com.bookservice.ui.activity.login.otp.IOtpView;
import com.bookservice.ui.activity.login.otp.OtpPresenter;
import com.bookservice.ui.base.BsBaseActivity;
import com.bookservice.utils.view.BsToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bookservice.constants.Constants.MOBILE;
import static com.bookservice.constants.Constants.SECRET;

public class OtpActivity extends BsBaseActivity implements IOtpView {

    @BindView(R.id.et_otp)
    EditText etOtp;

    Activity activity;
    OtpPresenter otpPresenter;
    String mobile;
    String secret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        activity = OtpActivity.this;
        showFullScreen();
        otpPresenter = new OtpPresenter(this);

        final Intent intent = getIntent();
        if (intent != null) {
            mobile = intent.getStringExtra(MOBILE);
            secret = intent.getStringExtra(SECRET);

        }
    }

    @OnClick(R.id.btn_submit_otp) //ButterKnife uses.
    public void submitOtp() {
        String strOtp = etOtp.getText().toString();
        otpPresenter.verifyOtp(strOtp, activity, secret);
    }

    @Override
    public void onSuccess(String message) {
        Intent intent = new Intent(this, PinConfirmationActivity.class);
        intent.putExtra(MOBILE, mobile);
        startActivity(intent);
    }

    @Override
    public void onError(String message) {
        BsToast.showLong(activity, message);
    }
}
