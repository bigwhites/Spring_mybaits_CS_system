package Server.example.VoluntaryReporting.service;


import Server.example.VoluntaryReporting.entity.UniverSity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UniverSityService {

    List<UniverSity> findAll();
    UniverSity findById(Integer uId);

    UniverSity findByName(String uName);

    int addUniverSity(UniverSity univerSity);
}
