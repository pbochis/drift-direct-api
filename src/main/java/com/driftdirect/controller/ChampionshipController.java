package com.driftdirect.controller;

import com.driftdirect.domain.Championship;
import com.driftdirect.domain.Round;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.ChampionshipDto;
import com.driftdirect.dto.championship.ChampionshipUpdateDTO;
import com.driftdirect.dto.round.RoundDto;
import com.driftdirect.exception.ObjectNotFoundException;
import com.driftdirect.service.ChampionshipService;
import com.driftdirect.util.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Created by Paul on 11/6/2015.
 */

@RestController
public class ChampionshipController {

    private ChampionshipService championshipService;

    @Autowired
    public ChampionshipController(ChampionshipService championshipService){
        this.championshipService = championshipService;
    }

    @RequestMapping(path = Routes.CHAMPIONSHIP, method = RequestMethod.GET)
    public List<ChampionshipDto> list() {
        return championshipService.findChampionships();
    }

    @RequestMapping(path = Routes.CHAMPIONSHIP, method = RequestMethod.POST)
    public ChampionshipDto createChampionship(@Valid ChampionshipCreateDTO c){
        return championshipService.createFromDto(c);
    }

    @RequestMapping(path = Routes.CHAMPIONSHIP, method = RequestMethod.PUT)
    public ChampionshipDto updateChampionship(@Valid ChampionshipUpdateDTO c) throws ObjectNotFoundException {
        return championshipService.update(c);
    }

    @RequestMapping(path = Routes.CHAMPIONSHIP_ID, method = RequestMethod.GET)
    public ChampionshipDto getById(@PathVariable Long id) {
        return championshipService.findChampionship(id);
    }

    @RequestMapping(path = Routes.CHAMPIONSHIP_ID, method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        championshipService.delete(id);
    }

    @RequestMapping(path = Routes.CHAMPIONSHIP_ID_ROUNDS, method = RequestMethod.GET)
    public List<RoundDto> getChampionshipRounds(@PathVariable Long id){
        return championshipService.championshipRounds(id);
    }
}
