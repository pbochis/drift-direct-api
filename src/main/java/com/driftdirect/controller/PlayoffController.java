package com.driftdirect.controller;

import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.round.playoff.PlayoffBattleRoundJudging;
import com.driftdirect.dto.round.playoff.PlayoffJudgeDto;
import com.driftdirect.exception.PreviousRunJudgingNotCompletedException;
import com.driftdirect.security.SecurityService;
import com.driftdirect.service.round.playoff.PlayoffService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Paul on 1/5/2016.
 */
@RestController
public class PlayoffController {

    @Autowired
    private PlayoffService playoffService;

    @Autowired
    private SecurityService securityService;
//    public static final String PLAYOFF_ID_START = "/playoff/{playoffId}/stage/{stageId}/battle/{battleId}/start";

    @Secured(Authorities.ROLE_JUDGE)
    @RequestMapping(path = RestUrls.PLAYOFF_ID_START, method = RequestMethod.GET)
    public ResponseEntity<PlayoffJudgeDto> startBattleJudging(@PathVariable(value = "battleId") Long battleId,
                                                              @AuthenticationPrincipal User currentUser) throws PreviousRunJudgingNotCompletedException {
        if (!securityService.canJudgePlayoff(currentUser, battleId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(playoffService.startPlayoffJudging(currentUser.getPerson(), battleId), HttpStatus.OK);
    }


    @RequestMapping(path = RestUrls.PLAYOFF_BATTLE_ID, method = RequestMethod.GET)
    public ResponseEntity<Object> getPlayoffBattle(@PathVariable(value = "battleId") Long battleId){
        return new ResponseEntity<>(playoffService.findBattle(battleId), HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_JUDGE)
    @RequestMapping(path = RestUrls.PLAYOFF_ID_SUBMIT, method = RequestMethod.POST)
    public ResponseEntity submitBattleRunJudging(@PathVariable(value = "battleId") Long battleId,
                                                 @RequestBody PlayoffBattleRoundJudging battleRoundJudging,
                                                 @AuthenticationPrincipal User currentUser) {
        if (!securityService.canJudgePlayoff(currentUser, battleId)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        playoffService.submitPlayoffJudging(currentUser.getPerson(), battleId, battleRoundJudging);
        return new ResponseEntity(HttpStatus.OK);
    }
}
