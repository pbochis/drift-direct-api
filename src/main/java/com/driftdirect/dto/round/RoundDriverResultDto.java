package com.driftdirect.dto.round;

import com.driftdirect.dto.person.PersonShortShowDto;

/**
 * Created by Paul on 1/7/2016.
 */
public class RoundDriverResultDto {
    private Long id;
    private PersonShortShowDto driver;

    private float qualifierPoints;
    private int qualifierRanking;

    private int playoffRanking;
    private float playoffPoints;

    private float roundScore;

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

    public float getQualifierPoints() {
        return qualifierPoints;
    }

    public void setQualifierPoints(float qualifierPoints) {
        this.qualifierPoints = qualifierPoints;
    }

    public int getQualifierRanking() {
        return qualifierRanking;
    }

    public void setQualifierRanking(int qualifierRanking) {
        this.qualifierRanking = qualifierRanking;
    }

    public int getPlayoffRanking() {
        return playoffRanking;
    }

    public void setPlayoffRanking(int playoffRanking) {
        this.playoffRanking = playoffRanking;
    }

    public float getPlayoffPoints() {
        return playoffPoints;
    }

    public void setPlayoffPoints(float playoffPoints) {
        this.playoffPoints = playoffPoints;
    }

    public float getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(float roundScore) {
        this.roundScore = roundScore;
    }
}
