package com.driftdirect.domain.round;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.file.File;
import org.hibernate.annotations.SortNatural;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.SortedSet;

/**
 * Created by Paul on 11/14/2015.
 */
@Entity
public class Round implements Comparable<Round> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String ticketsUrl;

    @Column(name = "start_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startDate;

    @Column(name = "end_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime endDate;

    @OneToOne
    private File logo;

    @ManyToOne
    @JoinColumn(name = "championship_id", nullable = false)
    private Championship championship;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    @SortNatural
    private SortedSet<RoundScheduleEntry> scheduele;

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

    public SortedSet<RoundScheduleEntry> getScheduele() {
        return scheduele;
    }

    public void setScheduele(SortedSet<RoundScheduleEntry> scheduele) {
        this.scheduele = scheduele;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    public String getTicketsUrl() {
        return ticketsUrl;
    }

    public void setTicketsUrl(String ticketsUrl) {
        this.ticketsUrl = ticketsUrl;
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public int compareTo(Round o) {
        if (id == o.getId()) {
            return 0;
        }
        if (startDate == null || o.getStartDate() == null) {
            return 0;
        }
        if (startDate.isBefore(o.getStartDate())) {
            return -1;
        }
        return 1;
    }

    public boolean isEnded() {
        return this.endDate.isBefore(DateTime.now());
    }

    public boolean isOngoing() {
        return this.startDate.isBefore(DateTime.now()) && this.endDate.isAfter(DateTime.now());
    }

    public boolean isFuture() {
        return this.startDate.isAfter(DateTime.now());
    }
}
