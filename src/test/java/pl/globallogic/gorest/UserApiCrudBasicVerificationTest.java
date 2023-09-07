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



    @BeforeMethodpublic void testSetUp() {    CreateUserRequestDTO userPayload =            new CreateUserRequestDTO(getRandomEmail(), "Ivan Paulouski", "male", "active" );    Response  resp = given().            body(userPayload).            contentType(ContentType.JSON).    when().            post(ENDPOINT).    then()            .extract().response();    ourUserId = resp.jsonPath().getString("id");    logger.info("Created user id: {}", ourUserId);}//Should fetch all users@Testpublic void shouldFetchAllUsersFromDefaultPageBodyExtract() {    int expectedListLength = 10;    Response resp =        when().            get(ENDPOINT).        then()            .extract().response();    List<OurUser> users = resp.jsonPath().getList("", OurUser.class);    logger.info("User names: {}",users);    Assert.assertEquals(expectedListLength, users.size());}@Testpublic void shouldFetchAllUsersFromDefaultPageAssertThat() {    when().            get(ENDPOINT).    then().            assertThat().            body("name[0]", equalTo("Ivan Paulouski"));}//should list user data using user id@Testpublic void userDataShouldContainId() {    String userId = ourUserId;    given().            pathParam("userId", userId).    when().            get(ENDPOINT + "/{userId}").    then().            assertThat().            body("id", equalTo(Integer.valueOf(ourUserId)));}//should create new user and return id@Testpublic void shouldCreateAUserAndReturnAnId() {    var userPayload =            new CreateUserRequestDTO(getRandomEmail(), "Ivan Paulouski", "male", "active" );    Response resp = given().            body(userPayload).    when().            post(ENDPOINT).    then()            .extract().response();    CreateUserResponseDTO user = resp.as(CreateUserResponseDTO.class);    logger.info("User object : {}", user);    Assert.assertNotNull(user.id());}private static String getRandomEmail() {    String email = "ivan.email" + (int) (Math.random() * 2000) + "@hotmail.com";    logger.info("Generated email: {}", email);    return email;}//should update user info with new information@Testpublic void shouldUpdateExistingUserWithNewData() {    String randomEmail = getRandomEmail();    String newName = "Ivan PaulouskiUPD";    logger.info("New user name: {}", newName);    CreateUserRequestDTO userPayload =            new CreateUserRequestDTO(randomEmail, newName, "male", "inactive" );    given().            pathParam("userId", ourUserId).            body(userPayload).    when().            put(ENDPOINT + "/{userId}").    then()            .assertThat()            .body("name", equalTo(newName));}//should delete user from system@Testpublic void shouldDeleteUserUsingId() {    Response resp =    given().            pathParam("userId", ourUserId).    when().            delete(ENDPOINT + "/{userId}");    int expectedStatusCode = 204;    Assert.assertEquals(resp.statusCode(), expectedStatusCode);}