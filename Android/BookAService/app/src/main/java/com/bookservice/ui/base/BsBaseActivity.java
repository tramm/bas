package com.bookservice.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.dialog.DefaultAlertDialog;
import com.bookservice.dialog.TwoButtonAlertDialogModel;
import com.bookservice.preference.BsPreference;
import com.bookservice.ui.activity.login.SignInActivity;
import com.bookservice.utils.view.BsToast;
import com.bookservice.utils.view.CustomTypefaceSpan;

import static com.bookservice.constants.Constants.KEY_TOKEN;


public abstract class BsBaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BsBaseActivity.this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.getEventBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BaseApplication.getEventBus().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showBackButton() {
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void showTile(String title) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textTitle = toolbar.findViewById(R.id.toolbar_title);
        textTitle.setText(title);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);

        if (mActionBar != null && !TextUtils.isEmpty(title)) {
            mActionBar.setDisplayShowTitleEnabled(false);
            textTitle.setText(title);
        }
    }

    public void showWhiteStatusBar(int color) {
        getWindow().setStatusBarColor(color);
    }

    public void showFullScreen() {

        getWindow().setNavigationBarColor(Color.BLACK);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    public void showLogo() {
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setLogo(R.drawable.ic_logo);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/avenirLTStd-Light.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void logOutDialog(final Activity activity) {
        String confirm = "Confirm";
        String logoutMessage = "Are you sure you want to Logout?";
        String yes = "OK";
        String cancel = "Cancel";
        TwoButtonAlertDialogModel dialogModel = new TwoButtonAlertDialogModel();
        dialogModel.setTitle(confirm);
        dialogModel.setMessage(logoutMessage);
        dialogModel.setPositiveButtonText(yes);
        dialogModel.setSecondButtonType(AlertDialog.BUTTON_NEGATIVE);
        dialogModel.setSecondButtonText(cancel);
        dialogModel.setCancellable(false);

        AlertDialog logOutDialog = DefaultAlertDialog.getTwoButtonDialog(this, dialogModel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        BsToast.showLong(activity, "Logged out successfully");
                        BsPreference.setString(KEY_TOKEN,"");
                        Intent intent = new Intent(activity, SignInActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
            }
        });
        logOutDialog.show();
    }
}


