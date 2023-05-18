package Server.example.VoluntaryReporting.service;

import Server.example.VoluntaryReporting.entity.SchoolChoose;

import java.util.List;

public interface SchoolChooseService {
    int searchCntBySId(Integer sId);

    List<SchoolChoose> findBySId(Integer sId);

    int insertChoose(SchoolChoose schoolChoose);
}
