package comp4350.triviasmack.tests.integration;

import org.junit.Test;

import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.tests.business.PracticeModeControllerTest;

public class PracticeModeControllerFlaskTest {

    @Test
    public void testPracticeModeController(){
        Services.closeServerAccess();

        System.out.println("Starting Integration test, PracticeModeController (using default server)");

        Services.createServerAccess();
        Services.createAsyncFacade(new SyncFetchFacade());

        PracticeModeControllerTest.practiceModeControllerTest();
    }
}
