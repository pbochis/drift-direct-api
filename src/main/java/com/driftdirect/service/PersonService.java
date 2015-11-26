package com.driftdirect.service;

import com.driftdirect.domain.driver.DriverDetails;
import com.driftdirect.domain.driver.Team;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.person.PersonType;
import com.driftdirect.dto.person.*;
import com.driftdirect.mapper.PersonMapper;
import com.driftdirect.repository.CountryRepository;
import com.driftdirect.repository.DriverDetailsRepository;
import com.driftdirect.repository.PersonRepository;
import com.driftdirect.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<PersonShortShowDto> findAll(){
        return repository.findAll().stream().map(PersonMapper::mapShort).collect(Collectors.toList());
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

    public void updatePerson(PersonUpdateDto dto){
        Person person = repository.findOne(dto.getId());
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setTelephone(dto.getTelephone());
        person.setCountry(countryRepository.findOne(dto.getCountry()));
        person.setYearsExperience(dto.getYearsExperience());
        person.setDescription(dto.getDescription());
        person.setPersonType(PersonType.valueOf(dto.getPersonType()));
        repository.save(person);
    }

    public void upateDriverDetails(DriverDetailsUpdateDto dto){
        DriverDetails details = driverDetailsRepository.findOne(dto.getId());
        details.setMake(dto.getMake());
        details.setModel(dto.getModel());
        details.setEngine(dto.getEngine());
        details.setSuspensionMods(dto.getSuspensionMods());
        details.setSteeringAngle(dto.getSteeringAngle());
        details.setWheels(dto.getWheels());
        details.setTires(dto.getTires());
        details.setOther(dto.getOther());
        details.setTeam(teamRepository.findOne(dto.getTeam()));
        driverDetailsRepository.save(details);
    }
    public void delete(Long id){
        repository.delete(id);
    }
}
