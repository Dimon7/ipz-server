package corse_work.demo.service.impl;

import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.model.Student;
import corse_work.demo.model.Team;
import corse_work.demo.model.User;
import corse_work.demo.model.repository.StudentRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import corse_work.demo.service.interfaces.StudentService;

import java.util.List;
import java.util.Optional;
@Log

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student create(Student student) {
        return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student update(Student student) {
        return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student getById(Long id) throws AppException {
        Optional<Student> student =  studentRepository.findById(id);

        if(!student.isPresent()){
            String error = "There is no ROLE_STUDENT with id=" + id;
            log.info(error);
            throw new AppException(error);
        }

        return student.get();
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<List<Student>> getStudentsByTeam(Team team) {
        return studentRepository.getStudentsByTeam(team);
    }

    @Override
    public Optional<Student> getStudentByUser(User u) {
        return studentRepository.getStudentByUser(u);
    }

    @Override
    public void delete(Long id) {

    }
}
