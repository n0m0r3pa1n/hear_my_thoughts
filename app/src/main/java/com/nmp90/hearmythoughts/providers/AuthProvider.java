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
        //User teacher = new User("5568baba6dd53c74232b3080", "Georgi Mirchev" + new Random().nextInt(), "", Role.TEACHER, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1NTY4YmFlMTZkZDUzYzc0MjMyYjMwODEiLCJpYXQiOjE0MzI5MjY5NDV9.L8zWzpoNAmC-ygcWJdvBWNglzssRgv4Y9Y3qoJyr2yQ");
        User student = new User("5568bae16dd53c74232b3081", "Georgi Mirchev" + new Random().nextInt(), "", Role.STUDENT, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1NTY4YmFiYTZkZDUzYzc0MjMyYjMwODAiLCJpYXQiOjE0MzI5MjY5MDZ9.DEA2YVbC2hH4QABdX4r0ruuCwhnRvjXS1evTD7YF6kQ");
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
