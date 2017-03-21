package comp4350.triviasmack.application;


import android.util.Log;
import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;
import static android.content.ContentValues.TAG;

public class MultiPlayer{
    private static MultiPlayer instance = null;
    private Socket socket;

    protected MultiPlayer() {
        try {
            socket = IO.socket("http://192.168.0.107:5000");
        }catch(URISyntaxException e){
            Log.e(TAG,"error:",e);
        }
    }

    public Socket getSocket(){return socket;}

    public static MultiPlayer getInstance(){
        if(instance == null) {
            instance = new MultiPlayer();
        }
        return instance;
    }
}
