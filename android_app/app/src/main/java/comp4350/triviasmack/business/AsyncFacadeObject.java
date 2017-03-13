package comp4350.triviasmack.business;


import android.util.Log;

import org.json.JSONObject;

import java.net.URL;

public class AsyncFacadeObject implements AsyncFacade {

    public JSONObject executeTask(URL baseUrl) {
        JSONObject result = null;

        ReceiveBackgroundTask serverTask;

        serverTask = new ReceiveBackgroundTask();

        try {
            result = serverTask.execute(baseUrl).get();
        } catch (java.lang.InterruptedException e) {
            Log.e("AsyncFacadeObject.java","InterruptedException",e);
        } catch (java.util.concurrent.ExecutionException e) {
            Log.e("AsyncFacadeObject.java","ExecutionException",e);
        }

        return result;
    }

    public void executeTask(String url, int score) {
        SendBackgroundTask serverTask;

        serverTask = new SendBackgroundTask();

        try {
            serverTask.execute(url, score+"").get();
        } catch (java.lang.InterruptedException e) {
            Log.e("AsyncFacadeObject.java","InterruptedException",e);
        } catch (java.util.concurrent.ExecutionException e) {
            Log.e("AsyncFacadeObject.java","ExecutionException",e);
        }
    }
}
