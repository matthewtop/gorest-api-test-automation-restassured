package pl.globallogic.gorest.testdata;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.globallogic.gorest.apis.UserAPI;
import pl.globallogic.gorest.dto.CreateUserRequestDTO;
import pl.globallogic.gorest.model.OurUser;
import pl.globallogic.gorest.testdata.UserApiCrudBasicVerificationTest;

import java.util.List;

import static io.restassured.RestAssured.when;


public class UserApiCrudBasicVerificationTest extends BaseUserApiTest{
    private String ourUserId;

    @BeforeMethod
    public void testSetUp() {
        CreateUserRequestDTO userPayload = UserApiTestDataGenerator.getRandomUser();
        ourUserId = String.valueOf(userAPI.createUser(userPayload).getId());
        logger.info("Created user id: {}", ourUserId);
    }

    @Test
    public void shouldFetchAllUsersFromDefaultPageBodyExtract(){
        int expectedListLength = 10;
        List<OurUser> users = userAPI.getAllUsers();
        logger.info("Users: {}", users);
        Assert.assertEquals(expectedListLength, users.size());
    }

    @Test
    public void userDataShouldContainId(){
        OurUser mati = userAPI.getUser(ourUserId);
        String expectedUserName = "Mateusz Tolpa";
        Assert.assertEquals(expectedUserName,mati.getName());
    }

    @Test
    public void shouldCreateAUserAndReturnAnId(){
        var userPayload = UserApiTestDataGenerator.getRandomUser();
        OurUser user = userAPI.createUser(userPayload);
        logger.info("User object: {}",user);
        Assert.assertNotEquals(user.getId(),0);
    }

    @Test
    public void shouldUpdateExistingUserWithNewData(){
        CreateUserRequestDTO userPayload = UserApiTestDatGenerator.getRandomUser();
        OurUser updatedUser = UserAPI.updateUserInfo(ourUserId,userPayload);
        Assert.assertEquals(userPayload.email(),updatedUser.getEmail());
    }

    @Test
    public void shouldDeleteUserUsingId(){Assert.assertTrue(userAPI.deleteUser(ourUserId));}
}
