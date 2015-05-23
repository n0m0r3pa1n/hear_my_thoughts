package com.nmp90.hearmythoughts.api.requests.base;

import com.android.volley.Response;

/**
 * Created by nmp on 15-5-23.
 */
public abstract class BaseGetRequest<T> extends BaseGsonRequest<T> {

    public BaseGetRequest(String url, Response.ErrorListener listener) {
        super(Method.GET, url, listener);
    }


    public abstract void deliverResponse(String response);
}
