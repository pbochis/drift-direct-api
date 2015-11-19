package com.driftdirect.service;

import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.user.UserCreateDTO;
import com.driftdirect.dto.user.UserShowDto;
import com.driftdirect.repository.RoleRepository;
import com.driftdirect.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Paul on 11/10/2015.
 */
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private MailSenderService mailSenderService;

    private final String USER_NOTIFY_EMAIL_TEMPLATE = "/template/templateAccountCreated";

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, MailSenderService mailSenderService){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
    }

    public User createFromDto(UserCreateDTO dto) throws MessagingException, IOException {
        User user = new User();
        user.setUsername(dto.getUsername());
        // do this OR generate new random password
        String password = dto.getPassword();
        if (password == null){
            password = RandomStringUtils.random(8, true, true);
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(dto.getEmail());
        user.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        for (String role: dto.getRoles()){
            roles.add(roleRepository.findRoleByAuthority(role));
        }
        user.setRoles(roles);
        notifyNewUser(user.getEmail(), password);
        return userRepository.save(user);
    }

    private void notifyNewUser(String email, String password) throws MessagingException, IOException {
        String[] args = new String[]{email, password};
        mailSenderService.sendWithTemplate(USER_NOTIFY_EMAIL_TEMPLATE, args, email, "Account created");
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUser(username);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
