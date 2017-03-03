package comp4350.triviasmack.tests.business;

import junit.framework.Test;
import junit.framework.TestSuite;

public class BusinessTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Business tests");
        suite.addTestSuite(GameControllerTest.class);
        suite.addTestSuite(ParseJSONTest.class);
        return suite;
    }
}
