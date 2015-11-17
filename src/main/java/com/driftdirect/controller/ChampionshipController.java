package com.driftdirect.controller;

import com.driftdirect.domain.Championship;
import com.driftdirect.domain.Round;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.ChampionshipDto;
import com.driftdirect.dto.championship.ChampionshipUpdateDTO;
import com.driftdirect.dto.round.RoundDto;
import com.driftdirect.exception.ObjectNotFoundException;
import com.driftdirect.service.ChampionshipService;
import com.driftdirect.util.Routes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Created by Paul on 11/6/2015.
 */

@RestController
@RequestMapping(path = Routes.CHAMPIONSHIP)
public class ChampionshipController {

    private ChampionshipService championshipService;
    Logger logger = LoggerFactory.getLogger(ChampionshipController.class);

    @Autowired
    public ChampionshipController(ChampionshipService championshipService){
        this.championshipService = championshipService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ChampionshipDto> list() {
        return championshipService.findChampionships();
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(method = RequestMethod.POST)
    public ChampionshipDto createChampionship(@Valid ChampionshipCreateDTO c){
        return championshipService.convertToDto(championshipService.createFromDto(c));
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(method = RequestMethod.PUT)
    public ChampionshipDto updateChampionship(@Valid ChampionshipUpdateDTO c) throws ObjectNotFoundException {
        return championshipService.update(c);
    }

    @RequestMapping(path = Routes.ID, method = RequestMethod.GET)
    public ChampionshipDto getById(@PathVariable Long id) {
        return championshipService.findChampionship(id);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = Routes.ID, method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        championshipService.delete(id);
    }

    @RequestMapping(path = Routes.Championship.ROUNDS, method = RequestMethod.GET)
    public List<RoundDto> getChampionshipRounds(@PathVariable Long id){
        return championshipService.championshipRounds(id);
    }
}
