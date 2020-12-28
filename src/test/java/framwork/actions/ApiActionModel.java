package framwork.actions;

import framwork.global.GlobalVariables;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.PlaceholderUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ApiActionModel {
    private String method = "get";
    private String url;
    private String body;
    private String contentType;
    private HashMap<String,String> query;
    private HashMap<String,String> headers;
    private String post;
    private String get;
    private String delete;
    private String put;
    private Response response;
    private ArrayList<String> formalParam;
    private HashMap<String,String> actionVariables = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public HashMap<String, String> getQuery() {
        return query;
    }

    public void setQuery(HashMap<String, String> query) {
        this.query = query;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public ArrayList<String> getFormalParam() {
        return formalParam;
    }

    public void setFormalParam(ArrayList<String> formalParam) {
        this.formalParam = formalParam;
    }


    public Response run(ArrayList<String> actualParameter){
        //存储替换之后的变量
        HashMap<String,String> finalQuery = new HashMap<>();
        String runBody = this.body;
        String runUrl = this.url;
        //通过yaml中获取的post或get赋值给url
        if(post != null){
            runUrl = post;
            method = "post";
        }else if (get != null){
            runUrl = get;
            method = "get";
        }else if (delete != null){
            runUrl = delete;
            method = "delete";
        }else if (put != null){
            runUrl = put;
            method = "put";
        }

        if (query != null){
            finalQuery.putAll(PlaceholderUtils.resolveMap(query, GlobalVariables.getGlobalVariables()));
        }

        //body全局变量替换
        if (body != null){
            runBody = PlaceholderUtils.resolveString(runBody,GlobalVariables.getGlobalVariables());
        }
        //url全局变量替换
        runUrl = PlaceholderUtils.resolveString(runUrl,GlobalVariables.getGlobalVariables());

        if (formalParam != null && actualParameter != null && formalParam.size() > 0 && actualParameter.size() > 0){
            //根据形参和实参替换内部变量map
            for (int i = 0; i < formalParam.size(); i++) {
                actionVariables.put(formalParam.get(i),actualParameter.get(i));
            }
            if (query != null){
                finalQuery = PlaceholderUtils.resolveMap(query,actionVariables);
            }
            runBody = PlaceholderUtils.resolveString(runBody,actionVariables);
            runUrl = PlaceholderUtils.resolveString(runUrl,actionVariables);
        }

        //拆分given通过读取数据发起请求
        RequestSpecification requestSpecification = given().log().all();
        if (contentType != null){
            requestSpecification.contentType(contentType);
        }
        if (headers != null){
            requestSpecification.headers(headers);
        }
        if (finalQuery != null && finalQuery.size() > 0){
            requestSpecification.formParams(finalQuery);
        }else if (runBody != null){
            requestSpecification.body(runBody);
        }
        //获取返回response，以后可以通过getResponse获取响应信息
        Response response = requestSpecification.request(method,runUrl).then().log().all().extract().response();
        this.response = response;
        return response;
    }
}
