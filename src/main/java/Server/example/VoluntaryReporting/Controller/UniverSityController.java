package Server.example.VoluntaryReporting.Controller;

import Server.example.VoluntaryReporting.entity.UniverSity;
import Server.example.VoluntaryReporting.service.Impl.UniverSityImpl;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/university")
public class UniverSityController {
    @Autowired
    UniverSityImpl univerSityImp;


    /*******
     * #Description:
     * #Param: [java.lang.Integer] -> [uniId]  院校代码
     * #return: java.lang.String  当前University对象的json串
     * #Date: 2023/5/16
     *******/
    @GetMapping("/searchById")
    @ResponseBody
    public String searchById(Integer uniId) {

        UniverSity uni = univerSityImp.findById(uniId);
        if (uni == null) {
            return null;
        } else {
            return JSON.toJSONString(uni);

        }
    }

    /*******
     * #Description:通过校名查询学校
     * #Param: [java.lang.String] -> [uName]
     * #return: java.lang.String  空值或对象的json串
     * #Date: 2023/5/16
     *******/
    @GetMapping("searchByName")
    @ResponseBody
    public String searchByName(@RequestParam("uName") String uName){
        UniverSity univerSity = univerSityImp.findByName(uName);
        if(univerSity==null){
            return null;
        }
        return JSON.toJSONString(univerSity);
    }

    /*******
     * #Description:
     * #Param: void
     * #return: java.lang.String 所有学生数据的json串
     * #Date: 2023/5/16
     *******/
    @GetMapping("/getAll")
    @ResponseBody
    public String searchAll() {
        List<UniverSity> ulist = univerSityImp.findAll();
        return JSON.toJSONString(ulist);
    }

    /*******
     * #Description:  新增大学 （可以同时新增多个）
     * #Param: [java.lang.String] -> [uniListJStr] 大学对象的list的json串 （请求体）
     * #return: java.lang.String  ->成功新增的数量的字符串
     * #Date: 2023/5/16
     *******/
    @PostMapping("/add")
    @ResponseBody
    public String addUniversity(@RequestBody String uniListJStr){
        List<UniverSity> univerSities = JSON.parseArray(uniListJStr,UniverSity.class);
        int res = 0;
        for(UniverSity u : univerSities){
            res += univerSityImp.addUniverSity(u);
        }
        return String.valueOf(res);
    }

    /*******
     * #Description: 更新学校的名字
     * #Param: [java.lang.String] -> [jsonStr]
     * #return: java.lang.String
     * #Date: 2023/5/21
     *******/
    @PostMapping("/update")
    @ResponseBody
    public String update(@RequestBody String jsonStr){
        UniverSity univerSity = JSON.parseObject(jsonStr, UniverSity.class);
        return String.valueOf(univerSityImp.update(univerSity));
    }


}
