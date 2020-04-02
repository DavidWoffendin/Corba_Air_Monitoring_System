package com.u1654949.airnox.mc;

import com.u1654949.corba.common.Alarm;
import com.u1654949.corba.common.NoxReading;
import com.u1654949.corba.common.TLSData;
import com.u1654949.corba.ls.TLS;
import com.u1654949.corba.ls.TLSHelper;
import com.u1654949.corba.mc.MCS;
import com.u1654949.corba.mc.MCSHelper;
import com.u1654949.corba.mc.MCSPOA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.u1654949.airnox.common.Constants;

import org.omg.CORBA.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TMCDriver extends MCSPOA {

    private static final Logger logger = LoggerFactory.getLogger(MCS.class);
    private final HashSet<String> theLocalServers = new HashSet<>();
    private final HashSet<String> serverLocations = new HashSet<>();
    private NamingContextExt nameService;
    private final List<Alarm> alarms = new ArrayList<>();
    private final ORB orb;
    private HashSet<Agency> agencies = new HashSet<>();

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

    }

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

    @Override
    public boolean register_tls_connection(String name) {
        logger.info("Successfully received connection from TLS `{}`", name);
        theLocalServers.add(name);
        TLS tempServer = get_connected_tls(name);
        serverLocations.add(tempServer.location());
        return true;
    }

    @Override
    public boolean remove_tls_connection(String name) {
        logger.info("Removed connection from TLS `{}`", name);
        theLocalServers.remove(name);
        return true;
    }

    public Alarm[] get_alarms(String id) {
        return alarms.toArray(new Alarm[alarms.size()]);
    }

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

    public String[] get_known_servers() {
        return theLocalServers.toArray(new String[theLocalServers.size()]);
    }

    public String[] get_known_locations() {
        return serverLocations.toArray(new String[serverLocations.size()]);
    }

    public TLS get_connected_tls(String name) {
        TLS tempServer = null;
        try {
            tempServer = TLSHelper.narrow(nameService.resolve_str(name));
        } catch (NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName e) {
            logger.error("nameServiceObj = null" + e);
        }
        return tempServer;
    }

    public void register_agency(String name, String email, String location) {
        Agency newAgency = new Agency(name, email, location);
        agencies.add(newAgency);
    }
}

class Agency {

    private String name;
    private String email;
    private String location;

    public Agency(String name, String email, String location) {
        this.name = name;
        this.email = email;
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getLocation() {
        return this.location;
    }

}
