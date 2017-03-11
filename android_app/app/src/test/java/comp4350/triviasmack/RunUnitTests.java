package comp4350.triviasmack;

// import junit.framework.Test;
// import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp4350.triviasmack.tests.business.BusinessTests;
import comp4350.triviasmack.tests.objects.ObjectTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({BusinessTests.class, ObjectTests.class})
public class RunUnitTests {
}
