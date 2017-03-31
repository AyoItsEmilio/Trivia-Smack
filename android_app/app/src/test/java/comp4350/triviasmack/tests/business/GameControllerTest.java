package comp4350.triviasmack.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.GameController;
import comp4350.triviasmack.objects.Question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotEquals;


public class GameControllerTest {

    @After
    public void tearDown() throws Exception {
        Services.closeServerAccess();
        GameController.destroy();
    }

    public static void gameControllerTest() {

        GameController dummyGameController = GameController.getInstance();
        int bigNum = 100;

        System.out.println("Testing GameController: Singleton");

        GameController first = GameController.getInstance();
        GameController second = GameController.getInstance();

        assertEquals(first, second);
        assertSame(first, second);
        assertEquals(first.getScore(), second.getScore());
        first.start();
        assertEquals(first.isStarted(), second.isStarted());

        assertNotEquals(first.getNextQuestion().getQuestion(),
                second.getNextQuestion().getQuestion());

        second.increaseScore(1);
        assertEquals(first.getScore(), second.getScore());

        System.out.println("Testing GameController: Start");

        dummyGameController.destroy();
        dummyGameController = GameController.getInstance();

        assertFalse(dummyGameController.isStarted());
        dummyGameController.start();
        assertTrue(dummyGameController.isStarted());

        System.out.println("Testing GameController: IncreaseScore");

        dummyGameController.start();
        assertEquals(0, dummyGameController.getScore());
        dummyGameController.increaseScore(1);
        assertEquals(1, dummyGameController.getScore());
        dummyGameController.increaseScore(1);
        assertEquals(2, dummyGameController.getScore());

        for (int i = 0; i < Main.numQuestions * bigNum; i++) {
            dummyGameController.increaseScore(1);
        }

        int maxScore = Main.numQuestions * 10;
        assertEquals(maxScore, dummyGameController.getScore());

        System.out.println("Testing GameController: getNextQuestion");

        Question questionObj;
        ArrayList<Question> questions = new ArrayList<>();

        dummyGameController.start();

        questionObj = dummyGameController.getNextQuestion();

        assertNotNull(questionObj);

        assertNotEquals(dummyGameController.getNextQuestion().getQuestion(),
                dummyGameController.getNextQuestion().getQuestion());

        dummyGameController.start();

        questionObj = dummyGameController.getNextQuestion();
        while (null != questionObj) {
            questions.add(questionObj);
            questionObj = dummyGameController.getNextQuestion();
        }

        assertEquals(questions.size(), Main.numQuestions);

        System.out.println("Testing GameController: isFinished");

        dummyGameController.destroy();
        dummyGameController = GameController.getInstance();

        assertFalse(dummyGameController.finished());
        dummyGameController.start();
        assertFalse(dummyGameController.finished());

        questionObj = dummyGameController.getNextQuestion();
        while (null != questionObj) {
            questionObj = dummyGameController.getNextQuestion();
        }

        assertTrue(dummyGameController.finished());
    }

    @Test
    public void testServerAccess() {
        Services.closeServerAccess();
        Services.createServerAccess(new ServerAccessStub());
        System.out.println("Testing ServerAccess (stub)");
        gameControllerTest();
    }
}
