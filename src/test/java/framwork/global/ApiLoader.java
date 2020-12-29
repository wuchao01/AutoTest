package framwork.global;

import framwork.actions.ApiActionModel;
import framwork.api.ApiObjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ApiLoader {
    public static final Logger logger = LoggerFactory.getLogger(ApiLoader.class);
    //加载所有api object对象，并保存到本列表中
    private static ArrayList<ApiObjectModel> apis = new ArrayList<>();

    //从文件夹读取所有yaml文件
    public static void load(String dir){
        Arrays.stream(new File(dir).list()).forEach(path -> {
            try {
                apis.add(ApiObjectModel.load(dir+"/"+path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static ApiActionModel getAction(String apiName, String actionName){
        final ApiActionModel[] apiActionModel = {new ApiActionModel()};
        apis.stream().filter(api -> api.getName().equals(apiName)).forEach(api -> {
            apiActionModel[0] = api.getActions().get(actionName);
        });
        if (apiActionModel[0] != null){
            return apiActionModel[0];
        }else {
            logger.info("没有找到接口对象：" + apiName + "中的action：" + actionName );
        }
        return null;
    }

}
