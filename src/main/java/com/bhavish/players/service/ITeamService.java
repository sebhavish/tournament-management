package com.bhavish.players.service;

import com.bhavish.players.model.Team;
import org.springframework.data.domain.Page;

public interface ITeamService {
    public Page<Team> getAllTeams(int pageNo, int pageSize, String sortBy, String sortDir);
    public Team getTeamById(long id);
    public Team createTeam(Team team);
    public Team updateTeam(long id, Team team);
    public void deleteTeam(long id);
}
