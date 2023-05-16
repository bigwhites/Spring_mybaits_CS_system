package Server.example.VoluntaryReporting.service.Impl;

import Server.example.VoluntaryReporting.entity.UniverSity;
import Server.example.VoluntaryReporting.mapper.UniverSityMapper;
import Server.example.VoluntaryReporting.service.UniverSityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniverSityImpl implements UniverSityService {
    @Autowired
    UniverSityMapper univerSityMapper;

    @Override
    public UniverSity findById(Integer uId) {
        return univerSityMapper.findById(uId);
    }

    @Override
    public int addUniverSity(UniverSity univerSity) {
        //先保证无重复再添加
        return (univerSityMapper.findById(univerSity.getUId()) == null) &&
                (univerSityMapper.addUniverSity(univerSity) == 1) ? 1 : 0;
    }
}
