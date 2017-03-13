package comp4350.triviasmack.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comp4350.triviasmack.R;
import comp4350.triviasmack.business.GifImageView;

public class PlayerSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gametype_page);

        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.android);
    }

    public void waitForPlayer(View view){
        TextView textView = (TextView) findViewById(R.id.waitingText);
        textView.setVisibility(View.VISIBLE);

        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setVisibility(View.VISIBLE);

        Button button = (Button) findViewById(R.id.cancelBtn);
        button.setVisibility(View.VISIBLE);
    }

    public void cancelWaitForPlayer(View view){
        TextView textView = (TextView) findViewById(R.id.waitingText);
        textView.setVisibility(View.INVISIBLE);

        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setVisibility(View.INVISIBLE);

        Button button = (Button) findViewById(R.id.cancelBtn);
        button.setVisibility(View.INVISIBLE);
    }

    public void questionPage(View view){
        startActivity(new Intent(PlayerSelectActivity.this, QuestionPageActivity.class));
    }
}
