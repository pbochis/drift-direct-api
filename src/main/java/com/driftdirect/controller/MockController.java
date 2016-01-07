package com.driftdirect.controller;

import com.driftdirect.domain.comment.Comment;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.battle.Battle;
import com.driftdirect.domain.round.battle.BattleRound;
import com.driftdirect.domain.round.battle.BattleRoundRun;
import com.driftdirect.domain.round.playoff.PlayoffStage;
import com.driftdirect.domain.round.playoff.PlayoffTree;
import com.driftdirect.domain.round.qualifiers.QualifiedDriver;
import com.driftdirect.dto.comment.CommentCreateDto;
import com.driftdirect.dto.round.playoff.PlayoffBattleRoundDriverJudging;
import com.driftdirect.dto.round.playoff.PlayoffBattleRoundJudging;
import com.driftdirect.dto.round.playoff.graphic.PlayoffTreeGraphicDisplayDto;
import com.driftdirect.repository.CommentRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.playoff.BattleRepository;
import com.driftdirect.service.round.RoundService;
import com.driftdirect.service.round.playoff.PlayoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 1/7/2016.
 */
@RestController
public class MockController {

    List<CommentCreateDto> someComments = new ArrayList<>();
    @Autowired
    private PlayoffService playoffService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private RoundService roundService;
    @Autowired
    private BattleRepository battleRepository;

    private CommentCreateDto createComment(String commentText, boolean positive) {
        Comment comment = new Comment();
        comment.setComment(commentText);
        comment.setPositive(positive);
        comment = commentRepository.save(comment);
        CommentCreateDto dto = new CommentCreateDto();
        dto.setId(comment.getId());
        return dto;
    }

    private void initSomeComments() {
        someComments.add(createComment("Good run", true));
        someComments.add(createComment("Nice slide", true));

        someComments.add(createComment("Weels off trqack", false));
        someComments.add(createComment("Oversteered too much. He cannot drive properly", false));

    }

    @RequestMapping(path = "/mock/playoff/score/{id}/{stage}", method = RequestMethod.GET)
    private void scorePlayoff(@PathVariable(value = "id") Long roundId, @PathVariable(value = "stage") int stageOrder) {
        Round round = roundRepository.findOne(roundId);
        List<Person> judges = round.getChampionship().getJudges().stream().map(e -> e.getJudge()).collect(Collectors.toList());
        PlayoffTree tree = round.getPlayoffTree();
        PlayoffStage selectedStage = null;
        for (PlayoffStage stage : tree.getPlayoffStages()) {
            if (stage.getOrder() == stageOrder) {
                selectedStage = stage;
            }
        }
        List<Long> battleIds = selectedStage.getBattles().stream().map(e -> e.getId()).collect(Collectors.toList());
        for (Long battleId : battleIds) {
            Battle battle = battleRepository.findOne(battleId);

            if (battle.isAutoWin()) {
                continue;
            }
            BattleRound battleRound = battle.getBattleRounds().get(0);
            for (Person judge : judges) {
                playoffService.submitPlayoffJudging(
                        judge,
                        battle.getId(),
                        createJudging(battleRound, battleRound.getFirstRun(), battle.getDriver1(), battle.getDriver2()));
            }

            for (Person judge : judges) {
                playoffService.submitPlayoffJudging(
                        judge,
                        battle.getId(),
                        createJudging(battleRound, battleRound.getSecondRun(), battle.getDriver1(), battle.getDriver2()));
            }
        }
    }

    @RequestMapping(path = "/mock/playoff/{id}", method = RequestMethod.GET)
    private PlayoffTreeGraphicDisplayDto mockPlayoffs(@PathVariable(value = "id") Long roundId) {
        initSomeComments();
        roundService.finishQualifiers(roundId);
        return playoffService.generatePlayoffTree(roundId);
    }

    private PlayoffBattleRoundJudging createJudging(BattleRound round, BattleRoundRun run, QualifiedDriver driver1, QualifiedDriver driver2) {
        PlayoffBattleRoundJudging judging = new PlayoffBattleRoundJudging();

        PlayoffBattleRoundDriverJudging driver1Judging = new PlayoffBattleRoundDriverJudging();
        driver1Judging.setPoints(6);
        driver1Judging.setComments(someComments);
        driver1Judging.setQualifiedDriverId(driver1.getId());

        PlayoffBattleRoundDriverJudging driver2Judging = new PlayoffBattleRoundDriverJudging();
        driver2Judging.setPoints(4);
        driver2Judging.setComments(someComments);
        driver2Judging.setQualifiedDriverId(driver2.getId());

        judging.setDriver1(driver1Judging);
        judging.setDriver2(driver2Judging);
        judging.setRoundId(round.getId());
        judging.setRunId(run.getId());

        return judging;
    }

}
