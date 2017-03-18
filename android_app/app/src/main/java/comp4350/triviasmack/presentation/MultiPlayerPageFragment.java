package comp4350.triviasmack.presentation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import comp4350.triviasmack.R;
import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.business.ServerAccessObject;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static android.os.Build.VERSION_CODES.M;

public class MultiPlayerPageFragment extends Fragment {
    private Socket socket;
    private boolean isConnected = true;
    private final String TAG = "MultiPlayerPageFragment";
    TextView tv;
    public MultiPlayerPageFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_multi_player_page, container, false);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.d(TAG,"im here");
    }

    public void onDestroy(){
        super.onDestroy();
        socket.disconnect();
        socket.off(Socket.EVENT_CONNECT, onConnect);
       // socket.off(Socket.EVENT_DISCONNECT,onDisconnect);
       // socket.off(Socket.EVENT_CONNECT_ERROR,onConnectError);
       // socket.off(Socket.EVENT_CONNECT_TIMEOUT,onConnectError);
       // socket.off("user joined", onUserJoined);
      //  socket.off("user left", onUserLeft);

    }

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        tv = (TextView)getActivity().findViewById(R.id.otherScore);
        try {
            socket = IO.socket("http://192.168.0.107:5000");
        }catch(URISyntaxException e){
                Log.e(TAG,"error:",e);
            }
        socket.on(Socket.EVENT_CONNECT,onConnect);
        socket.connect();
        //socket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        //socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.on("other_player_ready",onOtherPlayerReady);
        socket.on("other_player_done",onOtherPlayerDone);
        socket.on("join_waiting", onJoinWaiting);
        //socket.on("user left",onUserLeft);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    private Emitter.Listener onOtherPlayerReady = new Emitter.Listener(){

        @Override
        public void call(Object ...args){
            getActivity().runOnUiThread(new Runnable(){
                public void run(){
                    Intent MainPageIntent = new Intent(getActivity(), QuestionPageActivity.class);
                    MultiPlayerPageFragment.this.startActivity(MainPageIntent);
                }
            });
        }
    };

    private Emitter.Listener onJoinWaiting = new Emitter.Listener(){

        @Override
        public void call(Object ...args){
            getActivity().runOnUiThread(new Runnable(){
                public void run(){
                    TextView tv = (TextView)getActivity().findViewById(R.id.otherScore);
                    tv.setText("Waiting for other players to connect...");
                }
            });
        }
    };

    private Emitter.Listener onOtherPlayerDone = new Emitter.Listener(){

        @Override
        public void call(final Object... args){
            getActivity().runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    JSONObject data = (JSONObject) args[0];
                    int score=0;
                    try {
                        score = data.getInt("msg");
                        tv.setText(Integer.toString(score));
                    }catch(JSONException e){
                        Log.e(TAG, e.getMessage());
                    }
                    Toast.makeText(getActivity().getApplicationContext(),
                            score, Toast.LENGTH_LONG).show();
                }
            });
        }
    };
    private Emitter.Listener onConnect = new Emitter.Listener(){

        @Override
        public void call(Object ...args){
            Log.e(TAG, "being connected");
            getActivity().runOnUiThread(new Runnable(){
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
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Error connecting");
                    Toast.makeText(getActivity().getApplicationContext(),
                            "failed to connect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onDisonnect = new Emitter.Listener(){

        @Override
        public void call(Object ...args){
            Log.e(TAG, "being connected");
            getActivity().runOnUiThread(new Runnable(){
                public void run(){
                    if (!isConnected) {
                        isConnected = true;
                        Log.d(TAG,"Connected!");
                    }
                }
            });
        }
    };








}
