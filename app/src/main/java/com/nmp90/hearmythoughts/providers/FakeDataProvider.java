package com.nmp90.hearmythoughts.providers;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.nmp90.hearmythoughts.instances.GsonInstance;
import com.nmp90.hearmythoughts.ui.models.ChatItem;
import com.nmp90.hearmythoughts.ui.models.Message;
import com.nmp90.hearmythoughts.ui.models.RecentSession;
import com.nmp90.hearmythoughts.utils.FileUtils;
import com.nmp90.hearmythoughts.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nmp on 15-3-8.
 */
public class FakeDataProvider {
    public static final String TAG = FakeDataProvider.class.getSimpleName();
    public static List<RecentSession> getRecentSessions(Context context) {
        String recentSessions = FileUtils.readFileFromAssets(context, "data/recent_sessions.json");
        ArrayList<RecentSession> sessions =  GsonInstance.getInstance().fromJson(recentSessions, new TypeToken<ArrayList<RecentSession>>() {
        }.getType());

        SharedPrefsUtils.getInstance(context).setPreference("TEST", new ArrayList<RecentSession>());

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
//        messages.add(new Message("Hello to everyone!", new User("Georgi Mirchev", "http://cdn.mobcon.com/wp-content/uploads/2015/02/milan-nankov-bg.png", Role.STUDENT)));
//        messages.add(new Message("Hello, nice to meet you!", new User("Ivan Petrov", "http://cdn.mobcon.com/wp-content/uploads/2015/02/tim-messerschmidt2.png", Role.STUDENT)));
//        messages.add(new Message("What is this lecture about?", new User("Stoil Ivanov", "http://cdn.mobcon.com/wp-content/uploads/2015/02/pic11.png", Role.STUDENT)));
//        messages.add(new Message("We are currently speaking about how to help deaf people in universities and schools.", new User("Ivan Petrov", "http://cdn.mobcon.com/wp-content/uploads/2015/02/tim-messerschmidt2.png", Role.STUDENT)));
//        messages.add(new Message("Nice!", new User("Georgi Mirchev", "http://cdn.mobcon.com/wp-content/uploads/2015/02/milan-nankov-bg.png", Role.STUDENT)));

        return messages;
    }
}
