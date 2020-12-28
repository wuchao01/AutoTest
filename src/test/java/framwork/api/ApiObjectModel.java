package framwork.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ApiObjectModel {
    private String name;
    private HashMap<String,ApiObjectModel> actions;
    private HashMap<String,String> obVariables = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, ApiObjectModel> getActions() {
        return actions;
    }

    public void setActions(HashMap<String, ApiObjectModel> actions) {
        this.actions = actions;
    }

    public HashMap<String, String> getObVariables() {
        return obVariables;
    }

    public void setObVariables(HashMap<String, String> obVariables) {
        this.obVariables = obVariables;
    }

    //读取yaml文件
    public static ApiObjectModel load(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(path),ApiObjectModel.class);
    }
}
