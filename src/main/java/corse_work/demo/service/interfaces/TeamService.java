package corse_work.demo.service.interfaces;

import corse_work.demo.controllers.Exceptions.AppException;
import corse_work.demo.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {

    Team create(Team t);
    Team update(Team t);
    List<Team> getAll();
    Team getById(Long id) throws AppException;
    Team getTeamByNumer(String number);


    void delete(Long id);
}
