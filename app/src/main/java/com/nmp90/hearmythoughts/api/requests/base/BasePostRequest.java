package com.nmp90.hearmythoughts.api.requests.base;

import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.nmp90.hearmythoughts.instances.GsonInstance;

import java.io.UnsupportedEncodingException;

/**
 * Created by nmp on 15-5-23.
 */
public abstract class BasePostRequest<T> extends BaseGsonRequest<T> {
    public static final String TAG = BasePostRequest.class.getSimpleName();
    private static final String PROTOCOL_CHARSET = "utf-8";

    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private String body = null;

    public BasePostRequest(String url, Class<T> responseClass, Object body, Response.ErrorListener listener) {
        super(Method.POST, url, responseClass, listener);
        if(body != null) {
            this.body = GsonInstance.getInstance().toJson(body);
        }
    }

    public abstract void deliverResponse(String response);

    protected String getRequestBody(Object object) {
        return GsonInstance.getInstance().toJson(object);
    };

    @Override
    public byte[] getBody() {
        try {
            return body == null ? null : body.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    body, PROTOCOL_CHARSET);
            return null;
        }
    }

    @Override
    public byte[] getPostBody() {
        return getBody();
    }

    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }
}
