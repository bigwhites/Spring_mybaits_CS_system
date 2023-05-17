package Server.example.VoluntaryReporting;

import Server.example.VoluntaryReporting.entity.UniverSity;
import Server.example.VoluntaryReporting.service.Impl.UniverSityImpl;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

@SpringBootTest
public class UnversityiTest {

    @Autowired
    UniverSityImpl uniImpl;

    @Test
    void tFindById() {
//        UniverSity u = uniImpl.findById(200004);
//        System.out.println(u.toString());
//        System.out.println(uniImpl.findByName("清华大学").toString());
        List<UniverSity> ul = JSON.parseArray(JSON.toJSONString(uniImpl.findAll()),UniverSity.class);
        for (UniverSity uit : ul) {
            System.out.println(uit.toString());
        }
    }

    @Test
    void insertUni() {
        UniverSity u = new UniverSity();
        u.setUName("湖南大学");
        System.out.println(uniImpl.addUniverSity(u));

    }

    @Test
    void test() {
        String md = DigestUtils.md5Hex("admin");
        System.out.println(md);
    }
}
