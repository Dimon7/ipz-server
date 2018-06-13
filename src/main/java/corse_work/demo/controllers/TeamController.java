package corse_work.demo.controllers;


import corse_work.demo.controllers.DTO.TeamDTO;
import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.controllers.Exceptions.ExceptionControllerAdvice;
import corse_work.demo.model.Secretary;
import corse_work.demo.model.Team;
import corse_work.demo.service.interfaces.ExamService;
import corse_work.demo.service.interfaces.SecretaryService;
import corse_work.demo.service.interfaces.TeamService;
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
@RequestMapping(value = "team")
public class TeamController {

    @Resource
    private SecretaryService secretaryService;

    @Resource
    private ExamService examService;

    @Resource
    private TeamService teamService;

    @RequestMapping(value = "secretary/{secretaryId}/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addSubject(@PathVariable Long secretaryId,
                                     @Valid @RequestBody TeamDTO teamDTO, BindingResult result)throws AppException {

        log.info("Add Team ...");
        ExceptionControllerAdvice.CheckValid(result);

        Optional<Secretary> secretary = secretaryService.getById(secretaryId);

        if(!secretary.isPresent()){
            log.info("ERROR: Secretary with id "+ secretaryId +"not found");
            throw new AppException("ERROR: Secretary with id "+ secretaryId +"not found");
        }


        ModelMapper modelMapper = new ModelMapper();

        Team team = modelMapper.map(teamDTO, Team.class);

        if( teamService.getTeamByNumer(team.getNumber()) != null ){
            log.info(team.getNumber() + " team already exist");
            throw new AppException(team.getNumber() + " team already exist");
        }

        Team newTeam =  teamService.create(team);

        return ResponseEntity.ok().body( modelMapper.map(newTeam, TeamDTO.class) );

    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity addSubject() throws AppException {

        log.info("Get all team ...");

        ModelMapper modelMapper = new ModelMapper();

        List<Team> teams = teamService.getAll();
        List<TeamDTO> teamsDTO = new ArrayList<>();

        for( Team t: teams){
            teamsDTO.add( modelMapper.map( t, TeamDTO.class ));
        }

        return ResponseEntity.ok().body( teamsDTO );

    }
}
