package comp4350.triviasmack.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp4350.triviasmack.objects.Question;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class QuestionTest {

    private String question;
    private String[] options;
    private int answer;
    private Question questionObj;

    @Before
    public void setUp() throws Exception {
        question = "What's my favorite color?";
        options = new String[]{"blue", "green", "red"};
        answer = 0;
        questionObj = new Question(question, options, answer);
    }

    @After
    public void tearDown() throws Exception {
        questionObj = null;
    }

    @Test
    public void testConstructor() {
        System.out.println("Testing Question: Constructor");

        assertNotNull(questionObj);
        assertEquals(question, questionObj.getQuestion());
        assertArrayEquals(options, questionObj.getOptions());
        assertEquals(answer, questionObj.getAnswer());
    }

    @Test
    public void testAccessors() {
        System.out.println("Testing Question: Mutators");

        assertEquals(answer, questionObj.getAnswer());
        assertEquals(options, questionObj.getOptions());
        assertEquals(question, questionObj.getQuestion());
    }

    @Test
    public void testEmptyQuestion() {
        System.out.println("Testing Question: EmptyQuestion");

        Question q1 = new Question("", new String[0], 0);

        assertEquals(q1.getQuestion(), "");
        assertEquals(q1.getOptions().length, 0);
        assertEquals(q1.getAnswer(), 0);
    }

    @Test
    public void testFailure() {
        System.out.println("Testing Question: Invalid Args");

        try{
            new Question(null, null, -1);
            fail("Expected a NullPointerException");
        } catch (NullPointerException ignored) {}
    }
}
