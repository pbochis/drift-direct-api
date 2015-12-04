package com.driftdirect.domain.round;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.file.File;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Paul on 11/14/2015.
 */
@Entity
public class Round{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

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

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
