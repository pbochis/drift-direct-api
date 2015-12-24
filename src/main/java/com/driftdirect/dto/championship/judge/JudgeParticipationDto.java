package com.driftdirect.dto.championship.judge;

import com.driftdirect.dto.person.PersonShortShowDto;

import java.util.List;

/**
 * Created by Paul on 12/1/2015.
 */
public class JudgeParticipationDto {
    private Long id;
    private PersonShortShowDto judge;
    private String judgeType;

    private List<PointsAllocationDto> pointsAllocations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonShortShowDto getJudge() {
        return judge;
    }

    public void setJudge(PersonShortShowDto judge) {
        this.judge = judge;
    }

    public String getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(String judgeType) {
        this.judgeType = judgeType;
    }

    public List<PointsAllocationDto> getPointsAllocations() {
        return pointsAllocations;
    }

    public void setPointsAllocations(List<PointsAllocationDto> pointsAllocations) {
        this.pointsAllocations = pointsAllocations;
    }
}
