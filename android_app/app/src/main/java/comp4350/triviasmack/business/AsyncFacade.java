package comp4350.triviasmack.business;

import org.json.JSONObject;

import java.net.URL;


public interface AsyncFacade {
    JSONObject executeTask(URL baseUrl);
    void executeTask(String url, int score);
}
