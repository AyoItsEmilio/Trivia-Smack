package comp4350.triviasmack.tests.integration;

import junit.framework.TestCase;

import org.junit.Test;

import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.tests.business.ServerAccessTest;

public class ServerAccessFlaskTest extends TestCase {

    public ServerAccessFlaskTest(String arg0) {
        super(arg0);
    }

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