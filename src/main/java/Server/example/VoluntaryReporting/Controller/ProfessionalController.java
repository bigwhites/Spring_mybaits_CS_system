package Server.example.VoluntaryReporting.Controller;

import Server.example.VoluntaryReporting.entity.Professional;
import Server.example.VoluntaryReporting.entity.UniverSity;
import Server.example.VoluntaryReporting.service.Impl.ProfessionalImpl;
import Server.example.VoluntaryReporting.service.Impl.SchoolChooseImpl;
import Server.example.VoluntaryReporting.service.Impl.UniverSityImpl;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/professional")
public class ProfessionalController {

    @Autowired
    ProfessionalImpl proImpl;
    @Autowired
    UniverSityImpl uniImpl;
    @Autowired
    SchoolChooseImpl schoolChooseImpl;

    Logger logger = LoggerFactory.getLogger(ProfessionalController.class);
    /*******
     * #Description:  新增某大学的专业（接受多个专业对象）
     * #Param: [java.lang.String] -> [ProArrJStr] 专业对象list的json串
     * #return: java.lang.String  成功新增的专业个数（唯一 + 外键约束）
     * #Date: 2023/5/16
     *******/
    @PostMapping("/add")
    @ResponseBody
    public String addProfessional(@RequestBody String proArrJStr) {
        List<Professional> pros = JSON.parseArray(proArrJStr, Professional.class);
        int res = 0;
        for (Professional pro : pros) {
            res += proImpl.addProfessional(pro);
        }
        return String.valueOf(res);
    }


    /*******
     * #Description: 通过专业名查询专业信息
     * #Param: [java.lang.String] -> [pName] 专业名
     * #return: java.lang.String  对象数组的JSON串
     * #Date: 2023/5/16
     *******/
    @GetMapping("/searchByPName")
    @ResponseBody
    public String searchProByName(@RequestParam("proName") String pName) {
        List<Professional> res = proImpl.findByName(pName);
        if (res.size()==0) {
            return null;
        } else {
            return JSON.toJSONString(res);
        }
    }

    /*******
     * #Description: 通过学校名名查询专业信息
     * #Param: [java.lang.String] -> [pName] 专业名
     * #return: java.lang.String  对象数组的JSON串
     * #Date: 2023/5/16
     *******/
    @GetMapping("/searchByUName")
    @ResponseBody
    public String searchUniName(@RequestParam("uniName") String uName) {
        UniverSity uni = uniImpl.findByName(uName);
        if (uni == null || uni.getProfessionals().size()==0) {
            return null;
        } else {
            return JSON.toJSONString(uni.getProfessionals());
        }
    }

    /*******
     * #Description: 通过预测分数得到推荐专业
     * #Param: [java.lang.Integer] -> [foreScore]
     * #return: java.lang.String List<Pro> 的JSON串
     * #Date: 2023/5/20
     *******/
    @GetMapping("/getRecommendPros")
    @ResponseBody
    public String getProsByForeScore(@RequestParam("score") Integer foreScore){
        return JSON.toJSONString(proImpl.findByForeScore(foreScore));
    }

    @GetMapping("/getByName")
    @ResponseBody
    public String searchByName(@RequestParam("keyWord") String keyWord){
        List<Professional> professionals = proImpl.findNameLike(keyWord);
        if(professionals==null || professionals.size()==0 ){
            return null;
        }
        else {
            String jsonString = JSON.toJSONString(professionals);
            logger.info("size={}",professionals.size());
            logger.info(jsonString);
            return jsonString;
        }
    }


    @GetMapping("/deleteByProId")
    @ResponseBody
    public String deleteProById(@RequestParam("proId") Integer proId)
    {
        String s = String.valueOf(proImpl.deleteById(proId));
        logger.info(s);
        return s;
    }

    @PostMapping("/updateById")
    @ResponseBody
    public String updateById(@RequestBody  String jsonStr){
        Professional professional = JSON.parseObject(jsonStr, Professional.class);
        logger.info(professional.toString());
        int res = proImpl.updateBaseData(professional);
        return String.valueOf(res);
    }

}

