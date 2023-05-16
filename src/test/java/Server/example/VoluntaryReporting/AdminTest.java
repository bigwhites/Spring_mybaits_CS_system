package Server.example.VoluntaryReporting;

import Server.example.VoluntaryReporting.entity.Administrator;
import Server.example.VoluntaryReporting.service.AdministratorService;
import Server.example.VoluntaryReporting.service.Impl.AdministratorImpl;
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

}
