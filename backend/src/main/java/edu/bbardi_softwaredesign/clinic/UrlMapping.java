package edu.bbardi_softwaredesign.clinic;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String CLINIC = API_PATH + "/clinic";
    public static final String ENTITY = "/{id}";

    //patient paths
    public static final String PATIENT = CLINIC + "/patient";

    //users paths
    public static final String USER = CLINIC + "/user";

    //consult paths
    public static final String CONSULT = CLINIC + "/consult";

    //notifications paths
    public static final String WEBSOCK = CLINIC + "/notifications";
    public static final String USER_PREFIX= "/user";
    public static final String MESSAGE = "/message";
    //planning paths
    public static final String PLANNING = CLINIC + "/planning";
    public static final String DOCTORS = "/doctors";
    public static final String SLOTS = "/slots";
    //auth paths
    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign_in";
    public static final String SIGN_UP = "/sign_up";
}
