package comp4350.triviasmack.robotium.SinglePlayerTest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp4350.triviasmack.R;
import comp4350.triviasmack.presentation.MainActivity;
import comp4350.triviasmack.presentation.QuestionPageActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SinglePlayerTest{

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
    public void pressOptionBtnOnTime() throws Exception {

        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.button2));
        solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);

        solo.sleep(2000);
        solo.clickOnView(solo.getView(R.id.optionBtn1));

        solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);

        solo.sleep(2000);
        solo.clickOnView(solo.getView(R.id.optionBtn1));

        solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);

        solo.sleep(2000);
        solo.clickOnView(solo.getView(R.id.optionBtn1));

        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);

        int scoreVis = solo.getView(R.id.scoreText).getVisibility();
        assertEquals(scoreVis, View.VISIBLE);
    }

    @Test
    public void pressOptionBtnOverTime() throws Exception {

        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.button2));
        solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);

        solo.sleep(11000);

        solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);

        solo.sleep(11000);

        solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);

        solo.sleep(11000);

        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);

        int scoreVis = solo.getView(R.id.scoreText).getVisibility();
        assertEquals(scoreVis, View.VISIBLE);
    }
}
