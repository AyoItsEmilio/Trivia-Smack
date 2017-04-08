package comp4350.triviasmack.tests.integration;

import org.json.JSONObject;

import java.net.URL;

import comp4350.triviasmack.business.FetchFacade;
import comp4350.triviasmack.business.ReceiveBackgroundTask;

class SyncFetchFacade implements FetchFacade {

    public JSONObject executeTask(URL baseUrl) {
        return new ReceiveBackgroundTask().fetchFromUrl(baseUrl);
    }

    public void executeTask(String url, int score) {

    }
}
