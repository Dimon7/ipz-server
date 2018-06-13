package corse_work.demo.service.impl;

import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.model.Team;
import corse_work.demo.model.repository.TeamRepository;
import corse_work.demo.service.interfaces.TeamService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Log


@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team create(Team t) {
        return teamRepository.saveAndFlush(t);
    }

    @Override
    public Team update(Team t) {
        return teamRepository.saveAndFlush(t);
    }

    @Override
    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team getById(Long id) throws  AppException{
        Optional<Team> team = teamRepository.findById(id);

        if(!team.isPresent()){
            String error = "There is no team";
            log.info(error);
            throw new AppException(error);
        }

        return team.get();

    }

    @Override
    public Team getTeamByNumer(String number) {
        return teamRepository.getTeamByNumber(number);
    }

    @Override
    public void delete(Long id) {
        teamRepository.deleteById(id);
    }
}
