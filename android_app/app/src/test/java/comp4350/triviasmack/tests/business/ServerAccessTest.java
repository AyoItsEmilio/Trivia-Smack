package comp4350.triviasmack.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.objects.Question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ServerAccessTest {

    @After
    public void tearDown()
    {
        Services.closeServerAccess();
    }

    public static void serverAccessTest() {

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
    }

    @Test
    public void testServerAccess() {

        Services.closeServerAccess();

        System.out.println("Testing ServerAccess (stub)");

        Services.createServerAccess(new ServerAccessStub());
        serverAccessTest();
    }
}
