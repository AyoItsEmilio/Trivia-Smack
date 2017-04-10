package comp4350.triviasmack.robotium.PracticeModeActivityTest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp4350.triviasmack.R;
import comp4350.triviasmack.presentation.MainActivity;
import comp4350.triviasmack.presentation.PracticeQuestionActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class PracticeModeActivityTest {
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
    public void playPracticeModeTest() throws Exception {
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.practice_mode));
        solo.assertCurrentActivity("Expected activity: PracticeModeActivity", PracticeQuestionActivity.class);

        for(int i =0; i < 11; i++){
            solo.assertCurrentActivity("Expected activity: QuestionPageActivity", PracticeQuestionActivity.class);
            solo.sleep(2000);
            solo.clickOnView(solo.getView(R.id.optnBtn1));
        }
    }
}
