package com.driftdirect.controller;

import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.RoleDto;
import com.driftdirect.repository.RoleRepository;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 12/28/2015.
 */
@RestController
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @Secured({Authorities.ROLE_ADMIN, Authorities.ROLE_ORGANIZER})
    @RequestMapping(value = RestUrls.ROLES, method = RequestMethod.GET)
    public ResponseEntity<List<RoleDto>> getRoles(@AuthenticationPrincipal User currentUser) {
        //TODO: Oh god please forgive me for this code.
        Role userRole = (Role)currentUser.getRoles().toArray()[0];
        List<String> visibleRoles = new ArrayList<>();
        switch (userRole.getAuthority()){
            case Authorities.ROLE_ADMIN:
                visibleRoles.add(Authorities.ROLE_ADMIN);
                visibleRoles.add(Authorities.ROLE_JUDGE);
                visibleRoles.add(Authorities.ROLE_ORGANIZER);
                break;
            case Authorities.ROLE_ORGANIZER:
                visibleRoles.add(Authorities.ROLE_JUDGE);
                break;
        }
        return new ResponseEntity<>(roleRepository.findVisibleRoles(visibleRoles)
                                    .stream()
                                    .map(this::mapRole)
                                    .collect(Collectors.toList()), HttpStatus.OK);
    }

    private RoleDto mapRole(Role role){
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setRole(role.getAuthority());
        return dto;
    }
}
