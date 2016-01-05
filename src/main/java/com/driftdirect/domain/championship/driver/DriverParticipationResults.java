package com.driftdirect.domain.championship.driver;

import javax.persistence.*;

/**
 * Created by Paul on 12/1/2015.
 */
@Entity
@Table(name = "championship_driver_participation_results")
public class DriverParticipationResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rank;
    private Float totalPoints;

    @OneToOne
    private DriverParticipation participation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Float getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Float totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void addPoints(Float points) {
        if (this.getTotalPoints() == null) {
            this.totalPoints = points;
        } else {
            this.totalPoints += points;
        }
    }

    public DriverParticipation getParticipation() {
        return participation;
    }

    public void setParticipation(DriverParticipation participation) {
        this.participation = participation;
    }
}
