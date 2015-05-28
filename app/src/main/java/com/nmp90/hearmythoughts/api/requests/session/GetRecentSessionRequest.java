package com.nmp90.hearmythoughts.api.requests.session;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.nmp90.hearmythoughts.api.requests.base.auth.BaseAuthGetRequest;
import com.nmp90.hearmythoughts.constants.actions.SessionActions;
import com.nmp90.hearmythoughts.events.ServerEvent;
import com.nmp90.hearmythoughts.instances.EventBusInstance;

/**
 * Created by nmp on 15-5-28.
 */
public class GetRecentSessionRequest extends BaseAuthGetRequest<String> {
    public static final String TAG = GetRecentSessionRequest.class.getSimpleName();

    public GetRecentSessionRequest(Context context, Response.ErrorListener listener) {
        super(BASE_URL + "/user/sessions", context, listener);
    }

    @Override
    public void deliverResponse(String response) {
        Log.d(TAG, response);
        EventBusInstance.getInstance().post(new ServerEvent(response, SessionActions.RECENT_SESSIONS));
    }
}
