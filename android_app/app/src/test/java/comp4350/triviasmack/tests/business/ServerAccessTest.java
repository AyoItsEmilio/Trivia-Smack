package comp4350.triviasmack.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import static org.junit.Assert.*;

import comp4350.triviasmack.application.Main;
import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.objects.Question;

public class ServerAccessTest extends TestCase {

    public ServerAccessTest(String arg0) {
        super(arg0);
    }

    public static void serverAccessTest()
    {
        ServerAccess serverAccess;

        ArrayList<Question> questions;
        int numQuestions;

        serverAccess = Services.getServerAccess();
        questions = new ArrayList<>();
        numQuestions = 3;

        System.out.println("Testing ServerAccess: getRandomQuestions(3)");

        serverAccess.getRandomQuestions(questions, numQuestions);

        assertEquals(questions.size(), numQuestions);
        assertNotNull(questions);

        for (int i = 0; i < questions.size(); i++){
            assertTrue(questions.get(i) instanceof Question);
            assertNotNull(questions.get(i).getAnswer());
            assertNotNull(questions.get(i).getOptions());
        }

        numQuestions = 9;
        questions = new ArrayList<>();
        System.out.println("Testing ServerAccess: getRandomQuestions(9)");

        serverAccess.getRandomQuestions(questions, numQuestions);

        assertEquals(questions.size(), numQuestions);
        assertNotNull(questions);

        for (int i = 0; i < questions.size(); i++){
            assertTrue(questions.get(i) instanceof Question);
            assertNotNull(questions.get(i).getAnswer());
            assertNotNull(questions.get(i).getOptions());
        }

        System.out.println("Testing ServerAccess: getRandomQuestions(9)");

        numQuestions = 0;
        questions = new ArrayList<>();
        System.out.println("Testing ServerAccess: getRandomQuestions(0)");

        serverAccess.getRandomQuestions(questions, numQuestions);

        assertEquals(questions.size(), numQuestions);
        assertNotNull(questions);

        Services.closeServerAccess();
    }

    public void testServerAccess()
    {
        ServerAccess serverAccess;

        Services.closeServerAccess();

        serverAccess = Services.createServerAccess(new ServerAccessStub());

        System.out.println("Testing ServerAccess (stub)");
        serverAccessTest();
    }
}
