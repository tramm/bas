package com.bookservice.ui.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bookservice.R;
import com.bookservice.preference.BsPreference;
import com.bookservice.ui.activity.home.HomeActivity;
import com.bookservice.ui.activity.login.SignInActivity;
import com.bookservice.ui.base.BsBaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bookservice.constants.Constants.KEY_TOKEN;


public class SplashActivity extends BsBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        showFullScreen();

    }

    @OnClick(R.id.get_started) //ButterKnife uses.
    public void getStarted() {
        if (TextUtils.isEmpty(BsPreference.getString(KEY_TOKEN))) {
            Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }


}
