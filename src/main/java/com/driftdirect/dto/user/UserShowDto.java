package com.driftdirect.dto.user;

import com.driftdirect.domain.user.Role;

import java.util.Set;

/**
 * Created by Paul on 11/16/2015.
 */
public class UserShowDto {
    private String username;
    private String email;

    private Set<String> roles;

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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}