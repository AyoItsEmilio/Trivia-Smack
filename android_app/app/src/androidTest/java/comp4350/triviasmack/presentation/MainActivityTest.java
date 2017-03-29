package comp4350.triviasmack.presentation;

import org.junit.Rule;
import org.junit.Test;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import comp4350.triviasmack.R;

import static org.hamcrest.Matchers.allOf;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openSinglePlayerPage() throws Exception{
        //start solo play
        ViewInteraction singlePlayBtn = onView(allOf(withId(R.id.button2), withText("PLAY!"), isDisplayed()));
        singlePlayBtn.perform(click());
        //check next activity
        ViewInteraction timerText = onView(allOf(withId(R.id.timerTextView),
                withParent(allOf(withId(R.id.activity_question_page),
                        withParent(withId(android.R.id.content))))));
        timerText.check(matches(isDisplayed()));

    }

    @Test
    public void openMultiPlayerPage() throws Exception{
        //start online play
        ViewInteraction multiPlayBtn = onView(allOf(withId(R.id.button3), withText("Two player"), isDisplayed()));
        multiPlayBtn.perform(click());
        //check next activity
        ViewInteraction waitingText = onView(allOf(withId(R.id.otherScore),
                withParent(allOf(withId(R.id.container),
                        withParent(withId(android.R.id.content))))));
        waitingText.check(matches(isDisplayed()));
    }
}