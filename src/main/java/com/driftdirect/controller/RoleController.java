package com.driftdirect.controller;

import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.dto.RoleDto;
import com.driftdirect.repository.RoleRepository;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<RoleDto>> getRoles(){
        return new ResponseEntity<>(roleRepository.findAll()
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
