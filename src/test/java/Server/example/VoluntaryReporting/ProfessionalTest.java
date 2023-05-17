package Server.example.VoluntaryReporting;

import Server.example.VoluntaryReporting.entity.Professional;
import Server.example.VoluntaryReporting.service.Impl.ProfessionalImpl;
import Server.example.VoluntaryReporting.service.Impl.UniverSityImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProfessionalTest {

    @Autowired
    ProfessionalImpl proImpl;
    @Autowired
    UniverSityImpl uniImpl;
    @Test
    void testInsert(){
        for(int uid = 200002;uid<= 200004;++uid) {
            int x =proImpl.addProfessional(
                    new Professional(0, "电信工程", 640, 400, 0, null, uid, uniImpl.findById(uid))
            );
            System.out.println(x);
        }
    }

    @Test
    void ff(){
        System.out.println(proImpl.findById(1001));
    }
}
