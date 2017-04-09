package comp4350.triviasmack.business;

import java.util.ArrayList;

import comp4350.triviasmack.objects.Question;

public interface ServerAccess {

    void getRandomQuestions(ArrayList<Question> questions, int numQuestions, String category);
}
