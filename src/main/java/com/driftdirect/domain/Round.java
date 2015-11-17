package com.driftdirect.domain;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Created by Paul on 11/14/2015.
 */
@Entity
public class Round{

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "championship_id", nullable = false)
    private Championship championship;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoundSchedele> scheduele;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
