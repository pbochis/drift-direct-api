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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public ChampionshipController(ChampionshipService championshipService){
        this.championshipService = championshipService;
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP, method = RequestMethod.GET)
    public ResponseEntity<List<ChampionshipShowDto>> list() {
        return new ResponseEntity<>(championshipService.findChampionships(), HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.CHAMPIONSHIP, method = RequestMethod.POST)
    public ResponseEntity createChampionship(@Valid ChampionshipCreateDTO c){
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.CHAMPIONSHIP, method = RequestMethod.PUT)
    public ResponseEntity updateChampionship(@Valid ChampionshipUpdateDTO c) throws ObjectNotFoundException {
        championshipService.update(c);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID, method = RequestMethod.GET)
    public ResponseEntity<ChampionshipShowDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(championshipService.findChampionship(id), HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID, method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id) {
        championshipService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_ROUNDS, method = RequestMethod.GET)
    public ResponseEntity<List<RoundShowDto>> getChampionshipRounds(@PathVariable Long id){
        return new ResponseEntity<>(championshipService.championshipRounds(id), HttpStatus.OK);
    }
}
