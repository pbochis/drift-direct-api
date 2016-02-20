package com.driftdirect.service.round.qualifier;

import com.driftdirect.domain.championship.judge.JudgeParticipation;
import com.driftdirect.domain.championship.judge.JudgeType;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.qualifiers.AwardedPoints;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.domain.round.qualifiers.Run;
import com.driftdirect.domain.round.qualifiers.RunJudging;
import com.driftdirect.dto.comment.CommentCreateDto;
import com.driftdirect.dto.round.qualifier.QualifierFullDto;
import com.driftdirect.dto.round.qualifier.QualifierJudgeDto;
import com.driftdirect.dto.round.qualifier.run.AwardedPointsCreateDto;
import com.driftdirect.dto.round.qualifier.run.RunJudgingCreateDto;
import com.driftdirect.exception.PreviousRunJudgingNotCompletedException;
import com.driftdirect.exception.QualifierAlreadyJudgeException;
import com.driftdirect.mapper.round.qualifier.QualifierMapper;
import com.driftdirect.repository.PersonRepository;
import com.driftdirect.repository.championship.judge.PointsAllocationRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.qualifier.QualifierRepository;
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

/**
 * Created by Paul on 12/19/2015.
 */
@Service
@Transactional
public class QualifierService {
    private PersonRepository personRepository;
    private RoundRepository roundRepository;
    private QualifierRepository qualifierRepository;
    private DriverParticipationService driverParticipationService;
    private PointsAllocationRepository pointsAllocationRepository;
    private CommentService commentService;
    private RoundNotificationService roundNotificationService;

    @Autowired
    public QualifierService(PersonRepository personRepository,
                            RoundRepository roundRepository,
                            QualifierRepository qualifierRepository,
                            DriverParticipationService driverParticipationService,
                            PointsAllocationRepository pointsAllocationRepository,
                            CommentService commentService,
                            RoundNotificationService roundNotificationService) {
        this.personRepository = personRepository;
        this.roundRepository = roundRepository;
        this.qualifierRepository = qualifierRepository;
        this.driverParticipationService = driverParticipationService;
        this.pointsAllocationRepository = pointsAllocationRepository;
        this.commentService = commentService;
        this.roundNotificationService = roundNotificationService;
    }

    public void registerDrivers(Long roundId, List<Long> drivers) {
        Round round = roundRepository.findOne(roundId);
        for (Long driverId : drivers) {
            Person driver = personRepository.findOne(driverId);
            Qualifier qualifier = new Qualifier();
            qualifier.setDriver(driver);
            qualifier.setRound(round);
            qualifier.setFirstRun(new Run());
            qualifier.setSecondRun(new Run());
            qualifierRepository.save(qualifier);
            driverParticipationService.addDriverParticipation(round.getChampionship(), driver);
        }
    }

    public Qualifier registerDriver(Long roundId, Long driverId) {
        Round round = roundRepository.findOne(roundId);
        Person driver = personRepository.findOne(driverId);
        Qualifier qualifier = new Qualifier();
        qualifier.setDriver(driver);
        qualifier.setRound(round);
        qualifier.setFirstRun(new Run());
        qualifier.setSecondRun(new Run());
        qualifier = qualifierRepository.save(qualifier);
        driverParticipationService.addDriverParticipation(round.getChampionship(), driver);
        return qualifier;
    }

    public void deleteQualifier(Long qualifierId) throws QualifierAlreadyJudgeException {
        Qualifier qualifier = qualifierRepository.findOne(qualifierId);
        if (qualifier == null)
            return;
        if (qualifier.getFirstRun().getJudgings().size() > 0) {
            throw new QualifierAlreadyJudgeException("This qualifier is already being judged");
        }
        if (qualifier.getSecondRun().getJudgings().size() > 0) {
            throw new QualifierAlreadyJudgeException("This qualifier is already being judged");
        }
        qualifierRepository.delete(qualifier);
    }

    public Qualifier registerDriver(Long roundId, Long driverId, int order) {
        Round round = roundRepository.findOne(roundId);
        Person driver = personRepository.findOne(driverId);
        Qualifier qualifier = new Qualifier();
        qualifier.setDriver(driver);
        qualifier.setQualifierOrder(order);
        qualifier.setRound(round);
        qualifier.setFirstRun(new Run());
        qualifier.setSecondRun(new Run());
        qualifier = qualifierRepository.save(qualifier);
        driverParticipationService.addDriverParticipation(round.getChampionship(), driver);
        return qualifier;
    }

    private JudgeParticipation findJudgeParticipation(Qualifier qualifier, Person judge) throws AccessDeniedException {
        JudgeParticipation participation = null;
        for (JudgeParticipation jp : qualifier.getRound().getChampionship().getJudges()) {
            if (jp.getJudge().getId().equals(judge.getId())) {
                participation = jp;
            }
        }
        if (participation == null) {
            //This really should not happen. If happens investigate fully.
            throw new AccessDeniedException("You cannot submit a judging for this qualifier!");
        }
        return participation;
    }

    private Run findRun(Qualifier qualifier, Long runId) throws NoSuchElementException {
        Run run = null;
        if (qualifier.getFirstRun().getId().equals(runId)) {
            run = qualifier.getFirstRun();
        }
        if (qualifier.getSecondRun().getId().equals(runId)) {
            run = qualifier.getSecondRun();
        }
        if (run == null) {
            throw new NoSuchElementException();
        }
        return run;
    }

    private void updateCurrentDriver(Qualifier qualifier) throws IOException {
        Round round = qualifier.getRound();
        Qualifier currentDriver = round.getCurrentDriver();
        if (currentDriver == null || !currentDriver.equals(qualifier)) {
            round.setCurrentDriver(qualifier);
            roundRepository.save(round);
            roundNotificationService.notifyCurrentDriverUpdated(round.getId());
        }
    }

    public QualifierJudgeDto startQualifierJudging(Long qualifierId, Person judge) throws AccessDeniedException, PreviousRunJudgingNotCompletedException, IOException {
        Qualifier qualifier = qualifierRepository.findOne(qualifierId);
        updateCurrentDriver(qualifier);
        JudgeParticipation participation = findJudgeParticipation(qualifier, judge);
        if (participation == null){
            throw new AccessDeniedException("You cannot judge at this qualifier");
        }
        return QualifierMapper.mapForJudge(qualifier, findRunForJudge(qualifier, judge), participation, commentService.findAll());
    }

    private Run findRunForJudge(Qualifier qualifier, Person judge) throws PreviousRunJudgingNotCompletedException {
        Run run = qualifier.getFirstRun();
        for (RunJudging judging: run.getJudgings()){
            if (judging.getJudge().getJudge().equals(judge)){
                run = null;
            }
        }
        if (run != null){
            return run;
        }
        run = qualifier.getSecondRun();
        for (RunJudging judging: run.getJudgings()){
            if (judging.getJudge().getJudge().equals(judge)){
                run = null;
            }
        }
        if (run != null && qualifier.getFirstRun().getJudgings().size() < 3){
            throw new PreviousRunJudgingNotCompletedException("Please wait for the other judges to give their scores for the first run.");
        }
        return run;
    }

    public void submitRunJudging(Long qualifierId, Long runId, RunJudgingCreateDto runJudgingDto, Person judge) throws AccessDeniedException, NoSuchElementException {
        //TODO: continue this with more details(such as entry speed and judge role separation)
        Qualifier qualifier = qualifierRepository.findOne(qualifierId);
        Run run = findRun(qualifier, runId);
        JudgeParticipation participation = findJudgeParticipation(qualifier, judge);
        for (RunJudging existingJudging : run.getJudgings()) {
            if (existingJudging.getJudge().equals(participation)) {
                throw new AccessDeniedException("You already submitted a judging for this run");
            }
        }
        if (participation.getJudgeType().equals(JudgeType.STYLE) && runJudgingDto.getEntrySpeed() != null) {
            run.setEntrySpeed(runJudgingDto.getEntrySpeed());
        }
        RunJudging runJudging = new RunJudging();
        runJudging.setJudge(participation);
        runJudging.setRun(run);
        float totalAwardedPoints = 0;
        for (AwardedPointsCreateDto points : runJudgingDto.getAwardedPoints()) {
            AwardedPoints awardedPoints = new AwardedPoints();
            awardedPoints.setAwardedPoints(points.getAwardedPoints());
            awardedPoints.setAllocation(pointsAllocationRepository.findOne(points.getPointsAllocation()));
            runJudging.addAwardedPoints(awardedPoints);
            totalAwardedPoints += points.getAwardedPoints();
        }
        for (CommentCreateDto comment: runJudgingDto.getComments()) {
            runJudging.addComment(commentService.findOrCreate(comment));
        }
        run.addJudging(runJudging);
        run.addPoints(totalAwardedPoints);
        qualifierRepository.save(qualifier);
        updateQualifierScores(qualifier);
        checkAndNotifyRunResult(qualifier, run);
    }

    private void updateQualifierScores(Qualifier qualifier) {
        qualifier.setFinalScore(
                qualifier.getFirstRun().getTotalPoints() > qualifier.getSecondRun().getTotalPoints()
                        ? qualifier.getFirstRun().getTotalPoints()
                        : qualifier.getSecondRun().getTotalPoints());
        qualifierRepository.save(qualifier);
    }

    private void checkAndNotifyRunResult(Qualifier qualifier, Run run) {
        if (run.getJudgings().size() == 3) {
            //TODO: add GCM notify here
            Round round = qualifier.getRound();
            round.setCurrentDriver(null);
            roundRepository.save(round);
        }
    }

    public QualifierFullDto findQualifier(Long id){
        return QualifierMapper.mapFull(qualifierRepository.findOne(id));
    }
}
