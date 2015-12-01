package com.driftdirect.dto.championship;

import com.driftdirect.dto.person.PersonShortShowDto;

/**
 * Created by Paul on 11/30/2015.
 */
public class ChampionshipDriverParticipation {
    private PersonShortShowDto driver;
    private Long championshipId;

    public PersonShortShowDto getDriver() {
        return driver;
    }

    public void setDriver(PersonShortShowDto driver) {
        this.driver = driver;
    }

    public Long getChampionshipId() {
        return championshipId;
    }

    public void setChampionshipId(Long championshipId) {
        this.championshipId = championshipId;
    }
}
