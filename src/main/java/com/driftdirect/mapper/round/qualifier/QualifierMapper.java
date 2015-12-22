package com.driftdirect.mapper.round.qualifier;

import com.driftdirect.domain.championship.judge.PointsAllocation;
import com.driftdirect.domain.round.qualifiers.AwardedPoints;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.domain.round.qualifiers.Run;
import com.driftdirect.domain.round.qualifiers.RunJudging;
import com.driftdirect.dto.championship.judge.PointsAllocationDto;
import com.driftdirect.dto.round.qualifier.QualifierFullDto;
import com.driftdirect.dto.round.qualifier.QualifierShortDto;
import com.driftdirect.dto.round.qualifier.run.AwardedPointsDto;
import com.driftdirect.dto.round.qualifier.run.RunFullDto;
import com.driftdirect.dto.round.qualifier.run.RunJudgingDto;
import com.driftdirect.mapper.ChampionshipMapper;
import com.driftdirect.mapper.PersonMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 12/20/2015.
 */
public class QualifierMapper {

    private static void mapInternal(QualifierShortDto dto, Qualifier qualifier){
        dto.setId(qualifier.getId());
        dto.setFirstName(qualifier.getDriver().getFirstName());
        dto.setLastName(qualifier.getDriver().getLastName());
        dto.setNick(qualifier.getDriver().getNick());
        dto.setPoints(qualifier.getFinalScore() > 0 ? qualifier.getFinalScore() : null);
        dto.setProfilePicture(qualifier.getDriver().getProfilePicture().getId());
        dto.setCountry(qualifier.getDriver().getCountry().getFlag().getId());
    }

    public static QualifierFullDto mapFull(Qualifier qualifier){
        QualifierFullDto dto = new QualifierFullDto();
        dto.setId(qualifier.getId());
        dto.setFinalScore(qualifier.getFinalScore());
        dto.setDriver(PersonMapper.mapShort(qualifier.getDriver()));
        dto.setFirstRun(mapRun(qualifier.getFirstRun()));
        dto.setSecondRun(mapRun(qualifier.getSecondRun()));
        return dto;
    }

    private static RunFullDto mapRun(Run run){
        RunFullDto dto = new RunFullDto();
        dto.setId(run.getId());
        dto.setEntrySpeed(run.getEntrySpeed());
        dto.setTotalPoints(run.getTotalPoints());
        dto.setJudgings(run
            .getJudgings()
            .stream()
            .map(QualifierMapper::mapRunJuding)
            .collect(Collectors.toList())
        );
        return dto;
    }

    private static RunJudgingDto mapRunJuding(RunJudging runJudging){
        RunJudgingDto dto = new RunJudgingDto();
        dto.setId(runJudging.getId());
        dto.setAwardedPoints(runJudging
                .getAwardedPoints()
                .stream()
                .map(QualifierMapper::mapAwardedPoints)
                .collect(Collectors.toList())
        );
        dto.setJudgeParticipation(ChampionshipMapper.mapJudgeParticipation(runJudging.getJudge()));
        return dto;
    }

    private static AwardedPointsDto mapAwardedPoints(AwardedPoints awardedPoints){
        AwardedPointsDto dto = new AwardedPointsDto();
        dto.setId(awardedPoints.getId());
        dto.setAwardedPoints(awardedPoints.getAwardedPoints());
        dto.setPointsAllocation(mapPointsAllocation(awardedPoints.getAllocation()));
        return dto;
    }

    private static PointsAllocationDto mapPointsAllocation(PointsAllocation pointsAllocation){
        PointsAllocationDto dto = new PointsAllocationDto();
        dto.setId(pointsAllocation.getId());
        dto.setName(pointsAllocation.getName());
        dto.setMaxPoints(pointsAllocation.getMaxPoints());
        return dto;
    }

    public static QualifierShortDto mapShort(Qualifier qualifier) {
        QualifierShortDto dto = new QualifierShortDto();
        mapInternal(dto, qualifier);
        return dto;
    }

    public static List<QualifierShortDto> mapShort(List<Qualifier> qualifiers){
        return qualifiers
                .stream()
                .map(QualifierMapper::mapShort)
                .collect(Collectors.toList());
    }
}