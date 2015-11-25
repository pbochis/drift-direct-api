package com.driftdirect.domain.person;

import com.driftdirect.domain.Country;
import com.driftdirect.domain.driver.DriverDetails;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Paul on 11/20/2015.
 */
@Entity
public class Person {
    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private String telephone;

    @ManyToOne
    private Country country;

    private int yearsExperience;
    private String description;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    //null when the person.personType != PersonType.Driver
    @OneToOne(optional = true)
    private DriverDetails driverDetails;

    public DriverDetails getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(DriverDetails driverDetails) {
        this.driverDetails = driverDetails;
    }

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
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

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

