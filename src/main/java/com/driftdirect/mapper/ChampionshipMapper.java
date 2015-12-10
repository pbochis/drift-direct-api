package com.driftdirect.mapper;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.championship.ChampionshipDriverParticipation;
import com.driftdirect.domain.championship.ChampionshipJudgeParticipation;
import com.driftdirect.domain.round.Round;
import com.driftdirect.dto.championship.*;
import com.driftdirect.dto.round.RoundShortShowDto;
import com.driftdirect.dto.round.RoundStatus;

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
        dto.setInformation(c.getInformation());
        dto.setTicketsUrl(c.getTicketsUrl());
        dto.setBackgroundImage(c.getBackgroundImage().getId());
        dto.setLogo(c.getLogo().getId());
        if (c.getRules() != null) {
            ChampionshipRulesDto rules = new ChampionshipRulesDto();
            rules.setId(c.getRules().getId());
            rules.setRules(c.getRules().getRules());
            rules.setVideoUrl(c.getRules().getVideoUrl());
            dto.setRules(rules);
        }
        if (c.getRounds() != null) {
            dto.setRounds(mapRounds(c));
        }

        if (c.getJudges() != null) {
            dto.setJudges(c.getJudges()
                    .stream()
                    .map(ChampionshipMapper::mapJudgeParticipation)
                    .collect(Collectors.toList()));
        }

        if (c.getDrivers() != null) {
            dto.setDrivers(c.getDrivers()
                    .stream()
                    .map(e -> PersonMapper.mapShort(e.getDriver()))
                    .collect(Collectors.toList()));
        }

        if (c.getSponsors() != null) {
            dto.setSponsors(c.getSponsors()
                    .stream()
                    .map(SponsorMapper::map)
                    .collect(Collectors.toList()));
        }

        if (c.getNews() != null) {
            dto.setNews(c.getNews()
                    .stream()
                    .map(NewsMapper::map)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private static List<RoundShortShowDto> mapRounds(Championship c) {
        boolean next = true;
        List<RoundShortShowDto> rounds = new ArrayList<>();
        for (Round round : c.getRounds()) {
            if (round.isEnded()) rounds.add(RoundMapper.mapShort(round, RoundStatus.ENDED));
            else if (round.isOngoing()) rounds.add(RoundMapper.mapShort(round, RoundStatus.ONGOING));
            else if (next) rounds.add(RoundMapper.mapShort(round, RoundStatus.NEXT));
            else rounds.add(RoundMapper.mapShort(round, RoundStatus.FUTURE));
        }
        return rounds;
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

    public static ChampionshipShortShowDto mapShort(Championship c) {
        ChampionshipShortShowDto dto = new ChampionshipShortShowDto();
        dto.setId(c.getId());
        dto.setBackgroundImage(c.getBackgroundImage().getId());
        dto.setLogo(c.getLogo().getId());
        dto.setNextRound(getCurrentOrNextRound(c));
        return dto;
    }

    private static RoundShortShowDto getCurrentOrNextRound(Championship c) {
        for (Round round : c.getRounds()) {
            if (round.isOngoing())
                return RoundMapper.mapShort(round, RoundStatus.ONGOING, c.getRounds().headSet(round).size());
            if (round.isFuture())
                return RoundMapper.mapShort(round, RoundStatus.NEXT, c.getRounds().headSet(round).size());
        }
        return null;
    }

    public List<ChampionshipFullDto> map(List<Championship> championships) {
        return championships.stream().map(e -> map(e)).collect(Collectors.toList());
    }
}
