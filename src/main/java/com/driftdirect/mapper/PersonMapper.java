package com.driftdirect.mapper;

import com.driftdirect.domain.file.File;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.sponsor.Sponsor;
import com.driftdirect.dto.person.PersonFullDto;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.person.driver.DriverDetailsDto;
import com.driftdirect.dto.person.driver.DriverDetailsShortShowDto;

/**
 * Created by Paul on 11/21/2015.
 */
public class PersonMapper {
    public static PersonShortShowDto mapShort(Person person){
        PersonShortShowDto dto = new PersonShortShowDto();
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setId(person.getId());
        dto.setPersonType(person.getPersonType());
        dto.setDescription(person.getDescription());
        if (person.getProfilePicture() != null){
            dto.setProfilePicture(person.getProfilePicture().getId());
        }
        dto.setNick(person.getNick());
        dto.setBirthDate(person.getBirthDate());
        if (person.getCountry() != null) {
            dto.setCountry(person.getCountry().getFlag().getId());
        }
        if (person.getDriverDetails() != null){
            DriverDetailsShortShowDto driverDetails = new DriverDetailsShortShowDto();
            driverDetails.setId(person.getDriverDetails().getId());
            driverDetails.setMake(person.getDriverDetails().getMake());
            driverDetails.setModel(person.getDriverDetails().getModel());
            driverDetails.setHorsePower(person.getDriverDetails().getHorsePower());
            if (person.getDriverDetails().getTeam() != null){
                driverDetails.setTeam(TeamMapper.map(person.getDriverDetails().getTeam()));
            }
            for (Sponsor sponsor : person.getDriverDetails().getSponsors()) {
                driverDetails.addSponsor(SponsorMapper.map(sponsor));
            }
            dto.setDriverDetails(driverDetails);
        }

        for (File f : person.getGallery()) {
            dto.addToGallery(f.getId());
        }
        return dto;
    }

    public static PersonFullDto mapFull(Person p) {
        PersonFullDto dto = new PersonFullDto();
        dto.setId(p.getId());
        dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName());
        dto.setPersonType(p.getPersonType());
        if (p.getProfilePicture() != null) {
            dto.setProfilePicture(p.getProfilePicture().getId());
        }
        dto.setTelephone(p.getTelephone());
        dto.setCountry(CountryMapper.map(p.getCountry()));
        dto.setCareerStartDate(p.getCareerStartDate());
        dto.setPortfolio(p.getPortfolio());
        dto.setDescription(p.getDescription());
        dto.setWebsite(p.getWebsite());
        dto.setBirthDate(p.getBirthDate());
        for (File file : p.getGallery()) {
            dto.addPicture(file.getId());
        }
        if (p.getDriverDetails() != null) {
            DriverDetailsDto details = new DriverDetailsDto();
            details.setId(p.getDriverDetails().getId());
            details.setMake(p.getDriverDetails().getMake());
            details.setModel(p.getDriverDetails().getModel());
            details.setEngine(p.getDriverDetails().getEngine());
            details.setSteeringAngle(p.getDriverDetails().getSteeringAngle());
            details.setSuspensionMods(p.getDriverDetails().getSuspensionMods());
            details.setWheels(p.getDriverDetails().getWheels());
            details.setTires(p.getDriverDetails().getTires());
            details.setOther(p.getDriverDetails().getOther());
            if (details.getTeam() != null) {
                details.setTeam(TeamMapper.map(p.getDriverDetails().getTeam()));
            }
            for (Sponsor sponsor : p.getDriverDetails().getSponsors()) {
                details.addSponsor(SponsorMapper.map(sponsor));
            }
            details.setHorsePower(p.getDriverDetails().getHorsePower());
            dto.setDriverDetails(details);
        }
        return dto;
    }

}
