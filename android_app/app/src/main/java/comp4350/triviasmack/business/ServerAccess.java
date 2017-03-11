package comp4350.triviasmack.business;

import java.util.ArrayList;
import java.util.List;

import comp4350.triviasmack.objects.Question;

public interface ServerAccess {
    void open();

    void close();

    void getRandomQuestions(ArrayList<Question> questions, int numQuestions);
}
