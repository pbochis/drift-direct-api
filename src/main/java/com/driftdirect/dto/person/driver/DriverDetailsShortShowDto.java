package com.driftdirect.dto.person.driver;

import com.driftdirect.dto.sponsor.SponsorShowDto;
import com.driftdirect.dto.team.TeamShowDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 12/21/2015.
 */
public class DriverDetailsShortShowDto {
    private Long id;
    private String make;
    private String model;
    private int horsePower;
    private TeamShowDto team;
    private List<SponsorShowDto> sponsors = new ArrayList<>();

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

    public List<SponsorShowDto> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<SponsorShowDto> sponsors) {
        this.sponsors = sponsors;
    }

    public void addSponsor(SponsorShowDto sponsor) {
        this.sponsors.add(sponsor);
    }
}
