package comp4350.triviasmack.presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.socket.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import comp4350.triviasmack.R;
import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.business.MultiPlayer;
import comp4350.triviasmack.business.GameController;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private static int otherScore;
    private GameController gameController;
    private MultiPlayer multiPlayer = MultiPlayer.getInstance();
    private Socket socket;
    private final String TAG = "MainActivity";
    private static boolean stillPlaying;
    private static TextView otherScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Main.startUp();

        setContentView(R.layout.activity_main);
        if (isNetworkAvailable()){
            gameController = GameController.getInstance();
            otherScoreText = (TextView) findViewById(R.id.otherScoreText);
            displayScores();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void displayScores() {
        if (GameController.getInstance().isStarted()) {
            displayScore();
            if (multiPlayer.isConnected()) {
                displayOtherScore();
            }
        }
    }

    private void makeInvisible() {
        otherScoreText.setVisibility(View.INVISIBLE);
    }

    private void displayScore() {
        int score = gameController.getScore();
        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setVisibility(View.VISIBLE);
        scoreText.setText(scoreText.getText() + "" + score);
    }

    private void displayOtherScore() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                otherScoreText.setVisibility(View.VISIBLE);
                if (otherScore != -1) {
                    if(stillPlaying){
                        otherScoreText.setText("Other player is still playing");
                    }else {
                        otherScoreText.setText("Their score: " + otherScore);
                    }
                }else{
                    otherScoreText.setText("The other player disconnected.");
                }
            }
        });
    }

    private Emitter.Listener onOtherPlayerDone = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        if (!data.getString("msg").equals("null")) {
                            MainActivity.otherScore = data.getInt("msg");
                        } else {
                            otherScore = -1;
                        }
                        stillPlaying = false;
                        Log.d(TAG, "onOtherPayerDone" + otherScore);
                        displayScores();
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            });
        }
    };

    public void renderSelectCategoryPage(View v) {
        gameController.start();
        if (isNetworkAvailable()) {
            makeInvisible();
            if (multiPlayer.isConnected()) {
                multiPlayer.disconnect();
            }
            Intent SelectCategoryIntent = new Intent(MainActivity.this, SelectCategoryActivity.class);
            MainActivity.this.startActivity(SelectCategoryIntent);
        }
        else {
            noInternetDialog();
        }
    }

    public void renderMultiPlayerPage(View v) {
        gameController.start();
        if (isNetworkAvailable()) {
            multiPlayer.connect();
            socket = multiPlayer.getSocket();
            stillPlaying = true;
            socket.on("other_player_done", onOtherPlayerDone);
            Intent MultiPlayerPageIntent = new Intent(MainActivity.this, MultiPlayerPageActivity.class);
            MainActivity.this.startActivity(MultiPlayerPageIntent);
        }
        else {
            noInternetDialog();
        }
    }

    private void noInternetDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("No internet connection!");
        alertDialog.setMessage("You need internet connection to play!");
        alertDialog.show();
    }
}
