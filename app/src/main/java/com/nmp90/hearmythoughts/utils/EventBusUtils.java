package com.nmp90.hearmythoughts.utils;

import com.squareup.otto.Bus;

/**
 * Created by nmp on 15-3-7.
 */
public class EventBusUtils {
    private static Bus bus;
    private static EventBusUtils eventBusUtils;
    private EventBusUtils() {
        bus = new Bus();
    }

    public Bus getBus() {
        return eventBusUtils.bus;
    }

    public static void init() {
        eventBusUtils = new EventBusUtils();
    }

    public static void register(Object object) {
        eventBusUtils.getBus().register(object);
    }

    public static void unregister(Object object) {
        eventBusUtils.getBus().unregister(object);
    }

    public static void post(Object event) {
        eventBusUtils.getBus().post(event);
    }
}
