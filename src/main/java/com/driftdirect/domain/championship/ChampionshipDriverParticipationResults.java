package com.driftdirect.domain.championship;

import javax.persistence.*;

/**
 * Created by Paul on 12/1/2015.
 */
@Entity
@Table(name = "championship_driver_participation_results")
public class ChampionshipDriverParticipationResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rank;
    private int totalPoints;

    @OneToOne
    private ChampionshipDriverParticipation participation;

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

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public ChampionshipDriverParticipation getParticipation() {
        return participation;
    }

    public void setParticipation(ChampionshipDriverParticipation participation) {
        this.participation = participation;
    }
}
