package Server.example.VoluntaryReporting.service.Impl;

import Server.example.VoluntaryReporting.entity.Administrator;
import Server.example.VoluntaryReporting.mapper.AdministratorMapper;
import Server.example.VoluntaryReporting.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorImpl implements AdministratorService {
    @Autowired
    AdministratorMapper administratorMapper;
    @Override
    public Administrator findByUsrName(String userName) {
        return administratorMapper.findByUserName(userName);
    }
}
