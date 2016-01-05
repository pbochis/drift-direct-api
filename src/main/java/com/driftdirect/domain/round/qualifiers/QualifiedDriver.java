package com.driftdirect.domain.round.qualifiers;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;

import javax.persistence.*;

/**
 * Created by Paul on 1/4/2016.
 */
@Entity
@Table(name = "qualified_driver")
public class QualifiedDriver {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person driver;
    private int ranking;

    @ManyToOne
    private Round round;

    public Person getDriver() {
        return driver;
    }

    public void setDriver(Person driver) {
        this.driver = driver;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }
}
