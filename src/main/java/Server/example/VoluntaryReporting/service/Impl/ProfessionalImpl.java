package Server.example.VoluntaryReporting.service.Impl;

import Server.example.VoluntaryReporting.entity.Professional;
import Server.example.VoluntaryReporting.entity.UniverSity;
import Server.example.VoluntaryReporting.mapper.ProfessionalMapper;
import Server.example.VoluntaryReporting.mapper.UniverSityMapper;
import Server.example.VoluntaryReporting.service.ProfessionalService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public class ProfessionalImpl implements ProfessionalService {

    @Autowired
    ProfessionalMapper professionalMapper;
    @Autowired
    UniverSityMapper univerSityMapper;
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
}
