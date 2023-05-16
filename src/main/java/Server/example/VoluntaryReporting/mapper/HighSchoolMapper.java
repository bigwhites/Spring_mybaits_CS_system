package Server.example.VoluntaryReporting.mapper;

import Server.example.VoluntaryReporting.entity.HighSchool;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HighSchoolMapper {
    HighSchool findById(Integer schId);

}
