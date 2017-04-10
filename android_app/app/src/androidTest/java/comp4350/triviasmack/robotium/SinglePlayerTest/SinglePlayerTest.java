package comp4350.triviasmack.robotium.SinglePlayerTest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import comp4350.triviasmack.application.Main;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp4350.triviasmack.R;
import comp4350.triviasmack.presentation.MainActivity;
import comp4350.triviasmack.presentation.QuestionPageActivity;
import comp4350.triviasmack.presentation.SelectCategoryActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SinglePlayerTest{
    private static final int numQuestions= Main.numQuestions;
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
        solo.clickOnView(solo.getView(R.id.one_player));
        solo.assertCurrentActivity("Expected activity: SelectCategoryActivity", SelectCategoryActivity.class);
        solo.clickOnView(solo.getView(R.id.all));

        for(int i =0; i < numQuestions; i++){
            solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);
            solo.sleep(2000);
            solo.clickOnView(solo.getView(R.id.optionBtn2));
        }
        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        int scoreVis = solo.getView(R.id.scoreText).getVisibility();
        assertEquals(scoreVis, View.VISIBLE);
    }

    @Test
    public void selectHistoryCategory() throws Exception{
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.one_player));
        solo.assertCurrentActivity("Expected activity: SelectCategoryActivity", SelectCategoryActivity.class);
        solo.clickOnView(solo.getView(R.id.history));
        for(int i =0; i < numQuestions; i++){
            solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);
            solo.sleep(2000);
            solo.clickOnView(solo.getView(R.id.optionBtn2));
        }
        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        int scoreVis = solo.getView(R.id.scoreText).getVisibility();
        assertEquals(scoreVis, View.VISIBLE);
    }

    @Test
    public void selectGeographyCategory() throws Exception{
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.one_player));
        solo.assertCurrentActivity("Expected activity: SelectCategoryActivity", SelectCategoryActivity.class);
        solo.clickOnView(solo.getView(R.id.geography));
        for(int i =0; i < numQuestions; i++){
            solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);
            solo.sleep(2000);
            solo.clickOnView(solo.getView(R.id.optionBtn2));
        }
        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        int scoreVis = solo.getView(R.id.scoreText).getVisibility();
        assertEquals(scoreVis, View.VISIBLE);

    }

    @Test
    public void selectPopCultureCategory() throws Exception{
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.one_player));
        solo.assertCurrentActivity("Expected activity: SelectCategoryActivity", SelectCategoryActivity.class);
        solo.clickOnView(solo.getView(R.id.pop_culture));
        for(int i =0; i < numQuestions; i++){
            solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);
            solo.sleep(2000);
            solo.clickOnView(solo.getView(R.id.optionBtn2));
        }
        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        int scoreVis = solo.getView(R.id.scoreText).getVisibility();
        assertEquals(scoreVis, View.VISIBLE);

    }

    @Test
    public void selectOtherCategory() throws Exception{
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.one_player));
        solo.assertCurrentActivity("Expected activity: SelectCategoryActivity", SelectCategoryActivity.class);
        solo.clickOnView(solo.getView(R.id.other));
        for(int i =0; i < numQuestions; i++){
            solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);
            solo.sleep(2000);
            solo.clickOnView(solo.getView(R.id.optionBtn2));
        }
        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        int scoreVis = solo.getView(R.id.scoreText).getVisibility();
        assertEquals(scoreVis, View.VISIBLE);

    }

    @Test
    public void selectAllCategory() throws Exception{
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.one_player));
        solo.assertCurrentActivity("Expected activity: SelectCategoryActivity", SelectCategoryActivity.class);
        solo.clickOnView(solo.getView(R.id.all));
        for(int i =0; i < numQuestions; i++){
            solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);
            solo.sleep(2000);
            solo.clickOnView(solo.getView(R.id.optionBtn2));
        }
        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        int scoreVis = solo.getView(R.id.scoreText).getVisibility();
        assertEquals(scoreVis, View.VISIBLE);

    }

    @Test
    public void selectMathScienceCategory() throws Exception{

        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.one_player));
        solo.assertCurrentActivity("Expected activity: SelectCategoryActivity", SelectCategoryActivity.class);
        solo.clickOnView(solo.getView(R.id.math_and_science));
        for(int i =0; i < numQuestions; i++){
            solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);
            solo.sleep(2000);
            solo.clickOnView(solo.getView(R.id.optionBtn2));
        }
        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        int scoreVis = solo.getView(R.id.scoreText).getVisibility();
        assertEquals(scoreVis, View.VISIBLE);

    }

    @Test
    public void pressOptionBtnOverTime() throws Exception {

        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.one_player));
        solo.assertCurrentActivity("Expected activity: SelectCategoryActivity", SelectCategoryActivity.class);
        solo.clickOnView(solo.getView(R.id.all));

        for(int i =0; i < numQuestions; i++) {
            solo.sleep(10000);
            solo.assertCurrentActivity("Expected activity: QuestionPageActivity", QuestionPageActivity.class);
        }
        solo.sleep(1000);
        solo.assertCurrentActivity("Expected activity: MainActivity", MainActivity.class);

        int scoreVis = solo.getView(R.id.scoreText).getVisibility();
        assertEquals(scoreVis, View.VISIBLE);
    }
}
