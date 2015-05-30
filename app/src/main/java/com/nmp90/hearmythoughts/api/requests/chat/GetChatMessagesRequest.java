package com.nmp90.hearmythoughts.api.requests.chat;

import android.content.Context;

import com.android.volley.Response;
import com.nmp90.hearmythoughts.api.requests.base.auth.BaseAuthGetRequest;
import com.nmp90.hearmythoughts.constants.actions.ChatActions;
import com.nmp90.hearmythoughts.events.ServerEvent;
import com.nmp90.hearmythoughts.instances.EventBusInstance;
import com.nmp90.hearmythoughts.providers.SessionProvider;
import com.nmp90.hearmythoughts.ui.models.MessagesList;

/**
 * Created by nmp on 15-5-29.
 */
public class GetChatMessagesRequest extends BaseAuthGetRequest<MessagesList> {
    public GetChatMessagesRequest(Context context, Response.ErrorListener listener) {
        super(BASE_URL + "/sessions/" + SessionProvider.getInstance(context).getSession().getShortId() + "/chat", context, listener);
    }

    @Override
    public void deliverResponse(String response) {
        EventBusInstance.getInstance().post(new ServerEvent(response, ChatActions.LOAD_MESSAGES));
    }
}
