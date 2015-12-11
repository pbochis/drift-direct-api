package com.driftdirect.dto.person;

import org.joda.time.DateTime;

/**
 * Created by Paul on 11/21/2015.
 */
public class PersonCreateDto {
    private String firstName;
    private String lastName;
    private String telephone;
    private Long profilePicture;
    private Long country;

    private DateTime careerStartDate;
    private String portfolio;
    private String description;

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

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
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
}
