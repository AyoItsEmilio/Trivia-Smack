package comp4350.triviasmack.tests.objects;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp4350.triviasmack.tests.objects.QuestionTest;

public class ObjectTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Object tests");

        suite.addTestSuite(QuestionTest.class);
        return suite;
    }
}

