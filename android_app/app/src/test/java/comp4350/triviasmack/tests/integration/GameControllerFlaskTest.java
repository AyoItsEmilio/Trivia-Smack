package comp4350.triviasmack.tests.integration;

import org.junit.Test;

import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.tests.business.GameControllerTest;

public class GameControllerFlaskTest {

    @Test
    public void testGameController() {

        Services.closeServerAccess();

        System.out.println("Starting Integration test, GameController (using default server)");

        Services.createServerAccess();
        Services.createAsyncFacade(new AsyncFacadeStub());

        GameControllerTest.gameControllerTest();
    }
}
