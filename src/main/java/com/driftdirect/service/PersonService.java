package com.driftdirect.service;

import com.driftdirect.domain.driver.DriverDetails;
import com.driftdirect.domain.driver.Team;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.person.PersonType;
import com.driftdirect.dto.person.DriverDetailsCreateDto;
import com.driftdirect.dto.person.PersonCreateDto;
import com.driftdirect.repository.CountryRepository;
import com.driftdirect.repository.DriverDetailsRepository;
import com.driftdirect.repository.PersonRepository;
import com.driftdirect.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Paul on 11/20/2015.
 */
@Service
public class PersonService {

    private PersonRepository repository;
    private CountryRepository countryRepository;
    private DriverDetailsRepository driverDetailsRepository;
    private TeamRepository teamRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, CountryRepository countryRepository, TeamRepository teamRepository, DriverDetailsRepository driverDetailsRepository){
        this.repository = personRepository;
        this.countryRepository = countryRepository;
        this.teamRepository = teamRepository;
        this.driverDetailsRepository = driverDetailsRepository;
    }

    public void createFromDto(PersonCreateDto dto){
        Person p = new Person();
        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        p.setCountry(countryRepository.findOne(dto.getCountry()));
        p.setTelephone(dto.getTelephone());
        p.setYearsExperience(dto.getYearsExperience());
        p.setDescription(dto.getDescription());
        p.setPersonType(PersonType.valueOf(dto.getPersonType()));
        if (p.getPersonType() == PersonType.Driver){
            p.setDriverDetails(createDriverDetails(dto.getDriverDetails()));
        }
        repository.save(p);
    }

    private DriverDetails createDriverDetails(DriverDetailsCreateDto dto){
        DriverDetails driverDetails = new DriverDetails();
        Team team = teamRepository.findOne(dto.getTeam());
        driverDetails.setMake(dto.getMake());
        driverDetails.setModel(dto.getModel());
        driverDetails.setEngine(dto.getEngine());
        driverDetails.setSteeringAngle(dto.getSteeringAngle());
        driverDetails.setSuspensionMods(dto.getSuspensionMods());
        driverDetails.setWheels(dto.getWheels());
        driverDetails.setTires(dto.getTires());
        driverDetails.setOther(dto.getOther());
        driverDetails.setTeam(team);
        return driverDetailsRepository.save(driverDetails);
    }

}
