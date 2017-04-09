package comp4350.triviasmack.presentation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import comp4350.triviasmack.R;
import comp4350.triviasmack.business.GameController;

public class SelectCategoryActivity extends AppCompatActivity {
    private GameController gameController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        gameController = GameController.getInstance();
    }

    public void renderQuestionPage(View v) {
        gameController.start("all");
        Intent QuestionPageIntent = new Intent(SelectCategoryActivity.this, QuestionPageActivity.class);
        SelectCategoryActivity.this.startActivity(QuestionPageIntent);
    }
}
