package corse_work.demo.controllers;

import corse_work.demo.controllers.DTO.EditStudentDTO;
import corse_work.demo.controllers.DTO.StudentDTO;
import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.model.Exam;
import corse_work.demo.model.Student;
import corse_work.demo.model.Team;
import corse_work.demo.service.interfaces.ExamService;
import corse_work.demo.service.interfaces.TeamService;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import corse_work.demo.service.interfaces.StudentService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
@Controller
@RequestMapping(value = "student")

public class StudentController {


    @Resource
    private StudentService studentService;

    @Resource
    private TeamService teamService;

    @Resource
    private ExamService examService;


    /*
     * Request GET /ROLE_STUDENT/{id}
     * Response -
     * TODO: getStudent
     *
     */

    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET )
    @ResponseBody

    @PreAuthorize("hasRole('ROLE_STUDENT') and @accessPermission.canAccessCustomer(authentication , #studentId)")

    public StudentDTO getStudent(@PathVariable Long studentId) throws AppException {


        log.info("getStudent by id ...");

        Student student = studentService.getById(studentId);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(student, StudentDTO.class);

    }

    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET )
    @ResponseBody
    public ResponseEntity getAllStudent() {

        log.info("getAll Students");

        List<Student> students = studentService.getAll();

        ModelMapper modelMapper = new ModelMapper();
        List<StudentDTO> studentsDTO = new ArrayList<>();


        for(Student s: students){
            studentsDTO.add( modelMapper.map(s, StudentDTO.class) );
        }

        return  ResponseEntity.ok().body(studentsDTO);

    }

    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    @RequestMapping(value = "/edit", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity editStudent(@RequestBody EditStudentDTO editStudentDTO) throws AppException {

        log.info("edit Student");

        List<String> ids = editStudentDTO.getIds();
        List<StudentDTO> studentsDTO = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();


        Team team = teamService.getById( editStudentDTO.getIdteam() );

        Optional<List<Exam>> exams = examService.getExamsByTeam( team );

        for( String i: ids) {

            Student student = studentService.getById(Long.parseLong(i));

            //TODO: shit
            if(exams.isPresent()) {
                if (!exams.get().contains(student)) {
                    System.out.println("НЕМА ЙОГО");
                    Exam exam = new Exam();
                    exam.setTeam(team);
                    exam.setTeacher(exams.get().get(0).getTeacher());
                    exam.setSubject(exams.get().get(0).getSubject());
                    exam.setDate(exams.get().get(0).getDate());
                    exam.setStudent(student);

                    examService.create(exam);
                }
            }

            student.setTeam(team);

            Student newStudent = studentService.update( student );

            studentsDTO.add( modelMapper.map( newStudent, StudentDTO.class ) );
        }

        return ResponseEntity.ok().body(studentsDTO);
    }

}
