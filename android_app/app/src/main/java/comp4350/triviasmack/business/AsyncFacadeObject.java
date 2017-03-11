package comp4350.triviasmack.business;


import org.json.JSONObject;

import java.net.URL;

public class AsyncFacadeObject implements AsyncFacade {

    public JSONObject executeTask(URL baseUrl) {
        JSONObject result = null;

        BackgroundTask serverTask;

        serverTask = new BackgroundTask();

        try {
            result = serverTask.execute(baseUrl).get();
        } catch (java.lang.InterruptedException e) {
        } catch (java.util.concurrent.ExecutionException e) {
        }

        return result;
    }
}
