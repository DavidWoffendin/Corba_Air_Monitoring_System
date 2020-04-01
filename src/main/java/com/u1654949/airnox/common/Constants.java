package com.u1654949.airnox.common;

public final class Constants {

    /**
     * Shouldn't ever make a new instance of this class, it's static.
     */
    private Constants(){ }

    /**
     * A default value for an alert level if none are previously set.
     */
    public static final Integer DEFAULT_ALERT_LEVEL = 60;

    /**
     * A default value for a warning level if none are previously set.
     */
    public static final Integer DEFAULT_WARNING_LEVEL = 40;

    /**
     * The NameService constant, in order to avoid any typos when retrieving.
     */
    public static final String NAME_SERVICE = "NameService";

    /**
     * The name of the Monitoring Centre, to avoid typos.
     */
    public static final String THE_MONITORING_CENTRE = "The Monitoring Centre";

    /**
     * The name of the RootPOA to retrieve via the CORBA service.
     */
    public static final String ROOT_POA = "RootPOA";

}
