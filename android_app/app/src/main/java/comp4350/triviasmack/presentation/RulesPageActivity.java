package comp4350.triviasmack.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import comp4350.triviasmack.R;

public class RulesPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_page);

        TextView view = (TextView)findViewById(R.id.TEXT_STATUS_ID);
        String rules = parseRules();
        view.setText(rules);
    }

    public void renderMainPage(View v) {
        Intent MainPageIntent = new Intent(RulesPageActivity.this, MainActivity.class);
        MainPageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        RulesPageActivity.this.startActivity(MainPageIntent);
    }

    private String parseRules() {

        InputStream rulesText = getResources().openRawResource(R.raw.rules);
        BufferedReader reader = new BufferedReader(new InputStreamReader(rulesText));
        StringBuilder out = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                out.append(line+"\n");
            }
            reader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return out.toString();
    }
}
