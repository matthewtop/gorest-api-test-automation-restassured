package pl.globallogic.verification;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.globallogic.gorest.BaseUserApiTest;
import pl.globallogic.gorest.constants.HostConfigurationParams;
import pl.globallogic.gorest.dto.CreateUserRequestDTO;

import static io.restassured.RestAssured.given;

public class JsonSchemaValidationTest extends BaseUserApiTest {

    private static final String ENDPOINT = HostConfigurationParams.BASE_URI
            + HostConfigurationParams.BASE_PATH + "/users";

    private static final String token = "edb4afe94ffa5dd5ee817c3eb2ff3a0408777f6781098e96969cd9cb742050d4";

    private String ourUserId;

    @BeforeMethod
    public void testSetUp() {
        CreateUserRequestDTO userPayload =
                new CreateUserRequestDTO(getRandomEmail(), "Mateusz Tolpa", "male", "active" );
        ourUserId = String.valueOf(userAPI.createUser(userPayload).getId());
    }

    @Test(enabled = false)
    public void responseShouldBeValidAgainstSchema() {
        String userId = ourUserId;
        given().
                pathParam("userId", userId).
                contentType(ContentType.JSON).
                when().
                get(ENDPOINT + "/{userId}").
                then()
                .log().all().
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("user_schema.json"));
    }

    private static String getRandomEmail() {
        return "mati.email" + (int) (Math.random() * 2000000) + "@hotmail.com";
    }
}
