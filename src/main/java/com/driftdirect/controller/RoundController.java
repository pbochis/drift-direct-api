package com.driftdirect.controller;

import com.driftdirect.domain.Championship;
import com.driftdirect.domain.Round;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.RoundDto;
import com.driftdirect.dto.round.RoundUpdateDto;
import com.driftdirect.service.ChampionshipService;
import com.driftdirect.service.RoundService;
import com.driftdirect.util.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Paul on 11/14/2015.
 */
@RestController
@RequestMapping(path = Routes.ROUND)
public class RoundController {
    private RoundService roundService;
    private ChampionshipService championshipService;

    @Autowired
    public RoundController(RoundService roundService, ChampionshipService championshipService){
        this.roundService = roundService;
        this.championshipService = championshipService;
    }


    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(method = RequestMethod.POST)
    public Round createFromDto(@RequestBody @Valid RoundCreateDto dto){
        dto.setChampionship(championshipService.findById(dto.getChampionshipId()));
        return roundService.createFromDto(dto);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(method = RequestMethod.PUT)
    public RoundDto updateRound(@Valid RoundUpdateDto dto) throws NoSuchElementException {
        dto.setChampionship(championshipService.findById(dto.getChampionshipId()));
        return roundService.update(dto);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = Routes.ID, method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        roundService.delete(id);
    }

    @RequestMapping(path = Routes.ID, method = RequestMethod.GET)
    public RoundDto getById(@PathVariable Long id) {
        return roundService.findRound(id);
    }
}


