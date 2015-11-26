package com.driftdirect.dto.person;

/**
 * Created by Paul on 11/25/2015.
 */
public class PersonUpdateDto extends PersonCreateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String telephone;

    private Long country;

    private int yearsExperience;
    private String description;

    private String personType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getTelephone() {
        return telephone;
    }

    @Override
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public Long getCountry() {
        return country;
    }

    @Override
    public void setCountry(Long country) {
        this.country = country;
    }

    @Override
    public int getYearsExperience() {
        return yearsExperience;
    }

    @Override
    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getPersonType() {
        return personType;
    }

    @Override
    public void setPersonType(String personType) {
        this.personType = personType;
    }
}
