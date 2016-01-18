package com.driftdirect.dto.person;

import com.driftdirect.dto.country.CountryShowDto;
import org.joda.time.DateTime;

/**
 * Created by Paul on 11/21/2015.
 */
public class PersonCreateDto {
    private String firstName;
    private String lastName;
    private String telephone;
    private Long profilePicture;
    private CountryShowDto country;
    private DateTime birthDate;
    private DateTime careerStartDate;
    private String portfolio;
    private String description;
    private String nick;
    private String website;

    private String personType;

    private DriverDetailsCreateDto driverDetails;

    public DriverDetailsCreateDto getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(DriverDetailsCreateDto driverDetails) {
        this.driverDetails = driverDetails;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public CountryShowDto getCountry() {
        return country;
    }

    public void setCountry(CountryShowDto country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public Long getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Long profilePicture) {
        this.profilePicture = profilePicture;
    }

    public DateTime getCareerStartDate() {
        return careerStartDate;
    }

    public void setCareerStartDate(DateTime careerStartDate) {
        this.careerStartDate = careerStartDate;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
