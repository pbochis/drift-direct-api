package com.driftdirect.dto.round.qualifier;

import com.driftdirect.dto.person.PersonShortShowDto;

/**
 * Created by Paul on 12/20/2015.
 */
public class QualifierShortDto {
    private Long id;
    private PersonShortShowDto driver;
    private Float points;

    private Float firstRunScore;
    private Float secondRunScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonShortShowDto getDriver() {
        return driver;
    }

    public void setDriver(PersonShortShowDto driver) {
        this.driver = driver;
    }

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }

    public Float getFirstRunScore() {
        return firstRunScore;
    }

    public void setFirstRunScore(Float firstRunScore) {
        this.firstRunScore = firstRunScore;
    }

    public Float getSecondRunScore() {
        return secondRunScore;
    }

    public void setSecondRunScore(Float secondRunScore) {
        this.secondRunScore = secondRunScore;
    }
}
