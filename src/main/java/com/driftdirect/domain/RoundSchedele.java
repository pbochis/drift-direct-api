package com.driftdirect.domain;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by Paul on 11/17/2015.
 */
@Entity
public class RoundSchedele {

    @Id
    @GeneratedValue
    private long id;

    String name;
    ZonedDateTime startDate;
    ZonedDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    Round round;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
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
}
