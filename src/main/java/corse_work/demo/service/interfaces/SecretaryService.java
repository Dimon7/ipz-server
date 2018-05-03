package corse_work.demo.service.interfaces;

import corse_work.demo.model.Secretary;
import corse_work.demo.model.Student;
import corse_work.demo.model.User;

import java.util.Optional;

public interface SecretaryService {

    Secretary create(Secretary user);
    Secretary update(Secretary user);
    Optional<Secretary> getById(Long id);
    Optional<Secretary> getSecretaryByUser(User u);

    void delete(Long id);
}
