package com.nmp90.hearmythoughts.api.requests.base;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.instances.GsonInstance;

/**
 * Created by nmp on 15-5-23.
 */
public abstract class BaseGsonRequest<T> extends Request<T> {
    public static final String TAG = BaseGsonRequest.class.getSimpleName();
    public static String BASE_URL = Constants.BASE_URL;
    private NetworkResponse networkResponse;

    private Class<T> clazz;

    public BaseGsonRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
        clazz = getParsedAppClass();
    }

    public BaseGsonRequest(int method, String url, Class<T> clazz, Response.ErrorListener listener) {
        super(method, url, listener);
        this.clazz = clazz;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        networkResponse = response;
        try {
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(GsonInstance.getInstance().fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch(Exception e) {
            Log.d(TAG, "parseNetworkResponse " + e);
            return null;
        }
    }

    protected NetworkResponse getRawResponse() {
        return networkResponse;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    public Class<T> getParsedAppClass() {
        return clazz;
    };

    @Override
    public abstract void deliverResponse(T response);
}
