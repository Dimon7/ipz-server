package corse_work.demo.service.interfaces;

import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.model.Exam;
import corse_work.demo.model.Student;
import corse_work.demo.model.Teacher;
import corse_work.demo.model.Team;

import java.util.List;
import java.util.Optional;

public interface ExamService {


    Exam create(Exam ex);
    Exam update(Exam ex);
    Optional<Exam> getById(Long id);
    Optional<List<Exam>> getExamsByTeacher(Teacher t);
    Optional<List<Exam>> getExamsByTeam(Team t) ;
    List<Exam> getAll();

    Optional<List<Exam>> getExamsByStudent(Student s);

    void delete(Long id);
}
