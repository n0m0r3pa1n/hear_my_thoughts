package com.nmp90.hearmythoughts.api.requests.session;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nmp90.hearmythoughts.api.requests.base.auth.BaseAuthGetRequest;
import com.nmp90.hearmythoughts.constants.actions.SessionActions;
import com.nmp90.hearmythoughts.events.ServerEvent;
import com.nmp90.hearmythoughts.instances.EventBusInstance;

/**Ny3fvvcE
 * Created by nmp on 15-5-24.
 */
public class JoinSessionRequest extends BaseAuthGetRequest<String> {
    public JoinSessionRequest(String shortId, Context context) {
        super(BASE_URL + "/sessions/" + shortId + "/actions/join", context, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) {
                    Log.d(TAG, new String(error.networkResponse.data));
                    EventBusInstance.getInstance().post(new ServerEvent("", SessionActions.JOIN_SESSION, false, new String(error.networkResponse.data)));
                }
            }
        });
        Log.d(TAG, "JoinSessionRequest " + shortId);
    }

    @Override
    public void deliverResponse(String response) {
        EventBusInstance.getInstance().post(new ServerEvent(response, SessionActions.JOIN_SESSION));
    }
}
