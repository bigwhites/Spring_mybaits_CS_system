package Server.example.VoluntaryReporting.service.Impl;

import Server.example.VoluntaryReporting.entity.HighSchool;
import Server.example.VoluntaryReporting.mapper.HighSchoolMapper;
import Server.example.VoluntaryReporting.service.HighSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HighSchoolSerImpl implements HighSchoolService {

    @Autowired
    private HighSchoolMapper hSMapper;

    @Override
    public HighSchool findById(Integer id) {
        return hSMapper.findById(id);
    }



}
