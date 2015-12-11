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

}
