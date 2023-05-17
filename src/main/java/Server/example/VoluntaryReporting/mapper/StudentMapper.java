package Server.example.VoluntaryReporting.mapper;

import Server.example.VoluntaryReporting.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {
     Student findById(Integer sId);

     Student findByICrdId(String cardId);

     List<Student> findAll();

     int addStudent(Student student);
}
