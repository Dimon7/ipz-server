package corse_work.demo.controllers;


import corse_work.demo.controllers.DTO.ExamDTO;
import corse_work.demo.controllers.DTO.SummarDTO;
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
    public ResponseEntity addExam( @RequestBody ExamDTO examDTO, BindingResult result) throws AppException, ParseException {

        System.out.println(examDTO.toString());
        log.info("Add Exam ...");
//        ExceptionControllerAdvice.CheckValid(result);

        Team team = teamService.getById( Long.parseLong(examDTO.getTeamId()) );

        Optional<Subject> subject = subjectService.getById( Long.parseLong( (examDTO.getSubjectId() ) ));


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
        if(!exams.isPresent()){
            String error = "ERROR: Tou haven't got any Exams";
            log.info( error );
            throw new AppException(error);
        }

        List<ExamDTO> examsDTO = new ArrayList<>();
        Set<String> teams = new LinkedHashSet<>();
        Set<String> subjects = new LinkedHashSet<>();

        for(Exam e : exams.get()){
            examsDTO.add( modelMapper.map(e, ExamDTO.class) );
            teams.add( e.getTeam().getNumber() );
            subjects.add( e.getSubject().getName() );
        }

        examsDTO.get(0).setTeams( teams );
        examsDTO.get(0).setSubjects( subjects );

        log.warning(teams.toString());
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

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity summary(){

        log.info("Summary");
        SummarDTO summarDTO = new SummarDTO();

        team( summarDTO );
        subject( summarDTO );
        student( summarDTO);

        return ResponseEntity.ok().body(summarDTO);
    }

    private void student(SummarDTO summarDTO){
        List<Student> students = studentService.getAll();

        int count = 0;
        Double avr = 0.0;
        Long sum = 0L;

        for(Student s: students){
            Optional<List<Exam>> exam = examService.getExamsByStudent(s);
            if(exam.isPresent()){
                for (Exam e : exam.get()) {
                    Long grade = e.getGrade();

                    if (grade != null) {
                        sum += grade;
                    }
                    count++;


                }

                if (sum != 0) {
                    avr = (double) (sum / count);
                }

                count = 0;
                sum = 0L;
                String name = s.getUser().getName();
                if(avr < 50){

                    summarDTO.setBorg( name );
                }
                if(avr > 50 && avr < 90){
                    summarDTO.setBad( name );
                }
                if(avr > 90){
                    summarDTO.setGood( name );
                }

                avr = 0.0;
            }

        }
    }

    private void subject(SummarDTO summarDTO){
        List<Subject> subjects =  subjectService.getAll();

        int count = 0;
        Double avr = 0.0;
        Long sum = 0L;

        for(Subject s : subjects ){

            Optional<List<Exam>> exam = examService.getExamsBySubject(s);

            if(exam.isPresent()) {
                for (Exam e : exam.get()) {
                    Long grade = e.getGrade();

                    if (grade != null) {
                        sum += grade;
                        count++;
                    }

                }

                if (sum != 0) {
                    avr = (double) (sum / count);
                }

                count = 0;
                sum = 0L;


                summarDTO.setS( s.getName(), avr );
                avr = 0.0;

            }
        }
    }

    private void team(SummarDTO summarDTO){
        List<Team> teams =  teamService.getAll();

        int count = 0;
        Double avr = 0.0;
        Long sum = 0L;



        for(Team t : teams ){

            Optional<List<Exam>> exam = examService.getExamsByTeam(t);

            if(exam.isPresent()) {
                for (Exam e : exam.get()) {
                    Long grade = e.getGrade();

                    if (grade != null) {
                        sum += grade;
                        count++;
                    }
                }

                if (sum != 0) {
                    avr = (double) (sum / count);
                }

                count = 0;
                sum = 0L;
                summarDTO.setT( t.getNumber(), avr );
                avr = 0.0;
            }
        }
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

        Optional<Exam> exam = examService.getById( Long.parseLong(examDTO.getId()));

        if(!exam.isPresent()){
            String error = "ERROR: Exam with id "+ examDTO.getId()  +"not found";
            log.info( error );
            throw new AppException(error);
        }


        exam.get().setGrade( Long.parseLong(examDTO.getGrade()) );

        Exam newExam =  examService.update(exam.get());

        return ResponseEntity.ok().body( modelMapper.map( newExam, ExamDTO.class) );

    }
}
