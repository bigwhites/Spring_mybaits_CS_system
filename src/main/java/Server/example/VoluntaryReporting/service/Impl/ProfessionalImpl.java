package Server.example.VoluntaryReporting.service.Impl;

import Server.example.VoluntaryReporting.entity.Professional;
import Server.example.VoluntaryReporting.entity.UniverSity;
import Server.example.VoluntaryReporting.mapper.ProfessionalMapper;
import Server.example.VoluntaryReporting.mapper.SchoolChooseMapper;
import Server.example.VoluntaryReporting.mapper.UniverSityMapper;
import Server.example.VoluntaryReporting.service.ProfessionalService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Mapper
@Repository
public class ProfessionalImpl implements ProfessionalService {

    @Autowired
    ProfessionalMapper professionalMapper;
    @Autowired
    UniverSityMapper univerSityMapper;
    @Autowired
    SchoolChooseMapper schoolChooseMapper;
    @Override
    public int addProfessional(Professional professional) {

        UniverSity uni = univerSityMapper.findById(professional.getUId());
        if(uni != null){ //外键约束
            List<Professional> sameNamePros = professionalMapper.findByName(professional.getProName());  //查找同名专业 若同校则重复
            for(Professional pro : sameNamePros){
                if(pro.getUniverSity().getUName().equals(uni.getUName())){
                    return  0;   //同专业名且同学校则重复
                }
            }
        }
        return professionalMapper.addProfessional(professional);
    }

    @Override
    public Professional findById(Integer proId) {
        return professionalMapper.findById(proId);
    }

    @Override
    public List<Professional> findAll() {
        return professionalMapper.findAll();
    }

    @Override
    public List<Professional> findByName(String proName) {
        return professionalMapper.findByName(proName);
    }

    @Override
    public int update(Professional professional) {
        return professionalMapper.update(professional);
    }

    @Override
    public int updateBaseData(Professional professional) {
        List<Professional> professionals = professionalMapper.findByName(professional.getProName());
        for (Professional proSameName : professionals) {
            if(professional.getProId() == proSameName.getProId()){
                continue;
            }
            else if(Objects.equals(proSameName.getProName(), professional.getProName())){
              return -1;
            }
        }
        return professionalMapper.updateBaseData(professional);
    }

    @Override
    public List<Professional> findByForeScore(Integer foreScore) {
        return professionalMapper.findByForeScore(foreScore);
    }

    /*******
     * #Description: 查询所有以keyWord为字串的专业名对应的专业对象
     * #Param: [java.lang.String] -> [keyWord]
     * #return: java.util.List<Server.example.VoluntaryReporting.entity.Professional>
     * #Date: 2023/5/20
     *******/
    @Override
    public List<Professional> findNameLike(String keyWord) {
        StringBuilder stringBuilder = new StringBuilder(keyWord);
        stringBuilder.append("%");
        stringBuilder.insert(0,"%");
        return professionalMapper.findNameLike(stringBuilder.toString());
    }

    @Override
    public int deleteById(Integer proId) {
        if(schoolChooseMapper.getCntByProId(proId)!=0){
            return 0;
        }
        else {
            return professionalMapper.deleteById(proId);
        }

    }
}
