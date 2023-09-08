package pl.globallogic.gorest.testdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.globallogic.gorest.dto.CreateUserRequestDTO;

public class UserApiTestDataGenerator {

    protected static Logger logger = LoggerFactory.getLogger(UserApiTestDataGenerator.class);

    public static CreateUserRequestDTO getRandomUser(){
        return new CreateUserRequestDTO(getRandomEmail(),"Mateusz Tolpa","male","active");
    }

    private static String getRandomEmail(){
        String email = "mati.email"+(int)(Math.random()*2000000)+"hotmail.com";
        logger.info("Generated email: {}",email);
        return email;
    }
}