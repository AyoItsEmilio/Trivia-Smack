package comp4350.triviasmack.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.CountDownTimer;

import comp4350.triviasmack.R;
import comp4350.triviasmack.business.BackButtonDialog;
import comp4350.triviasmack.business.Exitable;
import comp4350.triviasmack.business.MultiPlayer;
import comp4350.triviasmack.business.GameController;
import comp4350.triviasmack.objects.Question;

public class QuestionPageActivity extends AppCompatActivity implements Exitable {

    private GameController gameController = GameController.getInstance();
    private final int one_second = 1000;
    private final int five_seconds = one_second * 5;
    private final int ten_seconds = one_second * 10 + 100;
    private QuestionTimer questionTimer = null;
    private int secondsUntilFinished = 0;
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
        final TextView[] timerTextView = {null};
        questionTimer = new QuestionTimer(ten_seconds, one_second, timerTextView);
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

    public class QuestionTimer extends CountDownTimer {
        private final int one_second = 1000;
        private int secondsUntilFinished = 0;
        private boolean isTicking = false;
        private TextView[] timerTextView = null;

        public QuestionTimer(long millisInFuture, long countDownInterval, TextView[] timerTextView) {
            super(millisInFuture, countDownInterval);
            this.timerTextView = timerTextView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            secondsUntilFinished = (int)Math.ceil(millisUntilFinished / one_second);

            if (timerTextView != null) {
                timerTextView[0] = (TextView) findViewById(R.id.timerTextView);
                timerTextView[0].setText("Time remaining: " + secondsUntilFinished);

                if (millisUntilFinished < five_seconds) {
                    timerTextView[0].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.nice_red));
                }
            }
        }

        @Override
        public void onFinish() {
            timerTextView[0].setText("Time is up!");
            isTicking = false;
            cancel();
            advancePage();
        }

        public int getTimeRemaining() {
            return secondsUntilFinished;
        }

        public void startTimer() {
            super.start();
            isTicking = true;
        }

        public void stopTimer() {
            isTicking = false;
            cancel();
        }

        public boolean isTicking() {
            return isTicking;
        }
    }
}