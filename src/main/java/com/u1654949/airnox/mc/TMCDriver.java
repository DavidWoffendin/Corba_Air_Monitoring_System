package com.u1654949.airnox.mc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.u1654949.airnox.Constants;
import com.u1654949.corba.common.Alarm;
import com.u1654949.corba.common.NoxReading;
import com.u1654949.corba.common.TLSData;
import com.u1654949.corba.ls.TLS;
import com.u1654949.corba.ls.TLSHelper;
import com.u1654949.corba.mc.MCS;
import com.u1654949.corba.mc.MCSHelper;
import com.u1654949.corba.mc.MCSPOA;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TMCDriver extends MCSPOA {

    private static final Logger logger = LoggerFactory.getLogger(MCS.class);
    
    private final HashSet<String> theLocalServers = new HashSet<>();
    private final HashSet<String> serverLocations = new HashSet<>();    
    private final List<Alarm> alarms = new ArrayList<>();
    private final ORB orb;

    private static NamingContextExt nameService;
    private static HashSet<Agency> agencies = new HashSet<>();

    public TMCDriver(String[] args) throws Exception {

        // create and initialise the ORB
        orb = ORB.init(args, null);

        // get reference to rootpoa & activate the POAManager
        POA rootpoa = POAHelper.narrow(orb.resolve_initial_references(Constants.ROOT_POA));
        if (rootpoa != null) {
            rootpoa.the_POAManager().activate();
        } else {
            logger.error("Unable to retrieve POA!");
            return;
        }

        // Get a reference to the Naming service
        org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references(Constants.NAME_SERVICE);
        if (nameServiceObj == null) {
            logger.error("nameServiceObj = null");
            return;
        }

        // Use NamingContextExt which is part of the Interoperable
        // Naming Service (INS) specification.
        nameService = NamingContextExtHelper.narrow(nameServiceObj);
        if (nameService == null) {
            logger.error("nameService = null");
            return;
        }

        // get object reference from the servant
        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(this);
        MCS server_ref = MCSHelper.narrow(ref);

        // bind the Count object in the Naming service
        NameComponent[] countName = nameService.to_name(Constants.THE_MONITORING_CENTRE);
        nameService.rebind(countName, server_ref);

        // Server has loaded up correctly
        logger.info("The Monitoring Centre is operational.");
    }

    /**
     * Returns the name of this TMC.
     *
     * @return a String name
     */
    @Override
    public String name() {
        return Constants.THE_MONITORING_CENTRE;
    }

    /**
     * Simply returns true to the caller. Used simply as a connection test between
     * components.
     *
     * @return true
     */
    @Override
    public boolean ping() {
        return true;
    }

    
    /** 
     * This function takes a tls data object and cancels any alarms linked to it
     * This function is called and a tls wished to cancel an alarm
     * This function also informs any agencys that the alarm is cancelled
     * 
     * @param tls_data from tls station
     */
    @Override
    public void cancel_alarm(TLSData tls_data) {

        int size = alarms.size();
        for (int i = 0; i < size; i++) {
            Alarm alarm = alarms.get(i);

            if (alarm.data.equals(tls_data)) {
                alarms.remove(i);
                break;
            }
        }

        if (alarms.size() == size - 1) {
            logger.info("Removed alarm from sensor #{} in {} region", tls_data.stationData.station_name,
                    tls_data.stationData.region);
        }
        for (Agency tempAgency : agencies) {
            if (tls_data.tls_location.equals(tempAgency.getLocation())) {
                logger.info("Alerting agency: {} on email {} about alarm in area {} has been cancelled", tempAgency.getName(),
                        tempAgency.getEmail(), tempAgency.getLocation());
            }
        }

    }

    
    /** 
     * This method is called by a tls to provide a tmc with an alarm
     * The function stores the alarm if it is already not accounted for
     * The function then notifies any agencies register to the area
     * 
     * @param new_alarm from tls
     */
    @Override
    public void receive_alarm(Alarm new_alarm) {
        boolean stored = false;

        for (int i = 0, j = alarms.size(); i < j; i++) {

            Alarm storedAlert = alarms.get(i);

            if (storedAlert.data.equals(new_alarm.data)) {
                alarms.set(i, new_alarm);
                stored = true;
                break;
            }
        }

        if (!stored) {
            alarms.add(new_alarm);
        }

        logger.info("Received alarm from sensor #{} in {} region", new_alarm.data.stationData.station_name,
                new_alarm.data.stationData.region);

        for (Agency tempAgency : agencies) {
            if (new_alarm.data.tls_location.equals(tempAgency.getLocation())) {
                logger.info("Alerting agency: {} on email {} about alarm in area {}", tempAgency.getName(),
                        tempAgency.getEmail(), tempAgency.getLocation());
            }
        }

    }

    
    /** 
     * Function adds a tls to the stored list of tls's
     * 
     * @param name of tls
     * @return boolean if successful or not
     */
    @Override
    public boolean register_tls_connection(String name) {
        logger.info("Successfully received connection from TLS `{}`", name);
        theLocalServers.add(name);
        TLS tempServer = get_connected_tls(name);
        serverLocations.add(tempServer.location());
        return true;
    }

    
    /** 
     * Function removes a tls from the stored list of tls's
     * 
     * @param name of tls
     * @return boolean if successful or not
     */
    @Override
    public boolean remove_tls_connection(String name) {
        logger.info("Removed connection from TLS `{}`", name);
        theLocalServers.remove(name);
        return true;
    }

    
    /** 
     * Returns all current alarms
     * 
     * @return Alarm[] returns an array of alarms
     */
    public Alarm[] get_alarms() {
        return alarms.toArray(new Alarm[alarms.size()]);
    }

    
    /** 
     * Function polls all local servers to poll all their monitoring station
     * the result is an array of arrays of nox readings from each local server
     * 
     * @return NoxReading[][] of all collected nox readings
     */
    public NoxReading[][] get_local_station_readings() {
        NoxReading[][] noxReadings = new NoxReading[theLocalServers.size()][];
        int size = 0;
        for (String station : theLocalServers) {
            System.out.println(station);
            TLS tempServer = get_connected_tls(station);
            System.out.println(tempServer.name());
            NoxReading[] tempReadings = tempServer.take_readings();
            noxReadings[size] = tempReadings;
            size++;
        }
        return noxReadings;
    }

    
    /** 
     * Returns all know servers
     * 
     * @return String[] of known servers
     */
    public String[] get_known_servers() {
        return theLocalServers.toArray(new String[theLocalServers.size()]);
    }

    
    /** 
     * Returns all know locations
     * 
     * @return String[] of locations
     */
    public String[] get_known_locations() {
        return serverLocations.toArray(new String[serverLocations.size()]);
    }

    /** 
     * Returns a retrieved TLS object from a given name 
     * 
     * @param name of a connected tms
     * @return TLS object of a connected tms
     */
    public TLS get_connected_tls(String name) {
        TLS tempServer = null;
        try {
            tempServer = TLSHelper.narrow(nameService.resolve_str(name));
        } catch (NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName e) {
            logger.error("nameServiceObj = null" + e);
        }
        return tempServer;
    }

    
    /** 
     * Register agency function and add to array of agencys
     * 
     * @param name
     * @param email
     * @param location
     */
    public void register_agency(String name, String email, String location) {
        Agency newAgency = new Agency(name, email, location);
        agencies.add(newAgency);
    }
}

/**
 * Agency Object for storing agency data
 */
class Agency {

    private String name;
    private String email;
    private String location;

    /** 
     * Agency Constructor
     * 
     * @param name of agency
     * @param email of agency
     * @param location of agency
     */
    public Agency(String name, String email, String location) {
        this.name = name;
        this.email = email;
        this.location = location;
    }

    
    /** 
     * @return String name of agency
     */
    public String getName() {
        return this.name;
    }

    
    /** 
     * @return String email of agency
     */
    public String getEmail() {
        return this.email;
    }

    
    /** 
     * @return String location of agency
     */
    public String getLocation() {
        return this.location;
    }

}
