package Server.example.VoluntaryReporting.Controller;

import Server.example.VoluntaryReporting.entity.Administrator;
import Server.example.VoluntaryReporting.entity.AdmitResult;
import Server.example.VoluntaryReporting.service.Impl.AdministratorImpl;
import Server.example.VoluntaryReporting.service.Impl.AdmitResultImpl;
import Server.example.VoluntaryReporting.service.Impl.SchoolChooseImpl;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admit")
public class AdmitController { //录取结果
    Logger logger = LoggerFactory.getLogger(AdmitController.class);
@Autowired
    AdmitResultImpl admitResultImpl;
@Autowired
    AdministratorImpl administratorImpl;
@Autowired
    SchoolChooseImpl schoolChooseImpl;

    /*******
     * #Description: 查询是否开始录取
     * #Param: [] -> [] void
     * #return: java.lang.String 查询当前的录取状态 0/1串
     * #Date: 2023/5/18
     *******/
    @GetMapping("/queryStatus")
    @ResponseBody
    public String queryStatus(){
        return admitResultImpl.resultsCount()>0?"1":"0";
    }

    /*******
     * #Description: 开始录取
     * #Param: [java.util.Map<java.lang.String,java.lang.String>] -> [parms]
     * ->  useId,pwdMd5 ==》考生号，密码密文
     * #return: java.lang.String  成功录取的学生的数量的字符串  -1表示密码错误
     * #Date: 2023/5/18
     *******/
    @PostMapping("/startAdmin")
    @ResponseBody
    public String startAdmin(@RequestParam Map<String,String> parms){
        Administrator admin = getAdmin(parms);
        if(admin==null || ! admin.getPasswordMd5().equals(parms.get("pwdMd5"))){
            return "-1";
        }
        logger.info("admin {}",admin.getPasswordMd5());
        admitResultImpl.deleteAllResults();
        return String.valueOf(admitResultImpl.admitAndLog());
    }
    private Administrator getAdmin(Map<String, String> parms) {
        String userName = parms.get("userName");
        return administratorImpl.findByUsrName(userName);
    }

    /*******
     * #Description: 获取所有录取结果
     * #Param: [java.util.Map<java.lang.String,java.lang.String>]
     * ---> userName,pwdMd5 管理员的账号和密码
     * #return: java.lang.String AdmitResult对象List的JSON串
     * #Date: 2023/5/18
     *******/
    @PostMapping("/getAllResult")
    @ResponseBody
    public String getAll(@RequestParam Map<String,String> parms) {
        Administrator administrator = getAdmin(parms);
        if (administrator == null || !administrator.getPasswordMd5().equals(parms.get("pwdMd5"))) {
            return "ERROR PWD";
        } else if (admitResultImpl.resultsCount() == 0) {
            return null;
        } else {
            return JSON.toJSONString(admitResultImpl.findAll());
        }
    }



    @GetMapping("/getResultBySId")
    @ResponseBody
    public String getBySId(@RequestParam("sId") Integer sId){
        AdmitResult admitResult = admitResultImpl.findBySid(sId);
        return admitResult==null?null:JSON.toJSONString(admitResult);
    }

    /*******
     * #Description: 清除所有录取信息和填报志愿信息
     * #Param: [java.util.Map<java.lang.String,java.lang.String>] -> [parms]
     * #return: java.lang.String
     * #Date: 2023/5/20
     *******/
    @PostMapping("/clearAllRes")
    @ResponseBody
    public String clearAll(@RequestParam Map<String,String> parms){
        Administrator administrator = getAdmin(parms);
        if (administrator == null || !administrator.getPasswordMd5().equals(parms.get("pwdMd5"))) {
            return "ERROR PWD";
        } else{
            schoolChooseImpl.deleteAll();
            admitResultImpl.deleteAllResults();
            admitResultImpl.cleanRecord(); //清除专业表中的记录
            return "ok";
        }
    }

}
