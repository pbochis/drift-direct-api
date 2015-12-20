package com.driftdirect.security;

import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/25/2015.
 */
@Service
public class SecurityService {
    public User currentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean canCreateUser(User user, Set<String> roles){
        Set<String> userAuthorities = user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet());
        if (roles.contains(Authorities.ROLE_ADMIN) && !userAuthorities.contains(Authorities.ROLE_ADMIN)){
            return false;
        }
        if (roles.contains(Authorities.ROLE_ORGANIZER) && !userAuthorities.contains(Authorities.ROLE_ADMIN)){
            return false;
        }
        if (roles.contains(Authorities.ROLE_JUDGE) && !userAuthorities.contains(Authorities.ROLE_ORGANIZER)){
            return false;
        }
        return true;
    }

    public boolean canRegisterDriver(User user, Round round) {
        return false;
    }

}
