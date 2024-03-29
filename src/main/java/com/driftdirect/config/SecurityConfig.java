package com.driftdirect.config;


import com.driftdirect.repository.UserRepository;
import com.driftdirect.security.UserDetailsServiceImpl;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

/**
 * Created by Paul on 11/9/2015.
 */

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService(userRepository)).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserDetailsServiceImpl(userRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.GET,
                        RestUrls.CHAMPIONSHIP,
                        RestUrls.CHAMPIONSHIP_SHORT,
                        RestUrls.CHAMPIONSHIP_ID,
                        RestUrls.CHAMPIONSHIP_ID_ROUNDS,
                        RestUrls.CHAMPIONSHIP_ID_DRIVERS,
                        RestUrls.CHAMPIONSHIP_ID_DRIVERS_ID,
                        RestUrls.CHAMPIONSHIP_ID_JUDGES,
                        RestUrls.ROUND_ID,
                        RestUrls.QUALIFIER_ID,
                        RestUrls.PERSON_ID,
                        RestUrls.FILE_ID,
                        RestUrls.ROUND_ID_PLAYOFF,
                        RestUrls.PLAYOFF_BATTLE_ID,
                        RestUrls.PERMISSION_ROUND_GENERATE_BATTLE_TREE).permitAll()
                .antMatchers(HttpMethod.POST,
                        RestUrls.GCM_REGISTER).permitAll()
                .antMatchers(HttpMethod.GET, "/testTime").permitAll()
                    .anyRequest().authenticated()
                .and()
                .httpBasic().
                and()
                .csrf().disable();
    }
}
