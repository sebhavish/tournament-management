package com.bhavish.players.controller;

import com.bhavish.players.model.Player;
import com.bhavish.players.model.Team;
import com.bhavish.players.service.PlayerService;
import com.bhavish.players.utils.PagingConstants;
import com.bhavish.players.utils.ResponseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Player rest controller
@Api(value = "APIs for creating, retrieving, deleting and updating players")
@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    //Return the player using his id
    @ApiOperation(value = "Get the player using his id", notes = "using the player id you can retrieve all the player information", httpMethod = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getPlayerById(@PathVariable("id") long id) {
        Player player = playerService.getPlayerById(id);

        //wrapping player into response
        ResponseWrapper response = new ResponseWrapper("success", "successfully retrieved", player);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    //takes paging related inputs and returns the players based on paging related inputs, if no players found returns no content
    @ApiOperation(value = "Get all players", notes = "gives out all the players as response, based on the given page inputs", httpMethod = "GET")
    @GetMapping("/allPlayers")
    public ResponseEntity<ResponseWrapper> getAllPlayers(
            @RequestParam(value = "pageNo", defaultValue = PagingConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PagingConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PagingConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PagingConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        Page<Player> players = playerService.getAllPlayers(pageNo, pageSize, sortBy, sortDir);
        List<Player> playersList = players.getContent();
        if (playersList.isEmpty()) {
            ResponseWrapper response = new ResponseWrapper("no players", "players not found to retrieve", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        //Creating a response Object and wrapping into ResponseWrapper
        Map<String, Object> pageResponse = new HashMap<>();
        pageResponse.put("totalPlayers", players.getTotalElements());
        pageResponse.put("players", playersList);
        pageResponse.put("currentPage", (players.getNumber() + 1));
        pageResponse.put("totalPages", players.getTotalPages());
        ResponseWrapper response = new ResponseWrapper("success", "successfully retrieved", pageResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //using player id retrieves the Team and wraps into response
    @ApiOperation(value = "get team of a player", notes = "using the player id get his team details", httpMethod = "GET")
    @GetMapping("/getTeam/{id}")
    public ResponseEntity<ResponseWrapper> getTeamByPlayerId(@PathVariable("id") long id) {
        Team team = playerService.getTeamByPlayerId(id);
        ResponseWrapper response = new ResponseWrapper("success", "successfully retrieved", team);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //using team id retrieves all players of that particular team
    @ApiOperation(value = "get players of a team", notes = "using the team id retrieve all players of team", httpMethod = "GET")
    @GetMapping("/{teamId}/allPlayers")
    public ResponseEntity<ResponseWrapper> getAllPlayersByTeamId(@PathVariable("teamId") long teamId) {
        List<Player> players = playerService.getPlayersByTeamId(teamId);

        if (players.isEmpty()) {
            ResponseWrapper response = new ResponseWrapper("no players", "players not found to retrieve", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ResponseWrapper response = new ResponseWrapper("success", "successfully retrieved", players);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Creates player and adds player to the team with mentioned id
    @ApiOperation(value = "add a player to team", notes = "creates a player and add it to a team based on team id", httpMethod = "POST")
    @PostMapping("/{teamId}/addPlayer")
    public ResponseEntity<ResponseWrapper> addPlayer(@PathVariable("teamId") long teamId, @RequestBody Player player) {
        Player createdPlayer = playerService.addPlayer(teamId, player);
        ResponseWrapper response = new ResponseWrapper("success", "successfully created", createdPlayer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Update the players details using his id
    @ApiOperation(value = "update player's details", notes = "update the details of the player based on the player id", httpMethod = "PUT")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper> updatePlayer(@PathVariable("id") long id, @RequestBody Player player) {
        Player updatedPlayer = playerService.updatePlayer(id, player);
        ResponseWrapper response = new ResponseWrapper("success", "successfully updated", updatedPlayer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Deletes the player based on his id
    @ApiOperation(value = "delete a player", notes = "delete a player based on his id", httpMethod = "DELETE")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper> deletePlayer(@PathVariable("id") long id) {
        playerService.deletePlayer(id);
        ResponseWrapper result = new ResponseWrapper("success", "successfully deleted", null);
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }
}
