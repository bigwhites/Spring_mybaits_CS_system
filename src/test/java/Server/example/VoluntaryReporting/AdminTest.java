package Server.example.VoluntaryReporting;

import Server.example.VoluntaryReporting.entity.Administrator;
import Server.example.VoluntaryReporting.service.AdministratorService;
import Server.example.VoluntaryReporting.service.Impl.AdministratorImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminTest {

    @Autowired
    AdministratorImpl m;

    @Test
    void findUser(){
        Administrator ad = m.findByUsrName("admin");
        System.out.println(ad);
    }

    @Test
    void addAdmin(){
        Administrator ad = new Administrator("root", DigestUtils.md5Hex("123456"));
        System.out.println(m.addAdmin(ad));
    }

}
