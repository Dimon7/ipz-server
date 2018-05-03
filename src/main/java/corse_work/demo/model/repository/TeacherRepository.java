package corse_work.demo.model.repository;


import corse_work.demo.model.Teacher;
import corse_work.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{

    Optional<Teacher> getTeacherByUser(User u);


}
