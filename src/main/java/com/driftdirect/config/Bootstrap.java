package com.driftdirect.config;

import com.driftdirect.domain.ConfigSetting;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.dto.UserCreateDTO;
import com.driftdirect.repository.ConfigSettingRepository;
import com.driftdirect.repository.RoleRepository;
import com.driftdirect.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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
    @Autowired
    private ConfigSettingRepository configSettingRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        if (configSettingRepository.findByKey(APPLICATION_INIT) == null){
            System.out.println("********************************** STARTED APP BOOTSTRAP ******************************");
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
            userService.createFromDto(judge, new HashSet<>(Arrays.asList(adminRole)));

            ConfigSetting configSetting = new ConfigSetting();
            configSetting.setKey(APPLICATION_INIT);
            configSettingRepository.save(configSetting);
        }
    }

}
