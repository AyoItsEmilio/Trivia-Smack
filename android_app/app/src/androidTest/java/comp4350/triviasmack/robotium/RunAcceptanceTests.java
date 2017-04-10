package comp4350.triviasmack.robotium;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp4350.triviasmack.robotium.MainActivityTest.MainActivityTest;
import comp4350.triviasmack.robotium.PracticeModeActivityTest.PracticeModeActivityTest;
import comp4350.triviasmack.robotium.SinglePlayerTest.SinglePlayerTest;
import comp4350.triviasmack.robotium.TwoPlayerTest.TwoPlayersTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({MainActivityTest.class, SinglePlayerTest.class, TwoPlayersTest.class, PracticeModeActivityTest.class})
public class RunAcceptanceTests {
}
