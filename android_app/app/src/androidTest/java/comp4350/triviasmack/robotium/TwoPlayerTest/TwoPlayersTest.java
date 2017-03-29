package comp4350.triviasmack.robotium.TwoPlayerTest;

import com.robotium.solo.Solo;

import comp4350.triviasmack.R;
import comp4350.triviasmack.application.MultiPlayer;
import comp4350.triviasmack.presentation.MainActivity;
import comp4350.triviasmack.presentation.MultiPlayerPageActivity;
import comp4350.triviasmack.presentation.QuestionPageActivity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

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
    public void pressTwoPlayerBtn() throws Exception {
        solo.assertCurrentActivity("wrong activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.button3));

        solo.assertCurrentActivity("wrong activity", MultiPlayerPageActivity.class);
    }

}
