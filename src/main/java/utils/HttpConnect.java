package utils;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class HttpConnect {
    private final String BASIC_LOC = "http://localhost:8080";
    private static HttpConnect inst = new HttpConnect();
    private OkHttpClient client;
    private StringBuilder url;
    private boolean endPath;

    private HttpConnect() {
        client = new OkHttpClient();
        url = new StringBuilder(BASIC_LOC);
        endPath = false;
    }

    public static HttpConnect getInst() {
        return inst;
    }


    /*******
     * #Description:增加路径参数
     * #Param: [java.lang.CharSequence] -> [str]  路径参数
     * #return: void
     * #Date: 2023/5/16
     *******/
    public void addUrlPath(CharSequence str) {
        if(endPath){
            throw new RuntimeException("查询参数后不可再加入路径参数");
        }
        url.append(str);
    }

    public void addGetParam(CharSequence key, CharSequence value) {
        if (!endPath) {
            url.append("/?");
            endPath = true;
        } else {
            url.append("&");
        }
        url.append(key);
        url.append("=");
        url.append(value);
    }


    /*******
     * #Description:
     * #Param: void
     * #return: java.lang.CharSequence 当前的url
     * #Date: 2023/5/16
     *******/
    public CharSequence getCurUrl() {
        return url;
    }

    /*******
     * #Description:  根据给出的路径和查询参数进行get请求
     * #Param: none
     * #return: java.lang.String  请求得到的返回值 空值时为""
     * #Date: 2023/5/16
     *******/
    public String GetRequest() throws IOException {
        final String lastUrl = url.toString();
        url = new StringBuilder(BASIC_LOC);
        endPath = false;
        Request request = new Request.Builder().url(lastUrl).build();
        try (Response res = client.newCall(request).execute()) {
            assert res.body() != null;
            return res.body().string();
        }
    }

    /*******
     * #Description: 带有参数的POST请求
     * #Param: [Map<String,Object>] -> [parms] 请求参数<String,String(内容)>
     * #return: java.lang.String  返回的响应体
     * #Date: 2023/5/18
     *******/
    public String PostRequset(Map<String,String> parms) throws IOException{
        checkGetArg();
        FormBody.Builder fb = new FormBody.Builder();
        Set<String> keySet = parms.keySet();
        for(String k : keySet){
            fb.add(k, parms.get(k));
        }
        Request request = new  Request.Builder().post(fb.build()).url(String.valueOf(url)).build();
        url = new StringBuilder(BASIC_LOC);
        try (Response res = client.newCall(request).execute()) {
            assert res.body() != null;
            return res.body().string();
        }
    }

    /*******
     * #Description: 根据给出的路径进行post请求
     * #Param: [java.lang.String] -> [jsonStr]  请求的josn串  //空值返回”“
     * #return: java.lang.String 返回的响应体的字符串
     * #Date: 2023/5/16
     *******/
    public String PostRequest(String jsonStr) throws IOException{
        checkGetArg();
        final MediaType mediaType = MediaType.parse("application/json:charset=utf-8");
        Request req = new Request.Builder().url(url.toString())
                .post(FormBody.create(mediaType,jsonStr)).build();
        url = new StringBuilder(BASIC_LOC);
        try (Response res = client.newCall(req).execute()) {
            assert res.body() != null;
            return res.body().string();
        }
    }

    private void checkGetArg() throws IOException {
        if(endPath){  //含有查询参数 则非法
            throw new IOException(String.format("路径参数：%s 为非法的post请求url",url));
        }
    }
}
