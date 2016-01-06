package com.driftdirect.dto.round.playoff;

import com.driftdirect.dto.comment.CommentCreateDto;

import java.util.List;

/**
 * Created by Paul on 1/6/2016.
 */
public class PlayoffBattleRoundDriverJudging {
    private Long qualifiedDriverId;
    private int points;
    private List<CommentCreateDto> comments;

    public List<CommentCreateDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentCreateDto> comments) {
        this.comments = comments;
    }

    public Long getQualifiedDriverId() {
        return qualifiedDriverId;
    }

    public void setQualifiedDriverId(Long qualifiedDriverId) {
        this.qualifiedDriverId = qualifiedDriverId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
