package comp4350.triviasmack.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
    private final static int maxCharacters = 15;

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
                    if(!newUsername.equals("") && newUsername.length() <= maxCharacters) {
                        gameController.setNewUsername(newUsername);
                        setUsernameText();
                    }
                    else if(newUsername.length() > maxCharacters)
                        nameLengthPopup();
                }
                return handled;
            }
        });
        setUsernameText();
    }

    private void setUsernameText() {
        TextView usernameText = (TextView) findViewById(R.id.usernameText);
        usernameText.setText(gameController.getUsername());
    }

    public void renderMainPage(View v) {
        Intent MainPageIntent = new Intent(UsernameSelectActivity.this, MainActivity.class);
        UsernameSelectActivity.this.startActivity(MainPageIntent);
    }

    private void nameLengthPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UsernameSelectActivity.this);
        builder.setMessage("Username must be less than "+maxCharacters+" characters.");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
