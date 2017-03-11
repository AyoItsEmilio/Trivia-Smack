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
        if (numQuestions < 0){
            throw new IllegalArgumentException("Num questions cannot be less than 0");
        }
        serverAccess.getRandomQuestions(questions, numQuestions);
    }
}
