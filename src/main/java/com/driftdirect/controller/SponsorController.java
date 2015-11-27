package com.driftdirect.controller;

import com.driftdirect.dto.sponsor.SponsorCreateDto;
import com.driftdirect.dto.sponsor.SponsorShowDto;
import com.driftdirect.dto.sponsor.SponsorUpdateDto;
import com.driftdirect.service.SponsorService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Paul on 11/26/2015.
 */
@RestController
public class SponsorController {
    private SponsorService sponsorService;

    @Autowired
    public SponsorController(SponsorService sponsorService){
        this.sponsorService = sponsorService;
    }

    @RequestMapping(path = RestUrls.SPONSOR, method = RequestMethod.GET)
    public ResponseEntity<List<SponsorShowDto>> findAll(){
        return new ResponseEntity<>(sponsorService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.SPONSOR_ID, method = RequestMethod.GET)
    public ResponseEntity<SponsorShowDto> findOne(@PathVariable Long id){
        return new ResponseEntity<>(sponsorService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.SPONSOR_ID, method = RequestMethod.GET)
    public ResponseEntity delete(@PathVariable Long id){
        sponsorService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.SPONSOR, method = RequestMethod.POST)
    public ResponseEntity createSponsor(@RequestBody @Valid SponsorCreateDto dto){
        sponsorService.createFromDto(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(path = RestUrls.SPONSOR, method = RequestMethod.PUT)
    public ResponseEntity updateSponsor(@RequestBody @Valid SponsorUpdateDto dto){
        sponsorService.update(dto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
