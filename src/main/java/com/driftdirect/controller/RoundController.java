package com.driftdirect.controller;

import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.RoundScheduleCreateDto;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.dto.round.RoundUpdateDto;
import com.driftdirect.dto.round.track.TrackCreateDto;
import com.driftdirect.service.round.RoundService;
import com.driftdirect.service.round.qualifier.QualifierService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

/**
 * Created by Paul on 11/14/2015.
 */
@RestController
public class RoundController {
    private RoundService roundService;
    private QualifierService qualifierService;

    @Autowired
    public RoundController(RoundService roundService, QualifierService qualifierService) {
        this.roundService = roundService;
        this.qualifierService = qualifierService;
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.ROUND, method = RequestMethod.POST)
    public ResponseEntity createFromDto(@RequestBody @Valid RoundCreateDto dto){
        roundService.createFromDto(dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_ORGANIZER)
    @RequestMapping(path = RestUrls.ROUND, method = RequestMethod.PUT)
    public ResponseEntity updateRound(@Valid RoundUpdateDto dto) throws NoSuchElementException {
        roundService.update(dto);
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

    @RequestMapping(path = RestUrls.ROUND_ID_SHCEDULE, method = RequestMethod.POST)
    public ResponseEntity addSchedule(@PathVariable Long id, @Valid @RequestBody RoundScheduleCreateDto rs){
        roundService.addRoundSchedule(id, rs);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.ROUND_ID_TRACK, method = RequestMethod.POST)
    public ResponseEntity addTrackLayoiut(@PathVariable Long id, @Valid @RequestBody TrackCreateDto dto){
        roundService.addTrack(id, dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.ROUND_ID_REGISTER, method = RequestMethod.POST)
    public ResponseEntity registerDriver(@PathVariable(value = "roundId") Long roundId,
                                         @PathVariable(value = "driverId") Long driverId,
                                         @AuthenticationPrincipal User user) {
        //securityService.canRegisterDriver(user, round);
        qualifierService.registerDriver(roundId, driverId);
        return new ResponseEntity(HttpStatus.OK);
    }
}


