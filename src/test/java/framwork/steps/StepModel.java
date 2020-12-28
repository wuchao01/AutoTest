package framwork.steps;

import utils.PlaceholderUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class StepModel {
    private String api;
    private String action;
    private ArrayList<String> actualParameter;
    private HashMap<String,String> saveGlobal;
    private HashMap<String,String> save;
    private ArrayList<AssertModel> asserts;

    private ArrayList<String> finalActualParaeter = new ArrayList<>();
    private HashMap<String,String> stepVariables = new HashMap<>();
    //需要定义StepResult类

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
    public void run(HashMap<String,String> testCaseVariables){
        //判断case实参是否为空，如果不为空用case传进来的参数替换掉${XX}
        if (actualParameter != null){
            finalActualParaeter.addAll(PlaceholderUtils.resolveList(actualParameter,testCaseVariables));
        }
    }
}
