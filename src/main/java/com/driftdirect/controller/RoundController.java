package com.driftdirect.controller;

import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.dto.round.RoundUpdateDto;
import com.driftdirect.dto.round.playoff.graphic.PlayoffTreeGraphicDisplayDto;
import com.driftdirect.security.SecurityService;
import com.driftdirect.service.round.RoundService;
import com.driftdirect.service.round.playoff.PlayoffService;
import com.driftdirect.service.round.qualifier.QualifierService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Paul on 11/14/2015.
 */
@RestController
public class RoundController {
    private RoundService roundService;
    private QualifierService qualifierService;
    private SecurityService securityService;
    private PlayoffService playoffService;

    @Autowired
    public RoundController(RoundService roundService, QualifierService qualifierService, SecurityService securityService, PlayoffService playoffService) {
        this.roundService = roundService;
        this.qualifierService = qualifierService;
        this.securityService = securityService;
        this.playoffService = playoffService;
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.ROUND, method = RequestMethod.POST)
    public ResponseEntity createFromDto(@RequestBody @Valid RoundCreateDto dto, @AuthenticationPrincipal User currentUser) {
//        if (!securityService.canEditChampionship(currentUser, dto.getChampionshipId())) {
//            return new ResponseEntity(HttpStatus.FORBIDDEN);
//        }
//        roundService.createFromDto(dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.ROUND, method = RequestMethod.PUT)
    public ResponseEntity updateRound(@Valid RoundUpdateDto dto) throws NoSuchElementException {
//        roundService.update(dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.ROUND_ID, method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id) {
        roundService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.ROUND_ID, method = RequestMethod.GET)
    public RoundShowDto getById(@PathVariable Long id) {
        return roundService.findRound(id);
    }

//    @RequestMapping(path = RestUrls.ROUND_ID_SHCEDULE, method = RequestMethod.POST)
//    public ResponseEntity addSchedule(@PathVariable Long id, @Valid @RequestBody RoundScheduleEntryCreateDto rs){
//        roundService.addRoundSchedule(id, rs);
//        return new ResponseEntity(HttpStatus.OK);
//    }

//    @RequestMapping(path = RestUrls.ROUND_ID_TRACK, method = RequestMethod.POST)
//    public ResponseEntity addTrackLayoiut(@PathVariable Long id, @Valid @RequestBody TrackCreateDto dto){
//        roundService.addTrack(id, dto);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @RequestMapping(path = RestUrls.ROUND_ID_REGISTER, method = RequestMethod.POST)
    public ResponseEntity registerDriver(@PathVariable(value = "roundId") Long roundId,
                                         @PathVariable(value = "driverId") Long driverId,
                                         @AuthenticationPrincipal User user) {
        if (!securityService.canRegisterDriver(user, roundId)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        qualifierService.registerDriver(roundId, driverId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.ROUND_ID_REGISTER_DESK, method = RequestMethod.GET)
    public ResponseEntity<List<PersonShortShowDto>> getDriversForRegisterDesk(@PathVariable(value = "id") Long roundId){
        return new ResponseEntity<List<PersonShortShowDto>>(roundService.getPersonsForRegisterDesk(roundId), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.ROUND_ID_PLAYOFF_START, method = RequestMethod.POST)
    public ResponseEntity generatePlayoffTree(@PathVariable Long id) {
        if (roundService.finishQualifiers(id)) {
            playoffService.generatePlayoffTree(id);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(path = RestUrls.ROUND_ID_PLAYOFF, method = RequestMethod.GET)
    public ResponseEntity<PlayoffTreeGraphicDisplayDto> getRoundPlayoffs(@PathVariable Long id,
                                                                         @AuthenticationPrincipal User currentUser) {
        PlayoffTreeGraphicDisplayDto playoffs = roundService.getPlayoffs(id);
        if (playoffs == null && securityService.canGeneratePlayoffs(currentUser, id)) {
            if (roundService.finishQualifiers(id)) {
                playoffs = playoffService.generatePlayoffTree(id);
            }
        }
        return new ResponseEntity<>(playoffs, HttpStatus.OK);
    }
}


