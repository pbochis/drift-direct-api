package com.driftdirect.controller;

import com.driftdirect.domain.user.Authorities;
import com.driftdirect.dto.championship.*;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.round.RoundShortShowDto;
import com.driftdirect.exception.ObjectNotFoundException;
import com.driftdirect.service.ChampionshipService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<ChampionshipFullDto>> list() {
        return new ResponseEntity<>(championshipService.findChampionships(), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_SHORT, method = RequestMethod.GET)
    public ResponseEntity<List<ChampionshipShortShowDto>> listShort() {
        return new ResponseEntity<>(championshipService.getShortChampionshipList(), HttpStatus.OK);
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
    public ResponseEntity<ChampionshipFullDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(championshipService.findChampionship(id), HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID, method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id) {
        championshipService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_ROUNDS, method = RequestMethod.GET)
    public ResponseEntity<List<RoundShortShowDto>> getChampionshipRounds(@PathVariable Long id) {
        return new ResponseEntity<>(championshipService.championshipRounds(id), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_DRIVERS, method = RequestMethod.GET)
    public ResponseEntity<List<PersonShortShowDto>> getDrivers(@PathVariable Long id) {
        return new ResponseEntity<>(championshipService.findDrivers(id), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_DRIVERS_ID, method = RequestMethod.GET)
    public ResponseEntity<ChampionshipDriverParticipationDto> getDriver(@PathVariable(value = "championshipId") Long championshipId,
                                                                        @PathVariable(value = "driverId") Long driverId) {
        return new ResponseEntity<>(championshipService.findDriver(championshipId, driverId), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_JUDGES, method = RequestMethod.GET)
    public ResponseEntity<List<ChampionshipJudgeParticipationDto>> getJudges(@PathVariable Long id) {
        System.out.println("**********************************************************");
        return new ResponseEntity<>(championshipService.findJudges(id), HttpStatus.OK);
    }
}
