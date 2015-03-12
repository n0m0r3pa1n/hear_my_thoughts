package com.nmp90.hearmythoughts.instances;

import com.squareup.otto.Bus;

/**
 * Created by nmp on 15-3-7.
 */
public class EventBusInstance {
    private static Bus bus;
    private static EventBusInstance eventBusInstance;
    private EventBusInstance() {
        bus = new Bus();
    }

    public Bus getBus() {
        return eventBusInstance.bus;
    }

    public static void init() {
        eventBusInstance = new EventBusInstance();
    }

    public static void register(Object object) {
        eventBusInstance.getBus().register(object);
    }

    public static void unregister(Object object) {
        eventBusInstance.getBus().unregister(object);
    }

    public static void post(Object event) {
        eventBusInstance.getBus().post(event);
    }
}
