package Server.example.VoluntaryReporting;

import Server.example.VoluntaryReporting.entity.SchoolChoose;
import Server.example.VoluntaryReporting.service.Impl.SchoolChooseImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SchoolChooseTest {

    @Autowired
    SchoolChooseImpl schoolChooseImpl ;
    @Test
    void tstCnt(){
        System.out.println(schoolChooseImpl.searchCntBySId(210100));
    }

    @Test
    void tetsInsert(){
        System.out.println(schoolChooseImpl.insertChoose(new SchoolChoose(
                210106,1004,1014,null,null,1,1,200004,null,null
        )));
    }

    @Test
    void testfind(){
        List<SchoolChoose> schoolChooses = schoolChooseImpl.findBySId(210106);
        schoolChooses.forEach(System.out::println);
    }
}
