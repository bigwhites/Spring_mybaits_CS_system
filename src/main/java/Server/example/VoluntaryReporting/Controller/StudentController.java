package Server.example.VoluntaryReporting.Controller;

import Server.example.VoluntaryReporting.entity.HighSchool;
import Server.example.VoluntaryReporting.entity.SchoolChoose;
import Server.example.VoluntaryReporting.entity.Student;
import Server.example.VoluntaryReporting.service.Impl.HighSchoolSerImpl;
import Server.example.VoluntaryReporting.service.Impl.SchoolChooseImpl;
import Server.example.VoluntaryReporting.service.Impl.StudentImpl;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentImpl studentImpl;
    @Autowired
    HighSchoolSerImpl schooImpl ;
    @Autowired
    SchoolChooseImpl schoolChooseImpl;

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
            return null;
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
        return getChooseCnt(schoolChooses.get(0).getSId());
    }

}
