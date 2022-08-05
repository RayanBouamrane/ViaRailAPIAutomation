import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static io.restassured.RestAssured.given;

public class Services {

    public static final String baseURL = "https://jsonplaceholder.typicode.com/todos/";

    public static Response get(String url, int statusCode) {
        Response response = given()
                .relaxedHTTPSValidation()
                .accept(ContentType.JSON).when()
                .get(url);

        assertStatusCode(response, statusCode);

        return response;
    }

    private static void assertStatusCode(Response response, int statusCode) {
        response
                .then()
                .assertThat()
                .statusCode(statusCode)
                .log().all();
    }

    public static JSONArray searchResponseToJSONArray(Response response) throws JSONException {

        JsonPath jsonPath = response.jsonPath();

        String jsonString;

        try {
            Method method = jsonPath.getClass().getDeclaredMethod("toJsonString");
            method.setAccessible(true);
            jsonString = (String) method.invoke(jsonPath);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return new JSONArray(jsonString);

    }

    public static JSONObject getIDFromArray(JSONArray jsonArray, int id) {

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getInt("id") == id) {
                return jsonObject;
            }
        }

        return new JSONObject();
    }

}
