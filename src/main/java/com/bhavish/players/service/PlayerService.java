package com.bhavish.players.service;

import com.bhavish.players.exception.NoSuchResourceException;
import com.bhavish.players.model.Player;
import com.bhavish.players.model.Team;
import com.bhavish.players.repo.PlayerRepo;
import com.bhavish.players.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlayerService implements IPlayerService {

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private TeamRepo teamRepo;

    @Override
    public Page<Player> getAllPlayers(int pageNo, int pageSie, String sortBy, String sortDir) {
        //checks if sort direction is equals to ASC, if not then sets order to descending
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSie, sort);
        return playerRepo.findAll(pageable);
    }

    @Override
    public List<Player> getPlayersByTeamId(long teamId) {
        return playerRepo.findByTeamId(teamId);
    }

    @Override
    public Player getPlayerById(long id) {
        return playerRepo.findById(id).orElseThrow(() -> new NoSuchResourceException("The player with id " + id + " is not found."));
    }

    @Override
    public Team getTeamByPlayerId(long id) {
        Player player = getPlayerById(id);
        return player.getTeam();
    }

    @Override
    public Player addPlayer(long teamId, Player player) {
        Player createdPlayer = teamRepo.findById(teamId).map(team -> {
            Player newPlayer = new Player(player.getName(), player.getAge(), player.getPlayerType());
            newPlayer.setTeam(team);
            return playerRepo.save(newPlayer);
        }).orElseThrow(() -> new NoSuchResourceException("The team with id " + teamId + " is not found."));
        return createdPlayer;
    }

    @Override
    public Player updatePlayer(long id, Player player) {
        Player updatedPlayer = playerRepo.findById(id).map(existingPlayer -> {
            existingPlayer.setTeam(player.getTeam());
            existingPlayer.setAge(player.getAge());
            existingPlayer.setName(player.getName());
            existingPlayer.setPlayerType(player.getPlayerType());
            existingPlayer.setUpdatedAt(LocalDateTime.now());
            return existingPlayer;
        }).orElseThrow(() -> new NoSuchResourceException("The player with id " + id + " is not found"));
        return updatedPlayer;
    }

    @Override
    public void deletePlayer(long id) {
        playerRepo.deleteById(id);
    }
}
