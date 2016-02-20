package com.driftdirect.domain.round;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Paul on 11/17/2015.
 */
@Entity
@Table(name = "round_schedule_entry")
public class RoundScheduleEntry implements Comparable<RoundScheduleEntry> {
    String name;
    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    Round round;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startDate;

    @Column(name = "end_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime endDate;

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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RoundScheduleEntry)) {
            return false;
        }
        return this.getId().equals(((RoundScheduleEntry) obj).getId());
    }

    @Override
    public int compareTo(RoundScheduleEntry o) {
        if (startDate.isBefore(o.getStartDate()))
            return -1;
        return 1;
    }
}

