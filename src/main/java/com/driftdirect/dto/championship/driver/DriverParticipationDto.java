package com.driftdirect.dto.championship.driver;

import com.driftdirect.dto.person.PersonFullDto;

/**
 * Created by Paul on 11/30/2015.
 */
public class DriverParticipationDto {
    private Long id;
    private PersonFullDto driver;
    private DriverParticipationResultsDto results;

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

    public DriverParticipationResultsDto getResults() {
        return results;
    }

    public void setResults(DriverParticipationResultsDto results) {
        this.results = results;
    }
}
