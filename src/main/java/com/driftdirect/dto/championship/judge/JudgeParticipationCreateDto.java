package com.driftdirect.dto.championship.judge;

import com.driftdirect.domain.championship.judge.JudgeType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 12/17/2015.
 */
public class JudgeParticipationCreateDto {
    //id of the judge(person id)
    @NotNull
    private Long judge;
    private String title;
    @NotNull
    private JudgeType judgeType;

    @Size(min = 1)
    private List<PointsAllocationCreateDto> pointsAllocations = new ArrayList<>();

    public Long getJudge() {
        return judge;
    }

    public void setJudge(Long judge) {
        this.judge = judge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JudgeType getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(JudgeType judgeType) {
        this.judgeType = judgeType;
    }

    public List<PointsAllocationCreateDto> getPointsAllocations() {
        return pointsAllocations;
    }

    public void setPointsAllocations(List<PointsAllocationCreateDto> pointsAllocations) {
        this.pointsAllocations = pointsAllocations;
    }

    public int getMaximumAllocatedPoints() {
        int points = 0;
        for (PointsAllocationCreateDto point : pointsAllocations) {
            points += point.getMaxPoints();
        }
        return points;
    }

    public void addPointsAllocation(PointsAllocationCreateDto pointsAllocationCreateDto) {
        this.pointsAllocations.add(pointsAllocationCreateDto);
    }
}
