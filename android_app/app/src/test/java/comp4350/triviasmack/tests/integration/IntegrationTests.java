package comp4350.triviasmack.tests.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp4350.triviasmack.tests.business.ServerAccessTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ServerAccessTest.class, BusinessServerSeamTest.class})
public class IntegrationTests {
}
