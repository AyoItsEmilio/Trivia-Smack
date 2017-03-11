package comp4350.triviasmack.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.GameController;
import comp4350.triviasmack.objects.Question;


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
    }

    @Test
    public void testAccessors(){
        System.out.println("Testing GameController: Accessors");

        dummyGameController.start();

        ArrayList<Question> questions = new ArrayList<>();

        dummyGameController.getQuestions(questions);

        assertNotNull(questions);

        for (int i = 0; i < questions.size(); i++){
            assertTrue(questions.get(i) instanceof Question);
        }
    }

    @Test
    public void testGetNextQuestions(){
        System.out.println("Testing GameController: getNextQuestion");

        dummyGameController.start();

        Question questionObj = dummyGameController.getNextQuestion();

        assertNotNull(questionObj);
    }
}
