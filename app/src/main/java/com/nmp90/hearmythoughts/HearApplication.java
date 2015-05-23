package com.nmp90.hearmythoughts;

import android.app.Application;

import com.nmp90.hearmythoughts.utils.SharedPrefsUtils;

/**
 * Created by nmp on 15-3-7.
 */
public class HearApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefsUtils.getInstance(this);
    }
}
