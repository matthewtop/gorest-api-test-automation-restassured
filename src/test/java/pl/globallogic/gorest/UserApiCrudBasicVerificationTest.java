package pl.globallogic.gorest;

import io.restassured.http.ContentType;
import org.asynchttpclient.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.globallogic.gorest.model.CreateUserDTO;

import static io.restassured.RestAssured.*;

public class UserApiCrudBasicVerificationTest extends BaseApiTest {

    private static final String BASE_URI="https://gorest.co.in";
    private static final String BASE_PATH="/pubic/v2";
    private static final String ENDPOINT="/users";

    private static final String token="edb4afe94ffa5dd5ee817c3eb2ff3a0408777f6781098e96969cd9cb742050d4";
    private String ourUserId;



    @Test
    public void shouldFetchAllUsersFromDefaultPage() {
        given().
                log().all().
                when().
                get("https://gorest.co.in/public/v2/users").
                then().log().all();
        //should fetch all users
        //should list user data using user id
        //should create new user and return id
        //should update user info with new information
        //should delete user from system
    }

    @BeforeMethod
    public void testSetUp() {
        String randomEmail = getRandomEmail();
        CreateUserDTO userPayload =
                new CreateUserDTO(randomEmail, "Ivan Paulouski", "male", "active" );
        Response  resp = (Response) given().
                body(userPayload).
                header("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                when().
                post(BASE_URI + BASE_PATH + ENDPOINT).
                then().extract().response();
        ourUserId = resp.jsonPath().getString("id");
        logger.info("Created user id: {}", ourUserId);
    }

    @Test
    public void userDataShouldContainId() {
        String userId = ourUserId;
        given().
                pathParam("userId", userId).
                header("Authorization", "Bearer " + token).
                when().
                get(BASE_URI + BASE_PATH + ENDPOINT + "/{userId}").
                then()
                .log().all();
    }


    @Test
    public void shouldCreateAUserAndReturnAnId() {
        String randomEmail = "ivan.email" + (int)(Math.random() * 2000) + "@hotmail.com";
        CreateUserDTO userPayload =
                new CreateUserDTO(randomEmail, "Ivan Paulouski", "male", "active" );
        given().
                body(userPayload).
                header("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                when().
                post(BASE_URI + BASE_PATH + ENDPOINT).
                then()
                .log().all();


    }
    private static String getRandomEmail() {
        String email = "ivan.email" + (int) (Math.random() * 2000) + "@hotmail.com";
        logger.info("Generated email: {}"+ email);
        return email;
    }
    @Test
    public void shouldUpdateExistingUserWithNewData() {
        String randomEmail = getRandomEmail();
        CreateUserDTO userPayload =
                new CreateUserDTO(randomEmail, "Ivan PaulouskiUPD", "male", "inactive" );
        given().
                pathParam("userId", ourUserId).
                body(userPayload).
                header("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                when().
                put(BASE_URI + BASE_PATH + ENDPOINT + "/{userId}").
                then()
                .log().all();
    }

    @Test
    public void shouldDeleteUserUsingId() {
        Response resp =
                given().
                pathParam("userId", ourUserId).
                header("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                when().
                delete(BASE_URI + BASE_PATH + ENDPOINT + "/{userId}");
        int expectedStatusCode = 204;
        Assert.assertEquals(resp.statusCode(), expectedStatusCode);
    }


}
