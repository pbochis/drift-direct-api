package com.driftdirect.dto.championship;

import com.driftdirect.dto.person.PersonFullDto;

/**
 * Created by Paul on 11/30/2015.
 */
public class ChampionshipDriverParticipationDto {
    private Long id;
    private PersonFullDto driver;
    private ChampionshipDriverParticipationResultsDto results;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonFullDto getDriver() {
        return driver;
    }

    public void setDriver(PersonFullDto driver) {
        this.driver = driver;
    }

    public ChampionshipDriverParticipationResultsDto getResults() {
        return results;
    }

    public void setResults(ChampionshipDriverParticipationResultsDto results) {
        this.results = results;
    }
}
