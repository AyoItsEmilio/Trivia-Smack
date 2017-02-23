package comp4350groupa.triviasmack;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class QuestionPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);

        Button optionOneBtn = (Button) findViewById(R.id.optionBtn1);
        Button optionTwoBtn = (Button) findViewById(R.id.optionBtn2);
        Button optionThreeBtn = (Button) findViewById(R.id.optionBtn3);
        Button optionFourBtn = (Button) findViewById(R.id.optionBtn4);

        TextView txt = (TextView) findViewById(R.id.questionText);
        txt.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));

        optionOneBtn.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));
        optionTwoBtn.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));
        optionThreeBtn.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));
        optionFourBtn.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));
    }
}
