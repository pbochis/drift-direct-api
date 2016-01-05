package com.driftdirect.controller;

import com.driftdirect.domain.user.User;
import com.driftdirect.security.SecurityService;
import com.driftdirect.service.round.playoff.PlayoffService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(path = RestUrls.PLAYOFF_ID_START, method = RequestMethod.GET)
    public ResponseEntity startBattleJudging(@PathVariable(value = "playoffId") Long playoffId,
                                             @PathVariable(value = "stageId") Long stageId,
                                             @PathVariable(value = "battleId") Long battleId,
                                             @AuthenticationPrincipal User currentUser) {
        if (!securityService.canJudgePlayoff(currentUser, playoffId)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
