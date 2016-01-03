package com.driftdirect.controller;

import com.driftdirect.dto.country.CountryShowDto;
import com.driftdirect.mapper.CountryMapper;
import com.driftdirect.repository.CountryRepository;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 1/3/2016.
 */
@RestController
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;

    @RequestMapping(path = RestUrls.COUNTRY, method = RequestMethod.GET)
    public List<CountryShowDto> getCountries(){
        return countryRepository.findAll().stream().map(CountryMapper::map).collect(Collectors.toList());
    }
}
