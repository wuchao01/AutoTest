package framwork.test;

import framwork.global.ApiLoader;
import framwork.testcase.ApiTestCaseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.YamlUtil;

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
        ApiTestCaseModel apiTestCaseModel = YamlUtil.load("src/test/resources/testcase/createDepartment.yaml",ApiTestCaseModel.class);
        logger.info("Debugger!");
    }

    @Test
    public void run() throws IOException {
        ApiTestCaseModel apiTestCaseModel = YamlUtil.load("src/test/resources/testcase/createDepartment.yaml",ApiTestCaseModel.class);
        apiTestCaseModel.run();
        logger.info("Debugger!");
    }

}
