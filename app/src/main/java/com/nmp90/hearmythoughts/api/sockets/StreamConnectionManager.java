package com.nmp90.hearmythoughts.api.sockets;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.ui.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nmp on 15-5-28.
 */
public class StreamConnectionManager {
    public static final String TAG = StreamConnectionManager.class.getSimpleName();

    private static StreamConnectionManager streamConnectionManager;
    public static StreamConnectionManager getInstance() {
        if(streamConnectionManager == null) {
            streamConnectionManager = new StreamConnectionManager();
        }
        return streamConnectionManager;
    }

    List<OnStreamListener> listeners = new ArrayList<OnStreamListener>();
    private Socket socket;
    {
        try {
            socket = IO.socket(Constants.BASE_SERVER_URL + ":8082");
        } catch (URISyntaxException e) {
            Log.d(TAG, "instance initializer " + e);
        }
    }

    private Emitter.Listener onStreamReceive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                notifyListeners(data.getString("stream"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onStatusChange = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                notifyListeners(data.getBoolean("is_running"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void notifyListeners(String stream) {
        int size = listeners.size();
        for (int i = 0; i < size; i++) {
            listeners.get(i).onStreamReceived(stream);
        }
    }

    private void notifyListeners(boolean isRunning) {
        int size = listeners.size();
        for (int i = 0; i < size; i++) {
            listeners.get(i).onStreamStatusChange(isRunning);
        }
    }

    public void addOnStreamListener(OnStreamListener listener) {
        listeners.add(listener);
        if(listeners.size() > 0 && !socket.connected()) {
            socket.on("stream status", onStatusChange);
            socket.on("stream", onStreamReceive);
            socket.connect();
        }
    }

    public void removeOnStreamListener(OnStreamListener listener) {
        listeners.remove(listener);
        if(listeners.size() == 0 && socket.connected()) {
            socket.disconnect();
        }
    }

    public void addUserToStream(User user, String sessionCode) {
        if(socket.connected() == false) {
            socket.connect();
        }
        socket.emit("add user", user, sessionCode);
    }

    public void sendStream(String stream, String sessionCode) {
        socket.emit("stream", stream, sessionCode);
    }

    public void sendStreamStatus(boolean isRunning, String sessionCode) {
        socket.emit("stream status", isRunning, sessionCode);
    }

    public interface OnStreamListener {
        void onStreamReceived(String stream);
        void onStreamStatusChange(boolean isRunning);
    }


}
