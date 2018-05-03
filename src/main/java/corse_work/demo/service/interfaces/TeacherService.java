package corse_work.demo.service.interfaces;

import corse_work.demo.model.Teacher;
import corse_work.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    Teacher create(Teacher user);
    List<Teacher> getAll();
    Teacher update(Teacher user);
    Optional<Teacher> getById(Long id);

    Optional<Teacher> getTeacherByUser(User u);

    void delete(Long id);
}
