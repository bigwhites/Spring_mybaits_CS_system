package Server.example.VoluntaryReporting;

import Server.example.VoluntaryReporting.entity.UniverSity;
import Server.example.VoluntaryReporting.service.Impl.UniverSityImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.commons.codec.digest.DigestUtils;

@SpringBootTest
public class UnversityiTest {

    @Autowired
    UniverSityImpl uniImpl;

    @Test
    void tFindById() {
        UniverSity u = uniImpl.findById(200000);
        System.out.println(u.toString());
    }

    @Test
    void insertUni() {
        UniverSity u = new UniverSity();
        u.setUName("湖南大学");
        System.out.println(uniImpl.addUniverSity(u));

    }

    @Test
    void test(){
        String md = DigestUtils.md5Hex("admin");
        System.out.println(md);
    }
}
