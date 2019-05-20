package com.bookservice.utils.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bookservice.R;


public class BsToast {

    static String TAG = "BsToast";

    public static void showLong(Context context, @Nullable CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, @StringRes int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void showShort(Context context, @Nullable CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void showShort(Context context, @StringRes int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    /**
     * Default
     */
    private static void show(Context context, @Nullable CharSequence text, int duration) {
        if (context != null && !TextUtils.isEmpty(text)) {
            try {
                // Toast.makeText(context.getApplicationContext(), text, duration).show();

                Toast toast = Toast.makeText(context.getApplicationContext(), text, duration);
                View view = toast.getView();

                view.getBackground().setColorFilter(context.getResources().getColor(R.color.drab), PorterDuff.Mode.SRC_IN);

                TextView textView = view.findViewById(android.R.id.message);
                textView.setTextColor(context.getResources().getColor(R.color.snow));

                toast.show();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
    }
}
