package com.driftdirect.dto.round.playoff;

/**
 * Created by Paul on 1/6/2016.
 */
public class PlayoffBattleRoundJudging {
    private Long roundId;
    private Long runId;

    private PlayoffBattleRoundDriverJudging driver1;
    private PlayoffBattleRoundDriverJudging driver2;

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public PlayoffBattleRoundDriverJudging getDriver1() {
        return driver1;
    }

    public void setDriver1(PlayoffBattleRoundDriverJudging driver1) {
        this.driver1 = driver1;
    }

    public PlayoffBattleRoundDriverJudging getDriver2() {
        return driver2;
    }

    public void setDriver2(PlayoffBattleRoundDriverJudging driver2) {
        this.driver2 = driver2;
    }
}
