package com.driftdirect.mapper;

import com.driftdirect.domain.person.Person;
import com.driftdirect.dto.person.PersonShortShowDto;

/**
 * Created by Paul on 11/21/2015.
 */
public class PersonMapper {
    public static PersonShortShowDto mapShort(Person person){
        PersonShortShowDto dto = new PersonShortShowDto();
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        return dto;
    }
}
