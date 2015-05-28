package com.nmp90.hearmythoughts.stores;

import com.nmp90.hearmythoughts.api.models.Session;
import com.nmp90.hearmythoughts.api.models.RecentSessionsList;
import com.nmp90.hearmythoughts.constants.actions.SessionActions;
import com.nmp90.hearmythoughts.events.ErrorEvent;
import com.nmp90.hearmythoughts.events.ServerEvent;
import com.nmp90.hearmythoughts.events.UiEvent;
import com.nmp90.hearmythoughts.instances.EventBusInstance;
import com.nmp90.hearmythoughts.api.models.RecentSession;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by nmp on 15-5-24.
 */
public class SessionsStore extends Bus {
    public static final String TAG = SessionsStore.class.getSimpleName();
    public static SessionsStore sessionsStore;

    private Session session;
    private RecentSessionsList sessionsList;

    public SessionsStore() {
        super(TAG);
        EventBusInstance.getInstance().register(this);
    }

    public static SessionsStore getInstance() {
        if(sessionsStore == null)
            sessionsStore = new SessionsStore();

        return sessionsStore;
    }

    @Subscribe
    public void receiveServerEvent(ServerEvent event) {
        //May be we should check if the operation is successful before get the data,
        //It can be used for operation which don't need to load data.
        switch(event.getAction()) {
            case SessionActions.CREATE_SESSION:
                session = event.getData(Session.class);
                this.post(new SessionCreatedEvent(session));
                break;
            case SessionActions.JOIN_SESSION:
                if(!event.isSuccess()) {
                    this.post(new ErrorEvent());
                } else {
                    session = event.getData(Session.class);
                    this.post(new SessionJoinedEvent(session));
                }
                break;
            case SessionActions.RECENT_SESSIONS:
                sessionsList = event.getData(RecentSessionsList.class);
                this.post(new RecentSessionsEvent(sessionsList.getSessions()));
                break;
            default:
                return;
        }
    }

    public RecentSessionsList getSessionsList() {
        return sessionsList;
    }

    @Subscribe
    public void receiveUiEvent(UiEvent event) {

    }

    public static class SessionCreatedEvent {
        private Session session;

        public SessionCreatedEvent(Session session) {
            this.session = session;
        }

        public Session getSession() {
            return session;
        }
    }

    public static class SessionJoinedEvent extends SessionCreatedEvent {

        public SessionJoinedEvent(Session session) {
            super(session);
        }
    }

    public static class RecentSessionsEvent {
        private List<RecentSession> recentSessionList;

        public RecentSessionsEvent(List<RecentSession> recentSessionList) {
            this.recentSessionList = recentSessionList;
        }

        public List<RecentSession> getRecentSessionList() {
            return recentSessionList;
        }
    }
}
