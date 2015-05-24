package com.nmp90.hearmythoughts.api.requests.user;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nmp90.hearmythoughts.constants.actions.UserActions;
import com.nmp90.hearmythoughts.api.requests.base.BasePostRequest;
import com.nmp90.hearmythoughts.events.ServerEvent;
import com.nmp90.hearmythoughts.instances.EventBusInstance;

/**
 * Created by nmp on 15-5-23.
 */
public class UserLoginRequest extends BasePostRequest<String> {
    public static final String TAG = UserLoginRequest.class.getSimpleName();
    public UserLoginRequest(UserLoginModel model, Response.ErrorListener listener) {
        super(BASE_URL + "/users", String.class, model,  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });
    }

    @Override
    public void deliverResponse(String response) {
        EventBusInstance.getInstance().post(new ServerEvent(response, UserActions.LOGIN_USER));
    }
}

