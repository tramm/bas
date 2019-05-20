package com.bookservice.ui.base;

import android.app.Application;

import com.bookservice.R;
import com.bookservice.preference.BsPreference;
import com.splunk.mint.Mint;
import com.squareup.otto.Bus;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class BaseApplication extends Application {

    private static Bus sEventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        BsPreference.startWith(getApplicationContext());
        initCalligraphy();
        Mint.initAndStartSession(this, "ed5c6c0b");
    }

    private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/avenirLTStd-Light.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
    public static Bus getEventBus() {
        if (sEventBus == null) {
            synchronized (Bus.class) {
                if (sEventBus == null) {
                    sEventBus = new Bus();
                }
            }
        }
        return sEventBus;
    }

    public static void setEventBus(Bus mockBus) {
        sEventBus = mockBus;
    }
}
