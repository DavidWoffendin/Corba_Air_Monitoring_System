package com.u1654949.airnox.ls;

import com.u1654949.airnox.common.Constants;
import com.u1654949.corba.common.Alarm;
import com.u1654949.corba.common.MSData;
import com.u1654949.corba.ls.TLS;
import com.u1654949.corba.ls.TLSHelper;
import com.u1654949.corba.ls.TLSPOA;
import com.u1654949.corba.mc.MCS;
import com.u1654949.corba.mc.MCSHelper;

import org.omg.CORBA.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

class TLSDriver extends TLSPOA {

    private ORB orb;
    private MCS server;
    private String name;

    TLSDriver(ORB orb_val, String name){
        // store reference to ORB
        orb = orb_val;
        this.name = name;

		// look up the server
		try {

			// Get a reference to the Naming service
			org.omg.CORBA.Object nameServiceObj =
					orb.resolve_initial_references (Constants.NAME_SERVICE);
			if (nameServiceObj == null) {
				System.out.println("nameServiceObj = null");
				return;
			}

			// Use NamingContextExt instead of NamingContext. This is
			// part of the Interoperable naming Service.
			NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
			if (nameService == null) {
				System.out.println("nameService = null");
				return;
            }
            			
			server = MCSHelper.narrow(nameService.resolve_str(Constants.THE_MONITORING_CENTRE));
			
		} catch (Exception e) {
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		}

    }

    /**
     * Retrieves the name of this TLS.
     *
     * @return the String name
     */
    @Override
    public String name() {
        server.ping();   
        return name;
    }

    /**
     * Simple accessor method to check connection.
     *
     * @return true
     */
    @Override
    public Alarm[] alarm_log() {
        return null;
    }

    @Override
    public boolean ping() {        
        return true;
    }

    @Override
    public Alarm[] get_current_state() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MSData[] get_registered_tms() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MSData register_tms(String region) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void receive_alarm(Alarm new_alarm) {
        // TODO Auto-generated method stub
        server.ping();

    }

    @Override
    public boolean remove_tms(MSData data) {
        // TODO Auto-generated method stub
        return false;
    }    
}

public class TLSServer {
    public static void main(String[] args) {
		try {
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references(Constants.ROOT_POA));
            rootpoa.the_POAManager().activate();
            
            String name = "Relay";

			// create servant and register it with the ORB
			TLSDriver tlsRef = new TLSDriver(orb, name);

			// Get the 'stringified IOR'
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(tlsRef);
			TLS tref = TLSHelper.narrow(ref);			

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
			NameComponent[] countName = nameService.to_name(name);
			nameService.rebind(countName, tref);


			// wait for invocations from clients
			System.out.println("Relay started.  Waiting for clients...");
			orb.run();

		} catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
	}
}