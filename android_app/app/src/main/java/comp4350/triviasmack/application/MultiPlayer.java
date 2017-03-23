package comp4350.triviasmack.application;


import android.util.Log;
import java.net.URISyntaxException;

import comp4350.triviasmack.Constants;
import io.socket.client.IO;
import io.socket.client.Socket;
import static android.content.ContentValues.TAG;

public class MultiPlayer{
    private static MultiPlayer instance = null;
    private Socket socket;

    protected MultiPlayer() {}

    public Socket getSocket(){ return socket; }

    public void connect(){
        try {
            socket = IO.socket(Constants.SERVER_URL);
        }catch(URISyntaxException e){
            Log.e(TAG,"error:",e);
        }
    }

    public static MultiPlayer getInstance(){
        if(instance == null) {
            instance = new MultiPlayer();
        }
        return instance;
    }
}
