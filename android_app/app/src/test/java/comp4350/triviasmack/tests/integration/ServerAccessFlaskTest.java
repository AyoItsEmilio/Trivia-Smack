package comp4350.triviasmack.tests.integration;

import junit.framework.TestCase;

import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.tests.business.ServerAccessTest;

public class ServerAccessFlaskTest extends TestCase {

    private static int numQuestions = Main.numQuestions;

    public ServerAccessFlaskTest(String arg0) {
        super(arg0);
    }

    public void testDataAccess() {
        ServerAccess serverAccess;

        Services.closeServerAccess();

        System.out.println("Starting Integration test, ServerAccess (using default server)");

        Services.createServerAccess();
        Services.createAsyncFacade(new AsyncFacadeStub());
        serverAccess = Services.getServerAccess();

        ServerAccessTest.serverAccessTest();
    }
}