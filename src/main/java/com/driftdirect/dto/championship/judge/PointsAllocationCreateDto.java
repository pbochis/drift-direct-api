package com.driftdirect.dto.championship.judge;

/**
 * Created by Paul on 12/17/2015.
 */
public class PointsAllocationCreateDto {
    private String name;
    private int maxPoints;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }
}
