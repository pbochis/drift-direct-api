package com.driftdirect.mapper;

import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.user.UserShowDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        return dto;
    }

}
