package com.bhavish.players.service;

import com.bhavish.players.model.Player;
import com.bhavish.players.model.Team;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPlayerService {
    public Page<Player> getAllPlayers(int pageNo, int pageSize, String sortBy, String sortDir);
    public List<Player> getPlayersByTeamId(long teamId);
    public Player getPlayerById(long id);
    public Team getTeamByPlayerId(long id);
    public Player addPlayer(long teamId, Player player);
    public Player updatePlayer(long id, Player player);
    public void deletePlayer(long id);
}
