package corse_work.demo.model.repository;


import corse_work.demo.model.Secretary;
import corse_work.demo.model.Student;
import corse_work.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecretaryRepository extends JpaRepository<Secretary, Long>{

    Optional<Secretary> getSecretaryByUser(User u);

}
