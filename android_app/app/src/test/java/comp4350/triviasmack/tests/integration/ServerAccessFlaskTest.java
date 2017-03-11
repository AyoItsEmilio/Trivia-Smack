package comp4350.triviasmack.tests.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.tests.business.ServerAccessTest;

public class ServerAccessFlaskTest {

    private static int numQuestions = Main.numQuestions;

    @Test
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