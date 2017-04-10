package comp4350.triviasmack.tests.business;


import org.junit.After;
import org.junit.Test;

import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.PracticeModeController;
import comp4350.triviasmack.objects.Question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotNull;

public class PracticeModeControllerTest {
    @After
    public void tearDown() throws Exception {
        Services.closeServerAccess();
        PracticeModeController.destroy();
    }

    public static void practiceModeControllerTest() {
        System.out.println("Testing PracticeModeController: Singleton");

        PracticeModeController first = PracticeModeController.getInstance();
        PracticeModeController second = PracticeModeController.getInstance();

        assertEquals(first, second);
        assertSame(first, second);

        first.increaseNumQuestionsAttempted();
        assertSame(first.getNumQuestionsAttempted(),second.getNumQuestionsAttempted());
        first.increaseNumCorrect();
        assertSame(first.getNumQuestionsCorrect(),second.getNumQuestionsCorrect());

        first.destroy();
        second.destroy();

        PracticeModeController practiceModeController = PracticeModeController.getInstance();

        System.out.println("Testing PracticeModeController: Increasing Number of Questions Attempted");

        for(int i = 0; i < 5; i++){
            assertEquals(practiceModeController.getNumQuestionsAttempted(), i);
            practiceModeController.increaseNumQuestionsAttempted();
        }

        System.out.println("Testing PracticeModeController: Increasing Number of Questions Answered Correctly");

        for(int i = 0; i < 5; i++){
            assertEquals(practiceModeController.getNumQuestionsCorrect(), i);
            practiceModeController.increaseNumCorrect();
        }

        System.out.println("Testing PracticeModeController: getNextQuestion");
        Question question = practiceModeController.getNextQuestion();
        assertNotNull(question);
        assertEquals(question.getClass(),Question.class);

    }

    @Test
    public void testServerAccess() {
        Services.closeServerAccess();
        Services.createServerAccess(new ServerAccessStub());
        System.out.println("Testing ServerAccess (stub)");
        practiceModeControllerTest();
    }
}
