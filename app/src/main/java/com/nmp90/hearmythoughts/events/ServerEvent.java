package com.nmp90.hearmythoughts.events;

import com.nmp90.hearmythoughts.instances.GsonInstance;

/**
 * Created by nmp on 15-5-23.
 */
public class ServerEvent extends BaseEvent {
    public ServerEvent(String data, String action) {
        super(data, action);
    }

    public <T> T getData(Class<T> clazz) {
        return GsonInstance.getInstance().fromJson(getStringData(), clazz);
    }


    @Override
    protected ActionTypes getEventActionType() {
        return ActionTypes.SERVER_ACTION;
    }
}
