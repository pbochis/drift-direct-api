package com.driftdirect.dto.person.driver;

import com.driftdirect.dto.sponsor.SponsorShowDto;
import com.driftdirect.dto.team.TeamShowDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 11/30/2015.
 */
public class DriverDetailsDto {
    private long id;
    private String make;
    private String model;
    private String engine;
    private String steeringAngle;
    private String suspensionMods;
    private String wheels;
    private String tires;
    private String other;
    private int horsePower;
    private TeamShowDto team;
    private List<SponsorShowDto> sponsors = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getSteeringAngle() {
        return steeringAngle;
    }

    public void setSteeringAngle(String steeringAngle) {
        this.steeringAngle = steeringAngle;
    }

    public String getSuspensionMods() {
        return suspensionMods;
    }

    public void setSuspensionMods(String suspensionMods) {
        this.suspensionMods = suspensionMods;
    }

    public String getWheels() {
        return wheels;
    }

    public void setWheels(String wheels) {
        this.wheels = wheels;
    }

    public String getTires() {
        return tires;
    }

    public void setTires(String tires) {
        this.tires = tires;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public TeamShowDto getTeam() {
        return team;
    }

    public void setTeam(TeamShowDto team) {
        this.team = team;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public List<SponsorShowDto> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<SponsorShowDto> sponsors) {
        this.sponsors = sponsors;
    }

    public void addSponsor(SponsorShowDto sponsorShowDto) {
        this.sponsors.add(sponsorShowDto);
    }
}
