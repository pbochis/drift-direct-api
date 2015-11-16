package com.driftdirect.domain.user;

/**
 * Created by Paul on 11/16/2015.
 */
public enum Authorities {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_JUDGE("ROLE_JUDGE"),
    ROLE_ORGANIZER("ROLE_ORGANIZER");

    private String name;
    private Authorities(String name){
        this.name = name;
    }

    public String toString(){
        return this.name;
    }

}
