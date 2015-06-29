package com.nmp90.hearmythoughts.providers;

import android.content.Context;
import android.text.TextUtils;

import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.instances.GsonInstance;
import com.nmp90.hearmythoughts.ui.models.Role;
import com.nmp90.hearmythoughts.api.models.User;
import com.nmp90.hearmythoughts.utils.SharedPrefsUtils;

import java.util.Random;

/**
 * Created by nmp on 15-3-7.
 */
public class AuthProvider {
    private Context context;
    private static AuthProvider authProvider;
    private AuthProvider(Context context) {
        this.context = context;
    }

    public static AuthProvider getInstance(Context context) {
        if(authProvider == null) {
            authProvider = new AuthProvider(context);
        }

        return authProvider;
    }

    public boolean isUserLoggedIn() {
        return !TextUtils.isEmpty(SharedPrefsUtils.getInstance(context).getPreference(Constants.KEY_USER, ""));
    }

    public void login(User user) {
        SharedPrefsUtils.getInstance(context).setPreference(Constants.KEY_USER, user.toString());
    }

    public User getUser() {
        return GsonInstance.getInstance().fromJson(SharedPrefsUtils.getInstance(context).getPreference(Constants.KEY_USER, ""), User.class);
    }

    public void logout() {
        if(isUserLoggedIn()) {
            SharedPrefsUtils.getInstance(context).setPreference(Constants.KEY_USER, "");
        }
    }
}
