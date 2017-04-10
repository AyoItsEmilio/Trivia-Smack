package comp4350.triviasmack.presentation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comp4350.triviasmack.R;
import comp4350.triviasmack.business.PracticeModeController;
import comp4350.triviasmack.objects.Question;

public class PracticeQuestionActivity extends AppCompatActivity implements Exitable, Advanceable {
    private PracticeModeController practiceModeController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_practice_question);
        practiceModeController = PracticeModeController.getInstance();
        Question questionObj = practiceModeController.getNextQuestion();

        TextView qText = (TextView) findViewById(R.id.qText);
        qText.setText(questionObj.getQuestion());

        TextView categoryView = (TextView)findViewById(R.id.categoryView);
        categoryView.setText("CATEGORY: "+ questionObj.getCategory().toUpperCase());

        TextView numAttemptedView = (TextView)findViewById(R.id.numAttemptedView);
        numAttemptedView.setText(Integer.toString(practiceModeController.getNumQuestionsAttempted()));

        TextView numCorrectView = (TextView)findViewById(R.id.numCorrectView);
        numCorrectView.setText(Integer.toString(practiceModeController.getNumQuestionsCorrect()));

        TextView percentView = (TextView)findViewById(R.id.percentView);

        percentView.setText(practiceModeController.getPercentCorrectFmt() + "%");

        showOptions(questionObj.getOptions());
    }

    public void advancePage() {
        startActivity(getIntent());
    }

    public void exitAction() {
        practiceModeController.destroy();
        Intent ExitGameIntent = new Intent(PracticeQuestionActivity.this, MainActivity.class);
        ExitGameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PracticeQuestionActivity.this.startActivity(ExitGameIntent);

    }

    public void showOptions(String options[]) {
        for (int i = 0; i < options.length; i++) {
            String buttonID = "optnBtn" + (i + 1);
            int id = getResources().getIdentifier(buttonID, "id", "comp4350.triviasmack");
            Button optionButton = (Button) findViewById(id);
            optionButton.setVisibility(View.VISIBLE);
            optionButton.setText("• " + options[i]);
        }
    }

    public void processOptionButton(View v) {
        boolean result;
        String optionText;

        optionText = ((Button) v).getText() + "";
        optionText = optionText.substring(2);
        result = practiceModeController.evaluateAnswer(optionText);

        if (result) {
            ((Button) v).setText("• RIGHT!");
            v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.nice_green));
            practiceModeController.increaseNumCorrect();
        } else {
            ((Button) v).setText("• WRONG!");
            v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.nice_red));
        }
        advancePage();
    }

    @Override
    public void onBackPressed() {
        BackButtonDialog.buildExitDialog(this);
    }
}
