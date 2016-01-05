package com.driftdirect.dto.round.playoff;

import com.driftdirect.dto.comment.CommentDto;

import java.util.List;

/**
 * Created by Paul on 1/5/2016.
 */
public class PlayoffJudgeDto {
    private PlayoffBattleDriverToJudgeDto driver1;
    private PlayoffBattleDriverToJudgeDto driver2;
    private Long battleId;
    private Long runId;
    private Long battleRoundId;
    private int runNumber;

    private List<CommentDto> availableComments;

    public Long getBattleId() {
        return battleId;
    }

    public void setBattleId(Long battleId) {
        this.battleId = battleId;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(int runNumber) {
        this.runNumber = runNumber;
    }

    public List<CommentDto> getAvailableComments() {
        return availableComments;
    }

    public void setAvailableComments(List<CommentDto> availableComments) {
        this.availableComments = availableComments;
    }

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public Long getBattleRoundId() {
        return battleRoundId;
    }

    public void setBattleRoundId(Long battleRoundId) {
        this.battleRoundId = battleRoundId;
    }

    public PlayoffBattleDriverToJudgeDto getDriver1() {
        return driver1;
    }

    public void setDriver1(PlayoffBattleDriverToJudgeDto driver1) {
        this.driver1 = driver1;
    }

    public PlayoffBattleDriverToJudgeDto getDriver2() {
        return driver2;
    }

    public void setDriver2(PlayoffBattleDriverToJudgeDto driver2) {
        this.driver2 = driver2;
    }
}
