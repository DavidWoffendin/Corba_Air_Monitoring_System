package com.u1654949.airnox.mc;

import com.u1654949.corba.common.Alarm;
import com.u1654949.corba.common.TLSData;
import com.u1654949.corba.ls.TLS;
import com.u1654949.corba.mc.MCS;
import com.u1654949.corba.mc.MCSHelper;
import com.u1654949.corba.mc.MCSPOA;

import java.util.ArrayList;
import java.util.List;

import com.u1654949.airnox.common.Constants;

import org.omg.CORBA.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TMCDriver extends MCSPOA {

    private static final Logger logger = LoggerFactory.getLogger(TMC.class);

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
	    org.omg.CORBA.Object nameServiceObj =
        orb.resolve_initial_references (Constants.NAME_SERVICE);
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
     * Simply returns true to the caller. Used simply as a connection
     * test between components.
     *
     * @return true
     */
    @Override
    public boolean ping() {        
        return true;
    }

    @Override
    public void cancel_alarm(TLSData tls_data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void receive_alarm(Alarm new_alarm) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean register_mcc(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean remove_mcc(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean register_tls_connection(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean remove_tls_connection(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Alarm[] get_alarms(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Alarm[] get_region_state(String region) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] get_known_stations() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TLS get_connected_tls(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public ORB getEmbeddedOrb(){
        return this.orb;
    }
}



