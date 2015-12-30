package com.driftdirect.dto.round.qualifier.run;

import com.driftdirect.dto.comment.CommentCreateDto;

import java.util.List;

/**
 * Created by Paul on 12/22/2015.
 */
public class RunJudgingCreateDto {
    private List<AwardedPointsCreateDto> awardedPoints;
    private List<CommentCreateDto> comments;
    private Float entrySpeed;

    public List<AwardedPointsCreateDto> getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(List<AwardedPointsCreateDto> awardedPoints) {
        this.awardedPoints = awardedPoints;
    }

    public List<CommentCreateDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentCreateDto> comments) {
        this.comments = comments;
    }

    public Float getEntrySpeed() {
        return entrySpeed;
    }

    public void setEntrySpeed(Float entrySpeed) {
        this.entrySpeed = entrySpeed;
    }
}
