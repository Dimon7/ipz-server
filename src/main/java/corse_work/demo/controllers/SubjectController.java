package corse_work.demo.controllers;


import corse_work.demo.controllers.DTO.SubjectDTO;
import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.controllers.Exceptions.ExceptionControllerAdvice;
import corse_work.demo.model.Secretary;
import corse_work.demo.model.Subject;
import corse_work.demo.model.Teacher;
import corse_work.demo.service.interfaces.*;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
@Controller
@RequestMapping(value = "subject")
public class SubjectController {

    @Resource
    private SecretaryService secretaryService;

    @Resource
    private SubjectService subjectService;

    @Resource
    TeacherService teacherService;

    @Resource
    TeamService teamService;


    @Resource
    StudentService studentService;

    @Resource
    ExamService examService;

    @RequestMapping(value = "/secretary/{secretaryId}/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addSubject(@PathVariable Long secretaryId,
                                     @Valid @RequestBody SubjectDTO subjectDTO, BindingResult result)throws AppException {

        log.info("AddSubject ...");
        ExceptionControllerAdvice.CheckValid(result);

        Optional<Secretary> secretary = secretaryService.getById(secretaryId);

        if(!secretary.isPresent()){
            log.info("ERROR: Secretary with id "+ secretaryId +"not found");
            throw new AppException("ERROR: Secretary with id "+ secretaryId +"not found");
        }

        Optional<Teacher> teacher = teacherService.getById( subjectDTO.getTeacherId() );

        if(!teacher.isPresent()){
            String error = "ERROR: Teacher with id "+ subjectDTO.getTeacherId() +"not found";
            log.info( error );
            throw new AppException(error);
        }


        ModelMapper modelMapper = new ModelMapper();

        Subject subject = new Subject();


        subject.setName( subjectDTO.getName() );
        subject.setSecretary(secretary.get());
        subject.setTeacher(teacher.get());
        subject.setType( subjectDTO.getType() );

        Subject newSubject =  subjectService.create(subject);

        return ResponseEntity.ok().body( modelMapper.map(newSubject, SubjectDTO.class) );

    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity addSubject() throws AppException {

        log.info("Get all subject ...");

        ModelMapper modelMapper = new ModelMapper();

        List<Subject> subjects = subjectService.getAll();
        List<SubjectDTO> subjectsDTO = new ArrayList<>();

        for( Subject s: subjects){
            subjectsDTO.add( modelMapper.map( s, SubjectDTO.class ));
        }

        return ResponseEntity.ok().body( subjectsDTO );

    }

    @RequestMapping(value = "/teacher/{teacherId}/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getSubjectsByTeacherId(@PathVariable Long teacherId) throws AppException {

        log.info("Get all subject by TeacherId...");

        ModelMapper modelMapper = new ModelMapper();

        Optional<Teacher> teacher = teacherService.getById(teacherId);

        if(!teacher.isPresent()){
            String error = "ERROR: Teacher with id "+ teacherId +"not found";
            log.info( error );
            throw new AppException(error);
        }


        Optional<List<Subject>> subjects = subjectService.getSubjectsByTeacher(teacher.get());
        List<SubjectDTO> subjectsDTO = new ArrayList<>();

        for(Subject s : subjects.get()){
            subjectsDTO.add( modelMapper.map(s, SubjectDTO.class) );
        }

        return ResponseEntity.ok().body( subjectsDTO );

    }
}
