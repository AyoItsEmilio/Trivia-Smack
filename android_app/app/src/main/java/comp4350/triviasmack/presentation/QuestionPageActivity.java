package comp4350.triviasmack.presentation;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comp4350.triviasmack.R;
import comp4350.triviasmack.business.GameController;
import comp4350.triviasmack.objects.Question;

public class QuestionPageActivity extends AppCompatActivity {

    private GameController gameController = GameController.getInstance();

    private MediaPlayer rSound = null;
    private MediaPlayer wSound = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);

        rSound  = MediaPlayer.create(this, R.raw.right_sound);
        wSound  = MediaPlayer.create(this, R.raw.wrong_sound);

        Question questionObj = gameController.getNextQuestion();
        TextView questionTitle = (TextView)findViewById(R.id.questionText);

        questionTitle.setText(questionObj.getQuestion());
        showOptions(questionObj.getOptions());
    }

    public void showOptions(String options[]) {

        for(int i = 0; i < options.length; i++){
            String buttonID = "optionBtn"+(i+1);
            int id = getResources().getIdentifier(buttonID, "id", "comp4350.triviasmack");
            Button optionButton = (Button)findViewById(id);
            optionButton.setVisibility(View.VISIBLE);
            optionButton.setText("• "+options[i]);
        }
    }

    public void processOptionButton(View v) {
        boolean result;
        String optionText;

        optionText = ((Button)v).getText()+"";
        optionText = optionText.substring(2);
        result = gameController.evaluateAnswer(optionText);

        if (result) {
            ((Button) v).setText("• RIGHT!");
            playSound(rSound, R.raw.right_sound);
            v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.nice_green));
            gameController.increaseScore();
        }
        else{
            ((Button) v).setText("• WRONG!");
            playSound(wSound, R.raw.wrong_sound);
            v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.nice_red));
        }

        if (gameController.finished()){
            gameController.returnTotalScore();
            Intent MainPageIntent = new Intent(QuestionPageActivity.this, MainActivity.class);
            QuestionPageActivity.this.startActivity(MainPageIntent);
        }
        else {
            startActivity(getIntent());
            finish();
        }
    }

    private void playSound(MediaPlayer mp, int id){
        try {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();
                mp = MediaPlayer.create(this, id);
            } mp.start();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}