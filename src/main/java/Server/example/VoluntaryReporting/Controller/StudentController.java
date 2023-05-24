package Server.example.VoluntaryReporting.Controller;

import Server.example.VoluntaryReporting.entity.HighSchool;
import Server.example.VoluntaryReporting.entity.SchoolChoose;
import Server.example.VoluntaryReporting.entity.Student;
import Server.example.VoluntaryReporting.service.Impl.HighSchoolSerImpl;
import Server.example.VoluntaryReporting.service.Impl.SchoolChooseImpl;
import Server.example.VoluntaryReporting.service.Impl.StudentImpl;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentImpl studentImpl;
    @Autowired
    HighSchoolSerImpl schooImpl ;
    @Autowired
    SchoolChooseImpl schoolChooseImpl;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*******
     * #Description: 处理查询考生信息的get请求
     * #Param: [java.lang.Integer] -> [sId] 考生号
     * #return: java.lang.String 对应Student对象的JSON串 或 null
     * #Date: 2023/5/16
     *******/
    @GetMapping("/findById")
    @ResponseBody
    String findById(@RequestParam("sId") Integer sId){
        Student student = studentImpl.findById(sId);
        if(student==null){
            return null;
        }
        else {
            return JSON.toJSONString(student);
        }
    }

    /*******
     * #Description: 处理添加学生的POST请求
     * #Param: [java.lang.String] -> [jsonArrStr] 传入的对象数组
     * #return: java.lang.String 成功添加的个数
     * #Date: 2023/5/16
     *******/
    @PostMapping("/add")
    @ResponseBody
    String add(@RequestBody String jsonArrStr ){
        List<Student> students = JSON.parseArray(jsonArrStr,Student.class);
        int cnt = 0;
        for(Student student : students){
            cnt += studentImpl.addStudent(student);
        }
        return String.valueOf(cnt);
    }

    /*******
     * #Description:  根据学校名查找该学生的学校  校名唯一
     * #Param: [java.lang.String] -> [schName] 该学生的学校的名字
     * #return: java.lang.String   学校
     * #Date: 2023/5/17
     *******/
    @GetMapping("/findSchoolByName")
    @ResponseBody
    String finSchByName(@RequestParam("schName") String schName){
        HighSchool school = schooImpl.findByName(schName);
        if(school == null){
            return null;
        }
        else {
            return JSON.toJSONString(school);
        }
    }

    @GetMapping("/findAll")
    @ResponseBody
    public String findAll(){
        return JSON.toJSONString(studentImpl.findAll());
    }


    /*******
     * #Description: 查询学生报的填报的专业的数量
     * #Param: [java.lang.Integer] -> [sId]
     * #return: java.lang.String 报专业数量的字符串
     * #Date: 2023/5/17
     *******/
    @GetMapping("/getChooseCnt")
    @ResponseBody
    String getChooseCnt(@RequestParam("sId") Integer sId){
        if(studentImpl.findById(sId) ==null){
            return String.valueOf(0);
        }
        else {
            return String.valueOf(schoolChooseImpl.searchCntBySId(sId));
        }
    }


    /*******
     * #Description: 查询学生的所有志愿
     * #Param: [java.lang.Integer] -> [sId]
     * #return: java.lang.String 所填志愿的JSON串
     * #Date: 2023/5/17
     *******/
    @GetMapping("/getSchoolChooseBysId")
    @ResponseBody
    String getSchoolChoose(@RequestParam("sId") Integer sId){
        if(studentImpl.findById(sId)==null){
            return null;
        }
        List<SchoolChoose> bySId = schoolChooseImpl.findBySId(sId);
        logger.info("{}",bySId.size());
        return JSON.toJSONString(bySId);
    }

    /*******
     * #Description:  批量填报专业
     * #Param: [java.lang.String] -> [schChooseArrJsonStr] 所报专业的对象数组的JSON串
     * #return: java.lang.String 成功填报的专业的对象数组的JSON串
     * #Date: 2023/5/17
     *******/
    @PostMapping("/addSchoolChoose")
    @ResponseBody
    String addSchoolChoose(@RequestBody String schChooseArrJsonStr){
        List<SchoolChoose> schoolChooses = JSON.parseArray(schChooseArrJsonStr, SchoolChoose.class);
        for(SchoolChoose schoolChoose :schoolChooses){
            schoolChooseImpl.insertChoose(schoolChoose);
        }
        return String.valueOf(schoolChooseImpl.searchCntBySId(schoolChooses.get(0).getSId()));
    }

    @PostMapping("/upDatePwd")
    @ResponseBody
    String setPwd(@RequestParam Map<String,String> parms){
        Integer sId = Integer.parseInt(parms.get("sId"));
        String oriPwd = parms.get("oriPwdMd5");
        String newPwd = parms.get("newPwdMd5");
        logger.info("oriPwd={} newPwd={}",oriPwd,newPwd);
        Student student = studentImpl.findById(sId);
        String curPwd = student.getPassWordMd5();
        if(!curPwd.equals(oriPwd)) {
            return "0";
        }
        else if(oriPwd.equals(newPwd)){
            return "-1";
        }
        student.setPassWordMd5(newPwd);
        //更新
        return String.valueOf(studentImpl.upDateById(student));
    }

    /*******
     * #Description:  更新学生的信息
     * #Param: [java.lang.String] -> [stuJsonStr] 学生对象的JSON串
     * #return: java.lang.String 更新的结果对象
     * #Date: 2023/5/18
     *******/
    @PostMapping("/upDateData")
    @ResponseBody
    String upDateBySId(@RequestBody String stuJsonStr){
        Student student = JSON.parseObject(stuJsonStr,Student.class);
        if(studentImpl.findById(student.getSId())==null){
            return null;
        }
        else
        {
            studentImpl.upDateById(student);
            Student newStu = studentImpl.findById(student.getSId());
            logger.info("{}",newStu.toString());
            return JSON.toJSONString(newStu);
        }
    }

    @GetMapping("/addHighSchool")
    @ResponseBody
    HighSchool addHighSchool(@RequestParam("schName")String schName){
        logger.info("添加高中");
        return schooImpl.addHighSchool(schName);
    }
}
