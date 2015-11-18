package com.driftdirect.controller;

import com.driftdirect.domain.Round;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.dto.round.RoundScheduleCreateDto;
import com.driftdirect.dto.round.RoundUpdateDto;
import com.driftdirect.service.ChampionshipService;
import com.driftdirect.service.RoundService;
import com.driftdirect.util.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

/**
 * Created by Paul on 11/14/2015.
 */
@RestController
public class RoundController {
    private RoundService roundService;
    private ChampionshipService championshipService;

    @Autowired
    public RoundController(RoundService roundService, ChampionshipService championshipService){
        this.roundService = roundService;
        this.championshipService = championshipService;
    }


    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = Routes.ROUND, method = RequestMethod.POST)
    public Round createFromDto(@RequestBody @Valid RoundCreateDto dto){
        return roundService.createFromDto(dto);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = Routes.ROUND, method = RequestMethod.PUT)
    public RoundShowDto updateRound(@Valid RoundUpdateDto dto) throws NoSuchElementException {
        return roundService.update(dto);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = Routes.ROUND_ID, method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        roundService.delete(id);
    }

    @RequestMapping(path = Routes.ROUND_ID, method = RequestMethod.GET)
    public RoundShowDto getById(@PathVariable Long id) {
        return roundService.findRound(id);
    }

    @RequestMapping(path = Routes.ROUND_ID_SHCEDULE, method = RequestMethod.POST)
    public void addSchedule(@PathVariable Long id, @Valid @RequestBody RoundScheduleCreateDto rs){
        // [2000,5,15,0,0,0,0] [year, month, day, hour, minute, second, nanosecond]
        roundService.addRoundSchedule(id, rs);
    }
}


