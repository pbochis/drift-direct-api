package com.driftdirect.dto.round.qualifier;

import com.driftdirect.dto.person.PersonShortShowDto;

/**
 * Created by Paul on 12/20/2015.
 */
public class QualifierShortDto {
    private Long id;
    private PersonShortShowDto driver;
    private Integer points;

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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
