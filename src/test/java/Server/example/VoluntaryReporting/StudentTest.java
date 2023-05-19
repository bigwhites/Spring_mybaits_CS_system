package Server.example.VoluntaryReporting;

import Server.example.VoluntaryReporting.entity.Student;
import Server.example.VoluntaryReporting.mapper.StudentMapper;
import Server.example.VoluntaryReporting.service.Impl.StudentImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StudentTest {
    @Autowired
    StudentMapper  studentMapperm ;

    @Autowired
    StudentImpl studentImpl;

    @Test
    void fBid(){
        System.out.println(studentMapperm.findById(210100).toString());
      //  System.out.println(studentMapperm.findByICrdId("").toString());
    }
    @Test
    void pwd(){
        Student s = studentMapperm.findById(210101);
        s.setPassWordMd5(DigestUtils.md5Hex("ricky0sss"));
        System.out.println(studentMapperm.upDateById(s));
    }

    @Test
    void getStuByPage(){
        List<Integer> integers = studentMapperm.getSidByIndex(2,0);
        integers.forEach(System.out::println);
        studentImpl.findByRange(4,0).forEach(student -> {
            System.out.println(student.toString());
        });
    }

    @Test
    void cbr(){
        System.out.println(studentMapperm.getNum());
        System.out.println(studentImpl.getNum());
    }

}
