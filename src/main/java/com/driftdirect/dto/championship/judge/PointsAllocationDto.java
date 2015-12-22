package com.driftdirect.dto.championship.judge;

/**
 * Created by Paul on 12/21/2015.
 */
public class PointsAllocationDto {
    private Long id;
    private String name;
    private int maxPoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
