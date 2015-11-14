package com.driftdirect.controller;

import com.driftdirect.domain.Championship;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.ChampionshipUpdateDTO;
import com.driftdirect.exception.ObjectNotFoundException;
import com.driftdirect.service.ChampionshipService;
import com.driftdirect.util.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Paul on 11/6/2015.
 */

@RestController
public class ChampionshipController {

    private ChampionshipService championshipService;
    private int a = 2;

    @Autowired
    public ChampionshipController(ChampionshipService championshipService){
        this.championshipService = championshipService;
    }

    @RequestMapping(path = Routes.CHAMPIONSHIP, method = RequestMethod.GET)
    public List<Championship> list() throws ObjectNotFoundException {
        return championshipService.findAll();
    }

    @RequestMapping(path = Routes.CHAMPIONSHIP, method = RequestMethod.POST)
    public Championship createChampionship(@Valid ChampionshipCreateDTO c){
        return championshipService.createFromDto(c);
    }

    @RequestMapping(path = Routes.CHAMPIONSHIP, method = RequestMethod.PUT)
    public Championship updateChampionship(@Valid ChampionshipUpdateDTO c) throws ObjectNotFoundException {
        return championshipService.update(c);
    }

    @RequestMapping(path = Routes.CHAMPIONSHIP_ID, method = RequestMethod.GET)
    public Championship getById(@PathVariable Long id) {
        return championshipService.findById(id);
    }

    @RequestMapping(path = Routes.CHAMPIONSHIP_ID, method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        championshipService.delete(id);
    }
}
