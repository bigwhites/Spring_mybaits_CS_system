package Server.example.VoluntaryReporting.service.Impl;

import Server.example.VoluntaryReporting.entity.Professional;
import Server.example.VoluntaryReporting.entity.SchoolChoose;
import Server.example.VoluntaryReporting.entity.Student;
import Server.example.VoluntaryReporting.entity.UniverSity;
import Server.example.VoluntaryReporting.mapper.ProfessionalMapper;
import Server.example.VoluntaryReporting.mapper.SchoolChooseMapper;
import Server.example.VoluntaryReporting.mapper.StudentMapper;
import Server.example.VoluntaryReporting.mapper.UniverSityMapper;
import Server.example.VoluntaryReporting.service.SchoolChooseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


@Service
public class SchoolChooseImpl implements SchoolChooseService {


    @Autowired
    SchoolChooseMapper schoolChooseMapper;
    @Autowired
    UniverSityMapper univerSityMapper;
    @Autowired
    ProfessionalMapper professionalMapper;
    @Autowired
    StudentMapper studentMapper;

    @Override
    public int searchCntBySId(Integer sId) {
        return schoolChooseMapper.searchCntBySId(sId);
    }

    /*******
     * #Description: 根据学生id查找所有填报的志愿
     * #Param: [java.lang.Integer] -> [sId]
     * #return: java.util.List<SSchoolChoose> 填报的所有专业
     * #Date: 2023/5/17
     *******/
    @Override
    public List<SchoolChoose> findBySId(Integer sId) {
        Student s = studentMapper.findById(sId) ;
        if(s == null){
            return null;
        }
        List<SchoolChoose>  schoolChooses = schoolChooseMapper.findBySId(sId);
        for (SchoolChoose schoolChoose : schoolChooses){
            schoolChoose.setUniverSity(univerSityMapper.findById(schoolChoose.getUniId())); //查询学校
            LinkedList<Professional> professionals = new LinkedList<>();
            for(int i = 1 ; i <= 4 ; ++ i){  //查询对应的四个专业
                try {
                    Method gm = schoolChoose.getClass().getMethod("getProId"+String.valueOf(i));
                    Integer proId = (Integer) gm.invoke(schoolChoose);
                    Professional professional = professionalMapper.findById(proId);
                    professionals.addLast(professional);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {}
            }
            schoolChoose.setProfessionals(professionals.toArray(new Professional[4]));
        }
        return schoolChooses;
    }


    /*******
     * #Description:  填报志愿
     * #Param: [Server.example.VoluntaryReporting.entity.SchoolChoose] -> [schoolChoose] 待增加的学校记录
     * #return: int  是否成功添加
     * #Date: 2023/5/17
     *******/
    @Override
    public int insertChoose(SchoolChoose schoolChoose) {
        if (schoolChooseMapper.searchCntBySId(schoolChoose.getSId()) < SchoolChoose.MAX_CNT) {
            UniverSity m = null;
            if ((m = univerSityMapper.findById(schoolChoose.getUniId())) != null) {   //对学校的外键约束
                boolean ok = true;
                Integer[] proIds = {schoolChoose.getProId1(), schoolChoose.getProId2(),
                        schoolChoose.getProId3(), schoolChoose.getProId4()};
                for (Integer proId : proIds) {   //对专业的外键约束
                    Professional professional = null;
                    if (proId != null) {
                        professional = professionalMapper.findById(proId);
                    }
                    ok = proId == null || professional.getUId() == schoolChoose.getUniId(); //对数据正确性的冗余校验
                    if (!ok) {
                        break;
                    }
                }
                if (ok) {
                    try {
                        return schoolChooseMapper.insertChoose(schoolChoose);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }  //不满足主键唯一的情况由数据库处理
                }
            }
        }
        return 0;
    }

    @Override
    public int deleteAll() {
        return schoolChooseMapper.deleteAll();
    }
}
