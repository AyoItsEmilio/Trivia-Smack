package comp4350.triviasmack.business;

import android.util.Log;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.objects.Question;

public class ServerAccessObject implements ServerAccess {

    private static final String baseUrl =
            "http://trivia-env.vwcgzcxeet.us-west-2.elasticbeanstalk.com/";
    private String questionUrl;
    private String correctUrl;
    private URL url;

    public ServerAccessObject(){
        questionUrl = baseUrl + "api/question_data/";
        correctUrl = baseUrl + "api/post_score";
    }

    public void open() {}

    public void close() {}

    public void getRandomQuestions(ArrayList<Question> questions, int numQuestions) {
        try {
            if(numQuestions < 0){
                throw new IllegalArgumentException("Number of questions cannot be less than 0");
            }
            url = new URL(questionUrl + numQuestions + "");

        }catch (MalformedURLException e){
            Log.e("ServerAccessObject.java","MalformedURLException", e);
        }

        JSONObject result = Services.createAsyncFacade().executeTask(url);

        questions.addAll(ParseJSON.parseJSONQuestions(result));
    }

    public void sendTotalScore(int score) {
        SendBackgroundTask serverTask;

        serverTask = new SendBackgroundTask();

        serverTask.execute(correctUrl, new String(score+""));
    }
}
