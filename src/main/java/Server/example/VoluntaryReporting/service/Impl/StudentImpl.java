package Server.example.VoluntaryReporting.service.Impl;

import Server.example.VoluntaryReporting.entity.Student;
import Server.example.VoluntaryReporting.mapper.StudentMapper;
import Server.example.VoluntaryReporting.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Override
    public List<Student> findAll() {
        return studentMapper.findAll();
    }

    @Override
    public Student findById(Integer sId) {
        return studentMapper.findById(sId);
    }

    @Override
    public Student findByIcrdId(String cardId) {
        return studentMapper.findByICrdId(cardId);
    }

    @Override
    public int addStudent(Student student) {
        if(studentMapper.findByICrdId(student.getIdentyId()) !=null){  //保证不重复添加
            return 0;
        }
        return studentMapper.addStudent(student);
    }
}
