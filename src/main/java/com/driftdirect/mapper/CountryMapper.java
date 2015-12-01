package com.driftdirect.mapper;

import com.driftdirect.domain.Country;
import com.driftdirect.dto.country.CountryShowDto;

/**
 * Created by Paul on 11/30/2015.
 */
public class CountryMapper {
    public static CountryShowDto map(Country country) {
        if (country == null) {
            return null;
        }
        CountryShowDto dto = new CountryShowDto();
        dto.setName(country.getName());
        dto.setId(country.getId());
        dto.setFlag(country.getFlag().getId());
        return dto;
    }
}
