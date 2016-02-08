package com.nmp90.hearmythoughts.api.sockets;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.nmp90.hearmythoughts.api.models.User;
import com.nmp90.hearmythoughts.api.models.UsersList;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.instances.GsonInstance;
import com.nmp90.hearmythoughts.ui.models.Message;

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
            socket = IO.socket(Constants.BASE_SERVER_URL + "/chat" );
        } catch (URISyntaxException e) {
            Log.d(TAG, "instance initializer " + e);
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                notifyMessageListeners(new Message(data.getString("message"),
                        GsonInstance.getInstance().fromJson(data.getString("user"), User.class)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onInitialUsersListReceived = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                UsersList users = GsonInstance.getInstance().fromJson(data.toString(), UsersList.class);
                notifyUsersListReceivedListeners(users);
            } catch(Exception e) {
                Log.d(TAG, "call " + data.toString());
                e.printStackTrace();
                Log.d(TAG, "call " + e.getLocalizedMessage());
            }

        }
    };

    private Emitter.Listener onUserJoin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                notifyUserJoinedListeners(
                        GsonInstance.getInstance().fromJson(data.getString("user"), User.class)
                        );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                notifyUserLeftListeners(
                        GsonInstance.getInstance().fromJson(data.getString("user"), User.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private List<OnChatActionsListener> chatListeners = new ArrayList<OnChatActionsListener>();
    private List<OnUserChatActionsListener> userListeners = new ArrayList<OnUserChatActionsListener>();

    public synchronized void addChatListener(OnChatListener listener) {
        if(listener instanceof  OnChatActionsListener) {
            chatListeners.add((OnChatActionsListener) listener);
        } else {
            userListeners.add((OnUserChatActionsListener) listener);
        }

        Object threadLock = new Object();
        synchronized (threadLock) {
            if ((chatListeners.size() > 0 || userListeners.size() > 0) && !socket.connected()) {
                if(!socket.hasListeners("new message")) {
                    socket.on("users list", onInitialUsersListReceived);
                    socket.on("new message", onNewMessage);
                    socket.on("user joined", onUserJoin);
                    socket.on("user left", onUserLeft);
                    socket.connect();
                }
                else if (socket.hasListeners("new message") && !socket.connected()) {
                    socket.connect();
                }
            }
        }
    }

    public void removeChatListener(OnChatListener listener) {
        int size = chatListeners.size();
        if(listener instanceof  OnChatActionsListener) {
            removeChatActionsListener((OnChatActionsListener) listener);
        } else {
            removeUserChatActionsListener((OnUserChatActionsListener) listener);
        }

        if(chatListeners.size() == 0 && userListeners.size() == 0 && socket.connected()) {
            if(socket.hasListeners("new message"))
                socket.disconnect();
        }
    }

    private void removeChatActionsListener(OnChatActionsListener listener) {
        int size = chatListeners.size();
        for (int i = 0; i < size; i++) {
            if(listener == chatListeners.get(i)) {
                chatListeners.remove(i);
                break;
            }
        }
    }

    private void removeUserChatActionsListener(OnUserChatActionsListener listener) {
        int size = userListeners.size();
        for (int i = 0; i < size; i++) {
            if(listener == userListeners.get(i)) {
                userListeners.remove(i);
                break;
            }
        }
    }

    public void notifyMessageListeners(Message message) {
        int size = chatListeners.size();
        for (int i = 0; i < size; i++) {
            if(chatListeners.get(i) instanceof OnChatActionsListener) {
                OnChatActionsListener listener = (OnChatActionsListener) chatListeners.get(i);
                listener.onMessageReceived(message);
            }
        }
    }

    public void notifyUsersListReceivedListeners(UsersList usersList) {
        int size = userListeners.size();
        for (int i = 0; i < size; i++) {
            OnUserChatActionsListener listener = userListeners.get(i);
            listener.onInitialUsersListReceived(usersList);
        }
    }

    public void notifyUserJoinedListeners(User user) {
        int size = userListeners.size();
        for (int i = 0; i < size; i++) {
            OnUserChatActionsListener listener = userListeners.get(i);
            listener.onUserJoined(user);
        }
    }

    public void notifyUserLeftListeners(User user) {
        int size = userListeners.size();
        for (int i = 0; i < size; i++) {
            OnUserChatActionsListener listener = userListeners.get(i);
            listener.onUserLeft(user);
        }
    }

    public void addUserToChat(User user, String sessionCode) {
        if(socket.connected() == false) {
            socket.connect();
        }

        socket.emit("add user", user, sessionCode);
    }

    public void sendMessage(Message message, String sessionCode, String userId) {
        socket.emit("new message", message.getMessage(), sessionCode, userId);
    }

    public interface OnChatListener {

    }

    public interface OnChatActionsListener extends OnChatListener {
        void onMessageReceived(Message message);
    }

    public interface  OnUserChatActionsListener extends OnChatListener {
        void onUserJoined(User user);
        void onUserLeft(User user);
        void onInitialUsersListReceived(UsersList usersList);
    }
}
