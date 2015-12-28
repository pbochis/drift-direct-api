package com.driftdirect.dto.round.qualifier;

import com.driftdirect.dto.championship.judge.JudgeParticipationDto;
import com.driftdirect.dto.championship.judge.JudgeParticipationShowDto;
import com.driftdirect.dto.person.PersonShortShowDto;

/**
 * Created by Paul on 12/28/2015.
 */
public class QualifierJudgeDto {
    private Long id;
    private Long runId;
    private int runNumber;
    private PersonShortShowDto driver;
    private JudgeParticipationDto judge;

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
}
