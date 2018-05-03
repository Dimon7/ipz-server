package corse_work.demo.service.impl;

import corse_work.demo.model.Subject;
import corse_work.demo.model.Teacher;
import corse_work.demo.model.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import corse_work.demo.service.interfaces.SubjectService;

import java.util.List;
import java.util.Optional;


@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Subject create(Subject subject) {
        return subjectRepository.saveAndFlush(subject);
    }

    @Override
    public Subject update(Subject subject) {
        return subjectRepository.saveAndFlush(subject);
    }

    @Override
    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Optional<Subject> getById(Long id) {
        return subjectRepository.findById(id);
    }

    @Override
    public Optional<List<Subject>> getSubjectsByTeacher(Teacher t) {
        return subjectRepository.getSubjectsByTeacher(t);
    }

    @Override
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
}
