package com.driftdirect.service;

import com.driftdirect.domain.User;
import com.driftdirect.dto.UserCreateDTO;
import com.driftdirect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Paul on 11/10/2015.
 */
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createFromDto(UserCreateDTO dto) throws Exception {
        User existing= userRepository.findUserByName(dto.getUsername());
        if (existing != null){
            throw new Exception("existing username");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        return save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public User save(User user){
        return userRepository.save(user);
    }
}
