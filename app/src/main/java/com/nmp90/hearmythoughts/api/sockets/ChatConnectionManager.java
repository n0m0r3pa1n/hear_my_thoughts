package com.nmp90.hearmythoughts.api.sockets;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.instances.GsonInstance;
import com.nmp90.hearmythoughts.ui.models.Message;
import com.nmp90.hearmythoughts.ui.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nmp on 15-5-24.
 */
public class ChatConnectionManager {
    public static final String TAG = ChatConnectionManager.class.getSimpleName();
    private static ChatConnectionManager chatConnectionManager;

    private ChatConnectionManager() {}
    public static ChatConnectionManager getInstance() {
        if(chatConnectionManager == null) {
            chatConnectionManager = new ChatConnectionManager();
        }

        return chatConnectionManager;
    }

    private Socket socket;
    {
        try {
            socket = IO.socket(Constants.BASE_SERVER_URL + ":8081");
        } catch (URISyntaxException e) {
            Log.d(TAG, "instance initializer " + e);
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            Log.d(TAG, " " + data.toString());
            try {
                notifyListeners(new Message(data.getString("message"),
                        GsonInstance.getInstance().fromJson(data.getString("user"), User.class)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onUserJoin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                notifyListeners(new Message("",
                        GsonInstance.getInstance().fromJson(data.getString("user"), User.class)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private List<OnChatActionsListener> listeners = new ArrayList<OnChatActionsListener>();

    public void addChatActionsListener(OnChatActionsListener listener) {
        listeners.add(listener);
        if(listeners.size() > 0 && !socket.connected()) {
            socket.on("new message", onNewMessage);
            socket.on("user joined", onUserJoin);
            socket.connect();
        }
    }

    public void removeChatActionsListener(OnChatActionsListener listener) {
        int size = listeners.size();
        for (int i = 0; i < size; i++) {
            if(listener == listeners.get(i)) {
                listeners.remove(i);
                break;
            }
        }

        if(listeners.size() == 0 && socket.connected()) {
            socket.disconnect();
        }
    }

    public void notifyListeners(Message message) {
        int size = listeners.size();
        for (int i = 0; i < size; i++) {
            OnChatActionsListener listener = listeners.get(i);
            listener.onMessageReceived(message);
            listener.onUserJoined(message.getUser());
        }
    }

    public void addUserToChat(User user, String sessionCode) {
        socket.emit("add user", user, sessionCode);
    }

    public void sendMessage(Message message, String sessionCode) {
        socket.emit("new message", message.getMessage(), sessionCode);
    }

    public interface OnChatActionsListener {
        void onMessageReceived(Message message);
        void onUserJoined(User user);
    }
}
