package com.driftdirect.dto.championship.judge;

import com.driftdirect.domain.championship.judge.JudgeType;
import com.driftdirect.dto.person.PersonShortShowDto;

import java.util.List;

/**
 * Created by Paul on 12/1/2015.
 */
public class JudgeParticipationDto {
    private Long id;
    private JudgeType judgeType;
    private String title;
    private PersonShortShowDto judge;

    private List<PointsAllocationDto> pointsAllocations;

    public PersonShortShowDto getJudge() {
        return judge;
    }

    public void setJudge(PersonShortShowDto judge) {
        this.judge = judge;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JudgeType getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(JudgeType judgeType) {
        this.judgeType = judgeType;
    }

    public List<PointsAllocationDto> getPointsAllocations() {
        return pointsAllocations;
    }

    public void setPointsAllocations(List<PointsAllocationDto> pointsAllocations) {
        this.pointsAllocations = pointsAllocations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
