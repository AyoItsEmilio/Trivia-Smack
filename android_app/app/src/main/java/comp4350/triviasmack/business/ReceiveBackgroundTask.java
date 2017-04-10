package comp4350.triviasmack.business;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReceiveBackgroundTask extends AsyncTask<URL, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(URL... urls) {
        return fetchFromUrl(urls[0]);
    }

    public JSONObject fetchFromUrl(URL url) {

        JSONObject result = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String line;
        final String LOG_TAG = ReceiveBackgroundTask.class.getSimpleName();
        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                result = null;
            }
            reader = new BufferedReader(new InputStreamReader((inputStream)));

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                result = null;
            }
            result = new JSONObject(buffer.toString());

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return result;
    }
}
