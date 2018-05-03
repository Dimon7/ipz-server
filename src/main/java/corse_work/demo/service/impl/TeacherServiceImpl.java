package corse_work.demo.service.impl;

import corse_work.demo.model.Teacher;
import corse_work.demo.model.User;
import corse_work.demo.model.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import corse_work.demo.service.interfaces.TeacherService;

import java.util.List;
import java.util.Optional;


@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher create(Teacher teacher) {
        return teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher update(Teacher teacher) {
        return teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public Optional<Teacher> getById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Optional<Teacher> getTeacherByUser(User u) {
        return teacherRepository.getTeacherByUser(u);
    }

    @Override
    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }
}
