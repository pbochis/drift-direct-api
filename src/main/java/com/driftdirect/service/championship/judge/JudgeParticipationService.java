package com.driftdirect.service.championship.judge;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.championship.judge.JudgeParticipation;
import com.driftdirect.domain.championship.judge.PointsAllocation;
import com.driftdirect.dto.championship.judge.JudgeParticipationCreateDto;
import com.driftdirect.dto.championship.judge.PointsAllocationCreateDto;
import com.driftdirect.repository.PersonRepository;
import com.driftdirect.repository.championship.ChampionshipRepository;
import com.driftdirect.repository.championship.judge.JudgeParticipationRepository;
import com.driftdirect.repository.championship.judge.PointsAllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Paul on 12/17/2015.
 */
@Service
@Transactional
public class JudgeParticipationService {
    private ChampionshipRepository championshipRepository;
    private JudgeParticipationRepository judgeParticipationRepository;
    private PointsAllocationRepository pointsAllocationRepository;
    private PersonRepository personRepository;

    @Autowired
    public JudgeParticipationService(ChampionshipRepository championshipRepository,
                                     JudgeParticipationRepository judgeParticipationRepository,
                                     PointsAllocationRepository pointsAllocationRepository,
                                     PersonRepository personRepository) {
        this.championshipRepository = championshipRepository;
        this.judgeParticipationRepository = judgeParticipationRepository;
        this.pointsAllocationRepository = pointsAllocationRepository;
        this.personRepository = personRepository;
    }

    public void checkCanAddJudge(Championship championship, JudgeParticipationCreateDto judgeParticipationCreateDto) throws Exception {
        //TODO: add meaningful exceptions
        int allocatedPoints = 0;
        if (championship.getJudges() != null) {
            if (championship.getJudges().size() > 2) {
                throw new Exception("There already are 3 judges");
            }
            for (JudgeParticipation judge : championship.getJudges()) {
                allocatedPoints += judge.getMaximumAllocatedPoints();
                if (judge.getJudgeType().equals(judgeParticipationCreateDto.getJudgeType())) {
                    throw new Exception("Already existis judge of this type");
                }
            }
        }
        if (allocatedPoints + judgeParticipationCreateDto.getMaximumAllocatedPoints() > 100) {
            throw new Exception("Too many points");
        }
    }

    public void addJudge(Championship championship, JudgeParticipationCreateDto judgeParticipationCreateDto) throws Exception {
        checkCanAddJudge(championship, judgeParticipationCreateDto);
        JudgeParticipation judgeParticipation = new JudgeParticipation();
        judgeParticipation.setChampionship(championship);
        judgeParticipation.setJudge(personRepository.findOne(judgeParticipationCreateDto.getJudge()));
        judgeParticipation.setJudgeType(judgeParticipationCreateDto.getJudgeType());
        judgeParticipation.setTitle(judgeParticipationCreateDto.getTitle());
        for (PointsAllocationCreateDto pointsAllocationCreateDto : judgeParticipationCreateDto.getPointsAllocations()) {
            PointsAllocation pointsAllocation = new PointsAllocation();
            pointsAllocation.setName(pointsAllocationCreateDto.getName());
            pointsAllocation.setMaxPoints(pointsAllocationCreateDto.getMaxPoints());
            pointsAllocation = pointsAllocationRepository.save(pointsAllocation);
            judgeParticipation.addPointsAllocation(pointsAllocation);
        }
        judgeParticipation = judgeParticipationRepository.save(judgeParticipation);
        championship.addJudgeParticipation(judgeParticipation);
//        championshipRepository.save(championship);
    }
}
