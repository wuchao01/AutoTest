package framwork.global;

import java.util.HashMap;

public class GlobalVariables {
    //用来存储全局变量
    private static HashMap<String,String> globalVariables = new HashMap<>();

    public static HashMap<String, String> getGlobalVariables() {
        return globalVariables;
    }

    public static void setGlobalVariables(HashMap<String, String> globalVariables) {
        GlobalVariables.globalVariables = globalVariables;
    }
}
