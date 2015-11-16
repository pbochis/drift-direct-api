package com.driftdirect.domain.user;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Created by Paul on 11/15/2015.
 */
@Entity
public class Role implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Role(){}
    public Role(Authorities authorities){
        this.authority = authorities;
    }

    @Enumerated(value = EnumType.STRING)
    private Authorities authority;

    @Override
    public String getAuthority() {
        return authority.name();
    }
}
