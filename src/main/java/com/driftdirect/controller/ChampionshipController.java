package com.driftdirect.controller;

import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.ChampionshipFullDto;
import com.driftdirect.dto.championship.ChampionshipShortShowDto;
import com.driftdirect.dto.championship.ChampionshipUpdateDTO;
import com.driftdirect.dto.championship.driver.DriverParticipationDto;
import com.driftdirect.dto.championship.judge.JudgeParticipationDto;
import com.driftdirect.dto.news.ImageLinkCreateDto;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.exception.ObjectNotFoundException;
import com.driftdirect.security.SecurityService;
import com.driftdirect.service.championship.ChampionshipService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Paul on 11/6/2015.
 */
@RestController
public class ChampionshipController {
    private ChampionshipService championshipService;
    private SecurityService securityService;
    @Autowired
    public ChampionshipController(ChampionshipService championshipService, SecurityService securityService) {
        this.championshipService = championshipService;
        this.securityService = securityService;
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP, method = RequestMethod.GET)
    public ResponseEntity<List<ChampionshipFullDto>> list() {
        return new ResponseEntity<>(championshipService.findChampionships(), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_SHORT, method = RequestMethod.GET)
    public ResponseEntity<List<ChampionshipShortShowDto>> listShort(@RequestParam(name = "onlyEditable", required = false) Boolean onlyEditable,
                                                                    @AuthenticationPrincipal User currentUser) {
        if (onlyEditable != null) {
            if (currentUser == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(championshipService.getEditableChampionships(currentUser.getPerson(), securityService.isAdmin(currentUser)), HttpStatus.OK);
        }
        return new ResponseEntity<>(championshipService.getShortChampionshipList(currentUser), HttpStatus.OK);
    }

    @Secured({Authorities.ROLE_ORGANIZER, Authorities.ROLE_ADMIN})
    @RequestMapping(path = RestUrls.CHAMPIONSHIP, method = RequestMethod.POST)
    public ResponseEntity createChampionship(@Valid @RequestBody ChampionshipCreateDTO c, @AuthenticationPrincipal User currentUser) throws Exception {
        championshipService.createFromDto(c, currentUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured({Authorities.ROLE_ORGANIZER, Authorities.ROLE_ADMIN})
    @RequestMapping(path = RestUrls.CHAMPIONSHIP, method = RequestMethod.PUT)
    public ResponseEntity updateChampionship(@Valid ChampionshipUpdateDTO c, @AuthenticationPrincipal User currentUser) throws ObjectNotFoundException {
        if (!securityService.isChampionshipOrganizer(currentUser, c.getId())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
//        championshipService.update(c);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID, method = RequestMethod.GET)
    public ResponseEntity<ChampionshipFullDto> getById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(championshipService.findChampionship(id), HttpStatus.OK);
    }

    @Secured({Authorities.ROLE_ORGANIZER, Authorities.ROLE_ADMIN})
    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID, method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        if (!securityService.isChampionshipOrganizer(currentUser, id)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        championshipService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_ROUNDS, method = RequestMethod.POST)
    public ResponseEntity addRound(@PathVariable Long id,
                                   @RequestBody RoundCreateDto roundCreateDto,
                                   @AuthenticationPrincipal User currentUser) {
        if (!securityService.isChampionshipOrganizer(currentUser, id)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        championshipService.addRound(id, roundCreateDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_DRIVERS, method = RequestMethod.GET)
    public ResponseEntity<List<PersonShortShowDto>> getDrivers(@PathVariable Long id) {
        return new ResponseEntity<>(championshipService.findDrivers(id), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_DRIVERS_ID, method = RequestMethod.GET)
    public ResponseEntity<DriverParticipationDto> getDriver(@PathVariable(value = "championshipId") Long championshipId,
                                                            @PathVariable(value = "driverId") Long driverId) {
        return new ResponseEntity<>(championshipService.findDriver(championshipId, driverId), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_NEWS, method = RequestMethod.POST)
    public ResponseEntity addNews(@PathVariable(value = "id") Long id,
                                  @RequestBody @Valid ImageLinkCreateDto news,
                                  @AuthenticationPrincipal User currentUser) {
        if (!securityService.isChampionshipOrganizer(currentUser, id)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        championshipService.addNews(id, news);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_JUDGES, method = RequestMethod.GET)
    public ResponseEntity<List<JudgeParticipationDto>> getJudges(@PathVariable Long id) {
        return new ResponseEntity<>(championshipService.findJudges(id), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.CHAMPIONSHIP_ID_PUBLISH, method = RequestMethod.POST)
    public ResponseEntity publish(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        if (!securityService.isChampionshipOrganizer(currentUser, id)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        championshipService.publish(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
