package com.driftdirect.controller;

import com.driftdirect.domain.user.User;
import com.driftdirect.dto.user.UserCreateDTO;
import com.driftdirect.dto.user.UserShowDto;
import com.driftdirect.mapper.UserMapper;
import com.driftdirect.service.UserService;
import com.driftdirect.util.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by Paul on 11/16/2015.
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = Routes.USER, method = RequestMethod.GET)
    public UserShowDto whoAmI(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UserMapper.map(user);
    }

    @RequestMapping(value = Routes.USER, method = RequestMethod.POST)
    public void createUser(@Valid @RequestBody UserCreateDTO user) throws MessagingException, IOException {
        userService.createFromDto(user);
    }
}
