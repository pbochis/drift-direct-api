package com.driftdirect.dto.person.driver;

import com.driftdirect.dto.team.TeamShowDto;

/**
 * Created by Paul on 12/21/2015.
 */
public class DriverDetailsShortShowDto {
    private Long id;
    private String make;
    private String model;
    private int horsePower;
    private TeamShowDto team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public TeamShowDto getTeam() {
        return team;
    }

    public void setTeam(TeamShowDto team) {
        this.team = team;
    }
}
