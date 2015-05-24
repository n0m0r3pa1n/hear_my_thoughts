package com.nmp90.hearmythoughts.api.requests.session;

import android.content.Context;

import com.nmp90.hearmythoughts.api.requests.base.auth.BaseAuthPostRequest;
import com.nmp90.hearmythoughts.constants.actions.SessionActions;
import com.nmp90.hearmythoughts.events.ServerEvent;
import com.nmp90.hearmythoughts.instances.EventBusInstance;

/**
 * Created by nmp on 15-5-24.
 */
public class CreateSessionRequest extends BaseAuthPostRequest<String> {
    public static final String TAG = CreateSessionRequest.class.getSimpleName();

    public CreateSessionRequest(Object body, Context context) {
        super(BASE_URL + "/sessions", body, context, null);
    }

    @Override
    public void deliverResponse(String response) {
        EventBusInstance.getInstance().post(new ServerEvent(response, SessionActions.CREATE_SESSION));
    }
}
