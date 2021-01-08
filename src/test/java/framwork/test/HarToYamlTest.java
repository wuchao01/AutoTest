package framwork.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarRequest;
import framwork.actions.ApiActionModel;
import framwork.api.ApiObjectModel;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.YamlUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class HarToYamlTest {
    public static final Logger logger = LoggerFactory.getLogger(HarToYamlTest.class);


    @Test
    public void harTest() throws HarReaderException, IOException {
        HarReader harReader = new HarReader();
        Har har = harReader.readFromFile(new File("F:\\autotest\\src\\test\\resources\\har\\com.wework.har"));
        ApiActionModel apiActionModel = new ApiActionModel();
        ApiObjectModel apiObjectModel = new ApiObjectModel();
        HashMap<String,ApiActionModel> actions = new HashMap<>();
        HashMap<String,String> queryMap = new HashMap<>();
        har.getLog().getEntries().forEach(entry ->{
            HarRequest harRequest = entry.getRequest();
            entry.getRequest().getQueryString().forEach(query ->{
                queryMap.put(query.getName(),query.getValue());
            });
            String url = harRequest.getUrl();
            String method = harRequest.getMethod().toString();
            apiActionModel.setQuery(queryMap);
            //这个地方可以在增加delete、put变量的set和get方法
            if (method.toLowerCase().equals("get")){
                apiActionModel.setGet(url);
            }else if (method.toLowerCase().equals("post")){
                apiActionModel.setPost(url);
            }
            actions.put(getRequestName(url),apiActionModel);
        });
        //todo：这里可以使用yaml映射对象字段获取到action的apiName
        apiObjectModel.setName("tokenhelper");
        apiObjectModel.setActions(actions);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        //读取har文件写入yaml
        mapper.writeValue(new File("F:\\autotest\\src\\test\\resources\\har\\tokenhelper.yaml"),apiObjectModel);
    }

    public String getRequestName(String url) {
        String[] suburl = url.split("\\u003F")[0].split("/");
        String name = "";
        if (suburl.length > 1) {
            name = suburl[suburl.length - 1];
        }else if(1==suburl.length){
            name = suburl[0];
        }
        return name;
    }

    @Test
    public void runTest() throws IOException {
        ApiObjectModel apiObjectModel = YamlUtil.load("F:\\autotest\\src\\test\\resources\\har\\tokenhelper.yaml",ApiObjectModel.class);
        apiObjectModel.getActions().get("gettoken").run(null);
    }
}
