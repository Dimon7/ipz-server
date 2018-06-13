package corse_work.demo.controllers;

import corse_work.demo.controllers.DTO.*;
import corse_work.demo.security.JwtTokenProvider;
import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.controllers.Exceptions.ExceptionControllerAdvice;
import corse_work.demo.model.Secretary;
import corse_work.demo.model.Teacher;
import corse_work.demo.service.interfaces.SecretaryService;
import corse_work.demo.service.interfaces.TeacherService;
import lombok.extern.java.Log;
import corse_work.demo.model.Student;
import corse_work.demo.model.User;
import corse_work.demo.model.enums.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import corse_work.demo.service.PasswordService;
import corse_work.demo.service.interfaces.StudentService;
import corse_work.demo.service.interfaces.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;

@Log
@Controller
@RequestMapping(value = "reg")
public class RegistrationController {

    @Resource
    private UserService userService;

    @Resource
    private PasswordService passwordService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private SecretaryService secretaryService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     *
     * @param regUserDTO
     * @param result
     * @return
     * @throws AppException
     * TODO: userRegistration
     */
    @RequestMapping(value = "/{user}/registration", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity userRegistration(@Valid @RequestBody RegUserDTO regUserDTO, BindingResult result, @PathVariable String user)throws AppException {

        log.info("Registration of user...");
        ExceptionControllerAdvice.CheckValid(result);

        if(userService.exists( regUserDTO.getEmail() ) ){
            log.info("Email already exist");
            throw  new AppException("Email already exist", HttpStatus.BAD_REQUEST);
        }

        if(userService.getUserByName( regUserDTO.getName()).isPresent()){
            log.info("User with name " + regUserDTO.getName() + " already exist");
            throw  new AppException("User with name " + regUserDTO.getName() + " already exist", HttpStatus.BAD_REQUEST);
        }

        ModelMapper modelMapper = new ModelMapper();
        User newUser = modelMapper.map(regUserDTO, User.class);
        newUser.setPassword( passwordEncoder.encode(newUser.getPassword()) );

        if(user.equals("student")){
            Student student = this.student(newUser, regUserDTO);
            StudentDTO DTO = modelMapper.map( student, StudentDTO.class);
            String token = jwtTokenProvider.createToken( DTO.getUserName(), Role.ROLE_STUDENT);
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(token);

            return ResponseEntity.ok().body(
                    tokenDTO
            );
        }

        if(user.equals("teacher")){
            Teacher teacher = this.teacher(newUser);
            TeacherDTO DTO = modelMapper.map( teacher, TeacherDTO.class);

            String token =  jwtTokenProvider.createToken( DTO.getUserName(), Role.ROLE_TEACHER);

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(token);

            return ResponseEntity.ok().body(
                    tokenDTO
            );
        }

        if(user.equals("secretary")){
            Secretary secretary = this.secretary(newUser);

            SecretaryDTO DTO = modelMapper.map( secretary, SecretaryDTO.class);

            String token =  jwtTokenProvider.createToken( DTO.getUserName(), Role.ROLE_SECRETARY);

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(token);

            return ResponseEntity.ok().body(
                    tokenDTO
            );
        }

        log.info("ERROR");
        throw new AppException("Error bad request",HttpStatus.BAD_REQUEST);
    }

    private Secretary secretary(User newUser){
        newUser.setRole(Role.ROLE_SECRETARY);

        newUser=userService.create(newUser);
        Secretary newSecretary = new Secretary();
        newSecretary.setUser( newUser );

        return secretaryService.create( newSecretary );
    }

    private Student student(User newUser, RegUserDTO regUserDTO){
        newUser.setRole(Role.ROLE_STUDENT);
        newUser=userService.create(newUser);

        Student newStudent = new Student();
        newStudent.setTeam( null );

        newStudent.setUser( newUser );

        return studentService.create( newStudent );

    }


    private Teacher teacher(User newUser){

       newUser.setRole(Role.ROLE_TEACHER);

       newUser=userService.create(newUser);

       Teacher newTeacher = new Teacher();
       newTeacher.setUser( newUser );

       return teacherService.create( newTeacher );


    }


}
