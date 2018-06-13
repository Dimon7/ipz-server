package corse_work.demo.controllers;


import corse_work.demo.controllers.DTO.SubjectDTO;
import corse_work.demo.controllers.DTO.TeacherDTO;
import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.controllers.Exceptions.ExceptionControllerAdvice;
import corse_work.demo.model.Subject;
import corse_work.demo.model.Teacher;
import corse_work.demo.service.interfaces.SubjectService;
import corse_work.demo.service.interfaces.TeacherService;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
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
@RequestMapping(value = "teacher")
public class TeacherController {


    @Resource
    private SubjectService subjectService;

    @Resource
    private TeacherService teacherService;


    /*
     * Request GET /ROLE_STUDENT/{id}
     * Response -
     * TODO: getTeacher
     *
     */

    @PreAuthorize("hasRole('ROLE_TEACHER') and @accessPermission.canAccessCustomer(authentication , #teacherId)")
    @RequestMapping(value = "/{teacherId}", method = RequestMethod.GET )
    @ResponseBody
    public TeacherDTO getTeacher(@PathVariable Long teacherId) {

        log.info("get Teacher by ID");

        Optional<Teacher> teacher = teacherService.getById(teacherId);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(teacher.get(), TeacherDTO.class);

    }


    @RequestMapping(value = "/getAll", method = RequestMethod.GET )
    @ResponseBody
    public ResponseEntity getAllTeacher() {

        log.info("getAll Teachers");

        List<Teacher> teachers = teacherService.getAll();

        ModelMapper modelMapper = new ModelMapper();
        List<TeacherDTO> teachersDTO = new ArrayList<>();

        for(Teacher t: teachers){
            teachersDTO.add( modelMapper.map(t, TeacherDTO.class) );

        }

        return  ResponseEntity.ok().body(teachersDTO);

    }




    @RequestMapping(value = "{teacherId}/subject/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getSubject(@PathVariable Long teacherId, @PathVariable Long id) throws AppException{

        log.info("GetSubject ...");

        Optional<Teacher> teacher = teacherService.getById(teacherId);

        if(!teacher.isPresent()){
            log.info("ERROR: Teacher with id "+ teacherId +"not found");
            throw new AppException("ERROR: Teacher with id "+ teacherId +"not found");
        }

        Optional<Subject> subject = subjectService.getById(id);

        if( !subject.isPresent() ){
            log.info("ERROR: Subject with id "+ id +"not found");
            throw new AppException("ERROR: Subject with id "+ id +"not found");
        }

        ModelMapper modelMapper = new ModelMapper();

        return ResponseEntity.ok().body( modelMapper.map(subject.get(), SubjectDTO.class) );
    }
}
