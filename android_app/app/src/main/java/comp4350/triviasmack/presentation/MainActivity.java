package comp4350.triviasmack.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import comp4350.triviasmack.R;
import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.application.MultiPlayer;
import comp4350.triviasmack.business.GameController;
import io.socket.emitter.Emitter;

import static android.os.Build.VERSION_CODES.M;
import static comp4350.triviasmack.R.id.otherScore;
import static comp4350.triviasmack.R.id.scoreText;

public class MainActivity extends AppCompatActivity {
    private static int otherScore;

    private GameController gameController;
    private MultiPlayer multiPlayer;
    private Socket socket;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Main.startUp();
        gameController = GameController.getInstance();

        setContentView(R.layout.activity_main);
        displayScore();
        displayOtherScore();

        gameController.start();
    }

    private void displayScore() {
        int score = gameController.getScore();

        if (GameController.getInstance().isStarted()) {
            TextView scoreText = (TextView) findViewById(R.id.scoreText);
            scoreText.setVisibility(View.VISIBLE);
            scoreText.setText(scoreText.getText() + "" + score);
        }
    }

    public void displayOtherScore(){
        if(GameController.getInstance().isStarted()) {
            TextView otherScoreText = (TextView) findViewById(R.id.otherScoreText);
            otherScoreText.setVisibility(View.VISIBLE);
            otherScoreText.setText(otherScoreText.getText() + "" + MainActivity.otherScore);
        }
    }

    private Emitter.Listener onOtherPlayerDone = new Emitter.Listener(){

        @Override
        public void call(final Object... args){
        MainActivity.this.runOnUiThread(new Runnable(){
            @Override
            public void run(){
            JSONObject data = (JSONObject) args[0];
            try {
                Log.d(TAG, "onOtherPayerDone");
                MainActivity.otherScore = data.getInt("msg");
            }catch(JSONException e){
                Log.e(TAG, e.getMessage());
            }
            }
        });
        }
    };

    public void renderQuestionPage(View v) {
        Intent QuestionPageIntent = new Intent(MainActivity.this, QuestionPageActivity.class);
        MainActivity.this.startActivity(QuestionPageIntent);
    }

    public void renderMultiPlayerPage(View v){
        multiPlayer = MultiPlayer.getInstance();
        multiPlayer.connect();
        socket = multiPlayer.getSocket();
        socket.on("other_player_done", onOtherPlayerDone);

        Intent MultiPlayerPageIntent = new Intent(MainActivity.this, MultiPlayerPageActivity.class);
        MainActivity.this.startActivity(MultiPlayerPageIntent);
    }
}
