package com.driftdirect.config;

import com.driftdirect.domain.Championship;
import com.driftdirect.domain.ConfigSetting;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.dto.UserCreateDTO;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.ChampionshipDto;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.repository.ConfigSettingRepository;
import com.driftdirect.repository.RoleRepository;
import com.driftdirect.service.ChampionshipService;
import com.driftdirect.service.RoundService;
import com.driftdirect.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Paul on 11/10/2015.
 */
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final String APPLICATION_INIT = "init";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Environment environment;
    private ConfigSettingRepository configSettingRepository;
    private RoleRepository roleRepository;
    private UserService userService;
    private ChampionshipService championshipService;
    private RoundService roundService;

    @Autowired
    public Bootstrap(
            ConfigSettingRepository configSettingRepository,RoleRepository roleRepository,
            UserService userService, Environment environment, ChampionshipService championshipService,
            RoundService roundService){
        this.configSettingRepository = configSettingRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.environment = environment;
        this.championshipService = championshipService;
        this.roundService = roundService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        if (configSettingRepository.findByKey(APPLICATION_INIT) == null){
            System.out.println("********************************** STARTED APP BOOTSTRAP ******************************");
            if (Arrays.asList(this.environment.getActiveProfiles()).contains("dev")){
                initDevelopementDatabase();
            }

            ConfigSetting configSetting = new ConfigSetting();
            configSetting.setKey(APPLICATION_INIT);
            configSettingRepository.save(configSetting);
        }
    }

    private void initDevelopementDatabase(){
        initDevUsersAndRoles();
        initChampionshipAndRounds();
    }

    private void initChampionshipAndRounds(){
        ChampionshipCreateDTO dto = new ChampionshipCreateDTO();
        dto.setName("DN1Z");
        dto.setTicketsUrl("www.tickets.com");
        dto.setInformation("This is a drifting championship, mate");
        dto.setRules("These are the rules of drifting");
        Championship c = championshipService.createFromDto(dto);
        RoundCreateDto rc = new RoundCreateDto();
        rc.setName("Round 1");
        rc.setChampionship(c);
        roundService.createFromDto(rc);
    }

    private void initDevUsersAndRoles(){
        Role adminRole = roleRepository.save(new Role(Authorities.ROLE_ADMIN));
        Role judgeRole = roleRepository.save(new Role(Authorities.ROLE_JUDGE));

        UserCreateDTO judge = new UserCreateDTO();
        judge.setUsername("judge");
        judge.setPassword("judge");
        judge.setEmail("email@email.com");
        userService.createFromDto(judge, new HashSet<>(Arrays.asList(judgeRole)));

        UserCreateDTO admin = new UserCreateDTO();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@email.com");
        userService.createFromDto(admin, new HashSet<>(Arrays.asList(adminRole)));
    }

}
