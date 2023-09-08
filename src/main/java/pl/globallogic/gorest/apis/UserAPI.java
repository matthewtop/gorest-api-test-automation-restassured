package pl.globallogic.gorest.apis;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.globallogic.gorest.dto.CreateUserRequestDTO;
import pl.globallogic.gorest.model.OurUser;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class UserAPI {
    protected Logger logger = LoggerFactory.getLogger(UserAPI.class);
    private static final String endpoint = "/users";

    public UserAPI(){
    }

    public List<OurUser> getAllUsers(){
        Response resp = when().get(endpoint).then().extract().response();
        return resp.jsonPath().getList("", OurUser.class);
    }

    public OurUser getUser(String userId){
        return given().pathParam("userId", userId).when()
                .get(endpoint + "/{userId}").
                then().
                extract().response().as(OurUser.class);
    }

    public OurUser createUser(CreateUserRequestDTO userPayload){
        return given().body(userPayload).when().post(endpoint).then().extract().response().as(OurUser.class);
    }

    public static OurUser updateUserInfo(String userId, CreateUserRequestDTO userPayload){
        return given().pathParam("userId", userId).body(userPayload).when().
                put(endpoint,"/{userId}").then().extract().response().as(OurUser.class);
    }

    public boolean deleteUser(String ourUserId){
        return given().pathParam("userId",ourUserId)
                .when().delete(endpoint,"/{userId}").getStatusCode()==204;
    }
}
