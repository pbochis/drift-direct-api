package com.driftdirect.domain.championship.driver;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.person.Person;

import javax.persistence.*;

/**
 * Created by Paul on 12/1/2015.
 */
@Entity
@Table(name = "championship_driver_participation")
public class DriverParticipation implements Comparable<DriverParticipation> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person driver;

    @ManyToOne
    private Championship championship;

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "participation")
    private DriverParticipationResults results;

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

    public DriverParticipationResults getResults() {
        return results;
    }

    public void setResults(DriverParticipationResults results) {
        this.results = results;
    }

    @Override
    public int compareTo(DriverParticipation o) {
        if (results == null) {
            return o.getResults() == null ? 0 : -1;
        }
        if (o.getResults() == null) {
            return 1;
        }
        return results.getTotalPoints().compareTo(o.getResults().getTotalPoints());
    }
}
