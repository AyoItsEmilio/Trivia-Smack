package comp4350.triviasmack.tests.integration;

import org.junit.Test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.tests.business.ServerAccessTest;

import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import android.util.Log;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class ServerAccessFlaskTest {
    @Test
    public void testDataAccess() {
        PowerMockito.mockStatic(Log.class);

        ServerAccess serverAccess;

        Services.closeServerAccess();

        System.out.println("Starting Integration test, ServerAccess (using default server)");

        Services.createServerAccess();
        Services.createAsyncFacade(new AsyncFacadeStub());
        serverAccess = Services.getServerAccess();

        ServerAccessTest.serverAccessTest();
    }
}