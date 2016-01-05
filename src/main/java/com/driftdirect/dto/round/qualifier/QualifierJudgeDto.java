package com.driftdirect.dto.round.qualifier;

import com.driftdirect.dto.championship.judge.JudgeParticipationDto;
import com.driftdirect.dto.comment.CommentDto;
import com.driftdirect.dto.person.PersonShortShowDto;

import java.util.List;

/**
 * Created by Paul on 12/28/2015.
 */
public class QualifierJudgeDto {
    private Long id;
    private Long runId;
    private int runNumber;
    private Long roundId;
    private PersonShortShowDto driver;
    private JudgeParticipationDto judge;

    private List<CommentDto> availableComments;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonShortShowDto getDriver() {
        return driver;
    }

    public void setDriver(PersonShortShowDto driver) {
        this.driver = driver;
    }

    public JudgeParticipationDto getJudge() {
        return judge;
    }

    public void setJudge(JudgeParticipationDto judge) {
        this.judge = judge;
    }

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
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

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }
}
