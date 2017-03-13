package comp4350.triviasmack.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp4350.triviasmack.objects.Profile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ProfileTest {

    private String username;
    private int score;
    private Profile profileObj;

    @Before
    public void setUp() throws Exception {
        username = "Doug Exeter";
        score = 3;
        profileObj = new Profile(username, score);
    }

    @After
    public void tearDown() throws Exception {
        profileObj = null;
    }

    @Test
    public void testConstructor() {
        System.out.println("Testing Profile: Constructor");

        assertNotNull(profileObj);
        assertEquals(username, profileObj.getUsername());
        assertEquals(score, profileObj.getScore());
    }

    @Test
    public void testAccessors() {
        System.out.println("Testing Profile: Mutators");

        assertEquals(username, profileObj.getUsername());
        assertEquals(score, profileObj.getScore());
    }

    @Test
    public void testEmptyQuestion() {
        System.out.println("Testing Profile: EmptyQuestion");

        Profile p1 = new Profile("", 0);

        assertEquals(p1.getUsername(), "");
        assertEquals(p1.getScore(), 0);
    }

    @Test
    public void testSetters() {
        System.out.println("Testing Profile: Mutators");

        Profile p1 = new Profile("Cal Meacham", 1);
        p1.setScore(3);
        p1.setUsername(username);

        assertEquals(p1.getUsername(), username);
        assertEquals(p1.getScore(), score);

        p1.addScore(2);

        assertEquals(p1.getScore(), score+2);
    }

    @Test
    public void testFailure() {
        System.out.println("Testing Profile: Invalid Args");

        try {
            new Profile(null, -1);
            fail("Expected a NullPointerException");
        } catch (NullPointerException ignored) {
        }
    }
}
