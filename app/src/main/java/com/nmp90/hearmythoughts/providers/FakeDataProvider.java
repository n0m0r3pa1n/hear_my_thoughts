package com.nmp90.hearmythoughts.providers;

import com.nmp90.hearmythoughts.models.ChatItem;
import com.nmp90.hearmythoughts.models.Message;
import com.nmp90.hearmythoughts.models.RecentSession;
import com.nmp90.hearmythoughts.models.Role;
import com.nmp90.hearmythoughts.models.User;

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

    public static List<ChatItem> getUsersInChat() {
        ArrayList<ChatItem> users = new ArrayList<ChatItem>();
        users.add(new ChatItem("Ivan Ivanov", "http://cdn.mobcon.com/wp-content/uploads/2015/02/tim-messerschmidt2.png"));
        users.add(new ChatItem("Georgi Dimitrov", "http://cdn.mobcon.com/wp-content/uploads/2015/02/murat-yener.png"));
        users.add(new ChatItem("Stoil Ivanov", "http://cdn.mobcon.com/wp-content/uploads/2015/02/pic11.png"));
        users.add(new ChatItem("Stoil Georgiev", "http://cdn.mobcon.com/wp-content/uploads/2015/02/enrique-lopez.png"));

        return users;
    }

    public static List<Message> getChatMessages() {
        ArrayList<Message> messages = new ArrayList<Message>();
        messages.add(new Message("Hello to everyone!", new User("Georgi Mirchev", "http://cdn.mobcon.com/wp-content/uploads/2015/02/milan-nankov-bg.png", Role.STUDENT)));
        messages.add(new Message("Hello, nice to meet you!", new User("Ivan Petrov", "http://cdn.mobcon.com/wp-content/uploads/2015/02/tim-messerschmidt2.png", Role.STUDENT)));
        messages.add(new Message("What is this lecture about?", new User("Stoil Ivanov", "http://cdn.mobcon.com/wp-content/uploads/2015/02/pic11.png", Role.STUDENT)));
        messages.add(new Message("We are currently speaking about how to help deaf people in universities and schools.", new User("Ivan Petrov", "http://cdn.mobcon.com/wp-content/uploads/2015/02/tim-messerschmidt2.png", Role.STUDENT)));
        messages.add(new Message("Nice!", new User("Georgi Mirchev", "http://cdn.mobcon.com/wp-content/uploads/2015/02/milan-nankov-bg.png", Role.STUDENT)));

        return messages;
    }
}
