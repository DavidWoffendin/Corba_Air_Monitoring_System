package com.u1654949.airnox;

public final class Constants {

    /**
     * Shouldn't ever make a new instance of this class, it's static.
     */
    private Constants(){ }

    /**
     * A default value for an alarm level.
     */
    public static final Integer DEFAULT_ALARM_LEVEL = 100;

    /**
     * A default value for a warning level.
     */
    public static final Integer DEFAULT_WARNING_LEVEL = 50;

    /**
     * The NameService constant.
     */
    public static final String NAME_SERVICE = "NameService";

    /**
     * The name of the Monitoring Centre.
     */
    public static final String THE_MONITORING_CENTRE = "The Monitoring Centre";

    /**
     * The name of the RootPOA to retrieve via the CORBA service.
     */
    public static final String ROOT_POA = "RootPOA";

}
