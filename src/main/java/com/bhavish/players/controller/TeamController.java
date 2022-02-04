package com.bhavish.players.controller;

import com.bhavish.players.model.Team;
import com.bhavish.players.service.TeamService;
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

//Rest controller for teams
@Api(value = "APIs for creating, retrieving, deleting and updating teams")
@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    //Retrieves all teams based on the pagination parameters, if no team found response is no content
    @ApiOperation(value = "Retrieve all teams", notes = "gives out all teams, based on page inputs", httpMethod = "GET")
    @GetMapping("/allTeams")
    public ResponseEntity<ResponseWrapper> getAllTeams(
            @RequestParam(value = "pageNo", defaultValue = PagingConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PagingConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PagingConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PagingConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        Page<Team> teams = teamService.getAllTeams(pageNo, pageSize, sortBy, sortDir);
        List<Team> teamsList = teams.getContent();
        if (teamsList.isEmpty()) {
            ResponseWrapper response = new ResponseWrapper("no teams", "teams not found to retrieve", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        Map<String, Object> pageResponse = new HashMap<>();
        pageResponse.put("totalTeams", teams.getTotalElements());
        pageResponse.put("teams", teamsList);
        pageResponse.put("currentPage", (teams.getNumber() + 1));
        pageResponse.put("totalPages", teams.getTotalPages());
        ResponseWrapper response = new ResponseWrapper("success", "successfully retrieved", pageResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //return team details based on team id
    @ApiOperation(value = "get the team using id", notes = "using the team id, gives out the team details", httpMethod = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getTeamById(@PathVariable("id") long id) {
        Team team = teamService.getTeamById(id);
        ResponseWrapper response = new ResponseWrapper("success", "successfully retrieved", team);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //creates team
    @ApiOperation(value = "creates a new team", notes = "creates a new team", httpMethod = "POST")
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        ResponseWrapper response = new ResponseWrapper("success", "successfully created", createdTeam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //update the team details based on team id and input object
    @ApiOperation(value = "update team", notes = "updates team details based on it's id", httpMethod = "PUT")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper> updateTeam(@PathVariable("id") long id, @RequestBody Team team) {
        Team updatedTeam = teamService.updateTeam(id, team);
        ResponseWrapper response = new ResponseWrapper("success", "successfully updated", updatedTeam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //delete the team based on it's id and also deletes the team's players
    @ApiOperation(value = "delete a team", notes = "delete a team and it's players based on team id", httpMethod = "DELETE")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper> deleteTeam(@PathVariable("id") long id) {
        teamService.deleteTeam(id);
        ResponseWrapper response = new ResponseWrapper("success", "successfully deleted", null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
