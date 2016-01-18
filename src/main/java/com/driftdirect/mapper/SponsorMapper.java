package com.driftdirect.mapper;

import com.driftdirect.domain.sponsor.Sponsor;
import com.driftdirect.dto.sponsor.SponsorShowDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/26/2015.
 */
public class SponsorMapper {
    public static SponsorShowDto map(Sponsor sponsor){
        SponsorShowDto dto = new SponsorShowDto();
        dto.setId(sponsor.getId());
        dto.setName(sponsor.getName());
        dto.setDescription(sponsor.getDescription());
        dto.setUrl(sponsor.getUrl());
        if (sponsor.getLogo() != null) {
            dto.setLogo(sponsor.getLogo().getId());
        }
        return dto;
    }

    public static List<SponsorShowDto> map(List<Sponsor> sponsors){
        return sponsors.stream().map(SponsorMapper::map).collect(Collectors.toList());
    }
}
