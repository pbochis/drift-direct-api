package com.driftdirect.util;

/**
 * Created by Paul on 11/11/2015.
 */
public class RestUrls {
    public static final String CHAMPIONSHIP = "/championship";
    public static final String CHAMPIONSHIP_SHORT = "/championship/short";
    public static final String CHAMPIONSHIP_ID = "/championship/{id}";
    public static final String CHAMPIONSHIP_ID_ROUNDS = "/championship/{id}/rounds";
    public static final String CHAMPIONSHIP_ID_DRIVERS = "/championship/{id}/drivers";
    public static final String CHAMPIONSHIP_ID_DRIVERS_ID = "/championship/{championshipId}/drivers/{driverId}";
    public static final String CHAMPIONSHIP_ID_JUDGES = "/championship/{id}/judges";
    public static final String CHAMPIONSHIP_ID_NEWS = "/championship/{id}/news";
    public static final String CHAMPIONSHIP_ID_PUBLISH = "/championship/{id}/publish";

    public static final String ROUND = "/round";
    public static final String ROUND_ID = "/round/{id}";
    public static final String ROUND_ID_EDIT = "/round/{id}/edit";
    public static final String ROUND_ID_REGISTER = "/round/{roundId}/register";
    public static final String ROUND_ID_REGISTER_DESK = "/round/{id}/desk";
    public static final String ROUND_ID_PLAYOFF_START = "/round/{id}/playoff/start";
    public static final String ROUND_ID_PLAYOFF = "/round/{id}/playoffs";
    public static final String ROUND_ID_SCHEDULE = "/round/{id}/schedule";
    public static final String ROUND_ID_HIGHLIGHTS = "/round/{id}/highlights";
    public static final String ROUND_ID_GALLERY = "/round/{id}/gallery";

    public static final String QUALIFIER_ID = "/qualifier/{id}";
    public static final String QUALIFIER_ID_SUBMIT = "/qualifier/{id}/submit/run/{runId}";
    public static final String QUALIFIER_ID_START = "/qualifier/{id}/start";

    public static final String PLAYOFF_ID= "/playoff/{id}";
    public static final String PLAYOFF_ID_START = "/playoff/battle/{battleId}/start";
    public static final String PLAYOFF_ID_SUBMIT = "/playoff/battle/{battleId}/submit";
    public static final String PLAYOFF_BATTLE_ID = "/playoff/battle/{battleId}";

    public static final String USER = "/user";
    public static final String USERS = "/users";

    public static final String ROLES= "/roles";

    public static final String PERSON = "/person";
    public static final String PERSON_ID = "/person/{id}";

    public static final String PERSON_DRIVER_DETAILS = "/person/driver";

    public static final String SPONSOR = "/sponsor";
    public static final String SPONSOR_ID = "/sponspor/{id}";

    public static final String TEAM = "/team";
    public static final String TEAM_ID = "/team/{id}";

    public static final String FILE = "/file";
    public static final String FILE_ID = "/file/{id}";
    public static final String FILE_ID_NAME = "/file/{id}/name";

    public static final String COUNTRY = "/country";

    public static final String GCM_REGISTER = "/gcm/register";
}
