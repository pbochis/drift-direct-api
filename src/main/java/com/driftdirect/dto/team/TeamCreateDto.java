package com.driftdirect.dto.team;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by Paul on 11/26/2015.
 */
public class TeamCreateDto {
    @NotNull
    @Length(min = 3, max = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
