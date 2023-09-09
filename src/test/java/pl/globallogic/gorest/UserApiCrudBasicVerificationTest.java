package pl.globallogic.gorest;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.globallogic.gorest.dto.CreateUserRequestDTO;
import pl.globallogic.gorest.model.OurUser;
import pl.globallogic.gorest.testdata.UserApiTestDataGenerator;

import java.util.List;

public class UserApiCrudBasicVerificationTest extends BaseUserApiTest{

    private String ourUserId;
    //private final UserAPI userAPI = new UserAPI();


    @BeforeMethod
    public void testSetUp() {
        CreateUserRequestDTO userPayload = UserApiTestDataGenerator.getRandomUser();
        ourUserId = String.valueOf(userAPI.createUser(userPayload).getId());
        logger.info("Created user id: {}", ourUserId);

    }

    //Should fetch all users
    @Test
    public void shouldFetchAllUsersFromDefaultPageBodyExtract() {
        int expectedListLength = 10;
        List<OurUser> users = userAPI.getAllUsers();
        logger.info("Users : {}", users);
        Assert.assertEquals(expectedListLength, users.size());
    }

    @Test
    public void userDataShouldContainId() {
        OurUser ivan = userAPI.getUser(ourUserId);
        String expectedUserName = "Mateusz Tolpa";
        Assert.assertEquals(expectedUserName, ivan.getName());
    }
    //should create new user and return id
    @Test
    public void shouldCreateAUserAndReturnAnId() {
        var userPayload  = UserApiTestDataGenerator.getRandomUser();
        OurUser user = userAPI.createUser(userPayload);
        logger.info("User object : {}", user);
        Assert.assertNotEquals(user.getId(), 0);
    }


    //should update user info with new information
    @Test
    public void shouldUpdateExistingUserWithNewData() {
        CreateUserRequestDTO userPayload = UserApiTestDataGenerator.getRandomUser();
        OurUser updatedUser = userAPI.updateUserInfo(ourUserId, userPayload);
        Assert.assertEquals(userPayload.email(), updatedUser.getEmail());
    }
    //should delete user from system
    @Test
    public void shouldDeleteUserUsingId() {
        Assert.assertTrue(userAPI.deleteUser(ourUserId));
    }
}
