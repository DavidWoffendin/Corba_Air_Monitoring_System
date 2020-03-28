package com.u1654949.airnox.mc;

import com.u1654949.corba.common.Alarm;
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
import org.omg.CORBA.ORBPackage.InvalidName;
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

    private static final Logger logger = LoggerFactory.getLogger(TMC.class);
    private final HashSet<String> theLocalStations = new HashSet<>();

    private final List<Alarm> alarms = new ArrayList<>();

    private final ORB orb;

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
        // get object reference from the servant
        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(this);
        MCS server_ref = MCSHelper.narrow(ref);

        // Get a reference to the Naming service
        org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references(Constants.NAME_SERVICE);
        if (nameServiceObj == null) {
            logger.error("nameServiceObj = null");
            return;
        }
        // Use NamingContextExt which is part of the Interoperable
        // Naming Service (INS) specification.
        NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
        if (nameService == null) {
            logger.error("nameService = null");
            return;
        }

        // bind the Count object in the Naming service
        NameComponent[] countName = nameService.to_name(Constants.THE_MONITORING_CENTRE);
        nameService.rebind(countName, server_ref);

        // Server has loaded up correctly
        logger.info("The Monitoring Centre is operational.");

        // Wait for invocations from clients upon a new thread
        orb.run();
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
        for(int i = 0; i < size; i++){
            Alarm alarm = alarms.get(i);

            if(alarm.data.equals(tls_data)){
                alarms.remove(i);
                break;
            }
        }

        if(alarms.size() == size - 1){
            logger.info("Removed alarm from sensor #{} in {} region", tls_data.stationData.station_name, tls_data.stationData.region);
        } else {
            logger.warn("Request to remove unknown alarm from sensor #{} in {} region", tls_data.stationData.station_name, tls_data.stationData.region);
        }

    }

    @Override
    public void receive_alarm(Alarm new_alarm) {
        boolean stored = false;

        for(int i = 0, j = alarms.size(); i < j; i++){

            Alarm storedAlert = alarms.get(i);

            if(storedAlert.data.equals(new_alarm.data)){
                alarms.set(i, new_alarm);
                stored = true;
                break;
            }
        }

        if(!stored){
            alarms.add(new_alarm);
        }

        logger.info("Received alarm from sensor #{} in {} region", new_alarm.data.stationData.station_name, new_alarm.data.stationData.region);
    }

    

    @Override
    public boolean register_tls_connection(String name) {
        logger.info("Successfully received connection from TLS `{}`", name);
        theLocalStations.add(name);
        return true;
    }

    @Override
    public boolean remove_tls_connection(String name) {
        logger.info("Removed connection from TLS `{}`", name);
        theLocalStations.remove(name);
        return true;
    }

    @Override
    public Alarm[] get_alarms(String id) {
        return alarms.toArray(new Alarm[alarms.size()]);
    }

    @Override
    public Alarm[] get_County_state(String county) {
        TLS tls = get_connected_tls(county);
        try {
            return tls != null && tls.ping() ? tls.get_current_state() : null;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public String[] get_known_stations() {
        return theLocalStations.toArray(new String[theLocalStations.size()]);
    }

    @Override
    public TLS get_connected_tls(String name) {

        TLS tempServer = null;

        // Get a reference to the Naming service
        org.omg.CORBA.Object nameServiceObj;
        try {
            nameServiceObj = orb.resolve_initial_references(Constants.NAME_SERVICE);
            NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
            tempServer = TLSHelper.narrow(nameService.resolve_str(name));

        } catch (InvalidName | NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName e) {
            logger.error("nameServiceObj = null" + e);
        }

        return tempServer;
    }

    public ORB getEmbeddedOrb(){
        return this.orb;
    }
}



