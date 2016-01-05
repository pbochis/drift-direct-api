package com.driftdirect.dto.round.qualifier;

import com.driftdirect.dto.person.PersonShortShowDto;

/**
 * Created by Paul on 1/5/2016.
 */
public class QualifiedDriverDto {
    private Long id;
    private PersonShortShowDto driver;
    private int ranking;

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

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
