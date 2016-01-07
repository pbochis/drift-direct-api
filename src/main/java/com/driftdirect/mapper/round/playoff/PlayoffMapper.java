package com.driftdirect.mapper.round.playoff;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.battle.Battle;
import com.driftdirect.domain.round.battle.BattleRound;
import com.driftdirect.domain.round.battle.BattleRoundRun;
import com.driftdirect.domain.round.battle.BattleRoundRunDriverJudging;
import com.driftdirect.domain.round.playoff.PlayoffStage;
import com.driftdirect.domain.round.playoff.PlayoffTree;
import com.driftdirect.dto.round.playoff.battle.BattleRoundRunJudgeScores;
import com.driftdirect.dto.round.playoff.battle.PlayoffBattleFullDto;
import com.driftdirect.dto.round.playoff.battle.PlayoffBattleRoundFullDto;
import com.driftdirect.dto.round.playoff.graphic.BattleGraphicDisplayDto;
import com.driftdirect.dto.round.playoff.graphic.PlayoffStageGraphicDisplayDto;
import com.driftdirect.dto.round.playoff.graphic.PlayoffTreeGraphicDisplayDto;
import com.driftdirect.mapper.PersonMapper;
import com.driftdirect.mapper.comment.CommentMapper;
import com.driftdirect.mapper.round.RoundMapper;
import com.driftdirect.mapper.round.qualifier.QualifierMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 1/5/2016.
 */
public class PlayoffMapper {
    public static PlayoffTreeGraphicDisplayDto mapPlayoffForDisplay(PlayoffTree tree) {
        if (tree == null) {
            return null;
        }
        PlayoffTreeGraphicDisplayDto dto = new PlayoffTreeGraphicDisplayDto();
        dto.setId(tree.getId());
        dto.setRoundResults(tree.getRound().getRoundResults().stream().map(RoundMapper::mapRoundResult).collect(Collectors.toList()));
        for (PlayoffStage stage : tree.getPlayoffStages()) {
            dto.addStage(mapStageForDisplay(stage));
        }
        return dto;
    }

    private static PlayoffStageGraphicDisplayDto mapStageForDisplay(PlayoffStage stage) {
        PlayoffStageGraphicDisplayDto dto = new PlayoffStageGraphicDisplayDto();
        dto.setId(stage.getId());
        dto.setOrder(stage.getOrder());
        for (Battle battle : stage.getBattles()) {
            dto.addBattle(mapBattle(battle));
        }
        return dto;
    }

    private static BattleGraphicDisplayDto mapBattle(Battle battle) {
        BattleGraphicDisplayDto dto = new BattleGraphicDisplayDto();
        dto.setId(battle.getId());
        dto.setDriver1(QualifierMapper.mapQualifiedDriver(battle.getDriver1()));
        dto.setDriver2(QualifierMapper.mapQualifiedDriver(battle.getDriver2()));
        dto.setWinner(QualifierMapper.mapQualifiedDriver(battle.getWinner()));
        dto.setOrder(battle.getOrder());
        return dto;
    }

    public static PlayoffBattleFullDto mapBattleFull(Battle battle){
        PlayoffBattleFullDto dto = new PlayoffBattleFullDto();
        dto.setDriver1(QualifierMapper.mapQualifiedDriver(battle.getDriver1()));
        dto.setDriver2(QualifierMapper.mapQualifiedDriver(battle.getDriver2()));
        dto.setWinner(QualifierMapper.mapQualifiedDriver(battle.getWinner()));
        for (BattleRound round: battle.getBattleRounds()){
            dto.addRound(mapBattleRound(round));
        }
        return dto;
    }

    private static PlayoffBattleRoundFullDto mapBattleRound(BattleRound round){
        PlayoffBattleRoundFullDto dto = new PlayoffBattleRoundFullDto();
        List<Person> firstRunJudges = round.getFirstRun().getDriver1().getJudgings().stream().map(e -> e.getJudge()).collect(Collectors.toList());
        for (Person judge: firstRunJudges){
            dto.addFirstRunScore(mapScores(round.getFirstRun(), judge));
        }
        List<Person> secondRunJudges = round.getSecondRun().getDriver1().getJudgings().stream().map(e -> e.getJudge()).collect(Collectors.toList());
        for (Person judge: secondRunJudges){
            dto.addSecondRunScore(mapScores(round.getFirstRun(), judge));
        }
        return dto;
    }

    private static BattleRoundRunJudgeScores mapScores(BattleRoundRun run, Person judge){
        BattleRoundRunJudgeScores dto = new BattleRoundRunJudgeScores();
        BattleRoundRunDriverJudging firstDriverJudging = null;
        for (BattleRoundRunDriverJudging judging: run.getDriver1().getJudgings()){
            if (judging.getJudge().equals(judge)){
                firstDriverJudging = judging;
            }
        }

        BattleRoundRunDriverJudging secondDriverJudging = null;
        for (BattleRoundRunDriverJudging judging: run.getDriver2().getJudgings()){
            if (judging.getJudge().equals(judge)){
                secondDriverJudging = judging;
            }
        }

        dto.setDriver1Score(firstDriverJudging.getPoints());
        dto.setDriver2Score(secondDriverJudging.getPoints());
        dto.setDriver1Comments(firstDriverJudging.getComments().stream().map(CommentMapper::map).collect(Collectors.toList()));
        dto.setDriver2Comments(secondDriverJudging.getComments().stream().map(CommentMapper::map).collect(Collectors.toList()));
        dto.setJudge(PersonMapper.mapShort(judge));
        return dto;
    }
}
