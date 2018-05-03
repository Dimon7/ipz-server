package corse_work.demo.controllers;


import corse_work.demo.controllers.DTO.EnterDTO;
import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.controllers.Exceptions.ExceptionControllerAdvice;
import corse_work.demo.model.*;
import corse_work.demo.model.enums.Role;
import corse_work.demo.service.PasswordService;
import corse_work.demo.service.interfaces.SecretaryService;
import corse_work.demo.service.interfaces.StudentService;
import corse_work.demo.service.interfaces.TeacherService;
import corse_work.demo.service.interfaces.UserService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

@Log
@Controller
@RequestMapping(value = "signin")
public class EnterController {

    @Resource
    private UserService userService;

    @Resource
    private PasswordService passwordService;

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private SecretaryService secretaryService;




    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity userRegistration(@Valid @RequestBody EnterDTO enterDTO,
                                           BindingResult result)throws AppException {

        log.info("Sign in ...");
        ExceptionControllerAdvice.CheckValid(result);

        Optional<User> user = userService.signIn( enterDTO.getEmail(), enterDTO.getPassword() );

        if( !user.isPresent()){
            log.info("There is no user");
            throw new AppException("There is no user");
        }

        Long id = null;

        if (enterDTO.getRole() == Role.student){
            System.out.println("St");
            Optional<Student> student = studentService.getStudentByUser(user.get());
            if(!student.isPresent()){
                log.info("There is no student");
                throw new AppException("There is no student");
            }

            id =  student.get().getId() ;
        }

        if (enterDTO.getRole() == Role.secretary){
            System.out.println("Sc");
            Optional<Secretary> secretary = secretaryService.getSecretaryByUser( user.get() );
            if(!secretary.isPresent()){
                log.info("There is no secretary");
                throw new AppException("There is no secretary");
            }
            id =  secretary.get().getId() ;
        }

        if (enterDTO.getRole() == Role.teacher){
            System.out.println("Tec");
            Optional<Teacher> teacher = teacherService.getTeacherByUser( user.get( ));
            if(!teacher.isPresent()){
                log.info("There is no teacher");
                throw new AppException("There is no teacher");
            }

            id =  teacher.get().getId() ;
        }

        return ResponseEntity.ok().body( id );

    }
}
