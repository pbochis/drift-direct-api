package com.driftdirect.security;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import com.driftdirect.repository.RoleRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.qualifier.QualifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/25/2015.
 */
@Service
@Transactional
public class SecurityService {
    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private QualifierRepository qualifierRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User currentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean canCreateUser(User user, Set<Long> createdRoles){
        List<String> roles = createdRoles.stream()
                .map(e -> roleRepository.findOne(e).getAuthority())
                .collect(Collectors.toList());
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

    public boolean canJudgeQualifier(User user, Long qualifierId) throws NoSuchElementException {
        Qualifier qualifier = qualifierRepository.findOne(qualifierId);
        if (qualifier == null) {
            throw new NoSuchElementException("Qualifier not found!");
        }
        return qualifier.getRound()
                .getChampionship()
                .getJudges()
                .stream()
                .anyMatch(judgeParticipation -> judgeParticipation.getJudge().getId().equals(user.getPerson().getId()));
    }
}
