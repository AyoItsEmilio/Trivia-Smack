package comp4350.triviasmack.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comp4350.triviasmack.R;
import comp4350.triviasmack.business.MultiPlayer;
import comp4350.triviasmack.business.GameController;
import comp4350.triviasmack.objects.Question;

public class QuestionPageActivity extends AppCompatActivity implements Exitable, Advanceable {

    private GameController gameController = GameController.getInstance();
    private QuestionTimer questionTimer = null;
    private TextView scoreView;
    private MultiPlayer multiPlayer = MultiPlayer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question_page);
        Question questionObj = gameController.getNextQuestion();
        TextView questionTitle = (TextView) findViewById(R.id.questionText);
        scoreView = (TextView) findViewById(R.id.scoreView);

        scoreView.setText("Score: " + gameController.getScore());
        questionTitle.setText(questionObj.getQuestion());
        showOptions(questionObj.getOptions());
        startTimer();
    }

    public void startTimer() {
        int one_second = 1000;
        int ten_seconds = one_second*10;
        questionTimer = new QuestionTimer(ten_seconds, one_second, this);
        questionTimer.startTimer();
    }

    public void showOptions(String options[]) {

        for (int i = 0; i < options.length; i++) {
            String buttonID = "optionBtn" + (i + 1);
            int id = getResources().getIdentifier(buttonID, "id", "comp4350.triviasmack");
            Button optionButton = (Button) findViewById(id);
            optionButton.setVisibility(View.VISIBLE);
            optionButton.setText("• " + options[i]);
        }
    }

    public void processOptionButton(View v) {
        boolean result;
        String optionText;

        questionTimer.stopTimer();
        optionText = ((Button) v).getText() + "";
        optionText = optionText.substring(2);
        result = gameController.evaluateAnswer(optionText);

        if (result) {
            ((Button) v).setText("• RIGHT!");
            v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.nice_green));
            gameController.increaseScore(questionTimer.getTimeRemaining());
            scoreView.setText("Score: " + gameController.getScore());
        } else {
            v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.nice_red));
        }

        advancePage();
    }

    public void advancePage() {
        if (gameController.finished()) {

            if (multiPlayer.isConnected()) {
                multiPlayer.sendScore(gameController.getScore());
            }

            Intent MainPageIntent = new Intent(QuestionPageActivity.this, MainActivity.class);
            MainPageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            QuestionPageActivity.this.startActivity(MainPageIntent);

        } else {
            startActivity(getIntent());
            finish();
        }
    }

    public void exitAction() {
        questionTimer.stopTimer();
        gameController.start();

        Intent ExitGameIntent = new Intent(QuestionPageActivity.this, MainActivity.class);
        ExitGameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        QuestionPageActivity.this.startActivity(ExitGameIntent);

        if (multiPlayer.isConnected()) {
            multiPlayer.disconnect();
        }
    }

    @Override
    public void onBackPressed() {
        BackButtonDialog.buildExitDialog(this);
    }
}