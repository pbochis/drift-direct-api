package com.driftdirect.controller;

import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.dto.round.qualifier.QualifierFullDto;
import com.driftdirect.service.round.qualifier.QualifierService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Paul on 12/22/2015.
 */
@RestController
public class QualifierController {
    private QualifierService qualifierService;

    @Autowired
    public QualifierController(QualifierService qualifierService){
        this.qualifierService = qualifierService;
    }

    @RequestMapping(value = RestUrls.QUALIFIER_ID, method = RequestMethod.GET)
    public ResponseEntity<QualifierFullDto> getQualifier(@PathVariable Long id){
        return new ResponseEntity<QualifierFullDto>(qualifierService.findQualifier(id), HttpStatus.OK);
    }
}
