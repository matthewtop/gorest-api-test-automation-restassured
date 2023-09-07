package pl.globallogic.gorest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;

import java.util.logging.Logger;

public class BaseApiTest {
    protected static Logger logger = LoggerFactory.getLogger(BaseApiTest.class);
    private static final String BASE_URI = "https://gorest.co.in";
    private static final String BASE_PATH = "/public/v2";
    private static String token = "edb4afe94ffa5dd5ee817c3eb2ff3a0408777f6781098e96969cd9cb742050d4";
    @BeforeClass(alwaysRun = true)
    public void globalSetUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = BASE_PATH;
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setContentType(ContentType.JSON).
                addHeader("Authorization", "Bearer " + token).
                build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .build();
    }


}
