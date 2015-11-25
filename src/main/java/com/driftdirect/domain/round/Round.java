package com.driftdirect.domain.round;

import com.driftdirect.domain.Championship;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "championship_id", nullable = false)
    private Championship championship;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoundScheduleEntry> scheduele;

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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Set<RoundScheduleEntry> getScheduele() {
        return scheduele;
    }

    public void setScheduele(Set<RoundScheduleEntry> scheduele) {
        this.scheduele = scheduele;
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
