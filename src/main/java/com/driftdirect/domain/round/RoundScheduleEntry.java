package com.driftdirect.domain.round;

import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Paul on 11/17/2015.
 */
@Entity
@Table(name = "round_schedule_entry")
public class RoundScheduleEntry {

    String name;
    DateTime startDate;
    DateTime endDate;
    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    Round round;
    @Id
    @GeneratedValue
    private long id;

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

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }
}
