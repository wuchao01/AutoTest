package framwork.test;

import framwork.actions.ApiActionModel;
import framwork.global.GlobalVariables;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class ApiActionModelTest {
    public static final Logger logger = LoggerFactory.getLogger(ApiActionModelTest.class);

    @Test
    public void runTest(){
        ArrayList<String> actualParameter = new ArrayList<>();
        actualParameter.add("wwa6e174bdb8889eff");
        actualParameter.add("yWX6DThWTj3ilKx9fQnECNbl2BDYGB2RerYf0ccEXWQ");

        ApiActionModel apiActionModel = new ApiActionModel();
        apiActionModel.setUrl("https://qyapi.weixin.qq.com/cgi-bin/${x}");

        HashMap<String,String> globalVariables = new HashMap<>();
        globalVariables.put("x","gettoken");
        GlobalVariables.setGlobalVariables(globalVariables);

        ArrayList<String> formalParameter = new ArrayList<>();
        formalParameter.add("corpid");
        formalParameter.add("corpsecret");
        apiActionModel.setFormalParam(formalParameter);

        HashMap<String,String> query = new HashMap<>();
        query.put("corpid","${corpid}");
        query.put("corpsecret","${corpsecret}");
        apiActionModel.setQuery(query);
        Response response = apiActionModel.run(actualParameter);
    }

}
