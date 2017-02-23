package comp4350groupa.triviasmack;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class userPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        TextView usernameTxt = (TextView) findViewById(R.id.username);
        TextView scoreTxt = (TextView) findViewById(R.id.scoreText);
        TextView leaderboardTxt = (TextView) findViewById(R.id.leaderboardText);
        TextView dynScore = (TextView) findViewById(R.id.dynamicScore);
        TextView dynRank = (TextView) findViewById(R.id.dynamicRank);

        usernameTxt.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));
        scoreTxt.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));
        leaderboardTxt.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));
        dynScore.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));
        dynRank.setTypeface(Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf"));

    }
}
