package comp4350.triviasmack.business;


import java.util.ArrayList;
import java.util.Collections;

import comp4350.triviasmack.objects.Question;

public class PracticeModeController {
    private static PracticeModeController instance = null;
    private ArrayList<Question> questions;
    private AccessQuestions accessQuestions;
    public static int numQuestionsAttempted;
    public static int numQuestionsCorrect;
    private Question currQuestion;

    protected PracticeModeController(){
        accessQuestions = new AccessQuestions();
        questions = new ArrayList<>();
        accessQuestions.getAllQuestions(questions);
        Collections.shuffle(questions);
        numQuestionsAttempted = 0;
        numQuestionsCorrect = 0;
    }

    public Question getNextQuestion() {
        if (questions.isEmpty()) {
            accessQuestions.getAllQuestions(questions);
            Collections.shuffle(questions);
        }
        currQuestion = questions.get(0);
        questions.remove(0);

        return currQuestion;
    }

    public static PracticeModeController getInstance() {
        if (instance == null) {
            instance = new PracticeModeController();
        }
        return instance;
    }

    public boolean evaluateAnswer(String playersAnswer) {
        increaseNumQuestionsAttempted();
        boolean result = false;
        String answer = currQuestion.getOptions()[currQuestion.getAnswer()];

        if (playersAnswer.equalsIgnoreCase(answer)) {
            result = true;
        }
        return result;
    }

    public void increaseNumCorrect(){
        numQuestionsCorrect++;
    }

    public void increaseNumQuestionsAttempted(){numQuestionsAttempted++;}

    public int getNumQuestionsAttempted(){
        return numQuestionsAttempted;
    }

    public int getNumQuestionsCorrect(){
        return numQuestionsCorrect;
    }

    public double getPercentCorrect(){
        return (double)numQuestionsCorrect/numQuestionsAttempted;
    }

    public String getPercentCorrectFmt(){
        String formattedPercent;

        if(getPercentCorrect() > 0){
            formattedPercent = String.format("%.2f", getPercentCorrect()*100);
        }
        else{
            formattedPercent = "0";
        }

        return formattedPercent;
    }

    public static void destroy(){
        instance = null;
    }
}
