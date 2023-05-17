package utils;

import Client.ScannerSingleInst;
import jdk.jfr.Frequency;
import okhttp3.*;

import java.io.IOException;

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
     * #Description: 根据给出的路径进行post请求
     * #Param: [java.lang.String] -> [jsonStr]  请求的josn串  //空值返回”“
     * #return: java.lang.String 返回的响应体的字符串
     * #Date: 2023/5/16
     *******/
    public String PostRequest(String jsonStr) throws IOException{
        if(endPath){  //含有查询参数 则非法
            throw new IOException(String.format("路径参数：%s 为非法的post请求url",url));
        }
        final MediaType mediaType = MediaType.parse("application/json:charset=utf-8");
        Request req = new Request.Builder().url(url.toString())
                .post(FormBody.create(mediaType,jsonStr)).build();
        url = new StringBuilder(BASIC_LOC);
        try (Response res = client.newCall(req).execute()) {
            assert res.body() != null;
            return res.body().string();
        }
    }
}
