package com.driftdirect.dto.person;

import com.driftdirect.dto.country.CountryShowDto;
import org.joda.time.DateTime;

/**
 * Created by Paul on 11/30/2015.
 */
public class PersonFullDto {
    private long id;
    private String firstName;
    private String lastName;
    private String telephone;
    private String website;
    private CountryShowDto country;
    private DateTime careerStartDate;
    private String portfolio;
    private String description;
    private Long profilePicture;
    private DateTime birthDate;
    private DriverDetailsDto driverDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Long profilePicture) {
        this.profilePicture = profilePicture;
    }

    public DriverDetailsDto getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(DriverDetailsDto driverDetails) {
        this.driverDetails = driverDetails;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }
}
