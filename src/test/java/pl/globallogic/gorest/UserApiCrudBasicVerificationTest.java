package pl.globallogic.gorest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.globallogic.gorest.model.CreateUserDTO;
import pl.globallogic.gorest.testdata.BaseUserApiTest;


import static io.restassured.RestAssured.*;

public class UserApiCrudBasicVerificationTest extends BaseUserApiTest {

//    private static final String BASE_URI = "https://gorest.co.in";
//    private static final String BASE_PATH = "/pubic/v2";
    private static final String ENDPOINT = "/users";

//    private static final String token = "edb4afe94ffa5dd5ee817c3eb2ff3a0408777f6781098e96969cd9cb742050d4";
    private String ourUserId;


    @BeforeMethod
    public void testSetUp() {
        CreateUserDTO userPayload =
                new CreateUserDTO(getRandomEmail(), "Mateusz Tolpa", "male", "active");
        Response resp = given().
                body(userPayload).
                contentType(ContentType.JSON).
                when().
                post(ENDPOINT).
                then()
                .extract().response();
        ourUserId = resp.jsonPath().getString("id");
        logger.info("Created user id: {}" + ourUserId);
    }
//Should fetch all users
//

    @Test
    public void shouldFetchAllUsersFromDefaultPageBodyExtract() {
        int expectedListLength = 10;
        Response resp =
                when().
                        get(ENDPOINT).
                        then()
                        .extract().response();
        List<OurUser> users = resp.jsonPath().getList("", OurUser.class);
        logger.info("User names: {}"+ users);
        Assert.assertEquals(expectedListLength, users.size());
    }

    @Test
    public void shouldFetchAllUsersFromDefaultPageAssertThat() {
        when().
                get(ENDPOINT).
                then().
                assertThat().
                body("name[0]", equalTo("Ivan Paulouski"));
    }

    //should list user data using user id
    @Test
    public void userDataShouldContainId() {
        String userId = ourUserId;
        given().
                pathParam("userId", userId).
                when().
                get(ENDPOINT + "/{userId}").
                then().
                assertThat().
                body("id", equalTo(Integer.valueOf(ourUserId)));
    }
    //should create new user and return id
     @Test
    public void shouldCreateAUserAndReturnAnId() {
        var userPayload =
                new CreateUserDTO(getRandomEmail(), "Mateusz Tolpa", "male", "active" );
        Response resp = given().
                body(userPayload).
                when().
                post(ENDPOINT).
                then()
                .extract().response();
        CreateUserDTO user = resp.as(CreateUserDTO.class);
        logger.info("User object : {}"+ user);
        Assert.assertNotNull(user.id());
    }
    private static String getRandomEmail() {
        String email = "mateusz.email" + (int) (Math.random() * 2000) + "@hotmail.com";
        logger.info("Generated email: {}"+ email);
        return email;}
    //should update user info with new information
     @Test
     public void shouldUpdateExistingUserWithNewData() {
        String randomEmail = getRandomEmail();
        String newName = "Mateusz TolpaUPD";
        logger.info("New user name: {}"+ newName);
        CreateUserDTO userPayload =
                new CreateUserDTO(randomEmail, newName, "male", "inactive" );
        given().
                pathParam("userId", ourUserId).
                body(userPayload).
                when().
                put(ENDPOINT + "/{userId}").
                then()
                .assertThat()
                .body("name", equalTo(newName));
    }
    //should delete user from system
     @Test
     public void shouldDeleteUserUsingId() {
        Response resp =
                given().
                        pathParam("userId", ourUserId).
                        when().
                        delete(ENDPOINT + "/{userId}");
        int expectedStatusCode = 204;
        Assert.assertEquals(resp.statusCode(), expectedStatusCode);
    }
}
