package comp4350.triviasmack.business;


import java.util.ArrayList;
import java.util.Collections;

import comp4350.triviasmack.objects.Question;

public class PracticeModeController {
    ArrayList<Question> questions;
    ArrayList<Question> seenQuestons;
    private AccessQuestions accessQuestions;

    protected PracticeModeController(){
        accessQuestions = new AccessQuestions();
        accessQuestions.getAllQuestions(questions);
        seenQuestons = new ArrayList<>();

        Collections.shuffle(questions);
    }

    public Question getNextQuestion() {
        if (questions.isEmpty()) {
            questions = new ArrayList<>(seenQuestons);
            seenQuestons = new ArrayList<>();

            Collections.shuffle(questions);
        }

        Question currQuestion = questions.get(0);
        seenQuestons.add(questions.remove(0));

        return currQuestion;
    }
}
