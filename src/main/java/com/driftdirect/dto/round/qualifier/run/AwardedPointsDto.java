package com.driftdirect.dto.round.qualifier.run;

import com.driftdirect.dto.championship.judge.PointsAllocationDto;

/**
 * Created by Paul on 12/21/2015.
 */
public class AwardedPointsDto {
    private Long id;
    private PointsAllocationDto pointsAllocation;
    private float awardedPoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PointsAllocationDto getPointsAllocation() {
        return pointsAllocation;
    }

    public void setPointsAllocation(PointsAllocationDto pointsAllocation) {
        this.pointsAllocation = pointsAllocation;
    }

    public float getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(float awardedPoints) {
        this.awardedPoints = awardedPoints;
    }
}
