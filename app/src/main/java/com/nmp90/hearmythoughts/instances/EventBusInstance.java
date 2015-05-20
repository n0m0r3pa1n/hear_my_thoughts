package com.nmp90.hearmythoughts.instances;

import com.squareup.otto.Bus;

/**
 * Created by nmp on 15-3-7.
 */
public class EventBusInstance {
    private Bus bus;
    private static EventBusInstance eventBusInstance;
    private EventBusInstance() {
        bus = new Bus();
    }

    public static EventBusInstance getInstance() {
        if(eventBusInstance == null) {
            eventBusInstance = new EventBusInstance();
        }

        return eventBusInstance;
    }

    public void register(Object object) {
        bus.register(object);
    }

    public void unregister(Object object) {
        bus.unregister(object);
    }

    public void post(Object event) {
        bus.post(event);
    }
}
