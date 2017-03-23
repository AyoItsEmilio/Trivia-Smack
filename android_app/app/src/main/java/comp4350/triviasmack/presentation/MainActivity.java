package comp4350.triviasmack.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import comp4350.triviasmack.R;
import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.business.GameController;

public class MainActivity extends AppCompatActivity {

    private GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Main.startUp();
        gameController = GameController.getInstance();
        setContentView(R.layout.activity_main);
        displayScore();
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

    public void renderQuestionPage(View v) {
        Intent QuestionPageIntent = new Intent(MainActivity.this, QuestionPageActivity.class);
        MainActivity.this.startActivity(QuestionPageIntent);
    }
}
