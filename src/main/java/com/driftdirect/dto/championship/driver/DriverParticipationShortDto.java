package com.driftdirect.dto.championship.driver;

import com.driftdirect.dto.person.PersonShortShowDto;

/**
 * Created by Paul on 12/1/2015.
 */
public class DriverParticipationShortDto implements Comparable<DriverParticipationShortDto> {
    private Long id;
    private PersonShortShowDto driver;
    private float points;

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

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    @Override
    public int compareTo(DriverParticipationShortDto o) {
        if (this.getPoints() > o.getPoints())
            return 1;
        if (this.getPoints() == o.getPoints())
            return 0;
        return -1;
    }
}
