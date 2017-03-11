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

    private GameController dummyGameController;

    @Before
    public void setUp() throws Exception {
        Services.closeServerAccess();
        Services.createServerAccess(new ServerAccessStub());
        dummyGameController = GameController.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        Services.closeServerAccess();
        GameController.destroy();
    }

    @Test
    public void testSingleton(){
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

        second.increaseScore();
        assertEquals(first.getScore(), second.getScore());
    }

    @Test
    public void testStart(){
        System.out.println("Testing GameController: Start");

        assertFalse(dummyGameController.isStarted());
        dummyGameController.start();
        assertTrue(dummyGameController.isStarted());
    }

    @Test
    public void testIncreaseScore(){
        System.out.println("Testing GameController: IncreaseScore");

        dummyGameController.start();
        assertEquals(0, dummyGameController.getScore());
        dummyGameController.increaseScore();
        assertEquals(1, dummyGameController.getScore());
        dummyGameController.increaseScore();
        assertEquals(2, dummyGameController.getScore());
    }

    @Test
    public void testGetNextQuestions(){
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
        while (null != questionObj){
            questions.add(questionObj);
            questionObj = dummyGameController.getNextQuestion();
        }

        assertEquals(questions.size(), Main.numQuestions);
    }

    @Test
    public void testIsFinished() {
        System.out.println("Testing GameController: isFinished");

        Question questionObj;

        assertFalse(dummyGameController.finished());
        dummyGameController.start();
        assertFalse(dummyGameController.finished());

        questionObj = dummyGameController.getNextQuestion();
        while (null != questionObj){
            questionObj = dummyGameController.getNextQuestion();
        }

        assertTrue(dummyGameController.finished());
    }
}
