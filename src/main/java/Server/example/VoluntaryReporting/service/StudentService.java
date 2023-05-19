package Server.example.VoluntaryReporting.service;

import Server.example.VoluntaryReporting.entity.Student;


import java.util.List;

public interface StudentService {

    List<Student> findAll();

    Student findById(Integer sId);

    Student findByIcrdId(String cardId);

    int addStudent(Student student);

    int upDateById(Student student);

    List<Student> findByRange(Integer pageSize , Integer begin);

    int getNum();

}
