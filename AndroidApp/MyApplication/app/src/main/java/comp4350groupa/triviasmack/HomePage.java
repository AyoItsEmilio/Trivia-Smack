package comp4350groupa.triviasmack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button quickPlayBtn = (Button) findViewById(R.id.quickPlayBtn);
        Button leaderbdBtn = (Button) findViewById(R.id.leaderboardsBtn);
        Button userBtn = (Button) findViewById(R.id.usernameBtn);
        Button settingBtn = (Button) findViewById(R.id.settingsBtn);

        quickPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, QuestionPageActivity.class));
            }
        });

        leaderbdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Leaderboard.class));
            }
        });

        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, userPage.class));
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Settings.class));
            }
        });
    }
}
