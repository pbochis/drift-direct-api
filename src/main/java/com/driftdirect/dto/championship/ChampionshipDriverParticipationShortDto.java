package com.driftdirect.dto.championship;

import com.driftdirect.dto.person.PersonShortShowDto;

/**
 * Created by Paul on 12/1/2015.
 */
public class ChampionshipDriverParticipationShortDto {
    private Long id;
    private PersonShortShowDto driver;

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
}
