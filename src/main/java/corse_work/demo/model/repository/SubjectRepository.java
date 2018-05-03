package corse_work.demo.model.repository;


import corse_work.demo.model.Student;
import corse_work.demo.model.Subject;
import corse_work.demo.model.Teacher;
import corse_work.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{


    Optional<List<Subject>> getSubjectsByTeacher(Teacher t);



}
