package corse_work.demo.service.interfaces;

import corse_work.demo.model.Subject;
import corse_work.demo.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface SubjectService {

    Subject create(Subject user);
    Subject update(Subject user);
    List<Subject> getAll();
    Optional<Subject> getById(Long id);

    Optional<List<Subject>> getSubjectsByTeacher(Teacher t);


    void delete(Long id);
}
