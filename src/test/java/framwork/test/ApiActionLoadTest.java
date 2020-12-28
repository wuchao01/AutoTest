package framwork.test;

import framwork.api.ApiObjectModel;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

public class ApiActionLoadTest {
    public static final Logger logger = LoggerFactory.getLogger(ApiActionLoadTest.class);

    @Test
    public void loadTest() throws IOException {
        ArrayList<String> actualParameter = new ArrayList<>();
        actualParameter.add("wwa6e174bdb8889eff");
        actualParameter.add("yWX6DThWTj3ilKx9fQnECNbl2BDYGB2RerYf0ccEXWQ");

        ApiObjectModel apiObjectModel = ApiObjectModel.load("src/test/resources/api/tokenHelper.yaml");
        apiObjectModel.getActions().get("getToken").run(actualParameter);

    }

}
