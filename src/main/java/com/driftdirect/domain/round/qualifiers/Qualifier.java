package com.driftdirect.domain.round.qualifiers;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;

import javax.persistence.*;

/**
 * Created by Paul on 12/15/2015.
 */
@Entity
public class Qualifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Person driver;
    @OneToOne
    private Run firstRun;
    @OneToOne
    private Run secondRun;
    private int finalScore;

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getDriver() {
        return driver;
    }

    public void setDriver(Person driver) {
        this.driver = driver;
    }

    public Run getFirstRun() {
        return firstRun;
    }

    public void setFirstRun(Run firstRun) {
        this.firstRun = firstRun;
    }

    public Run getSecondRun() {
        return secondRun;
    }

    public void setSecondRun(Run secondRun) {
        this.secondRun = secondRun;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }
}
