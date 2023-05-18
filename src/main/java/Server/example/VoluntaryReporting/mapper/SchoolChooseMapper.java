package Server.example.VoluntaryReporting.mapper;


import Server.example.VoluntaryReporting.entity.SchoolChoose;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SchoolChooseMapper {
    int searchCntBySId(Integer sId);

    List<SchoolChoose> findBySId(Integer sId);

    int insertChoose(SchoolChoose schoolChoose);
}
