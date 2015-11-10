package com.driftdirect.controller;

import com.driftdirect.domain.Championship;
import com.driftdirect.service.ChampionshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(path = "/champ", method = RequestMethod.GET)
    public List<Championship> list(){
        return championshipService.findAll();
    }

    @RequestMapping(path = "/champ", method = RequestMethod.POST)
    public Championship createChampionship(Championship c){
        System.out.println(c.toString());
        return championshipService.save(c);
    }




}
