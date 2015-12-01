package com.driftdirect.domain.championship;

import com.driftdirect.domain.person.Person;

import javax.persistence.*;

/**
 * Created by Paul on 12/1/2015.
 */
@Entity
@Table(name = "championship_driver_participation")
public class ChampionshipDriverParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person driver;

    @ManyToOne
    private Championship championship;

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "participation")
    private ChampionshipDriverParticipationResults results;

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

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    public ChampionshipDriverParticipationResults getResults() {
        return results;
    }

    public void setResults(ChampionshipDriverParticipationResults results) {
        this.results = results;
    }
}
