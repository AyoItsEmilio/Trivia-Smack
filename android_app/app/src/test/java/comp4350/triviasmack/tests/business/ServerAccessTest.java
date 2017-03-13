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
import static org.junit.Assert.fail;

public class ServerAccessTest {

    public static void testGetRandomQuestion3()
    {
        ServerAccess serverAccess = Services.getServerAccess();
        ArrayList<Question> questions;
        int numQuestions;

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
    }

    public static void testGetRandomQuestion9()
    {
        ServerAccess serverAccess = Services.getServerAccess();
        ArrayList<Question> questions;
        int numQuestions;

        questions = new ArrayList<>();
        numQuestions = 9;

        System.out.println("Testing ServerAccess: getRandomQuestions(9)");

        serverAccess.getRandomQuestions(questions, numQuestions);

        assertEquals(questions.size(), numQuestions);
        assertNotNull(questions);

        for (int i = 0; i < questions.size(); i++){
            assertTrue(questions.get(i) instanceof Question);
            assertNotNull(questions.get(i).getAnswer());
            assertNotNull(questions.get(i).getOptions());
        }
    }

    public static void testGetRandomQuestion0()
    {
        ServerAccess serverAccess = Services.getServerAccess();
        ArrayList<Question> questions;
        int numQuestions;

        questions = new ArrayList<>();
        numQuestions = 0;

        System.out.println("Testing ServerAccess: getRandomQuestions(0)");

        serverAccess.getRandomQuestions(questions, numQuestions);

        assertEquals(questions.size(), numQuestions);
        assertNotNull(questions);

        for (int i = 0; i < questions.size(); i++){
            assertTrue(questions.get(i) instanceof Question);
            assertNotNull(questions.get(i).getAnswer());
            assertNotNull(questions.get(i).getOptions());
        }
    }

    public static void testGetRandomQuestionNegative()
    {
        ServerAccess serverAccess = Services.getServerAccess();
        ArrayList<Question> questions;
        int numQuestions;

        questions = new ArrayList<>();
        numQuestions = -1;

        System.out.println("Testing ServerAccess: getRandomQuestions(-1)");

        try
        {
           serverAccess.getRandomQuestions(questions, numQuestions);

            fail("Failed to catch exception.");
        }
        catch(Exception e)
        {
            assertEquals("Didn't throw the right exception", IllegalArgumentException.class, e.getClass());

        }
    }


    public static void testGetRandomQuestionNull()
    {
        ServerAccess serverAccess = Services.getServerAccess();
        int numQuestions;

        numQuestions = 0;

        System.out.println("Testing ServerAccess: getRandomQuestions(questions=null)");

        try
        {
            serverAccess.getRandomQuestions(null, numQuestions);
            fail("Failed to catch exception.");
        }
        catch(Exception e)
        {
            assertEquals("Didn't throw the right exception", NullPointerException.class, e.getClass());

        }
    }

    public static void testGetRandomQuestionNullNegative()
    {
        ServerAccess serverAccess = Services.getServerAccess();
        ArrayList<Question> questions;
        int numQuestions;

        questions = new ArrayList<>();
        numQuestions = -1;

        System.out.println("Testing ServerAccess: getRandomQuestions(questions=null, -1)");

        try
        {
            serverAccess.getRandomQuestions(null, numQuestions);
            fail("Failed to catch exception.");
        }
        catch(Exception e)
        {
            assertEquals("Didn't throw the right exception", IllegalArgumentException.class, e.getClass());

        }
    }

    /*
    @Test
    Trying to test for number of questions greater than the actual amount of questions, but this
    will be different in the stub and sever....
    public void testGetRandomQuestion10()
    {
        ServerAccess serverAccess = Services.getServerAccess();
        ArrayList<Question> questions;
        int numQuestions;

        questions = new ArrayList<>();
        numQuestions = 10;

        System.out.println("Testing ServerAccess: getRandomQuestions(10)");

        try{
            serverAccess.getRandomQuestions(questions, numQuestions);
            fail("Failed to catch exception");
        }
        catch (Exception e){
            assertEquals("Didn't throw the right exception", IndexOutOfBoundsException.class, e.getClass());
        }
    }
    */

    public static void serverAccessTest()
    {
        testGetRandomQuestion0();
        testGetRandomQuestion3();
        testGetRandomQuestion9();
        testGetRandomQuestionNull();
        testGetRandomQuestionNegative();
        testGetRandomQuestionNullNegative();
    }

    @Test
    public void testServerAccess()
    {
        System.out.println("Testing ServerAccess (stub)");
        serverAccessTest();
    }

    @Before
    public void setUp()
    {
        ServerAccess serverAccess;

        Services.closeServerAccess();

        serverAccess = Services.createServerAccess(new ServerAccessStub());
    }

    @After
    public void tearDown()
    {
        Services.closeServerAccess();
    }
}
