package com.nmp90.hearmythoughts.providers;

import com.nmp90.hearmythoughts.models.RecentSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nmp on 15-3-8.
 */
public class FakeDataProvider {
    public static List<RecentSession> getRecentSessions() {
        ArrayList<RecentSession> sessions = new ArrayList<RecentSession>();
        sessions.add(new RecentSession("", "My Session 1", "Ivan Petrov", "2015.01.03", "http://cdn.mobcon.com/wp-content/uploads/2015/02/tim-messerschmidt2.png" ,27));
        sessions.add(new RecentSession("", "My Session 2", "Georgi Dimitrov", "2015.01.03", "http://cdn.mobcon.com/wp-content/uploads/2015/02/murat-yener.png", 100));
        sessions.add(new RecentSession("", "My Session 3", "Stoil Ivanov", "2015.01.03", "http://cdn.mobcon.com/wp-content/uploads/2015/02/pic11.png", 10));
        sessions.add(new RecentSession("", "My Session 4", "Stoil Georgiev", "2015.01.03", "http://cdn.mobcon.com/wp-content/uploads/2015/02/enrique-lopez.png", 90));
        sessions.add(new RecentSession("", "My Session 5", "Dimitar Todorov", "2015.01.03", "http://cdn.mobcon.com/wp-content/uploads/2015/02/georgi-georgiev.png", 11));
        sessions.add(new RecentSession("", "My Session 6", "Ivan Dimitrov", "2015.01.03", "http://cdn.mobcon.com/wp-content/uploads/2015/02/milan-nankov-bg.png", 123));
        sessions.add(new RecentSession("", "My Session 7", "Eleonora Todorova", "2015.01.03", "http://cdn.mobcon.com/wp-content/uploads/2015/02/jeni-kyuchukovaa.png", 28));

        return sessions;
    }
}
