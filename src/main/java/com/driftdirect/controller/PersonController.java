package com.driftdirect.controller;

import com.driftdirect.domain.person.PersonType;
import com.driftdirect.dto.person.PersonCreateDto;
import com.driftdirect.dto.person.PersonFullDto;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.person.PersonUpdateDto;
import com.driftdirect.service.PersonService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @RequestMapping(path = RestUrls.PERSON, method = RequestMethod.GET)
    public ResponseEntity<List<PersonShortShowDto>> findAll(@RequestParam(name = "personType", required = false) PersonType personType) {
        if (personType != null) {
            return new ResponseEntity<>(personService.findByType(personType), HttpStatus.OK);
        }
        return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.PERSON, method = RequestMethod.POST)
    public ResponseEntity<Long> createPerson(@RequestBody @Valid PersonCreateDto dto){
        Long id = personService.createFromDto(dto).getId();
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.PERSON_ID, method = RequestMethod.PUT)
    public ResponseEntity updatePerson(@RequestBody @Valid PersonUpdateDto dto){
        personService.updatePerson(dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.PERSON_ID, method = RequestMethod.GET)
    public ResponseEntity<PersonFullDto> getPerson(@PathVariable("id") Long id) {
        return new ResponseEntity<>(personService.findPerson(id), HttpStatus.OK);
    }

    @RequestMapping(path = RestUrls.PERSON_ID, method = RequestMethod.DELETE)
    public ResponseEntity deletePerson(@PathVariable Long id){
        personService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
