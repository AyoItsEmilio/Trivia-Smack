package comp4350.triviasmack.application;


import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import comp4350.triviasmack.Constants;
import comp4350.triviasmack.R;
import comp4350.triviasmack.presentation.MultiPlayerPageActivity;
import comp4350.triviasmack.presentation.QuestionPageActivity;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static android.content.ContentValues.TAG;

public class MultiPlayer{
    private static MultiPlayer instance = null;
    private Socket socket;
    private static boolean connected;

    protected MultiPlayer() {}

    public Socket getSocket(){ return socket; }

    public void connect(){
        try {
            socket = IO.socket(Constants.SERVER_URL);
            socket.connect();
            socket.on(Socket.EVENT_CONNECT,onConnect);
            socket.on(Socket.EVENT_DISCONNECT,onDisconnect);
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            connected = true;
        }catch(URISyntaxException e){
            Log.e(TAG,"error:",e);
        }
    }
    private Emitter.Listener onConnect = new Emitter.Listener(){
        @Override
        public void call(Object ...args){
            Log.e(TAG, "being connected");
            socket.emit("join_game");
        }

    };
    public void disconnect(){
        socket.disconnect();
        connected = false;
    }

    public boolean isConnected(){
        return connected;
    }

    public static MultiPlayer getInstance(){
        if(instance == null) {
            instance = new MultiPlayer();
        }
        return instance;
    }

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "Error connecting");
        }
    };

    public void sendScore(int score){
        JSONObject scoreJSON = new JSONObject();
        try {
            scoreJSON.put("score", score);
            System.out.println("sent score");
            socket.emit("game_over", scoreJSON);
        }catch(JSONException e){
            Log.e(TAG, "JSONExcpetion",e);
        }
    }

    private Emitter.Listener onDisconnect = new Emitter.Listener(){
        @Override
        public void call(Object ...args){
        Log.e(TAG, "Disconnected");
        connected = false;
        }
    };
}
