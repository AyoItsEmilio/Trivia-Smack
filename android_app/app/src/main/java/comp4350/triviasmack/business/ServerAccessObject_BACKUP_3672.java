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

    public ServerAccessObject() {
        baseUrl = "http://trivia-env.vwcgzcxeet.us-west-2.elasticbeanstalk.com/api/android/question_data/";
    }

    public void open() {
    }

    public void close() {
    }

    public void getRandomQuestions(ArrayList<Question> questions, int numQuestions) {
        try {
            url = new URL(baseUrl + numQuestions + "");
        } catch (java.net.MalformedURLException e) {
        }
<<<<<<< HEAD
        catch (MalformedURLException e){
            Log.e("ServerAccessObject","MalformedURLException thrown", e);
        }
=======
>>>>>>> 5133d1772ad0436658103385e431f07a3b24ebc2

        JSONObject result = Services.createAsyncFacade().executeTask(url);

        questions.addAll(ParseJSON.parseJSONquestions(result));
    }
}
