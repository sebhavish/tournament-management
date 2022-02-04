package com.bhavish.players.service;

import com.bhavish.players.exception.NoSuchResourceException;
import com.bhavish.players.model.Team;
import com.bhavish.players.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TeamService implements ITeamService{

    @Autowired
    private TeamRepo teamRepo;

    @Override
    public Page<Team> getAllTeams(int pageNo, int pageSize, String sortBy, String sortDir) {
        //checks if sort direction is equals to ASC, if not then sets order to descending
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return teamRepo.findAll(pageable);
    }

    //returns the team using it's id, if not found throws NoSuchResourceException
    @Override
    public Team getTeamById(long id) {
        return teamRepo.findById(id).orElseThrow(() -> new NoSuchResourceException("The team with id " + id + " is not found."));
    }

    @Override
    public Team createTeam(Team team) {
        return teamRepo.save(new Team(team.getName(), team.getLocation()));
    }

    @Override
    public Team updateTeam(long id, Team team) {
        Team updatedTeam = teamRepo.findById(id).map(existingTeam -> {
            existingTeam.setName(team.getName());
            existingTeam.setLocation(team.getLocation());
            existingTeam.setUpdatedAt(LocalDateTime.now());
            return existingTeam;
        }).orElseThrow(() -> new NoSuchResourceException("The team with id " + id + " is not found."));
        return updatedTeam;
    }

    @Override
    public void deleteTeam(long id) {
        teamRepo.deleteById(id);
    }
}
