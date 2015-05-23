package com.nmp90.hearmythoughts.api;

import android.content.Context;

import com.nmp90.hearmythoughts.api.requests.user.UserLoginModel;
import com.nmp90.hearmythoughts.api.requests.user.UserLoginRequest;
import com.nmp90.hearmythoughts.instances.VolleyInstance;

/**
 * Created by nmp on 15-5-23.
 */
public class UserAPI {
    public static void loginUser(Context context, String email, String name) {
        VolleyInstance.getInstance(context).addToRequestQueue(new UserLoginRequest(new UserLoginModel(email, name), null));
    }
}
