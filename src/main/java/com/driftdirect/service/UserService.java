package com.driftdirect.service;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.person.PersonType;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.user.UserCreateDTO;
import com.driftdirect.dto.user.UserShowDto;
import com.driftdirect.mapper.UserMapper;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/10/2015.
 */
@Service
public class UserService {
    private final String USER_NOTIFY_EMAIL_TEMPLATE = "/template/templateAccountCreated.html";
    private UserRepository userRepository;
    private PersonService personService;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private MailSenderService mailSenderService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PersonService personService,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       MailSenderService mailSenderService){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
        this.personService = personService;
    }

    public User createFromDto(UserCreateDTO dto, Long personId) throws IOException, MessagingException {
        Person person = personService.findOne(personId);
        return createUser(dto, person);
    }

    public List<UserShowDto> getUsers(){
        return userRepository.findAll()
                .stream()
                .map(UserMapper::map)
                .collect(Collectors.toList());
    }

    public User createFromDto(UserCreateDTO dto) throws MessagingException, IOException {
        Role role = roleRepository.findOne(dto.getRole());
        Person person = personService.createFromDto(dto.getPerson());
        if (role.getAuthority().equals(Authorities.ROLE_JUDGE)) {
            person.setPersonType(PersonType.Judge);
        }
        if (role.getAuthority().equals(Authorities.ROLE_ORGANIZER)) {
            person.setPersonType(PersonType.Organizer);
        }
        return createUser(dto, person);
    }

    private User createUser(UserCreateDTO dto, Person person) throws IOException, MessagingException {
        User user = new User();
        user.setPerson(person);
        if (dto.getUsername() != null && !dto.getUsername().equals("")){
            user.setUsername(dto.getUsername());
        }else{
            user.setUsername(dto.getEmail());
        }
        String password = dto.getPassword();
        if (password == null){
            password = RandomStringUtils.random(8, true, true);
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(dto.getEmail());
        user.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findOne(dto.getRole()));
        user.setRoles(roles);
        notifyNewUser(user.getEmail(), password);
        return userRepository.save(user);

    }

    private void notifyNewUser(String email, String password) throws MessagingException, IOException {
        String[] args = new String[]{email, password};
        mailSenderService.sendWithTemplate(USER_NOTIFY_EMAIL_TEMPLATE, args, email, "Account created");
    }
}
