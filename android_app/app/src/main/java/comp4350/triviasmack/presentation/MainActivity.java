package comp4350.triviasmack.presentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import comp4350.triviasmack.R;
import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.application.MultiPlayer;
import comp4350.triviasmack.business.GameController;
import io.socket.emitter.Emitter;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;
import static comp4350.triviasmack.R.id.otherScore;
import static comp4350.triviasmack.R.id.otherScoreText;
import static comp4350.triviasmack.R.id.scoreText;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private static int otherScore;
    private GameController gameController;
    private MultiPlayer multiPlayer = MultiPlayer.getInstance();
    private Socket socket;
    private final String TAG = "MainActivity";
    private static TextView otherScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Main.startUp();
        gameController = GameController.getInstance();
        setContentView(R.layout.activity_main);
        otherScoreText = (TextView) findViewById(R.id.otherScoreText);
        displayScores();
        gameController.start();
    }

    private void displayScores(){
        if(GameController.getInstance().isStarted()){
            displayScore();
            if(multiPlayer.isConnected()) {
                displayOtherScore();
            }
        }
    }

    private void makeInvisible(){
        otherScoreText.setVisibility(View.INVISIBLE);
    }

    private void displayScore() {
        int score = gameController.getScore();
        TextView scoreText = (TextView)findViewById(R.id.scoreText);
        scoreText.setVisibility(View.VISIBLE);
        scoreText.setText(scoreText.getText() + "" + score);
    }

    private void displayOtherScore() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                otherScoreText.setVisibility(View.VISIBLE);
                if (otherScore != -1) {
                    otherScoreText.setText("Their score:" + otherScore);
                } else {
                    otherScoreText.setText("The other player disconnected.");
                }
            }
        });
    }

    private Emitter.Listener onOtherPlayerDone = new Emitter.Listener(){
        @Override
        public void call(final Object... args){
            MainActivity.this.runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    JSONObject data = (JSONObject) args[0];
                    try {
                        if(!data.getString("msg").equals("null")) {
                            MainActivity.otherScore = data.getInt("msg");
                        }else{
                            otherScore = -1;
                        }
                        Log.d(TAG, "onOtherPayerDone" + otherScore);
                        displayScores();
                    }catch(JSONException e){
                        Log.e(TAG, e.getMessage());
                    }
                }
            });
        }
    };

    public void renderQuestionPage(View v) {
        makeInvisible();
        if (multiPlayer.isConnected()) {
            multiPlayer.disconnect();
        }
        Intent QuestionPageIntent = new Intent(MainActivity.this, QuestionPageActivity.class);
        MainActivity.this.startActivity(QuestionPageIntent);
    }

    public void renderMultiPlayerPage(View v){
        multiPlayer.connect();
        socket = multiPlayer.getSocket();
        socket.on("other_player_done", onOtherPlayerDone);
        Intent MultiPlayerPageIntent = new Intent(MainActivity.this, MultiPlayerPageActivity.class);
        MainActivity.this.startActivity(MultiPlayerPageIntent);
    }
}
