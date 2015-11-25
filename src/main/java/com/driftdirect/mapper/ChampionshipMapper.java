package com.driftdirect.mapper;

import com.driftdirect.domain.Championship;
import com.driftdirect.domain.round.Round;
import com.driftdirect.dto.championship.ChampionshipShowDto;
import com.driftdirect.dto.round.RoundShowDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/18/2015.
 */
public class ChampionshipMapper {
    public static ChampionshipShowDto map(Championship c){
        ChampionshipShowDto dto = new ChampionshipShowDto();
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

    public List<ChampionshipShowDto> map(List<Championship> championships){
        return championships.stream().map(e -> map(e)).collect(Collectors.toList());
    }
}
