package com.driftdirect.service.round.playoff;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.battle.*;
import com.driftdirect.domain.round.playoff.PlayoffStage;
import com.driftdirect.domain.round.playoff.PlayoffTree;
import com.driftdirect.domain.round.qualifiers.QualifiedDriver;
import com.driftdirect.dto.round.playoff.PlayoffBattleDriverToJudgeDto;
import com.driftdirect.dto.round.playoff.PlayoffJudgeDto;
import com.driftdirect.dto.round.playoff.graphic.PlayoffTreeGraphicDisplayDto;
import com.driftdirect.mapper.comment.CommentMapper;
import com.driftdirect.mapper.round.playoff.PlayoffMapper;
import com.driftdirect.mapper.round.qualifier.QualifierMapper;
import com.driftdirect.repository.CommentRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.playoff.BattleRepository;
import com.driftdirect.repository.round.playoff.PlayoffStageRepository;
import com.driftdirect.repository.round.playoff.PlayoffTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Created by Paul on 1/5/2016.
 */
@Service
@Transactional
public class PlayoffService {
    private PlayoffTreeRepository playoffTreeRepository;
    private PlayoffStageRepository playoffStageRepository;
    private BattleRepository battleRepository;
    private RoundRepository roundRepository;
    private CommentRepository commentRepository;
    @Autowired
    public PlayoffService(PlayoffTreeRepository playoffTreeRepository,
                          RoundRepository roundRepository,
                          PlayoffStageRepository playoffStageRepository,
                          BattleRepository battleRepository,
                          CommentRepository commentRepository) {
        this.playoffTreeRepository = playoffTreeRepository;
        this.roundRepository = roundRepository;
        this.playoffStageRepository = playoffStageRepository;
        this.battleRepository = battleRepository;
        this.commentRepository = commentRepository;
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
                return false;
            }
        }
        return true;
    }

    private boolean checkBattleHasFinalResult(Long battleId) {
        return false;
    }

    public PlayoffJudgeDto startPlayoffJudging(Person judge, Long battleId) throws NoSuchElementException {
        if (checkBattleHasFinalResult(battleId)) {
            return null;
        }
        Battle battle = battleRepository.findOne(battleId);
        if (battle == null){
            throw new NoSuchElementException("No such battle!");
        }

        BattleRound roundToJudge = null;
        BattleRoundRun runToJudge = null;
        int runNumber = 0;
        BattleRoundRun previousRun = null;
        for (BattleRound round : battle.getBattleRounds()) {
            //remember the last battleRound and battleRoundRun where for battleRoundRun the judge has made no judgings
            runNumber++;
            if (checkCanJudgeRun(judge, round.getFirstRun())) {
                roundToJudge = round;
                runToJudge = round.getFirstRun();
                break;
            }
            previousRun = round.getFirstRun();
            runNumber++;
            if (checkCanJudgeRun(judge, round.getSecondRun())) {
                roundToJudge = round;
                runToJudge = round.getSecondRun();
                break;
            }
            previousRun = round.getSecondRun();
        }
        if (roundToJudge == null || runToJudge == null) {
            throw new NoSuchElementException("Nothing to judge");
        }
        PlayoffJudgeDto dto = new PlayoffJudgeDto();
        dto.setBattleId(battleId);
        dto.setBattleRoundId(roundToJudge.getId());
        dto.setRunId(runToJudge.getId());
        dto.setRunNumber(runNumber);
        dto.setAvailableComments(commentRepository.findAll()
                                    .stream()
                                    .map(CommentMapper::map)
                                    .collect(Collectors.toList()));
        PlayoffBattleDriverToJudgeDto driver1 = new PlayoffBattleDriverToJudgeDto();
        driver1.setLead(runNumber % 2 != 0);
        driver1.setAdvantage(previousRun != null && previousRun.getDriver1().getPoints() > previousRun.getDriver2().getPoints());
        driver1.setDriver(QualifierMapper.mapQualifiedDriver(battle.getDriver1()));

        PlayoffBattleDriverToJudgeDto driver2 = new PlayoffBattleDriverToJudgeDto();
        driver2.setLead(runNumber % 2 == 0);
        driver2.setAdvantage(previousRun != null && previousRun.getDriver2().getPoints() > previousRun.getDriver1().getPoints());
        driver2.setDriver(QualifierMapper.mapQualifiedDriver(battle.getDriver2()));

        dto.setDriver1(driver1);
        dto.setDriver2(driver2);
        return dto;
    }

    // That means that this judge submitted scores for all the battle rounds already created.
    // We will create a new BattleRound.
    // If there are already 4 battles for which this judge has submitted scores fully,
    // then this run is a sudden death run.
    // The BattleRound is created after scores are calculated.
}
