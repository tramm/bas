package com.bookservice.utils;

import android.app.Activity;
import android.content.Intent;

public class Utils {
    public static final String DATE_FORMATTER_FOR_SYNC_DATA = "yyyy-MM-dd";
    public static final String DATE_FORMATTER_WITH_TIME = "yyyy-MM-dd HH:mm";


    public static void share(Activity activity, String name, String description, String serviceCenter) {
        String shareBody = "Checkout Offers from Book a Service App" + " , " + name + " , " + description + " from " + serviceCenter;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share Offers from Book a Service App"));
    }

    /*public static void hideIME(final Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }

    public static long getTimeInMilliSecs() {
        return System.currentTimeMillis();
    }

    public static String getTimeInUTC() {
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        calendar.setTime(myDate);
        calendar.add(Calendar.MINUTE, -2);
        Date time = calendar.getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat(DATE_FORMATTER_FOR_SYNC_DATA);
        outputFmt.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        String dateAsString = outputFmt.format(time);

        return dateAsString;
    }

    public static String getTime() {
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        calendar.setTime(myDate);
        calendar.add(Calendar.MINUTE, -1);
        Date time = calendar.getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat(DATE_FORMATTER_WITH_TIME);
        outputFmt.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        String dateAsString = outputFmt.format(time);

        return dateAsString;
    }*/
}
