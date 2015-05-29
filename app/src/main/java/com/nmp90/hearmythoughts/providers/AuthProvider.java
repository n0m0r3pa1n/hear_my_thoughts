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
        //TODO: Remove
        //User teacher = new User("123"+ new Random().nextInt(), "Georgi Mirchev" + new Random().nextInt(), "", Role.TEACHER, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1NTY4OTUyZWM4NDBlNmUyNTUwMjlkYWEiLCJpYXQiOjE0MzI5MTcyOTR9.RFp5Hyfuq2ftbjM-p7YmmIoci9T-tVL5RpY9IlwaR-w");
        User student = new User("123"+ new Random().nextInt(), "Georgi Mirchev" + new Random().nextInt(), "", Role.STUDENT, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1NTYxNzdjOGQwMjVlOTA5NjhhMzA2OTMiLCJpYXQiOjE0MzI0NTEwMTZ9.WWVLviT23dWLCGMbB4aRkvxKBG7fKTNqtZw1RKoiA2M");
        if(TextUtils.isEmpty(SharedPrefsUtils.getInstance(context).getPreference(Constants.KEY_USER, ""))) {
            login(student);
        }
        return GsonInstance.getInstance().fromJson(SharedPrefsUtils.getInstance(context).getPreference(Constants.KEY_USER, ""), User.class);
    }

    public void logout() {
        if(isUserLoggedIn()) {
            SharedPrefsUtils.getInstance(context).setPreference(Constants.KEY_USER, "");
        }
    }
}
