package Server.example.VoluntaryReporting.Controller;

import Server.example.VoluntaryReporting.entity.Administrator;
import Server.example.VoluntaryReporting.entity.Student;
import Server.example.VoluntaryReporting.service.Impl.AdministratorImpl;
import Server.example.VoluntaryReporting.service.Impl.StudentImpl;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LogInController {


    @Autowired
    AdministratorImpl administratorImpl;
    @Autowired
    StudentImpl studentImpl;
    final  Logger logger = LoggerFactory.getLogger(LogInController.class);
    /*******
     * #Description:  处理管理员的登录请求
     * #Param: [java.lang.String] -> [userName] 用户名
     * #return: java.lang.String   密码的md5密文
     * #Date: 2023/5/16
     *******/
    @GetMapping("/admin")
    @ResponseBody
    public String checkPassWord(@RequestParam(value = "userName") String userName) {
        Administrator admin = administratorImpl.findByUsrName(userName);
        return admin == null ? null : admin.getPasswordMd5();
    }


    /*******
     * #Description:  处理学生的登录请求
     * #Param: [java.lang.String] -> [userName] 用户名
     * #return: java.lang.String  学生对象的MD5加密后的密文 不存在的学生返回null
     * #Date: 2023/5/16
     *******/
    @GetMapping("/student")
    @ResponseBody
    public String checkPassword(@RequestParam(value = "uId") Integer uId){
        Student student = studentImpl.findById(uId);
        if(student==null){
            return null;
        }
        else {
            return student.getPassWordMd5();
        }
    }

    /*******
     * #Description: 接受GET注册请求 成功返回 “ok" 失败返回"user exist"
     * #Param: [java.lang.String, java.lang.String] -> [userName, md5Pwd] 用户名，md5加密后的密文
     * #return: java.lang.String
     * #Date: 2023/5/16
     *******/
    @GetMapping("/signUp")
    @ResponseBody
    public String signUp(@RequestParam(value = "userName") String userName,@RequestParam(value = "md5Pwd") String md5Pwd) {
        if (administratorImpl.findByUsrName(userName) != null) {
            return "user exist";
        }
        administratorImpl.addAdmin(new Administrator(userName, md5Pwd));
        return "ok";
    }

    /*******
     * #Description:  管理员账号注销
     * #Param: [java.lang.String] -> [userName] 管理员的用户名
     * #return: java.lang.String 0/1的串代表成功或失败
     * #Date: 2023/5/18
     *******/
    @GetMapping("/deleteAdmin")
    @ResponseBody
    public String deleteAdmin(@RequestParam("userName") String userName){
        logger.info("{} is delete ",userName);
        return String.valueOf(administratorImpl.deleteByUserName(userName));
    }

}

