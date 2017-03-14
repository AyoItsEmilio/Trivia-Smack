package comp4350.triviasmack;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp4350.triviasmack.tests.integration.IntegrationTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({IntegrationTests.class})

public class RunIntegrationTests {
}
