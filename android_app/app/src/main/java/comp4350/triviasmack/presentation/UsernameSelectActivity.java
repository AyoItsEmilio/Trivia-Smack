package comp4350.triviasmack.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import comp4350.triviasmack.R;
import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.business.GameController;
import comp4350.triviasmack.objects.Profile;

public class UsernameSelectActivity extends AppCompatActivity {

    private GameController gameController;
    private final int MAX_CHARACTERS = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameController = GameController.getInstance();
        setContentView(R.layout.activity_username_page);

        EditText editText = (EditText) findViewById(R.id.editUsernameText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public  boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if(i == EditorInfo.IME_ACTION_DONE) {
                    String newUsername = textView.getText().toString();
                    if(!newUsername.equals("") && newUsername.length() <= MAX_CHARACTERS)
                        gameController.setNewUsername(newUsername);
                }
                return handled;
            }
        });

        TextView usernameText = (TextView) findViewById(R.id.usernameText);
        usernameText.setText(gameController.getUsername());
    }

    public void renderMainPage(View v) {
        Intent MainPageIntent = new Intent(UsernameSelectActivity.this, MainActivity.class);
        UsernameSelectActivity.this.startActivity(MainPageIntent);
    }
}
