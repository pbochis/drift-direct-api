package com.driftdirect.mapper;

import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.user.UserShowDto;

import java.util.stream.Collectors;

/**
 * Created by Paul on 11/19/2015.
 */
public class UserMapper {
    public static UserShowDto map(User user){
        UserShowDto dto = new UserShowDto();
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet()));
        dto.setFirstName(user.getPerson().getFirstName());
        dto.setLastName(user.getPerson().getLastName());
        if (user.getPerson().getProfilePicture() != null) {
            dto.setProfilePicture(user.getPerson().getProfilePicture().getId());
        }
        return dto;
    }

}
