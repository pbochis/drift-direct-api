package com.driftdirect.domain.round;

import com.driftdirect.domain.person.Person;

import javax.persistence.*;

/**
 * Created by Paul on 1/7/2016.
 */
@Entity
public class RoundDriverResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Round round;

    @ManyToOne
    private Person driver;

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

    public Person getPerson() {
        return driver;
    }

    public float getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(float roundScore) {
        this.roundScore = roundScore;
    }

    public Person getDriver() {
        return driver;
    }

    public void setDriver(Person driver) {
        this.driver = driver;
    }

    public float getQualifierPoints() {
        return qualifierPoints;
    }

    public void setQualifierPoints(float qualifierPoints) {
        this.roundScore += qualifierPoints;
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

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public float getPlayoffPoints() {
        return playoffPoints;
    }

    public void setPlayoffPoints(float playoffPoints) {
        this.roundScore += playoffPoints;
        this.playoffPoints = playoffPoints;
    }
}
