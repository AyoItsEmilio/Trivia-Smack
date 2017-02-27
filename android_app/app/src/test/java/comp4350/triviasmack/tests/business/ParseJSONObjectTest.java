package comp4350.triviasmack.tests.business;

import junit.framework.TestCase;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import comp4350.triviasmack.business.ServerAccessObject;
import comp4350.triviasmack.objects.Question;

import static org.junit.Assert.assertArrayEquals;

public class ParseJSONObjectTest extends TestCase {

    private ServerAccessObject serverAccessObject;

    public ParseJSONObjectTest(String arg0) {
        super(arg0);
        serverAccessObject = new ServerAccessObject(0);
    }

    public void testParseJSON(){

        System.out.println("Testing ParseJSONObject: Parse Basic JSON Object");
        ArrayList<Question> q;

        try {
            JSONObject json = new JSONObject();
            JSONArray result = new JSONArray();

            result.put(buildJSONObject());
            json.put("result", result);

            q = serverAccessObject.parseJSONObject(json);

            assertNotNull(q);
            assertEquals(1, q.size());
            assertEquals("The Balkans are in:", q.get(0).getQuestion());

            assertEquals("South America", q.get(0).getOptions()[0]);
            assertEquals("Europe", q.get(0).getOptions()[1]);
            assertEquals("Australia", q.get(0).getOptions()[2]);
            assertEquals("Asia", q.get(0).getOptions()[3]);

            assertEquals(1, q.get(0).getAnswer());

        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void testBadFormat(){

        System.out.println("Testing ParseJSONObject: Bad Formatted JSON");
        ArrayList<Question> q;
        JSONObject json = new JSONObject();

        try {
            json.put("Bad", "NULL");
            json.put(" Still Bad", "NULL");
            q = serverAccessObject.parseJSONObject(json);
            assertNull(q);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void testLargeJSONArray(){
        System.out.println("Testing ParseJSONObject: Parse Large JSON Object");
        ArrayList<Question> q;
        final int MAX_ARRAY_SIZE = 100;

        try {
            JSONObject json = new JSONObject();
            JSONArray result = new JSONArray();

            for(int i = 0; i < MAX_ARRAY_SIZE; i++)
                result.put(buildJSONObject());
            json.put("result", result);

            q = serverAccessObject.parseJSONObject(json);

            assertNotNull(q);
            assertEquals(MAX_ARRAY_SIZE, q.size());

            for(int i = 0; i < MAX_ARRAY_SIZE; i++) {
                assertEquals("The Balkans are in:", q.get(i).getQuestion());

                assertEquals("South America", q.get(i).getOptions()[0]);
                assertEquals("Europe", q.get(i).getOptions()[1]);
                assertEquals("Australia", q.get(i).getOptions()[2]);
                assertEquals("Asia", q.get(i).getOptions()[3]);

                assertEquals(1, q.get(i).getAnswer());
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    private JSONObject buildJSONObject(){

        JSONObject json;

        try {
            json = new JSONObject();
            JSONArray options = new JSONArray();

            options.put("South America");
            options.put("Europe");
            options.put("Australia");
            options.put("Asia");

            json.put("answer", new Integer(1));
            json.put("options", options);
            json.put("question", "The Balkans are in:");
        }
        catch(JSONException e){
            e.printStackTrace();
            json = null;
        }
        return json;
    }
}
