package Server.example.VoluntaryReporting;

import Server.example.VoluntaryReporting.entity.AdmitResult;
import Server.example.VoluntaryReporting.service.Impl.AdmitResultImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdmitTest {

    @Autowired
    AdmitResultImpl admitResultImpl;

    @Test
    void ssss(){
        admitResultImpl.admitAndLog();
    }

    @Test
    void t(){
        for (AdmitResult admitResult : admitResultImpl.findAll()) {
            System.out.println(admitResult.toString());
        }
    }
}
