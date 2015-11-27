package com.driftdirect.controller;

import com.driftdirect.dto.team.TeamCreateDto;
import com.driftdirect.dto.team.TeamShowDto;
import com.driftdirect.dto.team.TeamUpdateDto;
import com.driftdirect.service.TeamService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Paul on 11/27/2015.
 */
@RestController
public class TeamController {

    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService){
        this.teamService = teamService;
    }

    @RequestMapping(path = RestUrls.TEAM, method = RequestMethod.GET)
    public ResponseEntity<List<TeamShowDto>> findAll(){
        return new ResponseEntity<>(teamService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.TEAM_ID, method = RequestMethod.GET)
    public ResponseEntity<TeamShowDto> findOne(@PathVariable Long id){
        return new ResponseEntity<>(teamService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.TEAM_ID, method = RequestMethod.GET)
    public ResponseEntity delete(@PathVariable Long id){
        teamService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.TEAM, method = RequestMethod.POST)
    public ResponseEntity createTeam(@RequestBody @Valid TeamCreateDto dto){
        teamService.createFromDto(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(path = RestUrls.TEAM, method = RequestMethod.PUT)
    public ResponseEntity updateTeam(@RequestBody @Valid TeamUpdateDto dto){
        teamService.update(dto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
