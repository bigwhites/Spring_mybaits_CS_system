package Server.example.VoluntaryReporting.service.Impl;

import Server.example.VoluntaryReporting.entity.Student;
import Server.example.VoluntaryReporting.mapper.HighSchoolMapper;
import Server.example.VoluntaryReporting.mapper.StudentMapper;
import Server.example.VoluntaryReporting.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class StudentImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    HighSchoolMapper highSchoolMapper;

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


    /*******
     * #Description:  根据学生对象内部的sId来更新学生
     * #Param: [Server.example.VoluntaryReporting.entity.Student] -> [student] 需要修改的学生参数
     * #return: int 是否成功 only（0/1）
     * #Date: 2023/5/18
     *******/
    @Override
    public int upDateById(Student student) {
        return studentMapper.upDateById(student);
    }

    @Override
    public List<Student> findByRange(Integer pageSize, Integer begin) {
            List<Integer> sIds = studentMapper.getSidByIndex(pageSize,begin);
            List<Student> students = new LinkedList<>();
            int i = 1;
            for(Integer sId : sIds){
                Student student  = studentMapper.findById(sId);
                student.setScoreIndex(begin + i++);
                students.add(student);
            }
            return students;
    }

    @Override
    public int getNum() {
       return studentMapper.getNum();
    }
}
