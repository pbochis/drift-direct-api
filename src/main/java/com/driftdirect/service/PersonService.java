package com.driftdirect.service;

import com.driftdirect.domain.driver.DriverDetails;
import com.driftdirect.domain.driver.Team;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.person.PersonType;
import com.driftdirect.dto.person.*;
import com.driftdirect.mapper.PersonMapper;
import com.driftdirect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/20/2015.
 */
@Service
public class PersonService {
    private PersonRepository personRepository;
    private CountryRepository countryRepository;
    private DriverDetailsRepository driverDetailsRepository;
    private TeamRepository teamRepository;
    private FileRepository fileRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, FileRepository fileRepository, CountryRepository countryRepository, TeamRepository teamRepository, DriverDetailsRepository driverDetailsRepository) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
        this.teamRepository = teamRepository;
        this.driverDetailsRepository = driverDetailsRepository;
        this.fileRepository = fileRepository;
    }
    public List<PersonShortShowDto> findAll(){
        return personRepository.findAll().stream().map(PersonMapper::mapShort).collect(Collectors.toList());
    }

    public List<PersonShortShowDto> findByType(PersonType personType) {
        return personRepository.findPersonsByType(personType).stream().map(PersonMapper::mapShort).collect(Collectors.toList());
    }

    public Person findOne(Long id){
        return personRepository.findOne(id);
    }

    public Person createFromDto(PersonCreateDto dto){
        Person p = new Person();
        mapPersonData(p, dto);
        if (p.getPersonType() == PersonType.Driver) {
            DriverDetails driverDetails = new DriverDetails();
            p.setDriverDetails(mapAndSaveDriverDetails(driverDetails, dto.getDriverDetails()));
        }
        return personRepository.save(p);
    }

    private void mapPersonData(Person p, PersonCreateDto dto) {
        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        if (dto.getCountry() != null){
            p.setCountry(countryRepository.findOne(dto.getCountry().getId()));
        }
        p.setNick(dto.getNick());
        p.setBirthDate(dto.getBirthDate());
        p.setTelephone(dto.getTelephone());
        p.setCareerStartDate(dto.getCareerStartDate());
        p.setDescription(dto.getDescription());
        p.setPortfolio(dto.getPortfolio());
        p.setWebsite(dto.getWebsite());
        p.setPersonType(PersonType.valueOf(dto.getPersonType()));
        if (dto.getProfilePicture() != null){
            p.setProfilePicture(fileRepository.findOne(dto.getProfilePicture()));
        }
    }

    private DriverDetails mapAndSaveDriverDetails(DriverDetails driverDetails, DriverDetailsCreateDto dto) {
        if (dto.getTeam() != null){
            Team team = teamRepository.findOne(dto.getTeam());
            driverDetails.setTeam(team);
        }
        driverDetails.setMake(dto.getMake());
        driverDetails.setModel(dto.getModel());
        driverDetails.setEngine(dto.getEngine());
        driverDetails.setSteeringAngle(dto.getSteeringAngle());
        driverDetails.setSuspensionMods(dto.getSuspensionMods());
        driverDetails.setWheels(dto.getWheels());
        driverDetails.setTires(dto.getTires());
        driverDetails.setOther(dto.getOther());
        return driverDetailsRepository.save(driverDetails);
    }

    public void updatePerson(PersonUpdateDto dto){
        Person person = personRepository.findOne(dto.getId());
        mapPersonData(person, dto);
        if (person.getPersonType() == PersonType.Driver) {
            DriverDetails driverDetails = person.getDriverDetails() != null ? person.getDriverDetails() : new DriverDetails();
            person.setDriverDetails(mapAndSaveDriverDetails(driverDetails, dto.getDriverDetails()));
        }
        personRepository.save(person);
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

    public PersonFullDto findPerson(Long id) {
        return PersonMapper.mapFull(personRepository.findOne(id));
    }

    public void delete(Long id){
        personRepository.delete(id);
    }
}
