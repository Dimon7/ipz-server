package corse_work.demo.model.repository;


import corse_work.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {


    Optional<List<Exam>> getExamsByTeacher(Teacher t);
    Optional<List<Exam>> getExamsBySubject(Subject sb);
    Optional<List<Exam>> getExamsByTeamAndSubject(Subject sb, Team t);

    Optional<List<Exam>> getExamsByStudent(Student s);
    Optional<List<Exam>> getExamsByTeam(Team t);

}
