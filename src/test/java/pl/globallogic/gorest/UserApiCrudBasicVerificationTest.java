package pl.globallogic.gorest;

import org.asynchttpclient.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class UserApiCrudBasicVerificationTest extends BaseApiTest {

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

}
