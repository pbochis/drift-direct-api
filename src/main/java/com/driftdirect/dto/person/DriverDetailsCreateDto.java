package com.driftdirect.dto.person;

/**
 * Created by Paul on 11/23/2015.
 */
public class DriverDetailsCreateDto {
    private String make;
    private String model;
    private String engine;
    private String steeringAngle;
    private String suspensionMods;
    private String wheels;
    private String tires;
    private String other;
    private Long team;

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

    public Long getTeam() {
        return team;
    }

    public void setTeam(Long team) {
        this.team = team;
    }
}
