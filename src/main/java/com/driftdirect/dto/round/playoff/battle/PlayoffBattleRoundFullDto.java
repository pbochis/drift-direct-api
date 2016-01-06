package com.driftdirect.dto.round.playoff.battle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 1/6/2016.
 */
public class PlayoffBattleRoundFullDto {
    private List<BattleRoundRunJudgeScores> firstRunScores = new ArrayList<>();
    private List<BattleRoundRunJudgeScores> secondRunScores = new ArrayList<>();

    public List<BattleRoundRunJudgeScores> getFirstRunScores() {
        return firstRunScores;
    }

    public void setFirstRunScores(List<BattleRoundRunJudgeScores> firstRunScores) {
        this.firstRunScores = firstRunScores;
    }

    public List<BattleRoundRunJudgeScores> getSecondRunScores() {
        return secondRunScores;
    }

    public void setSecondRunScores(List<BattleRoundRunJudgeScores> secondRunScores) {
        this.secondRunScores = secondRunScores;
    }

    public void addFirstRunScore(BattleRoundRunJudgeScores score){
        this.firstRunScores.add(score);
    }

    public void addSecondRunScore(BattleRoundRunJudgeScores score){
        this.secondRunScores.add(score);
    }
}

