package comp4350.triviasmack.business;

import java.util.ArrayList;

import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.objects.Question;

public class AccessQuestions {

    private ServerAccess serverAccess;

    public AccessQuestions(){
        serverAccess = Services.getServerAccess();
    }

    public void getRandomQuestions(ArrayList<Question> questions, int numQuestions){
        serverAccess.getRandomQuestions(questions, numQuestions);
    }
}
