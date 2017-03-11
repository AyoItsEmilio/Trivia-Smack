package comp4350.triviasmack.business;

import android.util.Log;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.objects.Question;

public class ServerAccessObject implements ServerAccess {

    private String baseUrl;
    private URL url;

    public ServerAccessObject(){
        baseUrl = "http://trivia-env.vwcgzcxeet.us-west-2.elasticbeanstalk.com/api/android/question_data/";
    }

    public void open(){}

    public void close(){}

    public void getRandomQuestions(ArrayList<Question> questions, int numQuestions){
        try {
            url = new URL(baseUrl + numQuestions + "");
        }
        catch (MalformedURLException e){
            Log.e("ServerAccessObject","MalformedURLException thrown", e);
        }

        JSONObject result = Services.createAsyncFacade().executeTask(url);

        questions.addAll(ParseJSON.parseJSONquestions(result));
    }
}
