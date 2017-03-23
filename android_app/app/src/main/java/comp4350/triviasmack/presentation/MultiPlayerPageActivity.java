package comp4350.triviasmack.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import comp4350.triviasmack.R;
import comp4350.triviasmack.application.MultiPlayer;
import comp4350.triviasmack.business.GameController;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MultiPlayerPageActivity extends AppCompatActivity{
    private MultiPlayer multiPlayer = MultiPlayer.getInstance();
    private boolean isConnected = true;
    private Socket socket;
    private final String TAG = "MultiPlayerPageActivity";
    TextView tv;

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_player_page_activity);
        tv = (TextView)findViewById(R.id.otherScore);

        socket = multiPlayer.getSocket();
        socket.on(Socket.EVENT_CONNECT,onConnect);
        socket.connect();
        socket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.on("other_player_ready",onOtherPlayerReady);
        socket.on("join_waiting", onJoinWaiting);
    }

    public void onDestroy(){
        super.onDestroy();
        socket.off(Socket.EVENT_CONNECT, onConnect);
        socket.off(Socket.EVENT_DISCONNECT,onDisconnect);
        socket.off(Socket.EVENT_CONNECT_ERROR,onConnectError);
        socket.off("join_waiting",onJoinWaiting);
        socket.off("other_player_ready",onOtherPlayerReady);

    }

    private Emitter.Listener onOtherPlayerReady = new Emitter.Listener(){

        @Override
        public void call(Object ...args){
            MultiPlayerPageActivity.this.runOnUiThread(new Runnable(){
                public void run(){
                    Intent returnIntent = new Intent(MultiPlayerPageActivity.this, QuestionPageActivity.class);
                    MultiPlayerPageActivity.this.startActivityForResult(returnIntent,1);
                }
            });
        }
    };

    private Emitter.Listener onJoinWaiting = new Emitter.Listener(){

        @Override
        public void call(Object ...args){
            MultiPlayerPageActivity.this.runOnUiThread(new Runnable(){
                public void run(){
                    TextView tv = (TextView)findViewById(R.id.otherScore);
                    tv.setText("Waiting for other players to connect...");
                }
            });
        }
    };


    private Emitter.Listener onConnect = new Emitter.Listener(){

        @Override
        public void call(Object ...args){
            Log.e(TAG, "being connected");
            MultiPlayerPageActivity.this.runOnUiThread(new Runnable(){
                public void run(){
                    if (!isConnected) {
                        isConnected = true;
                    }
                    socket.emit("join_game");
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            MultiPlayerPageActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Error connecting");
                    Toast.makeText(getApplicationContext(),
                            "failed to connect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener(){
        @Override
        public void call(Object ...args){
            MultiPlayerPageActivity.this.runOnUiThread(new Runnable(){
                public void run(){
                    Log.e(TAG, "Error connecting");
                    isConnected = false;
                    Toast.makeText(getApplicationContext(),
                            "disconnected, please check your connection", Toast.LENGTH_LONG).show();
                }
            });
        }
    };
}




