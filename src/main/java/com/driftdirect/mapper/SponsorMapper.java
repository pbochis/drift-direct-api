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
        dto.setName(sponsor.getName());
        dto.setEmail(sponsor.getEmail());
        dto.setTelephoneNr(sponsor.getTelephoneNr());
        return dto;
    }

    public static List<SponsorShowDto> map(List<Sponsor> sponsors){
        return sponsors.stream().map(SponsorMapper::map).collect(Collectors.toList());
    }
}
