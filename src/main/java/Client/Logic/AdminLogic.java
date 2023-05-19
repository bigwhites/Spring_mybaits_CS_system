package Client.Logic;

import Client.ScannerSingleInst;
import Server.example.VoluntaryReporting.entity.AdmitResult;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import utils.HttpConnect;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminLogic {
    private AdminLogic() {}

    /*******
     * #Description: 删除账户
     * #Param: [java.lang.String] -> [userId]
     * #return: void
     * #Date: 2023/5/18
     *******/
    public static boolean deleteById(String userId) throws IOException {
        HttpConnect.getInst().addUrlPath("/login/deleteAdmin");
        HttpConnect.getInst().addGetParam("userName",userId);
        return ! HttpConnect.getInst().GetRequest().equals("0");

    }

    /*******
     * #Description: 准备POST请求的参数
     * #Param: [] -> []
     * #return: Map<S,S> userName,pwdMd5
     * #Date: 2023/5/19
     *******/
    public static Map<String,String> prepareAdminData(){
        System.out.print("请输入您的用户名>>>>>>");
        String uesrName = ScannerSingleInst.getInst().next();
        System.out.print("请输入您的密码>>>>>>");
        String pwd = ScannerSingleInst.getInst().next();
        Map<String,String> parms = new HashMap<>();
        parms.put("userName",uesrName);
        parms.put("pwdMd5", DigestUtils.md5Hex(pwd));
        return parms;
    }

    public static void statAdmit() throws IOException {
        HttpConnect.getInst().addUrlPath("/admit/startAdmin");
        int respond = Integer.parseInt(HttpConnect.getInst().PostRequset(prepareAdminData()));
        if(respond==-1){
            System.out.println("管理员密码错误，录取失败");
        }
        else {
            System.out.printf("成功录取了%d个学生的信息\n",respond);
        }
    }

    public static void getAllAdmitRes() throws IOException {
        HttpConnect.getInst().addUrlPath("/admit/getAllResult");
        String resPond = HttpConnect.getInst().PostRequset(prepareAdminData());
        if(resPond.equals("ERROR PWD")){
            System.out.println("密码错误");
        }
        else if(resPond.equals("")) {
            System.out.println("还未开始录取");
        }
        List<AdmitResult> admitResults = JSON.parseArray(resPond, AdmitResult.class);
        for (AdmitResult admitResult : admitResults) {
            System.out.println(admitResult.toString());
        }
    }

    public static void queryAdmitStatus() throws IOException {
        HttpConnect.getInst().addUrlPath("/admit/queryStatus");
        String res = HttpConnect.getInst().GetRequest();
        if(res.equals("1")){
            System.out.println("录取工作已经结束");
        }
        else {
            System.out.println("录取工作还未开始");
        }
    }
}
