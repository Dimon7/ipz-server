package corse_work.demo.service.interfaces;

import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.model.Student;
import corse_work.demo.model.Team;
import corse_work.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student create(Student user);
    Student update(Student user);
    Student getById(Long id) throws AppException;
    List<Student> getAll();

    Optional<List<Student>> getStudentsByTeam(Team team);

    Optional<Student> getStudentByUser(User u);

    void delete(Long id);
}
