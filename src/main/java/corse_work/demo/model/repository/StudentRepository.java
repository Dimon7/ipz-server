package corse_work.demo.model.repository;

import corse_work.demo.model.Student;
import corse_work.demo.model.Team;
import corse_work.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<List<Student>> getStudentsByTeam(Team team);

    Optional<Student> getStudentByUser(User u);



}
