package framwork.test;

import framwork.global.ApiLoader;
import framwork.steps.AssertModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

public class stepModelTest {
    public static final Logger logger = LoggerFactory.getLogger(stepModelTest.class);

    @BeforeAll
    public static void loadTest() throws IOException {
        ApiLoader.load("src/test/resources/api");
        logger.info("debugger");
    }

    @Test
    public void runTest() throws IOException {
        //实参
        ArrayList<String> actualParameter = new ArrayList<>();
        actualParameter.add("wwa6e174bdb8889eff");
        actualParameter.add("yWX6DThWTj3ilKx9fQnECNbl2BDYGB2RerYf0ccEXWQ");

        //断言
        ArrayList<AssertModel> asserts = new ArrayList<>();
        AssertModel assertModel = new AssertModel();
        assertModel.setReason("getToken错误码校验01");
        assertModel.setActual("errcode");
        assertModel.setExpect("0");
        assertModel.setMatcher("equalTo");

    }

}
