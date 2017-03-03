package comp4350.triviasmack.business;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import comp4350.triviasmack.objects.Question;

public class ServerAccessObject implements ServerAccess {

    private URL baseUrl;

    public ServerAccessObject(int numQuestions){
        try {
            baseUrl
            = new URL("http://trivia-env.vwcgzcxeet.us-west-2.elasticbeanstalk.com/api/android/question_data/" +
                      "" + numQuestions);
        }
        catch (java.net.MalformedURLException e){}
    }

    public void open(){}

    public void close(){}

    public void getRandomQuestions(ArrayList<Question> questions){
        JSONObject result = null;
        BackgroundTask serverTask;

        serverTask = new BackgroundTask();

        try {
            result = serverTask.execute(baseUrl).get();
        }
        catch (java.lang.InterruptedException e){}
        catch (java.util.concurrent.ExecutionException e){}

        questions.addAll(ParseJSON.parseJSONquestions(result));
    }
}
