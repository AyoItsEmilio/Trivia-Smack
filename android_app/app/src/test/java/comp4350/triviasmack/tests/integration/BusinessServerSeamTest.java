package comp4350.triviasmack.tests.integration;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import comp4350.triviasmack.application.Services;
import comp4350.triviasmack.business.AccessQuestions;
import comp4350.triviasmack.objects.Question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class BusinessServerSeamTest {

    private int numQuestions;
    private AccessQuestions accessQuestions;
    private ArrayList<Question> questions;

    @Before
    public void setUp() throws Exception {
        Services.closeServerAccess();
        Services.createServerAccess();
        Services.createAsyncFacade(new SyncFetchFacade());
        questions = new ArrayList<>();
        accessQuestions = new AccessQuestions();
    }

    @After
    public void tearDown() throws Exception {
        Services.closeServerAccess();
        questions = null;
        accessQuestions = null;
    }

    @Test
    public void testAccessQuestions() {
        PowerMockito.mockStatic(Log.class);

        System.out.println("Starting Integration test, AccessQuestions (using default server)");

        System.out.println("Testing AccessQuestions: getRandomQuestions(3)");

        questions = new ArrayList<>();
        numQuestions = 3;
        accessQuestions.getRandomQuestions(questions, numQuestions);

        assertEquals(questions.size(), numQuestions);
        assertNotNull(questions);

        for (int i = 0; i < questions.size(); i++) {
            assertNotNull(questions.get(i));
            assertNotNull(questions.get(i).getAnswer());
            assertNotNull(questions.get(i).getOptions());
        }

        numQuestions = 9;
        questions = new ArrayList<>();
        System.out.println("Testing AccessQuestions: getRandomQuestions(9)");

        accessQuestions.getRandomQuestions(questions, numQuestions);

        assertEquals(questions.size(), numQuestions);
        assertNotNull(questions);

        for (int i = 0; i < questions.size(); i++) {
            assertNotNull(questions.get(i));
            assertNotNull(questions.get(i).getAnswer());
            assertNotNull(questions.get(i).getOptions());
        }

        numQuestions = 0;
        questions = new ArrayList<>();
        System.out.println("Testing AccessQuestions: getRandomQuestions(0)");

        accessQuestions.getRandomQuestions(questions, numQuestions);

        assertEquals(questions.size(), numQuestions);
        assertNotNull(questions);
        assertTrue(questions.isEmpty());

        numQuestions = 3;
        questions = new ArrayList<>();
        System.out.println("Testing AccessQuestions: getRandomQuestions(), uniqueness");

        accessQuestions.getRandomQuestions(questions, numQuestions);

        for (int i = 0; i < questions.size(); i++) {
            for (int j = 0; j < questions.size(); j++) {
                if (i != j) {
                    assertNotSame(questions.get(i), questions.get(j));
                }
            }
        }

        numQuestions = -1;
        questions = new ArrayList<>();
        System.out.println("Testing AccessQuestions: getRandomQuestions(), failure");

        try {
            accessQuestions.getRandomQuestions(questions, numQuestions);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }
}
