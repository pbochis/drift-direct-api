package com.driftdirect.service.round.playoff;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.battle.*;
import com.driftdirect.domain.round.playoff.PlayoffStage;
import com.driftdirect.domain.round.playoff.PlayoffTree;
import com.driftdirect.domain.round.qualifiers.QualifiedDriver;
import com.driftdirect.dto.round.playoff.PlayoffJudgeDto;
import com.driftdirect.dto.round.playoff.graphic.PlayoffTreeGraphicDisplayDto;
import com.driftdirect.mapper.round.playoff.PlayoffMapper;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.playoff.PlayoffStageRepository;
import com.driftdirect.repository.round.playoff.PlayoffTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Paul on 1/5/2016.
 */
@Service
@Transactional
public class PlayoffService {
    private PlayoffTreeRepository playoffTreeRepository;
    private PlayoffStageRepository playoffStageRepository;
    private RoundRepository roundRepository;

    @Autowired
    public PlayoffService(PlayoffTreeRepository playoffTreeRepository,
                          RoundRepository roundRepository,
                          PlayoffStageRepository playoffStageRepository) {
        this.playoffTreeRepository = playoffTreeRepository;
        this.roundRepository = roundRepository;
        this.playoffStageRepository = playoffStageRepository;
    }

    public PlayoffTreeGraphicDisplayDto generatePlayoffTree(Long roundId) {
        Round round = roundRepository.findOne(roundId);
        PlayoffTree tree = new PlayoffTree();
        tree.setRound(round);
        tree = playoffTreeRepository.save(tree);
        tree.addStage(generateFirstStage(round, tree)); // 16 battles
        tree.addStage(generateStage(8, tree));
        tree.addStage(generateStage(4, tree));
        tree.addStage(generateStage(2, tree));
        tree.addStage(generateStage(1, tree));
        return PlayoffMapper.mapPlayoffForDisplay(playoffTreeRepository.save(tree));
    }

    private PlayoffStage generateFirstStage(Round round, PlayoffTree tree) {
        List<QualifiedDriver> qualifiedDrivers = round.getQualifiedDrivers();
        PlayoffStage stage1 = new PlayoffStage();
        stage1.setPlayoffTree(tree);
        stage1 = playoffStageRepository.save(stage1);
        addBattle(stage1, qualifiedDrivers, 1, 9, 24);
        addBattle(stage1, qualifiedDrivers, 2, 1, null);
        addBattle(stage1, qualifiedDrivers, 3, 10, 23);
        addBattle(stage1, qualifiedDrivers, 4, 8, null);
        addBattle(stage1, qualifiedDrivers, 5, 11, 22);
        addBattle(stage1, qualifiedDrivers, 6, 5, null);
        addBattle(stage1, qualifiedDrivers, 7, 12, 21);
        addBattle(stage1, qualifiedDrivers, 8, 4, null);
        addBattle(stage1, qualifiedDrivers, 9, 13, 20);
        addBattle(stage1, qualifiedDrivers, 10, 3, null);
        addBattle(stage1, qualifiedDrivers, 11, 14, 19);
        addBattle(stage1, qualifiedDrivers, 12, 6, null);
        addBattle(stage1, qualifiedDrivers, 13, 15, 18);
        addBattle(stage1, qualifiedDrivers, 14, 7, null);
        addBattle(stage1, qualifiedDrivers, 15, 16, 17);
        addBattle(stage1, qualifiedDrivers, 16, 2, null);
        return stage1;
    }

    private PlayoffStage generateStage(int numberOfBattles, PlayoffTree tree) {
        PlayoffStage stage = new PlayoffStage();
        stage.setPlayoffTree(tree);
        stage = playoffStageRepository.save(stage);
        for (int i = 1; i <= numberOfBattles; i++) {
            addEmptyBattle(stage, i);
        }
        return stage;
    }

    private void addEmptyBattle(PlayoffStage stage, int battleOrder) {
        Battle battle = new Battle();
        battle.setOrder(battleOrder);
        battle.setPlayoffStage(stage);
        stage.addBattle(battle);
    }

    private void addBattle(PlayoffStage stage, List<QualifiedDriver> drivers, int order, int highPlace, Integer lowPlace) {
        Battle battle = new Battle();
        battle.setOrder(order);
        battle.setPlayoffStage(stage);
        QualifiedDriver person1 = drivers.get(highPlace - 1);
        battle.setDriver1(person1);
        if (lowPlace == null) {
            battle.setAutoWin(true);
        } else {
            QualifiedDriver person2 = drivers.get(lowPlace - 1);
            battle.setDriver2(person2);
            battle.addBattleRound(createBattleRound(person1, person2));
        }
        stage.addBattle(battle);
    }

    private BattleRound createBattleRound(QualifiedDriver person1, QualifiedDriver person2) {
        BattleRound battleRound = new BattleRound();
        battleRound.setFirstRun(createBattleRoundRun(person1, person2));
        battleRound.setSecondRun(createBattleRoundRun(person1, person2));
        return battleRound;
    }

    private BattleRoundRun createBattleRoundRun(QualifiedDriver person1, QualifiedDriver person2) {
        BattleRoundRun run = new BattleRoundRun();
        BattleRoundRunDriver driver1 = new BattleRoundRunDriver();
        BattleRoundRunDriver driver2 = new BattleRoundRunDriver();
        driver1.setDriver(person1);
        driver2.setDriver(person2);
        run.setDriver1(driver1);
        run.setDriver2(driver2);
        return run;
    }

    private PlayoffStage findStage(PlayoffTree tree, Long stageId) throws NoSuchElementException {
        for (PlayoffStage stage : tree.getPlayoffStages()) {
            if (stage.getId().equals(stageId)) {
                return stage;
            }
        }
        throw new NoSuchElementException("No such stage!");
    }

    private Battle findBattle(PlayoffStage stage, Long battleId) throws NoSuchElementException {
        for (Battle battle : stage.getBattles()) {
            if (battle.getId().equals(battleId)) {
                return battle;
            }
        }
        throw new NoSuchElementException("No such battle!");
    }

    private BattleRound findBattleRoundToJudge(Person judge, Battle battle) {
//        BattleRound roundToJudge = null;
//        BattleRoundRun runToJudge = null;
//        for (BattleRound round: battle.getBattleRounds()){
//            //remember the last battleRound and battleRoundRun where for battleRoundRun the judge has made no judgings
//            if (checkCanJudgeRun(judge, round.getFirstRun())){
//                roundToJudge = round;
//                runToJudge = round.getFirstRun();
//                break;
//            }
//            if (checkCanJudgeRun(judge, round.getSecondRun())){
//                roundToJudge = round;
//                runToJudge = round.getSecondRun();
//                break;
//            }
//        }
//        if (roundToJudge == null){
//            //
//        }
        return null;
    }

    private boolean checkCanJudgeRun(Person judge, BattleRoundRun battleRoundRun) {
        BattleRoundRunDriver driver = battleRoundRun.getDriver1();
        for (BattleRoundRunDriverJudging judging : driver.getJudgings()) {
            if (judging.getJudge().equals(judge)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkBattleHasFinalResult(Long playoffId, Long stageId, Long battleId) {
        return false;
    }

    private PlayoffJudgeDto startPlayoffJudging(Person judge, Long playoffId, Long stageId, Long battleId) throws NoSuchElementException {
        //need to send comments, round and run number
        if (checkBattleHasFinalResult(playoffId, stageId, battleId)) {
            return null;
        }
        PlayoffTree tree = playoffTreeRepository.findOne(playoffId);
        PlayoffStage stage = findStage(tree, stageId);
        Battle battle = findBattle(stage, battleId);

        BattleRound roundToJudge = null;
        BattleRoundRun runToJudge = null;

        for (BattleRound round : battle.getBattleRounds()) {
            //remember the last battleRound and battleRoundRun where for battleRoundRun the judge has made no judgings
            if (checkCanJudgeRun(judge, round.getFirstRun())) {
                roundToJudge = round;
                runToJudge = round.getFirstRun();
                break;
            }
            if (checkCanJudgeRun(judge, round.getSecondRun())) {
                roundToJudge = round;
                runToJudge = round.getSecondRun();
                break;
            }
        }
        if (roundToJudge == null) {
            //
        }
        PlayoffJudgeDto dto = new PlayoffJudgeDto();
        return dto;
    }
}
