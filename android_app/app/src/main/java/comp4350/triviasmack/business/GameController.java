package comp4350.triviasmack.business;

import java.util.ArrayList;

import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.objects.Question;

public class GameController {

    private static GameController instance = null;
    private final static int maxQuestions = Main.numQuestions;
    private final static int maxScore = maxQuestions * 10;

    private ArrayList<Question> questions;
    private int questionCount;
    private Question currQuestion;
    private int score;
    private boolean started;
    private String category;
    private AccessQuestions accessQuestions;

    protected GameController() {
        questions = null;
        questionCount = -1;
        currQuestion = null;
        category = null;
        score = -1;
        accessQuestions = new AccessQuestions();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public void start() {
        questionCount = 0;
        score = 0;
        questions = new ArrayList<>();
        accessQuestions.getRandomQuestions(questions, maxQuestions, category);

        started = true;
    }

    public Question getNextQuestion() {
        if (questionCount == maxQuestions) {
            currQuestion = null;
        } else {
            currQuestion = questions.get(questionCount);
            questionCount++;
        }

        return currQuestion;
    }

    public boolean evaluateAnswer(String playersAnswer) {
        boolean result = false;
        String answer = currQuestion.getOptions()[currQuestion.getAnswer()];

        if (playersAnswer.equalsIgnoreCase(answer)) {
            result = true;
        }
        return result;
    }

    public void increaseScore(int score) {
        if (this.score < maxScore) {
            this.score += score;
        }
    }

    public void setCategory(String category){
        this.category = category;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean finished() {
        return maxQuestions == questionCount;
    }

    public static void destroy() {
        instance = null;
    }
}
