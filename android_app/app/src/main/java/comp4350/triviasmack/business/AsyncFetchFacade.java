package comp4350.triviasmack.business;


import android.util.Log;

import org.json.JSONObject;

import java.net.URL;

public class AsyncFetchFacade implements FetchFacade {

    public JSONObject executeTask(URL baseUrl) {
        JSONObject result = null;

        ReceiveBackgroundTask serverTask;

        serverTask = new ReceiveBackgroundTask();

        try {
            result = serverTask.execute(baseUrl).get();
        } catch (java.lang.InterruptedException e) {
            Log.e("AsyncFetchFacade.java", "InterruptedException", e);
        } catch (java.util.concurrent.ExecutionException e) {
            Log.e("AsyncFetchFacade.java", "ExecutionException", e);
        }

        return result;
    }
}
