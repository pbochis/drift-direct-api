package com.driftdirect.mapper;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.championship.ChampionshipDriverParticipation;
import com.driftdirect.domain.championship.ChampionshipJudgeParticipation;
import com.driftdirect.domain.round.Round;
import com.driftdirect.dto.championship.*;
import com.driftdirect.dto.round.RoundShowDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/18/2015.
 */
public class ChampionshipMapper {
    public static ChampionshipFullDto map(Championship c) {
        ChampionshipFullDto dto = new ChampionshipFullDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setRules(c.getRules());
        dto.setInformation(c.getInformation());
        dto.setTicketsUrl(dto.getTicketsUrl());
        List<RoundShowDto> rounds = new ArrayList<>();
        if (c.getRounds() != null) {
            for (Round round : c.getRounds()) {
                rounds.add(RoundMapper.map(round));
            }
            dto.setRounds(rounds);
        }
        return dto;
    }

    public static ChampionshipDriverParticipationDto mapDriverParticipation(ChampionshipDriverParticipation participation) {
        ChampionshipDriverParticipationDto dto = new ChampionshipDriverParticipationDto();
        dto.setId(participation.getId());
        dto.setDriver(PersonMapper.mapFull(participation.getDriver()));
        if (participation.getResults() != null) {
            ChampionshipDriverParticipationResultsDto results = new ChampionshipDriverParticipationResultsDto();
            results.setId(participation.getResults().getId());
            results.setTotalPoints(participation.getResults().getTotalPoints());
            results.setRank(participation.getResults().getRank());
            dto.setResults(results);
        }
        return dto;
    }

    public static ChampionshipJudgeParticipationDto mapJudgeParticipation(ChampionshipJudgeParticipation participation) {
        ChampionshipJudgeParticipationDto dto = new ChampionshipJudgeParticipationDto();
        dto.setJudge(PersonMapper.mapShort(participation.getJudge()));
        dto.setId(participation.getId());
        dto.setJudgeType(participation.getJudgeType().getName());
        return dto;
    }

    public static ChampionshipShortShowDto mapShort(Championship c, Round round) {
        ChampionshipShortShowDto dto = new ChampionshipShortShowDto();
        dto.setId(c.getId());
        dto.setBackgroundImage(c.getBackgroundImage().getId());
        dto.setLogo(c.getLogo().getId());
        dto.setNextRound(RoundMapper.mapShort(round));
        return dto;
    }

    public List<ChampionshipFullDto> map(List<Championship> championships) {
        return championships.stream().map(e -> map(e)).collect(Collectors.toList());
    }
}
