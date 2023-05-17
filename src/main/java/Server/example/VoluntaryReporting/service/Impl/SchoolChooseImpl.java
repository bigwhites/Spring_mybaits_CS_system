package Server.example.VoluntaryReporting.service.Impl;

import Server.example.VoluntaryReporting.entity.Professional;
import Server.example.VoluntaryReporting.entity.SchoolChoose;
import Server.example.VoluntaryReporting.entity.UniverSity;
import Server.example.VoluntaryReporting.mapper.ProfessionalMapper;
import Server.example.VoluntaryReporting.mapper.SchoolChooseMapper;
import Server.example.VoluntaryReporting.mapper.UniverSityMapper;
import Server.example.VoluntaryReporting.service.SchoolChooseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SchoolChooseImpl implements SchoolChooseService {


    @Autowired
    SchoolChooseMapper schoolChooseMapper;
    @Autowired
    UniverSityMapper univerSityMapper;
    @Autowired
    ProfessionalMapper professionalMapper;

    @Override
    public int searchCntBySId(Integer sId) {
        return schoolChooseMapper.searchCntBySId(sId);
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
}
