package com.driftdirect.mapper;

import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.RoundScheduleEntry;
import com.driftdirect.dto.round.RoundScheduleEntryShowDto;
import com.driftdirect.dto.round.RoundShortShowDto;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.dto.round.RoundStatus;
import com.driftdirect.dto.round.track.TrackDto;

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
        dto.setTicketsUrl(round.getTicketsUrl());
        dto.setSchedule(mapSchedule(round.getScheduele()));
        if (round.getTrack() != null){
            TrackDto track = new TrackDto();
            track.setId(round.getTrack().getId());
            track.setDescription(round.getTrack().getDescription());
            track.setLayout(round.getTrack().getLayout().getId());
            track.setVideoUrl(round.getTrack().getVideoUrl());
            track.setJudgingCriteria(round.getTrack().getJudgingCriteria());
            dto.setTrack(track);
        }
        return dto;
    }

    public static RoundShortShowDto mapShort(Round round, RoundStatus status) {
        RoundShortShowDto dto = new RoundShortShowDto();
        dto.setId(round.getId());
        dto.setName(round.getName());
        dto.setLogo(round.getLogo().getId());
        dto.setStartDate(round.getStartDate());
        dto.setEndDate(round.getEndDate());
        dto.setRoundStatus(status);
        return dto;
    }

    public static RoundShortShowDto mapShort(Round round, RoundStatus status, int order) {
        RoundShortShowDto dto = mapShort(round, status);
        dto.setOrder(order);
        return dto;
    }


    public static List<RoundShowDto> map(List<Round> rounds) {
        return rounds.stream().map(e -> map(e)).collect(Collectors.toList());
    }

    private static List<RoundScheduleEntryShowDto> mapSchedule(Set<RoundScheduleEntry> schedule) {
        return schedule.stream().map(e -> map(e)).collect(Collectors.toList());
    }

    private static RoundScheduleEntryShowDto map(RoundScheduleEntry schedule) {
        RoundScheduleEntryShowDto dto = new RoundScheduleEntryShowDto();
        dto.setName(schedule.getName());
        dto.setEndDate(schedule.getEndDate());
        dto.setStartDate(schedule.getStartDate());
        return dto;
    }
}
