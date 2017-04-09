package comp4350.triviasmack.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import comp4350.triviasmack.R;
import comp4350.triviasmack.business.BackButtonDialog;
import comp4350.triviasmack.business.Exitable;
import comp4350.triviasmack.business.MultiPlayer;
import comp4350.triviasmack.business.GameController;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MultiPlayerPageActivity extends AppCompatActivity implements Exitable{
    private MultiPlayer multiPlayer = MultiPlayer.getInstance();
    private GameController gameController = GameController.getInstance();
    private Socket socket;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_player_page_activity);
        socket = multiPlayer.getSocket();
        socket.on("other_player_ready", onOtherPlayerReady);
    }

    public void onDestroy() {
        super.onDestroy();
        socket.off("other_player_ready", onOtherPlayerReady);
    }

    private Emitter.Listener onOtherPlayerReady = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            MultiPlayerPageActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Intent QuestionIntent = new Intent(MultiPlayerPageActivity.this, QuestionPageActivity.class);
                    MultiPlayerPageActivity.this.startActivity(QuestionIntent);
                }
            });
        }
    };

    public void exitAction() {
        gameController.start("all");
        Intent ExitGameIntent = new Intent(MultiPlayerPageActivity.this, MainActivity.class);
        ExitGameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MultiPlayerPageActivity.this.startActivity(ExitGameIntent);

        if (multiPlayer.isConnected()) {
            multiPlayer.disconnect();
        }
    }

    @Override
    public void onBackPressed() {
        BackButtonDialog.buildExitDialog(this);
    }
}




