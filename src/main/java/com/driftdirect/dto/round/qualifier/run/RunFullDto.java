package com.driftdirect.dto.round.qualifier.run;

import java.util.List;

/**
 * Created by Paul on 12/21/2015.
 */
public class RunFullDto {
    private Long id;
    private float entrySpeed;
    private int totalPoints;

    private List<RunJudgingDto> judgings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getEntrySpeed() {
        return entrySpeed;
    }

    public void setEntrySpeed(float entrySpeed) {
        this.entrySpeed = entrySpeed;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public List<RunJudgingDto> getJudgings() {
        return judgings;
    }

    public void setJudgings(List<RunJudgingDto> judgings) {
        this.judgings = judgings;
    }
}
