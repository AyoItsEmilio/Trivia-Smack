package comp4350.triviasmack.tests.integration;

import org.json.JSONObject;

import java.net.URL;

import comp4350.triviasmack.business.AsyncFacade;
import comp4350.triviasmack.business.BackgroundTask;

class AsyncFacadeStub implements AsyncFacade {

    public JSONObject executeTask(URL baseUrl) {
        return new BackgroundTask().fetchFromUrl(baseUrl);
    }

    public void executeTask(String url, int score) {

    }
}
