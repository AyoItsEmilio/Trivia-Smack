package comp4350.triviasmack.business;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SendBackgroundTask extends AsyncTask<String, Void, Void>{

    @Override
    protected Void doInBackground(String... args) {

        HttpURLConnection urlConnection = null;
        DataOutputStream writer = null;
        String data;
        URL url;

        try {

            url = new URL(args[0]);
            data = "score="+args[1];

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            writer = new DataOutputStream(urlConnection.getOutputStream());
            writer.writeBytes(data);
            writer.flush();
            writer.close();
            urlConnection.getInputStream();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
        }
        catch (Exception e) {
            Log.e("MainActivity", "Error communicating with server", e);
        }
        finally {

            if (urlConnection != null) { urlConnection.disconnect(); }

            if (writer != null) {

                try { writer.close(); }
                catch (final IOException e) {
                    Log.e("MainActivity", "Error closing Stream", e);
                }
            }
        }
        return null;
    }
}
