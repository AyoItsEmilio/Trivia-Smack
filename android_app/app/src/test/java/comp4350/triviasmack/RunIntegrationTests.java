package comp4350.triviasmack;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp4350.triviasmack.tests.integration.IntegrationTests;

public class RunIntegrationTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Integration tests");
        suite.addTest(IntegrationTests.suite());
        return suite;
    }
}
