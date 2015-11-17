package com.driftdirect.util;

/**
 * Created by Paul on 11/11/2015.
 */
public class Routes {
    public static final String CHAMPIONSHIP = "/championship";
    public static final String ROUND = "/round";
    public static final String USER = "/user";


    public static final String ID = "{id}";

    public static class Championship {
        public static final String ROUNDS = "/{id}/rounds";
    }

    public static class Round {
        public static final String ID = "/{id}";
    }
}
