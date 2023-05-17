package Server.example.VoluntaryReporting.service;

import Server.example.VoluntaryReporting.entity.SchoolChoose;

public interface SchoolChooseService {
    int searchCntBySId(Integer sId);


    int insertChoose(SchoolChoose schoolChoose);
}
