package com.driftdirect.controller;

import com.driftdirect.dto.person.PersonCreateDto;
import com.driftdirect.service.PersonService;
import com.driftdirect.util.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Paul on 11/21/2015.
 */
@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }


    @RequestMapping(path = Routes.PERSON, method = RequestMethod.POST)
    public ResponseEntity createPerson(@RequestBody @Valid PersonCreateDto dto){
        personService.createFromDto(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

//    @RequestMapping(path = Routes.PERSON_ID, method = RequestMethod.GET)
//    public ResponseEntity<PersonShowDto> get(@PathVariable Long id){
//        return personService.findPerson(id);
//    }
}
