package corse_work.demo.service.impl;

import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.model.Exam;
import corse_work.demo.model.Student;
import corse_work.demo.model.Teacher;
import corse_work.demo.model.Team;
import corse_work.demo.model.repository.ExamRepository;
import corse_work.demo.service.interfaces.ExamService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log
@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    ExamRepository examRepository;

    @Override
    public Exam create(Exam ex) {
        return examRepository.saveAndFlush(ex);
    }

    @Override
    public Exam update(Exam ex) {
        return examRepository.saveAndFlush(ex);
    }

    @Override
    public Optional<Exam> getById(Long id) {
        return examRepository.findById(id);
    }

    @Override
    public Optional<List<Exam>> getExamsByTeacher(Teacher t) {
        return examRepository.getExamsByTeacher(t);
    }

    @Override
    public Optional<List<Exam>> getExamsByTeam(Team t) {

        Optional<List<Exam>> exams = examRepository.getExamsByTeam(t);

        if(!exams.isPresent()){
            log.info("There is no exams");
        }

        return  exams;

    }

    @Override
    public List<Exam> getAll() {
        return examRepository.findAll();
    }

    @Override
    public Optional<List<Exam>> getExamsByStudent(Student s) {
        return examRepository.getExamsByStudent(s);
    }

    @Override
    public void delete(Long id) {
        examRepository.deleteById(id);

    }
}
