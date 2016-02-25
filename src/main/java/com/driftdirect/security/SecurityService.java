package com.driftdirect.security;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.battle.Battle;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import com.driftdirect.repository.RoleRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.playoff.BattleRepository;
import com.driftdirect.repository.round.qualifier.QualifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Autowired
    private BattleRepository battleRepository;


    public User currentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isAdmin(User user) {
        if (user == null) {
            return false;
        }
        return user.getRoles().stream().anyMatch(role -> role.getAuthority().equals(Authorities.ROLE_ADMIN));
    }

    public boolean isOrganizer(User user) {
        if (user == null) {
            return false;
        }
        return user.getRoles().stream().anyMatch(role -> role.getAuthority().equals(Authorities.ROLE_ORGANIZER));
    }

    public boolean isJudge(User user) {
        if (user == null) {
            return false;
        }
        return user.getRoles().stream().anyMatch(role -> role.getAuthority().equals(Authorities.ROLE_JUDGE));
    }

    public boolean canCreateUser(User user, Long createdRole) {
        String role = roleRepository.findOne(createdRole).getAuthority();
        Set<String> userAuthorities = user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet());
        if (role.equals(Authorities.ROLE_ADMIN) && !userAuthorities.contains(Authorities.ROLE_ADMIN)) {
            return false;
        }
        if (role.equals(Authorities.ROLE_ORGANIZER) && !userAuthorities.contains(Authorities.ROLE_ADMIN)) {
            return false;
        }
        if (role.equals(Authorities.ROLE_JUDGE) && !(userAuthorities.contains(Authorities.ROLE_ORGANIZER) || userAuthorities.contains(Authorities.ROLE_ADMIN))) {
            return false;
        }
        return true;
    }

    public boolean canEditRound(User user, Long roundId) {
        Round round = roundRepository.findOne(roundId);
        return user.getPerson().equals(round.getChampionship().getOrganizer());
    }

    public boolean isChampionshipOrganizer(User user, Long championshipId) {
        // TODO: permissions should be refactored
        if (isAdmin(user)) {
            return true;
        }
        for (Championship c : user.getPerson().getChampionships()) {
            if (c.getId().equals(championshipId)) {
                return true;
            }
        }
        return false;
    }

    public boolean canRegisterDriver(User user, Long roundId) {
        if (isAdmin(user)) {
            return true;
        }
        Round round = roundRepository.findOne(roundId);
        return isChampionshipOrganizer(user, round.getChampionship().getId()) || isChampionshipJudge(user, round.getChampionship());
    }

    public boolean canDeleteQualifier(User user, Long qualifierId) throws NoSuchElementException {
        Qualifier qualifier = qualifierRepository.findOne(qualifierId);
        Championship championship = qualifier.getRound().getChampionship();
        return isChampionshipJudge(user, championship) || isChampionshipOrganizer(user, championship.getId()) || isAdmin(user);
    }

    private boolean isChampionshipJudge(User user, Championship championship) {
        return championship
                .getJudges()
                .stream()
                .anyMatch(judgeParticipation -> judgeParticipation.getJudge().getId().equals(user.getPerson().getId()));
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

    public boolean canJudgePlayoff(User user, Long battleId) throws NoSuchElementException {
        Battle battle = battleRepository.findOne(battleId);
        if (battle == null) {
            throw new NoSuchElementException("Battle not found!");
        }
        return battle
                .getPlayoffStage()
                .getPlayoffTree()
                .getRound()
                .getChampionship()
                .getJudges()
                .stream()
                .anyMatch(judgeParticipation -> judgeParticipation.getJudge().getId().equals(user.getPerson().getId()));
    }

    public boolean canGeneratePlayoffs(User user, Long roundId) {
        if (isAdmin(user)) {
            return true;
        }
        if (user == null) {
            return false;
        }
        Round round = roundRepository.findOne(roundId);
        if (round == null) {
            return false;
        }
        if (user.getPerson().equals(round.getChampionship().getOrganizer())) {
            return true;
        }
        return round
                .getChampionship()
                .getJudges()
                .stream()
                .anyMatch(judgeParticipation -> judgeParticipation.getJudge().equals(user.getPerson()));


    }
}
