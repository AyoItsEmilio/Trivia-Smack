package comp4350.triviasmack.tests.integration;

import org.junit.Test;

import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.tests.business.GameControllerTest;


import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import android.util.Log;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class GameControllerFlaskTest {

    @Test(expected=NullPointerException.class)
    public void testGameController() {
        PowerMockito.mockStatic(Log.class);

        Services.closeServerAccess();

        System.out.println("Starting Integration test, GameController (using default server)");

        Services.createServerAccess();
        Services.createAsyncFacade(new AsyncFacadeStub());

        GameControllerTest.gameControllerTest();
    }
}
