package Server.example.VoluntaryReporting.Controller;

import Server.example.VoluntaryReporting.entity.Administrator;
import Server.example.VoluntaryReporting.service.Impl.AdministratorImpl;
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

    @GetMapping("/admin")
    @ResponseBody
    public String checkPassWord(@RequestParam(value = "userName" ) String userName){
        Administrator admin = administratorImpl.findByUsrName(userName);
        return admin==null?null:admin.getPasswordMd5();
    }

}

