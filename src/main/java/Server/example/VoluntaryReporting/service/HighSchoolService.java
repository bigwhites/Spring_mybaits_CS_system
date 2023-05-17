package Server.example.VoluntaryReporting.service;

import Server.example.VoluntaryReporting.entity.HighSchool;

public interface HighSchoolService {

    /*******
     * #Description:按照学校的id查询学校
     * #Param: [java.lang.Integer] -> [id]
     * #return: Server.example.VoluntaryReporting.entity.HighSchool
     * #Date: 2023/5/16
     *******/
    HighSchool findById(Integer id);

    HighSchool findByName(String name);
}
