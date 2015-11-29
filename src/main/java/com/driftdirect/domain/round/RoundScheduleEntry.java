package com.driftdirect.domain.round;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Paul on 11/17/2015.
 */
@Entity
@Table(name = "round_schedule_entry")
public class RoundScheduleEntry {

    String name;
    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    Round round;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
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
}
