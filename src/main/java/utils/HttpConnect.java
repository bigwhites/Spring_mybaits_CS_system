package utils;

import Client.ScannerSingleInst;
import jdk.jfr.Frequency;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpConnect {
    private  static HttpConnect inst = new HttpConnect();
    private  OkHttpClient client;
    private  StringBuilder url ;
    private  boolean endPath;
    private HttpConnect(){
        client = new OkHttpClient();
        url = new StringBuilder("http://localhost:8080");
        endPath = false;
    }
    public static HttpConnect getInst(){
        return inst;
    }

    public void addUrlPath(CharSequence str){
        url.append(str);
    }

    public  void addGetParam(CharSequence key , CharSequence value){
        if(!endPath){
            url.append("/?");
            endPath=true;
            url.append(key);
            url.append("=");
            url.append(value);
        }
    }
   /*******
    * #Description:
    * #Param: none
    * #return: java.lang.String  请求得到的返回值
    * #Date: 2023/5/16
    *******/
   public String GetRequest() throws IOException {
        final String lastUrl =  url.toString();
       Request request = new Request.Builder().url(lastUrl).build();
       try (Response res = client.newCall(request).execute()) {
           //assert res.body() !=null;
           assert res.body() != null;
           return res.body().string();
       }
   }
}
