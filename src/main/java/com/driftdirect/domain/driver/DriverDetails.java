package com.driftdirect.domain.driver;

import com.driftdirect.domain.sponsor.Sponsor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Paul on 11/20/2015.
 */
@Entity
@Table(name = "driver_details")
public class DriverDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Car specifications for the driver.
    // Think: maybe it's better to make this List<AttributeValue> attributes
    private String make;
    private String model;
    private String engine;
    private String steeringAngle;
    private String suspensionMods;
    private String wheels;
    private String tires;
    private int horsePower;
    private String other;

    @ManyToOne
    private Team team;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "driver_sponsor",
            joinColumns = {@JoinColumn(name = "driver_id")},
            inverseJoinColumns = {@JoinColumn(name = "sponsor_id")}
    )
    private Set<Sponsor> sponsors = new HashSet<>();

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public Set<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Set<Sponsor> sponsors) {
        this.sponsors.clear();
        this.sponsors.addAll(sponsors);
    }

    public void addSponsor(Sponsor sponsor) {
        sponsors.add(sponsor);
    }
}
