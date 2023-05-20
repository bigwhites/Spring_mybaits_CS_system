package Server.example.VoluntaryReporting.mapper;


import Server.example.VoluntaryReporting.entity.Professional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProfessionalMapper {

    List<Professional> findAll();

    Professional findById(Integer proId);

    List<Professional> findByName(String proName );
    int addProfessional(Professional professional);

    int update(Professional professional);

    List<Professional> findByForeScore(Integer foreScore);

    List<Professional> findNameLike(String keyWord);

    int deleteById(Integer proId);

    int updateBaseData(Professional professional);
}
