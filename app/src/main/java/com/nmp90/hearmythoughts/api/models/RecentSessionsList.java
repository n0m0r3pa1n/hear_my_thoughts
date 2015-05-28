package com.nmp90.hearmythoughts.api.models;

import java.util.List;

/**
 * Created by nmp on 15-5-28.
 */
public class RecentSessionsList {
    private List<RecentSession> sessions;

    public RecentSessionsList(List<RecentSession> sessions) {
        this.sessions = sessions;
    }

    public List<RecentSession> getSessions() {
        return sessions;
    }
}
