package corse_work.demo.service.interfaces;

import corse_work.demo.model.Student;
import corse_work.demo.model.User;

import java.util.Optional;

public interface UserService {

    User create(User user);
    User update(User user);

    void delete(Long id);
    Boolean exists(String email);

    Optional<User> signIn(String email, String pass);



}
