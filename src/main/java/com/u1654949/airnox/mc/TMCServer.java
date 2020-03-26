package com.u1654949.airnox.mc;

import com.u1654949.corba.common.Alarm;
import com.u1654949.corba.common.TLSData;
import com.u1654949.corba.ls.TLS;
import com.u1654949.corba.mc.MCS;
import com.u1654949.corba.mc.MCSHelper;
import com.u1654949.corba.mc.MCSPOA;
import com.u1654949.airnox.common.Constants;

import org.omg.CORBA.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

class TMCDriver extends MCSPOA {

    private final String name = Constants.THE_MONITORING_CENTRE;

    /**
     * Returns the name of this TMC.
     *
     * @return a String name
     */
    @Override
    public String name() {
        return name;
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

}

public class TMCServer {
    public static void main(String[] args) {
		try {
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references(Constants.ROOT_POA));
			rootpoa.the_POAManager().activate();

			// create servant and register it with the ORB
			TMCDriver tmcRef = new TMCDriver();

			// get the 'stringified IOR'
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(tmcRef);
			MCS href = MCSHelper.narrow(ref);			

			// Get a reference to the Naming service
			org.omg.CORBA.Object nameServiceObj =
					orb.resolve_initial_references (Constants.NAME_SERVICE);
			if (nameServiceObj == null) {
				System.out.println("nameServiceObj = null");
				return;
			}

			// Use NamingContextExt which is part of the Interoperable
			// Naming Service (INS) specification.
			NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
			if (nameService == null) {
				System.out.println("nameService = null");
				return;
			}

			// bind the Count object in the Naming service			
			NameComponent[] serverName = nameService.to_name(Constants.THE_MONITORING_CENTRE);
			nameService.rebind(serverName, href);

			// wait for invocations from clients
			System.out.println("Server started.  Waiting for clients...");
			orb.run();

		} catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
	}
}

