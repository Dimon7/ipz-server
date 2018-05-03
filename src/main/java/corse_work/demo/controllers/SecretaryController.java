package corse_work.demo.controllers;


import corse_work.demo.controllers.DTO.SecretaryDTO;
import corse_work.demo.controllers.DTO.SubjectDTO;
import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.controllers.Exceptions.ExceptionControllerAdvice;
import corse_work.demo.model.Secretary;
import corse_work.demo.model.Subject;
import corse_work.demo.model.Teacher;
import corse_work.demo.service.interfaces.SecretaryService;
import corse_work.demo.service.interfaces.SubjectService;
import corse_work.demo.service.interfaces.TeacherService;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

@Log
@Controller
@RequestMapping(value = "secretary")
public class SecretaryController {


    @Resource
    private SecretaryService secretaryService;

    @Resource
    private SubjectService subjectService;

    @Resource
    private TeacherService teacherService;

    @RequestMapping(value = "/{secretaryId}", method = RequestMethod.GET )
    @ResponseBody
    public SecretaryDTO getStudent(@PathVariable Long secretaryId) throws AppException{

        log.info("get Secretary");

        Optional<Secretary> secretary = secretaryService.getById(secretaryId);

        if(!secretary.isPresent()){
            String error = "Secretary with id=" + secretaryId + " not found";
            log.warning(error);
            throw  new AppException(error);
        }

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(secretary.get(), SecretaryDTO.class);

    }


}
