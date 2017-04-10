package comp4350.triviasmack.presentation;

import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import comp4350.triviasmack.R;

public class QuestionTimer extends CountDownTimer {

    private final int one_second = 1000;
    private final int five_seconds = one_second * 5;

    private int secondsUntilFinished = 0;
    private TextView timerTextView;
    private Advanceable advanceable;

    public QuestionTimer(long millisInFuture, long countDownInterval, Advanceable advanceable) {
        super(millisInFuture, countDownInterval);

        if (advanceable instanceof AppCompatActivity) {
            timerTextView = (TextView) ((AppCompatActivity)advanceable).findViewById(R.id.timerTextView);
        }
        else {
            throw new IllegalArgumentException("Expected activity to be AppCompatActivity");
        }

        this.advanceable = advanceable;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        secondsUntilFinished = (int)Math.ceil(millisUntilFinished / one_second);

        if (timerTextView != null) {
            timerTextView.setText("Time remaining: " + secondsUntilFinished);

            if (millisUntilFinished < five_seconds) {
                timerTextView.setTextColor(ContextCompat.getColor(
                        ((AppCompatActivity)advanceable).getApplicationContext(), R.color.nice_red));
            }
        }
    }

    @Override
    public void onFinish() {
        timerTextView.setText("Time is up!");
        cancel();
        advanceable.advancePage();
    }

    public int getTimeRemaining() {
        return secondsUntilFinished;
    }

    public void startTimer() {
        super.start();
    }

    public void stopTimer() {
        cancel();
    }
}