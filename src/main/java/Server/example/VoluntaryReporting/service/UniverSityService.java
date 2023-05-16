package Server.example.VoluntaryReporting.service;


import Server.example.VoluntaryReporting.entity.UniverSity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UniverSityService {

    UniverSity findById(Integer uId);

    int addUniverSity(UniverSity univerSity);
}
