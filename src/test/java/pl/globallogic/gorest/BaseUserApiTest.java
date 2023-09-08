package pl.globallogic.gorest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import pl.globallogic.gorest.apis.UserAPI;
import pl.globallogic.gorest.constants.HostConfigurationParams;



public class BaseUserApiTest {
    protected static Logger logger = LoggerFactory.getLogger(BaseUserApiTest.class);
//    private static final String BASE_URI = "https://gorest.co.in";
//    private static final String BASE_PATH = "/public/v2";
//    private static String token = "edb4afe94ffa5dd5ee817c3eb2ff3a0408777f6781098e96969cd9cb742050d4";
    protected UserAPI userAPI = new UserAPI();

    @BeforeClass(alwaysRun = true)
    public void globalSetUp() {
        String token = System.getProperty("token",
                "edb4afe94ffa5dd5ee817c3eb2ff3a0408777f6781098e96969cd9cb742050d4");
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setBaseUri(HostConfigurationParams.BASE_URI).
                setBasePath(HostConfigurationParams.BASE_PATH).
                setContentType(ContentType.JSON).
                addHeader("Authorization","Bearer: " +token).build();
    }


}
