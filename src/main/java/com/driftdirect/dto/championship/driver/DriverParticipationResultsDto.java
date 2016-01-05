package com.driftdirect.dto.championship.driver;

/**
 * Created by Paul on 12/1/2015.
 */
public class DriverParticipationResultsDto {
    private Long id;
    private int rank;
    private float totalPoints;

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

    public float getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(float totalPoints) {
        this.totalPoints = totalPoints;
    }
}
