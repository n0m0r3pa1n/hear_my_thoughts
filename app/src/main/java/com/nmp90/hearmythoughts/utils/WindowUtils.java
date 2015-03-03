package com.nmp90.hearmythoughts.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.WindowManager;

/**
 * Created by nmp on 15-3-3.
 */
public class WindowUtils {
    public static void setupLollipopScreen(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity)ctx).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }
}
