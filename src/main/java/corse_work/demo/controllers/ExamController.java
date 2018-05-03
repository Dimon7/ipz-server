package corse_work.demo.controllers;


import corse_work.demo.controllers.DTO.ExamDTO;
import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.controllers.Exceptions.ExceptionControllerAdvice;
import corse_work.demo.model.*;
import corse_work.demo.service.interfaces.*;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Log
@Controller
@RequestMapping(value = "exam")
public class ExamController {


    @Resource
    SecretaryService secretaryService;

    @Resource
    TeacherService teacherService;

    @Resource
    TeamService teamService;


    @Resource
    SubjectService subjectService;

    @Resource
    StudentService studentService;

    @Resource
    ExamService examService;



    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addExam(@Valid @RequestBody ExamDTO examDTO, BindingResult result) throws AppException, ParseException {

        log.info("Add Exam ...");
        ExceptionControllerAdvice.CheckValid(result);

        Team team = teamService.getById( examDTO.getTeamId() );

        Optional<Subject> subject = subjectService.getById(examDTO.getSubjectId());

        if(!subject.isPresent()){
            String error = "ERROR: Subject with id "+ examDTO.getSubjectId() +"not found";
            log.info( error );
            throw new AppException(error);
        }

        Optional<List<Student>> students = studentService.getStudentsByTeam( team );

        if(!students.isPresent()){
            String error = "ERROR: There is any Students in <"+ team.getNumber() +"> team";
            log.info( error );
            throw new AppException(error);
        }

        ModelMapper modelMapper = new ModelMapper();


        List<ExamDTO> examsDTO = new ArrayList<>();

        for( Student s: students.get() ){
            Exam exam = new Exam();
            exam.setTeacher( subject.get().getTeacher() );
            exam.setTeam( s.getTeam() );
            exam.setDate( examDTO.getDate() );
            exam.setSubject(subject.get());
            exam.setStudent( s );
            Exam newExam = examService.create(exam);

            examsDTO.add( modelMapper.map( newExam, ExamDTO.class) );

        }

        return ResponseEntity.ok().body( examsDTO );

    }

    @RequestMapping(value = "/teacher/{teacherId}/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getExamsByTeacherId(@PathVariable Long teacherId) throws AppException {

        log.info("Get all exams by TeacherId...");

        ModelMapper modelMapper = new ModelMapper();

        Optional<Teacher> teacher = teacherService.getById(teacherId);

        if(!teacher.isPresent()){
            String error = "ERROR: Teacher with id "+ teacherId +"not found";
            log.info( error );
            throw new AppException(error);
        }


        Optional<List<Exam>> exams = examService.getExamsByTeacher( teacher.get() );
        List<ExamDTO> examsDTO = new ArrayList<>();

        for(Exam e : exams.get()){
            examsDTO.add( modelMapper.map(e, ExamDTO.class) );
        }

        return ResponseEntity.ok().body( examsDTO );

    }

    @RequestMapping(value = "/student/{studentId}/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getExamsByStudentId(@PathVariable Long studentId) throws AppException {

        log.info("Get all exams by StudentId...");

        ModelMapper modelMapper = new ModelMapper();

        Student student = studentService.getById(studentId);


        Optional<List<Exam>> exams = examService.getExamsByStudent(student);
        List<ExamDTO> examsDTO = new ArrayList<>();

        for(Exam e : exams.get()){
            examsDTO.add( modelMapper.map(e, ExamDTO.class) );
        }

        return ResponseEntity.ok().body( examsDTO );

    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllExams(){

        ModelMapper modelMapper = new ModelMapper();

        List<Exam> exams = examService.getAll();

        List<ExamDTO> examsDTO = new ArrayList<>();

        for(Exam e : exams){
            examsDTO.add( modelMapper.map(e, ExamDTO.class) );
        }

        return ResponseEntity.ok().body( examsDTO );

    }

    @RequestMapping(value = "/teacher/{teacherId}/edit", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity editExam(@PathVariable Long teacherId, @RequestBody ExamDTO examDTO) throws AppException, ParseException {

        log.info("edit exam by TeacherId...");

        System.out.println(examDTO);

        ModelMapper modelMapper = new ModelMapper();

        Optional<Teacher> teacher = teacherService.getById(teacherId);

        if(!teacher.isPresent()){
            String error = "ERROR: Teacher with id "+ teacherId +"not found";
            log.info( error );
            throw new AppException(error);
        }

        Optional<Exam> exam = examService.getById(examDTO.getId());

        if(!exam.isPresent()){
            String error = "ERROR: Exam with id "+ examDTO.getId()  +"not found";
            log.info( error );
            throw new AppException(error);
        }


        exam.get().setGrade( examDTO.getGrade() );

        Exam newExam =  examService.update(exam.get());

        return ResponseEntity.ok().body( modelMapper.map( newExam, ExamDTO.class) );

    }
}
