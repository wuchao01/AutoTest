package framwork.test;

import framwork.global.ApiLoader;
import framwork.testcase.ApiTestCaseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ApiTestCaseTest {
    public static final Logger logger = LoggerFactory.getLogger(ApiTestCaseTest.class);

    @BeforeAll
    public static void loadTest() throws IOException {
        ApiLoader.load("src/test/resources/api");
        logger.info("debugger");
    }

    @Test
    public void loadApiTest() throws IOException {
        ApiTestCaseModel apiTestCaseModel = ApiTestCaseModel.load("src/test/resources/testcase/createDepartment.yaml");
        logger.info("Debugger!");
    }

    @Test
    public void run() throws IOException {
        ApiTestCaseModel apiTestCaseModel = ApiTestCaseModel.load("src/test/resources/testcase/createDepartment.yaml");
        apiTestCaseModel.run();
        logger.info("Debugger!");
    }

}
