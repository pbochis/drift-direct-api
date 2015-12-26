package com.driftdirect.dto.round.qualifier.run;

import com.driftdirect.domain.round.qualifiers.AwardedPoints;
import com.driftdirect.dto.championship.judge.JudgeParticipationDto;
import com.driftdirect.dto.comment.CommentDto;

import java.util.List;

/**
 * Created by Paul on 12/21/2015.
 */
public class RunJudgingDto {
    private Long id;
    private JudgeParticipationDto judgeParticipation;
    private List<AwardedPointsDto> awardedPoints;
    private List<CommentDto> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JudgeParticipationDto getJudgeParticipation() {
        return judgeParticipation;
    }

    public void setJudgeParticipation(JudgeParticipationDto judgeParticipation) {
        this.judgeParticipation = judgeParticipation;
    }

    public List<AwardedPointsDto> getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(List<AwardedPointsDto> awardedPoints) {
        this.awardedPoints = awardedPoints;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
