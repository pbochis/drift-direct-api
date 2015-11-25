package com.driftdirect.dto.user;

import com.driftdirect.domain.user.Role;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * Created by Paul on 11/11/2015.
 */
public class UserCreateDTO {
    private String username;

    @NotNull
    @Size(min = 5, max = 12)
    private String password;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @Size(min=4, max = 50)
    private String firstName;

    @NotNull
    @Size(min=4, max = 50)
    private String lastName;


    @Size(min = 1)
    private Set<String> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
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
}
