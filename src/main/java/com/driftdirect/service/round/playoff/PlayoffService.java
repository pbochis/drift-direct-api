package com.driftdirect.service.round.playoff;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.RoundDriverResult;
import com.driftdirect.domain.round.battle.*;
import com.driftdirect.domain.round.playoff.PlayoffStage;
import com.driftdirect.domain.round.playoff.PlayoffTree;
import com.driftdirect.domain.round.qualifiers.QualifiedDriver;
import com.driftdirect.dto.comment.CommentCreateDto;
import com.driftdirect.dto.round.playoff.PlayoffBattleDriverToJudgeDto;
import com.driftdirect.dto.round.playoff.PlayoffBattleRoundDriverJudging;
import com.driftdirect.dto.round.playoff.PlayoffBattleRoundJudging;
import com.driftdirect.dto.round.playoff.PlayoffJudgeDto;
import com.driftdirect.dto.round.playoff.battle.PlayoffBattleFullDto;
import com.driftdirect.dto.round.playoff.graphic.PlayoffTreeGraphicDisplayDto;
import com.driftdirect.exception.PreviousRunJudgingNotCompletedException;
import com.driftdirect.mapper.comment.CommentMapper;
import com.driftdirect.mapper.round.playoff.PlayoffMapper;
import com.driftdirect.mapper.round.qualifier.QualifierMapper;
import com.driftdirect.repository.round.RoundDriverResultRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.playoff.BattleRepository;
import com.driftdirect.repository.round.playoff.PlayoffStageRepository;
import com.driftdirect.repository.round.playoff.PlayoffTreeRepository;
import com.driftdirect.service.CommentService;
import com.driftdirect.service.championship.driver.DriverParticipationService;
import com.driftdirect.service.round.RoundNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;
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
    private RoundDriverResultRepository roundResultRepository;
    private CommentService commentService;
    private DriverParticipationService driverParticipationService;
    private RoundNotificationService roundNotificationService;

    @Autowired
    public PlayoffService(RoundDriverResultRepository roundResultRepository,
                          PlayoffTreeRepository playoffTreeRepository,
                          RoundRepository roundRepository,
                          PlayoffStageRepository playoffStageRepository,
                          BattleRepository battleRepository,
                          CommentService commentService,
                          DriverParticipationService driverParticipationService,
                          RoundNotificationService roundNotificationService) {
        this.playoffTreeRepository = playoffTreeRepository;
        this.roundRepository = roundRepository;
        this.playoffStageRepository = playoffStageRepository;
        this.battleRepository = battleRepository;
        this.commentService = commentService;
        this.roundResultRepository = roundResultRepository;
        this.driverParticipationService = driverParticipationService;
        this.roundNotificationService = roundNotificationService;
    }

    public PlayoffBattleFullDto findBattle(Long id){
        return PlayoffMapper.mapBattleFull(battleRepository.findOne(id));
    }

    public void delete(Long id){
        PlayoffTree tree = playoffTreeRepository.findOne(id);
        for (PlayoffStage stage: tree.getPlayoffStages()){
            for (Battle battle: stage.getBattles()){
                for (BattleRound battleRound: battle.getBattleRounds()){
                    battleRound.getFirstRun().getDriver1().setDriver(null);
                    battleRound.getFirstRun().getDriver2().setDriver(null);
                    battleRound.getSecondRun().getDriver1().setDriver(null);
                    battleRound.getSecondRun().getDriver2().setDriver(null);
                }
                battle.setDriver1(null);
                battle.setDriver2(null);
                battle.setWinner(null);
            }
        }
        tree.setRound(null);
        playoffTreeRepository.save(tree);
    }

    public PlayoffTreeGraphicDisplayDto generatePlayoffTree(Long roundId) {
        //TODO: Migrate drivers to round 2 if two battles in a row are autowin(cases where drivers are < 24)
        Round round = roundRepository.findOne(roundId);
        PlayoffTree tree = new PlayoffTree();
        tree.setRound(round);
        tree = playoffTreeRepository.save(tree);
        tree.addStage(generateFirstStage(round, tree)); // 16 battles
        tree.addStage(generateStage(8, 2, tree));
        tree.addStage(generateStage(4, 3, tree));
        tree.addStage(generateStage(2, 4, tree));
        tree.addStage(generateFinalsStage(tree));
        updateVoidBattles(tree);
        return PlayoffMapper.mapPlayoffForDisplay(playoffTreeRepository.save(tree));
    }

    private void updateVoidBattles(PlayoffTree tree) {
        //TODO: Fix battle tree to accept less than 16 drivers
        PlayoffStage stage = findStage(tree, 1);
        int battleNumber = stage.getBattles().size();
        for (int i = 1; i <= battleNumber; i++) {
            Battle battle = findBattle(stage, i);
            Battle pairBattle = findPairBattle(battle);
            if (battle.getWinner() != null && pairBattle.getWinner() != null) {
                moveWinnerUp(battle);
            }
        }
    }

    private PlayoffStage findStage(PlayoffTree tree, int order) {
        for (PlayoffStage stage : tree.getPlayoffStages()) {
            if (stage.getOrder() == order) {
                return stage;
            }
        }
        return null;
    }

    private Battle findPairBattle(Battle battle) {
        int pairBattleOrder = -1;
        if (battle.getOrder() % 2 == 1) {
            pairBattleOrder = battle.getOrder() + 1;
        } else {
            pairBattleOrder = battle.getOrder() - 1;
        }
        return findBattle(battle.getPlayoffStage(), pairBattleOrder);
    }

    private Battle findBattle(PlayoffStage stage, int order) {
        for (Battle stageBattle : stage.getBattles()) {
            if (stageBattle.getOrder() == order) {
                return stageBattle;
            }
        }
        return null;
    }

    private PlayoffStage generateFirstStage(Round round, PlayoffTree tree) {
        List<QualifiedDriver> qualifiedDrivers = round.getQualifiedDrivers();
        PlayoffStage stage1 = new PlayoffStage();
        stage1.setPlayoffTree(tree);
        stage1.setOrder(1);
        stage1 = playoffStageRepository.save(stage1);
        addBattle(stage1, qualifiedDrivers, 1, 1, 32);
        addBattle(stage1, qualifiedDrivers, 2, 16, 17);
        addBattle(stage1, qualifiedDrivers, 3, 8, 25);
        addBattle(stage1, qualifiedDrivers, 4, 9, 24);
        addBattle(stage1, qualifiedDrivers, 5, 4, 29);
        addBattle(stage1, qualifiedDrivers, 6, 13, 20);
        addBattle(stage1, qualifiedDrivers, 7, 5, 28);
        addBattle(stage1, qualifiedDrivers, 8, 12, 21);
        addBattle(stage1, qualifiedDrivers, 9, 2, 31);
        addBattle(stage1, qualifiedDrivers, 10, 15, 18);
        addBattle(stage1, qualifiedDrivers, 11, 7, 26);
        addBattle(stage1, qualifiedDrivers, 12, 10, 23);
        addBattle(stage1, qualifiedDrivers, 13, 3, 30);
        addBattle(stage1, qualifiedDrivers, 14, 14, 19);
        addBattle(stage1, qualifiedDrivers, 15, 6, 27);
        addBattle(stage1, qualifiedDrivers, 16, 11, 22);
        return stage1;
    }
    private PlayoffStage generateFinalsStage(PlayoffTree tree) {
        PlayoffStage stage = new PlayoffStage();
        stage.setPlayoffTree(tree);
        stage.setOrder(5);
        stage = playoffStageRepository.save(stage);
        addEmptyBattle(stage, 1);
        addEmptyBattle(stage, 2, true);
        return stage;
    }

    private PlayoffStage generateStage(int numberOfBattles, int order, PlayoffTree tree) {
        PlayoffStage stage = new PlayoffStage();
        stage.setPlayoffTree(tree);
        stage.setOrder(order);
        stage = playoffStageRepository.save(stage);
        for (int i = 1; i <= numberOfBattles; i++) {
            addEmptyBattle(stage, i);
        }
        return stage;
    }
    private void addEmptyBattle(PlayoffStage stage, int battleOrder, boolean grandFinal) {
        Battle battle = new Battle();
        battle.setOrder(battleOrder);
        battle.setPlayoffStage(stage);
        battle.setGrandFinal(true);
        stage.addBattle(battle);
    }

    private void addEmptyBattle(PlayoffStage stage, int battleOrder) {
        Battle battle = new Battle();
        battle.setOrder(battleOrder);
        battle.setPlayoffStage(stage);
        stage.addBattle(battle);
    }

    private void addBattle(PlayoffStage stage, List<QualifiedDriver> drivers, int order, int highPlace, Integer lowPlace) {
        QualifiedDriver driver1 = highPlace > drivers.size() ? null : drivers.get(highPlace - 1);
        QualifiedDriver driver2 = lowPlace > drivers.size() ? null : drivers.get(lowPlace - 1);
        createBattle(stage, order, driver1, driver2);
    }

    private void createBattle(PlayoffStage stage, int order, QualifiedDriver driver1, QualifiedDriver driver2) {
        Battle battle = new Battle();
        battle.setOrder(order);
        battle.setPlayoffStage(stage);
        battle.setDriver1(driver1);
        if (driver2 == null) {
            battle.setAutoWin(true);
            battle.setWinner(driver1);
        } else {
            battle.setDriver2(driver2);
            battle.addBattleRound(createBattleRound(driver1, driver2));
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

    private boolean checkBattleHasFinalResult(Battle battle) {
        return battle.getWinner() != null;
    }

    private void updateCurrentBattleAndNotify(PlayoffTree tree, Battle battle) throws IOException {
        Round round = tree.getRound();
        boolean notify = false;
        if (battle == null){
            if (tree.getCurrentBattle() == null){
                return;
            }else{
                notify = true;
            }
        }else if (!battle.equals(tree.getCurrentBattle())){
            notify = true;
        }
        if (notify){
            tree.setCurrentBattle(battle);
            tree = playoffTreeRepository.save(tree);
            roundNotificationService.notifyCurrentBattleUpdated(round.getId());
        }
    }

    public PlayoffJudgeDto startPlayoffJudging(Person judge, Long battleId) throws NoSuchElementException, PreviousRunJudgingNotCompletedException, IOException {
        Battle battle = battleRepository.findOne(battleId);
        if (battle == null){
            throw new NoSuchElementException("No such battle!");
        }

        if (checkBattleHasFinalResult(battle)) {
            return null;
        }

        updateCurrentBattleAndNotify(battle.getPlayoffStage().getPlayoffTree(), battle);

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
                if (previousRun.getDriver1().getJudgings().size() < 3){
                    throw new PreviousRunJudgingNotCompletedException("Please wait for the other judges to give their scores for the first run.");
                }
                roundToJudge = round;
                runToJudge = round.getSecondRun();
                break;
            }
            previousRun = round.getSecondRun();
        }
        if (roundToJudge == null || runToJudge == null) {
            throw new PreviousRunJudgingNotCompletedException("The results of this battle round have not been concluded yet. Please wait for the other judges to give their scores.");
        }
        PlayoffJudgeDto dto = new PlayoffJudgeDto();
        dto.setBattleId(battleId);
        dto.setBattleRoundId(roundToJudge.getId());
        dto.setRunId(runToJudge.getId());
        dto.setRunNumber(runNumber);
        dto.setAvailableComments(commentService.findAll()
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

    private BattleRound findRound(Battle battle, Long roundId) {
        for (BattleRound round : battle.getBattleRounds()) {
            if (round.getId().equals(roundId)) {
                return round;
            }
        }
        throw new NoSuchElementException("No such playoff round!");
    }

    public void submitPlayoffJudging(Person judge, Long battleId, PlayoffBattleRoundJudging judging) throws NoSuchElementException, IOException {
        Battle battle = battleRepository.findOne(battleId);
        BattleRound round = findRound(battle, judging.getRoundId());
        BattleRoundRun run = null;
        boolean updateScores = true;
        if (round.getFirstRun().getId().equals(judging.getRunId())) {
            run = round.getFirstRun();
            updateScores = false;
        }
        if (round.getSecondRun().getId().equals(judging.getRunId())) {
            updateScores = true;
            run = round.getSecondRun();
        }
        addBattleRunScores(run.getDriver1(), judge, judging.getDriver1());
        addBattleRunScores(run.getDriver2(), judge, judging.getDriver2());
        battle = battleRepository.save(battle);
        if (updateScores) {
            checkAndUpdateFinalScores(battle);
        }
    }

    private void checkAndUpdateFinalScores(Battle battle) throws IOException {
        //Firstly, add the points for each driver;
        BattleRound round = battle.getBattleRounds().get(battle.getBattleRounds().size() - 1);
        boolean canUpdate = round.getFirstRun().getDriver1().getJudgings().size() == 3;
        canUpdate = round.getSecondRun().getDriver1().getJudgings().size() == 3 && canUpdate;
        if (!canUpdate)
            return;
        PlayoffTree tree = battle.getPlayoffStage().getPlayoffTree();
       updateCurrentBattleAndNotify(tree, null);
        int firstDriverTotalScore = round.getFirstRun().getDriver1().getPoints()
                + round.getSecondRun().getDriver1().getPoints();
        int secondDriverTotalScore = round.getFirstRun().getDriver2().getPoints()
                + round.getSecondRun().getDriver2().getPoints();
        boolean moveDriverUp = battle.getPlayoffStage().getPlayoffTree().getPlayoffStages().tailSet(battle.getPlayoffStage()).size() > 0;
        if (firstDriverTotalScore == secondDriverTotalScore) {
            //Then it's a tie and we create a new BattleRound
            BattleRound omt = new BattleRound();
            omt.setFirstRun(createBattleRoundRun(battle.getDriver1(), battle.getDriver2()));
            omt.setSecondRun(createBattleRoundRun(battle.getDriver1(), battle.getDriver2()));
            battle.addBattleRound(omt);
            moveDriverUp = false;
        }
        else if (firstDriverTotalScore > secondDriverTotalScore) {
            battle.setWinner(battle.getDriver1());
        }
        else if (firstDriverTotalScore < secondDriverTotalScore) {
            battle.setWinner(battle.getDriver2());
        }
        if (moveDriverUp)
            moveWinnerUp(battle);
        battle = battleRepository.save(battle);
        if (battle.isGrandFinal()){
            generateFinalResults(battle.getPlayoffStage().getPlayoffTree());
        }
    }

    public void moveWinnerUp(Battle battle) {
        if (battle.getPlayoffStage().getBattles().size() == 1) {
            //the winner of this battle is the winner of the championship. Grats.
            return;
        }
        QualifiedDriver winner = battle.getWinner();
        Battle pairBattle = null;
        int pairBattleOrder;
        if (battle.getOrder() % 2 == 1) {
            pairBattleOrder = battle.getOrder() + 1;
        } else {
            pairBattleOrder = battle.getOrder() - 1;
        }
        for (Battle stageBattle : battle.getPlayoffStage().getBattles()) {
            if (stageBattle.getOrder() == pairBattleOrder) {
                pairBattle = stageBattle;
            }
        }
        // found pair battle
        // if it doesn't have a winner, then the battle from the next stage will be decided when pairBattle will
        // have a winner.
        if (pairBattle.getWinner() == null) {
            return;
        }
        PlayoffStage nextStage = getNextStage(battle.getPlayoffStage());
        // here we check if the next stage is the last one
        // and if it is we use different logic for creating the next stage.
        QualifiedDriver winner1 = battle.getWinner().getRanking() < pairBattle.getWinner().getRanking() ? battle.getWinner() : pairBattle.getWinner();
        QualifiedDriver winner2 = battle.getWinner().getRanking() > pairBattle.getWinner().getRanking() ? battle.getWinner() : pairBattle.getWinner();
        if (nextStage.getOrder() == 5) {
            Battle grandFinal = nextStage.getBattles().last(); // order of grand final is 2;
            grandFinal.setDriver1(winner1);
            grandFinal.setDriver2(winner2);
            grandFinal.addBattleRound(createBattleRound(winner1, winner2));
            battleRepository.save(grandFinal);

            QualifiedDriver loser1 = battle.getLoser().getRanking() < pairBattle.getLoser().getRanking() ? battle.getLoser() : pairBattle.getLoser();
            QualifiedDriver loser2 = battle.getLoser().getRanking() > pairBattle.getLoser().getRanking() ? battle.getLoser() : pairBattle.getLoser();
            Battle smallFinal = nextStage.getBattles().first(); // order of 3rd-4th place match is 1
            smallFinal.setDriver1(loser1);
            smallFinal.setDriver2(loser2);
            smallFinal.addBattleRound(createBattleRound(loser1, loser2));
            battleRepository.save(smallFinal);
        }else {
            Battle newBattle = null;
            int nextBattleOrder = Math.max(pairBattleOrder, battle.getOrder()) / 2;
            for (Battle nextStageBattle : nextStage.getBattles()) {
                if (nextStageBattle.getOrder() == nextBattleOrder) {
                    newBattle = nextStageBattle;
                }
            }
            if (newBattle.getDriver1() != null && newBattle.getDriver2() != null) {
                //that means this has already been generated
                return;
            }
            newBattle.setDriver1(winner1);
            newBattle.setDriver2(winner2);
            newBattle.addBattleRound(createBattleRound(winner1, winner2));
            newBattle = battleRepository.save(newBattle);
        }
        playoffTreeRepository.save(nextStage.getPlayoffTree());
    }

    private PlayoffStage getNextStage(PlayoffStage stage) {
        SortedSet<PlayoffStage> stages = stage.getPlayoffTree().getPlayoffStages();
        for (PlayoffStage nextStage : stages) {
            if (stage.getBattles().size() == nextStage.getBattles().size() * 2
                || (stage.getBattles().size() == nextStage.getBattles().size() && !stage.getId().equals(nextStage.getId()))) {
                return nextStage;
            }
        }
        return null;
    }


    private void addBattleRunScores(BattleRoundRunDriver driver, Person judge, PlayoffBattleRoundDriverJudging driverJudging) throws AccessDeniedException {
        for (BattleRoundRunDriverJudging existingJudging : driver.getJudgings()) {
            if (existingJudging.getJudge().equals(judge)) {
                throw new AccessDeniedException("You have already judged this battle run!");
            }
        }
        //not here
//        driver.addPoints(driverJudging.getPoints());
        BattleRoundRunDriverJudging newJudging = new BattleRoundRunDriverJudging();
        newJudging.setJudge(judge);
        newJudging.setPoints(driverJudging.getPoints());
        for (CommentCreateDto comment : driverJudging.getComments()) {
            newJudging.addComment(commentService.findOrCreate(comment));
        }
        driver.addJudging(newJudging);
        if (driver.getJudgings().size() == 3) {
            //update final points now that all judges submited
            int points = 0;
            for (BattleRoundRunDriverJudging judging : driver.getJudgings()) {
                points += judging.getPoints();
            }
            driver.setPoints(points);
        }
    }

    private void generateFinalResults(PlayoffTree tree) {
        Round round = tree.getRound();
        PlayoffStage finalsStage = tree.getPlayoffStages().last();
        updateRoundResult(round, finalsStage.getBattles().last().getWinner().getDriver(), 1);
        updateRoundResult(round, finalsStage.getBattles().last().getLoser().getDriver(), 2);
        updateRoundResult(round, finalsStage.getBattles().first().getWinner().getDriver(), 3);
        updateRoundResult(round, finalsStage.getBattles().first().getLoser().getDriver(), 4);
        for (PlayoffStage stage : tree.getPlayoffStages()) {
            if (stage.getBattles().size() >= 4) {
                for (Battle battle : stage.getBattles()) {
                    if (battle.getLoser() != null) {
                        updateRoundResult(round, battle.getLoser().getDriver(), stage.getBattles().size() + 1);
                    }
                }
            }
        }
    }

    private void updateRoundResult(Round round, Person driver, int playoffRanking) {
        RoundDriverResult result = roundResultRepository.findByRoundAndDriver(round, driver);
        result.setPlayoffRanking(playoffRanking);
        result.setPlayoffPoints(getPointsForRanking(playoffRanking));
        result = roundResultRepository.save(result);
        driverParticipationService.addResult(round.getChampionship(), result);
    }

    public float getPointsForRanking(int ranking) {
        if (ranking == 1)
            return 100;
        if (ranking == 2)
            return 80;
        if (ranking == 3)
            return 68;
        if (ranking == 4)
            return 60;
        if (ranking >= 5 && ranking <= 8)
            return 48;
        if (ranking >= 9 && ranking <= 16)
            return 32;
        return 16;
    }

    // That means that this judge submitted scores for all the battle rounds already created.
    // We will create a new BattleRound.
    // If there are already 4 battles for which this judge has submitted scores fully,
    // then this run is a sudden death run.
    // The BattleRound is created after scores are calculated.
}
