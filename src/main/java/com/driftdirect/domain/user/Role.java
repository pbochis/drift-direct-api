package com.driftdirect.domain.user;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Created by Paul on 11/15/2015.
 */
@Entity
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue
    private long id;

    private String authority;

    public Role(){}

    public Role(String authority){
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
