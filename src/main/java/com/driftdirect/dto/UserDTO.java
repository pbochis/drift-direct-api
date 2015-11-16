package com.driftdirect.dto;

import com.driftdirect.domain.user.Role;

import java.util.Set;

/**
 * Created by Paul on 11/16/2015.
 */
public class UserDTO {
    private String username;
    private String email;

    private Set<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
