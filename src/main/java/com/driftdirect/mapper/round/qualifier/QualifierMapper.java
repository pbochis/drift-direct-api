package com.driftdirect.mapper.round.qualifier;

import com.driftdirect.domain.championship.judge.JudgeParticipation;
import com.driftdirect.domain.comment.Comment;
import com.driftdirect.domain.round.qualifiers.AwardedPoints;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.domain.round.qualifiers.Run;
import com.driftdirect.domain.round.qualifiers.RunJudging;
import com.driftdirect.dto.championship.judge.JudgeParticipationDto;
import com.driftdirect.dto.round.qualifier.QualifierFullDto;
import com.driftdirect.dto.round.qualifier.QualifierJudgeDto;
import com.driftdirect.dto.round.qualifier.QualifierShortDto;
import com.driftdirect.dto.round.qualifier.run.AwardedPointsDto;
import com.driftdirect.dto.round.qualifier.run.RunFullDto;
import com.driftdirect.dto.round.qualifier.run.RunJudgingDto;
import com.driftdirect.mapper.ChampionshipMapper;
import com.driftdirect.mapper.PersonMapper;
import com.driftdirect.mapper.comment.CommentMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 12/20/2015.
 */
public class QualifierMapper {

    private static void mapInternal(QualifierShortDto dto, Qualifier qualifier){
        dto.setId(qualifier.getId());
        dto.setDriver(PersonMapper.mapShort(qualifier.getDriver()));
        dto.setPoints(qualifier.getFinalScore() > 0 ? qualifier.getFinalScore() : null);
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

    public static QualifierJudgeDto mapForJudge(Qualifier qualifier, JudgeParticipation participation, List<Comment> comments){
        QualifierJudgeDto dto = new QualifierJudgeDto();
        dto.setId(qualifier.getId());
        dto.setDriver(PersonMapper.mapShort(qualifier.getDriver()));
        dto.setJudge(ChampionshipMapper.mapJudgeParticipation(participation));
        if (qualifier.getFirstRun().getJudgings().size() == 0){
            dto.setRunId(qualifier.getFirstRun().getId());
            dto.setRunNumber(1);
        }else{
            dto.setRunId(qualifier.getSecondRun().getId());
            dto.setRunNumber(2);
        }
        dto.setAvailableComments(comments.stream()
                            .map(CommentMapper::map)
                            .collect(Collectors.toList()));
        return dto;
    }

    private static JudgeParticipationDto mapJudge(JudgeParticipation participation){
        JudgeParticipationDto dto = new JudgeParticipationDto();
        dto.setId(participation.getId());
        dto.setTitle(participation.getTitle());
        dto.setJudgeType(participation.getJudgeType());
        dto.setPointsAllocations(participation.getPointsAllocations()
                .stream()
                .map(ChampionshipMapper::mapPointsAllocation)
                .collect(Collectors.toList()));
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
        dto.setComments(runJudging.getComments()
                .stream()
                .map(CommentMapper::map)
                .collect(Collectors.toList()));
        return dto;
    }

    private static AwardedPointsDto mapAwardedPoints(AwardedPoints awardedPoints){
        AwardedPointsDto dto = new AwardedPointsDto();
        dto.setId(awardedPoints.getId());
        dto.setAwardedPoints(awardedPoints.getAwardedPoints());
        dto.setPointsAllocation(ChampionshipMapper.mapPointsAllocation(awardedPoints.getAllocation()));
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