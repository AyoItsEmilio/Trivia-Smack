package comp4350.triviasmack.business;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import comp4350.triviasmack.application.Constants;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MultiPlayer {
    private final String LOG_TAG = MultiPlayer.class.getSimpleName();
    private static MultiPlayer instance = null;
    private Socket socket;
    private static boolean connected;

    public Socket getSocket() {
        return socket;
    }

    public void connect() {
        try {
            socket = IO.socket(Constants.SERVER_URL);
            socket.connect();
            socket.on(Socket.EVENT_CONNECT, onConnect);
            socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            connected = true;
        } catch (URISyntaxException e) {
            Log.e(LOG_TAG, "error:", e);
        }
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(LOG_TAG, "being connected");
            socket.emit("join_game");
        }

    };

    public void disconnect() {
        socket.disconnect();
        connected = false;
    }

    public boolean isConnected() {
        return connected;
    }

    public static MultiPlayer getInstance() {
        if (instance == null) {
            instance = new MultiPlayer();
        }
        return instance;
    }

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(LOG_TAG, "Error connecting");
        }
    };

    public void sendScore(int score) {
        JSONObject scoreJSON = new JSONObject();
        try {
            scoreJSON.put("score", score);
            socket.emit("game_over", scoreJSON);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONExcpetion", e);
        }
    }

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(LOG_TAG, "Disconnected");
            connected = false;
        }
    };
}
