package com.driftdirect.mapper;

import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.RoundScheduleEntry;
import com.driftdirect.dto.round.RoundScheduleShowDto;
import com.driftdirect.dto.round.RoundShortShowDto;
import com.driftdirect.dto.round.RoundShowDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/18/2015.
 */
public class RoundMapper {
    public static RoundShowDto map(Round round){
        RoundShowDto dto = new RoundShowDto();
        dto.setId(round.getId());
        dto.setName(round.getName());
        dto.setStartDate(round.getStartDate());
        dto.setEndDate(round.getEndDate());
        dto.setSchedule(mapSchedule(round.getScheduele()));
        return dto;
    }

    public static RoundShortShowDto mapShort(Round round) {
        RoundShortShowDto dto = new RoundShortShowDto();
        dto.setId(round.getId());
        dto.setName(round.getName());
        dto.setOrder(3);
        dto.setLogo(round.getLogo().getId());
        dto.setStartDate(round.getStartDate());
        dto.setEndDate(round.getEndDate());
        dto.setRoundStatus("NEXT");
        return dto;
    }

    public static List<RoundShowDto> map(List<Round> rounds){
        return rounds.stream().map(e -> map(e)).collect(Collectors.toList());
    }

    private static List<RoundScheduleShowDto> mapSchedule(Set<RoundScheduleEntry> schedule){
        return schedule.stream().map(e -> map(e)).collect(Collectors.toList());
    }

    private static RoundScheduleShowDto map(RoundScheduleEntry schedule){
        RoundScheduleShowDto dto = new RoundScheduleShowDto();
        dto.setName(schedule.getName());
        dto.setEndDate(schedule.getEndDate());
        dto.setStartDate(schedule.getStartDate());
        return dto;
    }
}
