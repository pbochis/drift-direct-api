package com.driftdirect.controller;

import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.dto.round.RoundScheduleCreateDto;
import com.driftdirect.dto.round.RoundUpdateDto;
import com.driftdirect.service.ChampionshipService;
import com.driftdirect.service.RoundService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

/**
 * Created by Paul on 11/14/2015.
 */
@RestController
public class RoundController {
    private RoundService roundService;

    @Autowired
    public RoundController(RoundService roundService){
        this.roundService = roundService;
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.ROUND, method = RequestMethod.POST)
    public ResponseEntity createFromDto(@RequestBody @Valid RoundCreateDto dto){
        roundService.createFromDto(dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.ROUND, method = RequestMethod.PUT)
    public ResponseEntity updateRound(@Valid RoundUpdateDto dto) throws NoSuchElementException {
        roundService.update(dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.ROUND_ID, method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id) {
        roundService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.ROUND_ID, method = RequestMethod.GET)
    public RoundShowDto getById(@PathVariable Long id) {
        return roundService.findRound(id);
    }

    @RequestMapping(path = RestUrls.ROUND_ID_SHCEDULE, method = RequestMethod.POST)
    public ResponseEntity addSchedule(@PathVariable Long id, @Valid @RequestBody RoundScheduleCreateDto rs){
        // [2000,5,15,0,0,0,0] [year, month, day, hour, minute, second, nanosecond]
        roundService.addRoundSchedule(id, rs);
        return new ResponseEntity(HttpStatus.OK);
    }
}


