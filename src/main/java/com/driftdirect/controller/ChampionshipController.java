package com.driftdirect.controller;

import com.driftdirect.domain.user.Authorities;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.ChampionshipShowDto;
import com.driftdirect.dto.championship.ChampionshipUpdateDTO;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.exception.ObjectNotFoundException;
import com.driftdirect.service.ChampionshipService;
import com.driftdirect.util.RestUrls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Paul on 11/6/2015.
 */

@RestController
public class ChampionshipController {

    private ChampionshipService championshipService;
    Logger logger = LoggerFactory.getLogger(ChampionshipController.class);

    @Autowired
    public ChampionshipController(ChampionshipService championshipService){
        this.championshipService = championshipService;
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP, method = RequestMethod.GET)
    public List<ChampionshipShowDto> list() {
        return championshipService.findChampionships();
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.CHAMPIONSHIP, method = RequestMethod.POST)
    public ChampionshipShowDto createChampionship(@Valid ChampionshipCreateDTO c){
        return championshipService.createFromDto(c);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.CHAMPIONSHIP, method = RequestMethod.PUT)
    public ChampionshipShowDto updateChampionship(@Valid ChampionshipUpdateDTO c) throws ObjectNotFoundException {
        return championshipService.update(c);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID, method = RequestMethod.GET)
    public ChampionshipShowDto getById(@PathVariable Long id) {
        return championshipService.findChampionship(id);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID, method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        championshipService.delete(id);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_ROUNDS, method = RequestMethod.GET)
    public List<RoundShowDto> getChampionshipRounds(@PathVariable Long id){
        return championshipService.championshipRounds(id);
    }
}
