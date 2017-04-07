package comp4350.triviasmack.business;

import org.json.JSONObject;

import java.net.URL;


public interface FetchFacade {
    JSONObject executeTask(URL baseUrl);
}
