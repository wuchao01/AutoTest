package framwork.test;

import framwork.global.ApiLoader;
import framwork.testcase.ApiTestCaseModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ParameterTestCase {
    public static final Logger logger = LoggerFactory.getLogger(ParameterTestCase.class);

    //如果返回结果的方法名与用例名相同只需要写@MethodSource不需要写@MethodSource("method")
    @ParameterizedTest(name = "{index} {1}")
    @MethodSource
    public void apiTest(ApiTestCaseModel apiTestCaseModel,String name,String description){
        logger.info("[用例开始执行]");
        logger.info("用例名称：" + name);
        logger.info("用例描述：" + description);
        apiTestCaseModel.run();
    }

    public static List<Arguments> apiTest(){
        ApiLoader.load("F:\\autotest\\src\\test\\resources\\api");

        //读取所有测试用例
        List<Arguments> testcase = new ArrayList<>();
        String testcaseDir = "F:\\autotest\\src\\test\\resources\\testcase";

        Arrays.stream(new File(testcaseDir).list()).forEach(name -> {
            String path = testcaseDir+"\\"+name;
            try {
                ApiTestCaseModel apiTestCase = ApiTestCaseModel.load(path);
                testcase.add(arguments(apiTestCase,apiTestCase.getName(),apiTestCase.getDescription()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return testcase;
    }
}
