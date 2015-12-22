package com.driftdirect.domain.round.qualifiers;

import com.driftdirect.domain.championship.judge.PointsAllocation;

import javax.persistence.*;

/**
 * Created by Paul on 12/21/2015.
 */
@Entity
@Table(name = "awarded_points")
public class AwardedPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PointsAllocation allocation;

    private int awardedPoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PointsAllocation getAllocation() {
        return allocation;
    }

    public void setAllocation(PointsAllocation allocation) {
        this.allocation = allocation;
    }

    public int getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(int awardedPoints) {
        this.awardedPoints = awardedPoints;
    }
}
