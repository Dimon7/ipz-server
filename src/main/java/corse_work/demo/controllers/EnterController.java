package corse_work.demo.controllers;


import corse_work.demo.controllers.DTO.EnterDTO;
import corse_work.demo.controllers.DTO.TokenDTO;
import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.controllers.Exceptions.ExceptionControllerAdvice;
import corse_work.demo.model.*;
import corse_work.demo.model.enums.Role;
import corse_work.demo.service.PasswordService;
import corse_work.demo.service.interfaces.SecretaryService;
import corse_work.demo.service.interfaces.StudentService;
import corse_work.demo.service.interfaces.TeacherService;
import corse_work.demo.service.interfaces.UserService;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
public class EnterController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SecretaryService secretaryService;

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/regg", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity EnterUser(@RequestBody EnterDTO enterDTO) throws AppException {

        log.info("Enter ...");

        Optional<User> user = userService.getUserByEmail(enterDTO.getEmail());
        if(!user.isPresent()){
            log.info("There is no user with email " + enterDTO.getEmail());
            throw new AppException("There is no user with email " + enterDTO.getEmail());
        }

        System.out.println(user.toString());
        String token = userService.signIn( user.get().getName(), enterDTO.getPassword() );

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        Long id = null;
        String role = "";

        if (enterDTO.getRole().equals("student")){
            System.out.println("St");
            Optional<Student> student = studentService.getStudentByUser(user.get());
            if(!student.isPresent()){
                log.info("There is no student");
                throw new AppException("There is no student");
            }

            id =  student.get().getId() ;
            role = "student";
        }

        if (enterDTO.getRole().equals("secretary")){
            System.out.println("Sc");
            Optional<Secretary> secretary = secretaryService.getSecretaryByUser( user.get() );
            if(!secretary.isPresent()){
                log.info("There is no secretary");
                throw new AppException("There is no secretary");
            }
            id =  secretary.get().getId() ;
            role = "secretary";
        }

        if (enterDTO.getRole().equals("teacher")){
            System.out.println("Tec");
            Optional<Teacher> teacher = teacherService.getTeacherByUser( user.get( ));
            if(!teacher.isPresent()){
                log.info("There is no teacher");
                throw new AppException("There is no teacher");
            }

            id =  teacher.get().getId() ;
            role = "teacher";
        }

        tokenDTO.setRole(role);
        tokenDTO.setId( id );
        return ResponseEntity.ok().body( tokenDTO );


    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity userEnter(@Valid @RequestBody EnterDTO enterDTO, BindingResult result) throws AppException {

        log.info("Sign in ...");
        ExceptionControllerAdvice.CheckValid(result);

        Optional<User> user = userService.getUserByEmail(enterDTO.getEmail());
        String token = userService.signIn( user.get().getName(), enterDTO.getPassword() );

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        return ResponseEntity.ok().body( tokenDTO );
        /*Long id = null;

        if (enterDTO.getRole() == Role.ROLE_STUDENT){
            System.out.println("St");
            Optional<Student> ROLE_STUDENT = studentService.getStudentByUser(user.get());
            if(!ROLE_STUDENT.isPresent()){
                log.info("There is no ROLE_STUDENT");
                throw new AppException("There is no ROLE_STUDENT");
            }

            id =  ROLE_STUDENT.get().getId() ;
        }

        if (enterDTO.getRole() == Role.ROLE_SECRETARY){
            System.out.println("Sc");
            Optional<Secretary> ROLE_SECRETARY = secretaryService.getSecretaryByUser( user.get() );
            if(!ROLE_SECRETARY.isPresent()){
                log.info("There is no ROLE_SECRETARY");
                throw new AppException("There is no ROLE_SECRETARY");
            }
            id =  ROLE_SECRETARY.get().getId() ;
        }

        if (enterDTO.getRole() == Role.ROLE_TEACHER){
            System.out.println("Tec");
            Optional<Teacher> ROLE_TEACHER = teacherService.getTeacherByUser( user.get( ));
            if(!ROLE_TEACHER.isPresent()){
                log.info("There is no ROLE_TEACHER");
                throw new AppException("There is no ROLE_TEACHER");
            }

            id =  ROLE_TEACHER.get().getId() ;
        }

        return ResponseEntity.ok().body( id );*/

    }
}
