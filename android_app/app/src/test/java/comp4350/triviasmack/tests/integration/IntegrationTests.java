package comp4350.triviasmack.tests.integration;

import junit.framework.TestSuite;
import junit.framework.Test;

public class IntegrationTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Integration tests");
        suite.addTestSuite(ServerAccessFlaskTest.class);
        return suite;
    }
}
