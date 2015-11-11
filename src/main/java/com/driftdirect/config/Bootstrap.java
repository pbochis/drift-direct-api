package com.driftdirect.config;

import com.driftdirect.domain.ConfigSetting;
import com.driftdirect.domain.User;
import com.driftdirect.dto.UserCreateDTO;
import com.driftdirect.repository.ConfigSettingRepository;
import com.driftdirect.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

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
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        System.out.println("********************************** STARTED APP BOOTSTRAP ******************************");
        if (configSettingRepository.findByKey(APPLICATION_INIT) == null){
            UserCreateDTO user = new UserCreateDTO();
            user.setUsername("admin");
            user.setPassword("admin");
            try{
                userService.createFromDto(user);
            }
            catch (Exception e){

            }
            ConfigSetting configSetting = new ConfigSetting();
            configSetting.setKey(APPLICATION_INIT);
            configSettingRepository.save(configSetting);
        }
    }

}
