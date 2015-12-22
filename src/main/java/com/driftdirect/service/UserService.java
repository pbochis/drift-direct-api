package com.driftdirect.service;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.user.UserCreateDTO;
import com.driftdirect.repository.PersonRepository;
import com.driftdirect.repository.RoleRepository;
import com.driftdirect.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Paul on 11/10/2015.
 */
@Service
public class UserService {
    private final String USER_NOTIFY_EMAIL_TEMPLATE = "/template/templateAccountCreated.html";
    private UserRepository userRepository;
    private PersonRepository personRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private MailSenderService mailSenderService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PersonRepository personRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       MailSenderService mailSenderService){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
        this.personRepository = personRepository;
    }

    public User createFromDto(UserCreateDTO dto) throws MessagingException, IOException {
        Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person = personRepository.save(person);
        User user = new User();
        user.setPerson(person);
        user.setUsername(dto.getUsername());
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
}
