package com.nmp90.hearmythoughts.providers;

import android.text.TextUtils;

import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.instances.GsonInstance;
import com.nmp90.hearmythoughts.models.User;
import com.nmp90.hearmythoughts.utils.SharedPrefsUtils;

/**
 * Created by nmp on 15-3-7.
 */
public class AuthProvider {
    public static boolean isUserLoggedIn() {
        return !TextUtils.isEmpty(SharedPrefsUtils.getPreference(Constants.KEY_USER, ""));
    }

    public static void login(User user) {
        SharedPrefsUtils.setPreference(Constants.KEY_USER, user.toString());
    }

    public static User getUser() {
        return GsonInstance.getInstance().fromJson(SharedPrefsUtils.getPreference(Constants.KEY_USER, ""), User.class);
    }

    public static void logout() {
        if(isUserLoggedIn()) {
            SharedPrefsUtils.setPreference(Constants.KEY_USER, "");
        }
    }
}
