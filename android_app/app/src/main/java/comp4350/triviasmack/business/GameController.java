package comp4350.triviasmack.business;

import java.util.ArrayList;

import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.objects.Profile;
import comp4350.triviasmack.objects.Question;

public class GameController {

    private static GameController instance = null;
    private final static int maxQuestions = Main.numQuestions;

    private ArrayList<Question> questions;
    private int questionCount;
    private Question currQuestion;
    private Profile profile;
    private int score;
    private boolean started;
    private AccessQuestions accessQuestions;

    protected GameController() {
        questions = null;
        questionCount = -1;
        currQuestion = null;
        profile = new Profile();
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
        return profile.getScore();
    }

    public void start() {
        questionCount = 0;
        score = 0;
        questions = new ArrayList<Question>();
        accessQuestions.getRandomQuestions(questions, maxQuestions);
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

    public void increaseScore() {
        if (score < maxQuestions)
            score++;
    }

    public void setNewUsername(String username) {
        profile.setUsername(username);
    }

    public String getUsername() {
        return profile.getUsername();
    }

    public boolean isStarted() {
        return started;
    }

    public boolean finished() {
        boolean finished = false;

        if(maxQuestions == questionCount){
            finished = true;
            profile.addScore(score);
        }

        return finished;
    }

    public static void destroy() {
        instance = null;
    }
}
