package comp4350.triviasmack.robotium.TwoPlayerTest;

import com.robotium.solo.Solo;

import comp4350.triviasmack.R;
import comp4350.triviasmack.application.MultiPlayer;
import comp4350.triviasmack.presentation.MainActivity;
import comp4350.triviasmack.presentation.MultiPlayerPageActivity;
import comp4350.triviasmack.presentation.QuestionPageActivity;
import io.socket.client.Socket;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TwoPlayersTest{

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    public Solo solo;


    @Before
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), mActivityRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void twoPlayerTest() throws Exception {
        MultiPlayer dummyPlayer = MultiPlayer.getInstance();

        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        //open two player page
        solo.clickOnView(solo.getView(R.id.button3));

        solo.assertCurrentActivity("Expected activity: MultiPlayerPageActivity", MultiPlayerPageActivity.class);
        //opponent connect
        solo.sleep(1000);
        dummyPlayer.connect();
        solo.waitForActivity(QuestionPageActivity.class);

        solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);
        //wait for 1 seconds
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.optionBtn1));
        //go to the next question page
        solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);
        //wait for 1 seconds
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.optionBtn1));
        //go to the next question page
        solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);
        //wait for 1 seconds
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.optionBtn1));
        //opponent leave
        dummyPlayer.disconnect();

        solo.waitForActivity(MultiPlayerPageActivity.class);
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        //check score
        int scoreVis = solo.getView(R.id.scoreText).getVisibility();
        assertEquals(scoreVis, View.VISIBLE);
    }

}
