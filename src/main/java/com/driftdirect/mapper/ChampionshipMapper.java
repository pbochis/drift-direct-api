package com.driftdirect.mapper;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.dto.championship.ChampionshipDriverParticipation;
import com.driftdirect.dto.championship.ChampionshipFullDto;
import com.driftdirect.dto.championship.ChampionshipShortShowDto;
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

    public static ChampionshipDriverParticipation mapDriverParticipation(Long id, Person person) {
        ChampionshipDriverParticipation cdp = new ChampionshipDriverParticipation();
        cdp.setChampionshipId(id);
        cdp.setDriver(PersonMapper.mapShort(person));
        return cdp;
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
