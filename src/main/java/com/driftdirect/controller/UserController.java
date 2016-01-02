package com.driftdirect.controller;

import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.user.UserCreateDTO;
import com.driftdirect.dto.user.UserShowDto;
import com.driftdirect.mapper.UserMapper;
import com.driftdirect.security.SecurityService;
import com.driftdirect.service.UserService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by Paul on 11/16/2015.
 */
@RestController
public class UserController {

    private UserService userService;
    private SecurityService securityService;

    @Autowired
    public UserController(UserService userService, SecurityService securityService){
        this.securityService = securityService;
        this.userService = userService;
    }

    @RequestMapping(value = RestUrls.USER, method = RequestMethod.GET)
    public UserShowDto whoAmI(){
        return UserMapper.map(securityService.currentUser());
    }

    @Secured({Authorities.ROLE_ADMIN})
    @RequestMapping(value = RestUrls.USERS, method = RequestMethod.GET)
    public ResponseEntity<List<UserShowDto>> getUsers(@AuthenticationPrincipal User currentUser){
        return new ResponseEntity<List<UserShowDto>>(userService.getUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = RestUrls.USER, method = RequestMethod.POST)
    public ResponseEntity createUser(@Valid @RequestBody UserCreateDTO user) throws MessagingException, IOException {
        User currentUser = securityService.currentUser();
        if (securityService.canCreateUser(currentUser, user.getRole())) {
            userService.createFromDto(user);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}
