package com.nmp90.hearmythoughts.providers;

import android.text.TextUtils;

import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.utils.SharedPrefsUtils;

/**
 * Created by nmp on 15-3-7.
 */
public class AuthProvider {
    public static boolean isUserLoggedIn() {
        return !TextUtils.isEmpty(SharedPrefsUtils.getPreference(Constants.KEY_USER_EMAIL, ""));
    }
}
