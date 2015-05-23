package com.nmp90.hearmythoughts.instances;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by nmp on 15-5-23.
 */
public class VolleyInstance {
    private RequestQueue requestQueue;
    private Context context;
    private static VolleyInstance volleyInstance;
    public static synchronized VolleyInstance getInstance(Context context) {
        if(volleyInstance == null) {
            volleyInstance = new VolleyInstance(context);
        }

        return volleyInstance;
    }

    private VolleyInstance(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
