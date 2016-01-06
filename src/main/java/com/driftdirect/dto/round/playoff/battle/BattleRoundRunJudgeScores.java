package com.driftdirect.dto.round.playoff.battle;

import com.driftdirect.domain.comment.Comment;
import com.driftdirect.dto.comment.CommentDto;
import com.driftdirect.dto.person.PersonShortShowDto;

import java.util.List;

/**
 * Created by Paul on 1/6/2016.
 */
public class BattleRoundRunJudgeScores {
    private PersonShortShowDto judge;

    private int driver1Score;
    private List<CommentDto> driver1Comments;

    private int driver2Score;
    private List<CommentDto> driver2Comments;

    public PersonShortShowDto getJudge() {
        return judge;
    }

    public void setJudge(PersonShortShowDto judge) {
        this.judge = judge;
    }

    public int getDriver1Score() {
        return driver1Score;
    }

    public void setDriver1Score(int driver1Score) {
        this.driver1Score = driver1Score;
    }

    public List<CommentDto> getDriver1Comments() {
        return driver1Comments;
    }

    public void setDriver1Comments(List<CommentDto> driver1Comments) {
        this.driver1Comments = driver1Comments;
    }

    public int getDriver2Score() {
        return driver2Score;
    }

    public void setDriver2Score(int driver2Score) {
        this.driver2Score = driver2Score;
    }

    public List<CommentDto> getDriver2Comments() {
        return driver2Comments;
    }

    public void setDriver2Comments(List<CommentDto> driver2Comments) {
        this.driver2Comments = driver2Comments;
    }
}
