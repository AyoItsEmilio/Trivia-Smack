package comp4350.triviasmack.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.GameController;
import comp4350.triviasmack.objects.Question;

public class GameControllerTest extends TestCase {

    private GameController dummyGameController;

    public GameControllerTest(String arg0) {
        super(arg0);
    }

    @Override
    protected void setUp() throws Exception {
        Services.closeServerAccess();
        Services.createServerAccess(new ServerAccessStub(Main.numQuestions));
        dummyGameController = GameController.getInstance();
    }

    @Override
    protected void tearDown() throws Exception {
        Services.closeServerAccess();
        GameController.destroy();
    }

    public void testSingleton(){
        System.out.println("Testing GameController: Singleton");
        GameController first = GameController.getInstance();
        GameController second = GameController.getInstance();
        assertEquals(first, second);
    }

    public void testStart(){
        System.out.println("Testing GameController: Start");
        assertFalse(dummyGameController.isStarted());
        dummyGameController.start();
        assertTrue(dummyGameController.isStarted());
    }

    public void testIncreaseScore(){
        System.out.println("Testing GameController: IncreaseScore");
        dummyGameController.start();
        assertEquals(0, dummyGameController.getScore());
        dummyGameController.increaseScore();
        assertEquals(1, dummyGameController.getScore());
    }

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

    public void testGetNextQuestions(){
        System.out.println("Testing GameController: getNextQuestion");

        dummyGameController.start();

        Question questionObj = dummyGameController.getNextQuestion();

        assertNotNull(questionObj);
    }
}
