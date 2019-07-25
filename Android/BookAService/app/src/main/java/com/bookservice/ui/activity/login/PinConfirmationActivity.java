package com.bookservice.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.data.model.login.LoginResponse;
import com.bookservice.preference.BsPreference;
import com.bookservice.ui.activity.home.HomeActivity;
import com.bookservice.ui.activity.login.pinconfirmation.IPinConfirmView;
import com.bookservice.ui.activity.login.pinconfirmation.PinConfirmPresenter;
import com.bookservice.ui.base.BsBaseActivity;
import com.bookservice.utils.view.BsToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bookservice.constants.Constants.KEY_TOKEN;
import static com.bookservice.constants.Constants.KEY_USER_MOBILE;
import static com.bookservice.constants.Constants.MOBILE;

public class PinConfirmationActivity extends BsBaseActivity implements IPinConfirmView {

    @BindView(R.id.txt_terms_conditions)
    TextView txtTermsConditions;
    @BindView(R.id.et_pin)
    EditText etPin;
    @BindView(R.id.et_confirm_pin)
    EditText etConfirmPin;
    PinConfirmPresenter pinConfirmPresenter;
    Activity activity;
    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_confirmation);
        ButterKnife.bind(this);
        pinConfirmPresenter = new PinConfirmPresenter(this);

        activity = PinConfirmationActivity.this;
        showFullScreen();
        txtTermsConditions.setText(Html.fromHtml("<font color=#ffffff>By creating an account, your agree to our</font> <font color=#D97D54>Terms of service</font>  <font color=#ffffff> and</font> <font color=#D97D54>Privacy Policy</font>"));

        final Intent intent = getIntent();
        if (intent != null) {
            mobile = intent.getStringExtra(MOBILE);
        }

    }

    @OnClick(R.id.btn_pin_submit) //ButterKnife uses.
    public void submitPin() {
        String strPin = etPin.getText().toString();
        String strConfirmPin = etConfirmPin.getText().toString();
        pinConfirmPresenter.confirmPin(strPin, strConfirmPin, activity, mobile);
    }

    @Override
    public void onSuccess(LoginResponse result) {
        if (result.getAuth()) {
            BsPreference.setString(KEY_USER_MOBILE, mobile);
            BsPreference.setString(KEY_TOKEN, result.getToken());
            BsToast.showLong(activity, "Registered Successfully");
            Intent intent = new Intent(PinConfirmationActivity.this, HomeActivity.class);
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
