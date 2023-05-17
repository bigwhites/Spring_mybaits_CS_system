package Server.example.VoluntaryReporting;

import Server.example.VoluntaryReporting.entity.Student;
import Server.example.VoluntaryReporting.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentTest {
    @Autowired
    StudentMapper  studentMapperm ;

    @Test
    void fBid(){
        System.out.println(studentMapperm.findById(210100).toString());
      //  System.out.println(studentMapperm.findByICrdId("").toString());

    }

}
