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
    private Person person;

    private int roundScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }
}
