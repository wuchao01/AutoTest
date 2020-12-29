package framwork.test;

import framwork.global.ApiLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

public class ApiActionLoadDirTest {
    public static final Logger logger = LoggerFactory.getLogger(ApiActionLoadDirTest.class);

    @BeforeAll
    public static void loadTest() throws IOException {
        ApiLoader.load("src/test/resources/api");
        logger.info("debugger");
    }

    @Test
    public void getActionTest() throws IOException {
        ArrayList<String> actualParameter = new ArrayList<>();
        actualParameter.add("wwa6e174bdb8889eff");
        actualParameter.add("yWX6DThWTj3ilKx9fQnECNbl2BDYGB2RerYf0ccEXWQ");

        ApiLoader.getAction("tokenhelper","getToken").run(actualParameter);
    }

}
