package com.nmp90.hearmythoughts.api;

import android.content.Context;

import com.nmp90.hearmythoughts.api.requests.session.CreateSessionRequest;
import com.nmp90.hearmythoughts.api.requests.session.JoinSessionRequest;
import com.nmp90.hearmythoughts.instances.VolleyInstance;
import com.nmp90.hearmythoughts.providers.AuthProvider;

import java.util.HashMap;

/**
 * Created by nmp on 15-5-24.
 */
public class SessionsAPI {
    public static void createSession(Context context, String name) {
        String token = AuthProvider.getInstance(context).getUser().getToken();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", name);

        VolleyInstance.getInstance(context).addToRequestQueue(new CreateSessionRequest(map, context));
    }

    public static void joinSession(Context context, String shortCode) {
        VolleyInstance.getInstance(context).addToRequestQueue(new JoinSessionRequest(shortCode, context));
    }
}
