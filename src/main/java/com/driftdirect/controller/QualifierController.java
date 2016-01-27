package com.driftdirect.controller;

import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.round.qualifier.QualifierFullDto;
import com.driftdirect.dto.round.qualifier.QualifierJudgeDto;
import com.driftdirect.dto.round.qualifier.run.RunJudgingCreateDto;
import com.driftdirect.exception.PreviousRunJudgingNotCompletedException;
import com.driftdirect.exception.QualifierAlreadyJudgeException;
import com.driftdirect.security.SecurityService;
import com.driftdirect.service.round.qualifier.QualifierService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Paul on 12/22/2015.
 */
@RestController
public class QualifierController {
    private QualifierService qualifierService;
    private SecurityService securityService;

    @Autowired
    public QualifierController(QualifierService qualifierService, SecurityService securityService) {
        this.qualifierService = qualifierService;
        this.securityService = securityService;
    }

    @RequestMapping(value = RestUrls.QUALIFIER_ID, method = RequestMethod.GET)
    public ResponseEntity<QualifierFullDto> getQualifier(@PathVariable Long id){
        return new ResponseEntity<QualifierFullDto>(qualifierService.findQualifier(id), HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_JUDGE)
    @RequestMapping(value = RestUrls.QUALIFIER_ID_SUBMIT, method = RequestMethod.POST)
    public ResponseEntity submitRunJudging(@PathVariable(value = "id") Long id,
                                                @PathVariable(value = "runId") Long runId,
                                                @RequestBody RunJudgingCreateDto runJudging,
                                                @AuthenticationPrincipal User currentUser) {

        if (!securityService.canJudgeQualifier(currentUser, id)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        qualifierService.submitRunJudging(id, runId, runJudging, currentUser.getPerson());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured(Authorities.ROLE_JUDGE)
    @RequestMapping(value = RestUrls.QUALIFIER_ID_START, method = RequestMethod.GET)
    public ResponseEntity<QualifierJudgeDto> startJudging(@PathVariable(value = "id") Long id,
                                                          @AuthenticationPrincipal User currentUser) throws PreviousRunJudgingNotCompletedException {
        return new ResponseEntity<>(qualifierService.startQualifierJudging(id, currentUser.getPerson()), HttpStatus.OK);
    }

    @RequestMapping(value = RestUrls.QUALIFIER_ID, method = RequestMethod.DELETE)
    public ResponseEntity deleteQualifier(@PathVariable(value = "id") Long id,
                                          @AuthenticationPrincipal User currentUser) throws QualifierAlreadyJudgeException {
        if (!securityService.canDeleteQualifier(currentUser, id)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        qualifierService.deleteQualifier(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
