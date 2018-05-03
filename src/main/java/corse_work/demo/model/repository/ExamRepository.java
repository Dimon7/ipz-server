package corse_work.demo.model.repository;


import corse_work.demo.model.Exam;
import corse_work.demo.model.Student;
import corse_work.demo.model.Teacher;
import corse_work.demo.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {


    Optional<List<Exam>> getExamsByTeacher(Teacher t);
    Optional<List<Exam>> getExamsByStudent(Student s);
    Optional<List<Exam>> getExamsByTeam(Team t);

}
