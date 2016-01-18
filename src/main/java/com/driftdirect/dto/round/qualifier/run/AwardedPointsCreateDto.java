package com.driftdirect.dto.round.qualifier.run;

/**
 * Created by Paul on 12/22/2015.
 */
public class AwardedPointsCreateDto {
    private Long pointsAllocation;
    private float awardedPoints;

    public Long getPointsAllocation() {
        return pointsAllocation;
    }

    public void setPointsAllocation(Long pointsAllocation) {
        this.pointsAllocation = pointsAllocation;
    }

    public float getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(float awardedPoints) {
        this.awardedPoints = awardedPoints;
    }
}
