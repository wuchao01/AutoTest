package framwork.steps;

import framwork.global.ApiLoader;
import framwork.global.GlobalVariables;
import io.restassured.response.Response;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PlaceholderUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StepModel {
    private String api;
    private String action;
    private ArrayList<String> actualParameter;
    private HashMap<String,String> saveGlobal;
    private HashMap<String,String> save;
    private ArrayList<AssertModel> asserts;

    private ArrayList<String> finalActualParameter = new ArrayList<>();
    private HashMap<String,String> stepVariables = new HashMap<>();
    //用来存储返回信息，比如save或globSave的变量
    private StepResult stepResult = new StepResult();
    private ArrayList<Executable> assertList = new ArrayList<>();

    public static final Logger logger = LoggerFactory.getLogger(StepModel.class);

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<String> getActualParameter() {
        return actualParameter;
    }

    public void setActualParameter(ArrayList<String> actualParameter) {
        this.actualParameter = actualParameter;
    }

    public HashMap<String, String> getSaveGlobal() {
        return saveGlobal;
    }

    public void setSaveGlobal(HashMap<String, String> saveGlobal) {
        this.saveGlobal = saveGlobal;
    }

    public HashMap<String, String> getSave() {
        return save;
    }

    public void setSave(HashMap<String, String> save) {
        this.save = save;
    }

    public ArrayList<AssertModel> getAsserts() {
        return asserts;
    }

    public void setAsserts(ArrayList<AssertModel> asserts) {
        this.asserts = asserts;
    }

    //传入testcase变量
    public StepResult run(HashMap<String,String> testCaseVariables){
        //判断case实参是否为空，如果不为空用case传进来的参数替换掉${XX}
        if (actualParameter != null){
            finalActualParameter.addAll(PlaceholderUtils.resolveList(actualParameter,testCaseVariables));
        }

        //根据cese的yaml获取api和action进而得到action，然后通过run方法传递已经替换${xx}之后的值得到response
        Response response = ApiLoader.getAction(api,action).run(finalActualParameter);

        //存储save
        if (save != null){
            save.forEach((variablesName,path) -> {
                String value = response.path(path);
                stepVariables.put(variablesName,value);
                logger.info("step变量更新：" + stepVariables);

            });
        }

        //存储saveGlobal
        if (saveGlobal != null){
            saveGlobal.forEach((variablesName,path) -> {
                String value = response.path(path);
                GlobalVariables.getGlobalVariables().put(variablesName,value);
                logger.info("全局变量更新：" + GlobalVariables.getGlobalVariables());
            });
        }

        //处理软断言需要的中间断言数据，获取yaml中assert的值赋值到assertList中
        if (asserts != null){
            asserts.stream().forEach(assertModel -> {
                assertList.add(() -> {
                    assertThat(assertModel.getReason(),response.path(assertModel.getActual()).toString(),equalTo(assertModel.getExpect()));
                });
            });
        }

        //获取assert列表、save值set到stepResult中用来传递给下个参数
        stepResult.setAssertList(assertList);
        stepResult.setStepVariables(stepVariables);
        stepResult.setResponse(response);
        return stepResult;
    }
}
