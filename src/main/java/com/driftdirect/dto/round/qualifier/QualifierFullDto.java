package com.driftdirect.dto.round.qualifier;

import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.round.qualifier.run.RunFullDto;

/**
 * Created by Paul on 12/20/2015.
 */
public class QualifierFullDto {
    private Long id;
    private int finalScore;
    private PersonShortShowDto driver;
    private RunFullDto firstRun;
    private RunFullDto secondRun;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public RunFullDto getFirstRun() {
        return firstRun;
    }

    public void setFirstRun(RunFullDto firstRun) {
        this.firstRun = firstRun;
    }

    public RunFullDto getSecondRun() {
        return secondRun;
    }

    public void setSecondRun(RunFullDto secondRun) {
        this.secondRun = secondRun;
    }

    public PersonShortShowDto getDriver() {
        return driver;
    }

    public void setDriver(PersonShortShowDto driver) {
        this.driver = driver;
    }
}
