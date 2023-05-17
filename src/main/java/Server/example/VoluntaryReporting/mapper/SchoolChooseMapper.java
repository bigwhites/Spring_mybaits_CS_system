package Server.example.VoluntaryReporting.mapper;


import Server.example.VoluntaryReporting.entity.SchoolChoose;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SchoolChooseMapper {
    int searchCntBySId(Integer sId);



    int insertChoose(SchoolChoose schoolChoose);
}
