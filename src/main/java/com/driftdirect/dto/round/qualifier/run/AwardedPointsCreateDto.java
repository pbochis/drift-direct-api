package com.driftdirect.dto.round.qualifier.run;

/**
 * Created by Paul on 12/22/2015.
 */
public class AwardedPointsCreateDto {
    private Long pointsAllocation;
    private int awardedPoints;

    public Long getPointsAllocation() {
        return pointsAllocation;
    }

    public void setPointsAllocation(Long pointsAllocation) {
        this.pointsAllocation = pointsAllocation;
    }

    public int getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(int awardedPoints) {
        this.awardedPoints = awardedPoints;
    }
}
