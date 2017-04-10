package comp4350.triviasmack.business;


import android.util.Log;

import org.json.JSONObject;

import java.net.URL;

public class AsyncFetchFacade implements FetchFacade {

    public JSONObject executeTask(URL baseUrl) {
        JSONObject result = null;
        final String LOG_TAG=AsyncFetchFacade.class.getSimpleName();
        ReceiveBackgroundTask serverTask;

        serverTask = new ReceiveBackgroundTask();

        try {
            result = serverTask.execute(baseUrl).get();
        } catch (java.lang.InterruptedException e) {
            Log.e(LOG_TAG, "InterruptedException", e);
        } catch (java.util.concurrent.ExecutionException e) {
            Log.e(LOG_TAG, "ExecutionException", e);
        }

        return result;
    }
}
