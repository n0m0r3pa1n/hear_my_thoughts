package com.nmp90.hearmythoughts.api.requests.base.auth;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.nmp90.hearmythoughts.api.requests.base.BasePostRequest;
import com.nmp90.hearmythoughts.providers.AuthProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nmp on 15-5-24.
 */
public abstract class BaseAuthPostRequest<T> extends BasePostRequest<T> {

    private Context context;

    public BaseAuthPostRequest(String url, Object body, Context context, Response.ErrorListener listener) {
        super(url, body, listener);
        this.context = context;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Authorization", AuthProvider.getInstance(context).getUser().getToken());
        params.put("Accept", "application/json");
        params.put("Content-Type", "application/json");
        return params;
    }
}
