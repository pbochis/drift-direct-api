package com.driftdirect.dto.person;

import com.driftdirect.dto.country.CountryShowDto;

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
    private int yearsExperience;
    private String description;
    private Long profilePicture;
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

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
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
}
