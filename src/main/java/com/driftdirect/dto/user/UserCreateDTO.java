package com.driftdirect.dto.user;

import com.driftdirect.dto.person.PersonCreateDto;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Paul on 11/11/2015.
 */
public class UserCreateDTO {
    private String username;

    private String password;
    @NotNull
    @NotEmpty
    private String email;

    //TODO: refactor this to be a long. This is not good code and also not cool. Fuck me.
    private PersonCreateDto person;

    @NotNull
    private Long role;

    public PersonCreateDto getPerson() {
        return person;
    }

    public void setPerson(PersonCreateDto person) {
        this.person = person;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
}
