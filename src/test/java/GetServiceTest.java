import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static io.restassured.RestAssured.baseURI;

public class GetServiceTest {

    @BeforeAll
    static void setup() {
        baseURI = "https://jsonplaceholder.typicode.com/todos/";
    }

    JSONObject expected = new JSONObject( "{\"id\": 2, \"completed\": false}");

    @Test
    public void GetOpportunitiesByIdTest() throws JSONException {

        JSONArray jsonArray = Services.searchResponseToJSONArray(Services.get(baseURI, 200));
        JSONObject actual = Services.getIDFromArray(jsonArray, 2);
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
    }
}
