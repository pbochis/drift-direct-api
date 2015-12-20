package com.driftdirect.security;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import com.driftdirect.repository.round.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/25/2015.
 */
@Service
public class SecurityService {

    @Autowired
    private RoundRepository roundRepository;

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

    public boolean canEditChampionship(User user, Long championshipId) {
        for (Championship c : user.getPerson().getChampionships()) {
            if (c.getId().equals(championshipId)) {
                return true;
            }
        }
        return false;
    }

    public boolean canRegisterDriver(User user, Long roundId) {
        return canEditChampionship(user, roundRepository.findOne(roundId).getChampionship().getId());
    }
}
