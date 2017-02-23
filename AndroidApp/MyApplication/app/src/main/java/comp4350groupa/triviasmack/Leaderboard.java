package comp4350groupa.triviasmack;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Leaderboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        TextView leaderBoardTxt = (TextView) findViewById(R.id.leaderboardTxt);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView dynScore = (TextView) findViewById(R.id.dynScore);

        leaderBoardTxt.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));
        textView2.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));
        dynScore.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));
    }
}
