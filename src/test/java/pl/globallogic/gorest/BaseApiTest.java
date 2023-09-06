package pl.globallogic.gorest;

import io.restassured.RestAssured;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;

import java.util.logging.Logger;

public class BaseApiTest {
    protected static Logger logger = LoggerFactory.getLogger(BaseApiTest.class);
    private static final String BASE_URI="https://gorest.co.in";
    private static final String BASE_PATH="/pubic/v2";

    @BeforeClass(alwaysRun = true)
    public void globalSetup(){
        RestAssured.baseURI=BASE_URI;
        RestAssured.basePath=BASE_PATH;
    }


}
