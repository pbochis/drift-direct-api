package com.driftdirect.controller;

import com.driftdirect.domain.user.User;
import com.driftdirect.security.SecurityService;
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
 * Created by Paul on 3/21/2016.
 */
@RestController
public class PermissionController {

    private final SecurityService securityService;

    @Autowired
    public PermissionController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(path = RestUrls.PERMISSION_ROUND_GENERATE_BATTLE_TREE, method = RequestMethod.GET)
    public ResponseEntity<Boolean> canGenerateRoundBattleTree(@PathVariable(value = "roundId") Long roundId,
                                                              @AuthenticationPrincipal User currentUser) {
        return new ResponseEntity<Boolean>(securityService.canGeneratePlayoffs(currentUser, roundId), HttpStatus.OK);
    }
}
