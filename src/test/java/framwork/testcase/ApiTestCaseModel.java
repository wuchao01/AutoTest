package framwork.testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import framwork.steps.StepModel;
import framwork.steps.StepResult;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FakerUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;

public class ApiTestCaseModel {
    private String name;
    private String description;
    private ArrayList<StepModel> steps;
    //testcase临时变量
    private HashMap<String,String> testCaseVariables = new HashMap<>();
    //assertList临时变量
    private ArrayList<Executable> assertList = new ArrayList<>();
    public static final Logger logger = LoggerFactory.getLogger(ApiTestCaseModel.class);


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<StepModel> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepModel> steps) {
        this.steps = steps;
    }

    public HashMap<String, String> getTestCaseVariables() {
        return testCaseVariables;
    }

    public void setTestCaseVariables(HashMap<String, String> testCaseVariables) {
        this.testCaseVariables = testCaseVariables;
    }

    public ArrayList<Executable> getAssertList() {
        return assertList;
    }

    public void setAssertList(ArrayList<Executable> assertList) {
        this.assertList = assertList;
    }

    //读取yaml文件
    public static ApiTestCaseModel load(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path),ApiTestCaseModel.class);
    }

    public void run(){
        //加载用例层关键字变量
        this.testCaseVariables.put("getTimeStamp",FakerUtils.getTimeStamp());
        logger.info("用例变量更新：" + testCaseVariables);

        //遍历steps进行执行
        steps.forEach(step -> {
            StepResult stepResult = step.run(testCaseVariables);

            if (stepResult.getStepVariables().size() > 0){
                testCaseVariables.putAll(stepResult.getStepVariables());
                logger.info("testcase变量更新" + testCaseVariables);
            }

            //处理assertList,并进行统一断言
            if (stepResult.getAssertList().size() > 0){
                assertList.addAll(stepResult.getAssertList());
            }
        });

        //进行统一断言
        assertAll("",assertList.stream());
    }
}
