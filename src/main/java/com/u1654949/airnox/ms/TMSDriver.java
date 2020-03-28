package com.u1654949.airnox.ms;

import java.util.ArrayList;
import java.util.List;

import com.u1654949.airnox.common.Constants;
import com.u1654949.airnox.common.InputReader;
import com.u1654949.corba.common.Alarm;
import com.u1654949.corba.common.MSData;
import com.u1654949.corba.common.NoxReading;
import com.u1654949.corba.common.TLSData;
import com.u1654949.corba.ls.TLS;
import com.u1654949.corba.ls.TLSHelper;
import com.u1654949.corba.ms.TMS;
import com.u1654949.corba.ms.TMSHelper;
import com.u1654949.corba.ms.TMSPOA;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TMSDriver extends TMSPOA {

	private final List<NoxReading> noxReadingLog = new ArrayList<>();

	private static final Logger logger = LoggerFactory.getLogger(TMS.class);    
	private static TLS tls;
	private static InputReader console;

	private TLSData tlsData;
	private final ORB orb;

	private NoxReading currentReading;
	private Boolean status;
	private String name;

	public TMSDriver(String[] args) throws Exception {

		status = true;

		console = new InputReader(System.in);		
		
		String tlsName = console.readString("Please enter the local station name: ");

		String zoneName = console.readString("Please enter the monitoring station zone: ");

		logger.info("Sensor of zone {} connecting to LMS: {}", zoneName, tlsName);

		// Initialize the ORB
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
		TMS server_ref = TMSHelper.narrow(ref);

		// Get a reference to the Naming service
		org.omg.CORBA.Object nameServiceObj =
                        orb.resolve_initial_references (Constants.NAME_SERVICE);
        if (nameServiceObj == null) {
            logger.error("nameServiceObj = null");
            return;
		}
		
		// Use NamingContextExt instead of NamingContext. This is
		// part of the Interoperable naming Service.
		NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
		if (nameService == null) {
			logger.error("nameService = null");
			return;
		}

		// resolve the server object reference in the Naming service
		tls = TLSHelper.narrow(nameService.resolve_str(tlsName));

		tls.ping();

		// Register the Sensor with the TLS
		final MSData msData = tls.register_tms(zoneName);

		// set the station name
		name = msData.station_name + msData.region;

		// bind the Count object in the Naming service
		NameComponent[] sName = nameService.to_name(name);
		nameService.rebind(sName, server_ref);	

		// create and store the current metadata
		tlsData = new TLSData(tlsName, msData);		
	}

	@Override
	public String station_name() {		
		return name;
	}

	@Override
	public String location() {		
		return tlsData.stationData.region;
	}

	@Override
	public boolean deactivate() {
		if(status){
            tls.remove_tms(tlsData.stationData);
            status = false;
            return true;
        }
        return false;
	}

	@Override
	public boolean activate() {
		if(!status){
            try {
                tlsData.stationData = tls.register_tms(tlsData.stationData.region);
            } catch (Exception e) {
                logger.error("Error occurred: " + e);
            	e.printStackTrace();
            }
            status = true;
            return true;
        }
        return false;
	}

	@Override
	public void reset() {
		currentReading = null;
        noxReadingLog.clear();

	}

	@Override
	public void send_alarm(int measurement) {
		if(!status){
            System.err.println("System is disconnected");
            return;
		}
        NoxReading noxReading = new NoxReading(System.currentTimeMillis(), measurement);
        try {
			logger.info("ping"); 
			tls.ping();
			logger.info("pong");    
        } catch(Exception e) {
            System.err.println("TLS `" + tlsData.tls_name + "` is unreachable!");
            return;
		}
		
		TLSData config = new TLSData(tls.name(), tlsData.stationData);
        tls.receive_alarm(new Alarm(config, noxReading));

        currentReading = noxReading;
        noxReadingLog.add(noxReading);
        if(measurement > tlsData.stationData.alarm_level){
            System.err.println("Reading is above alarm level of " + tlsData.stationData.alarm_level + " at " + measurement + "!");
        } else {
            System.out.println("Registered new reading: " + measurement);
        }
	}

	@Override
	public NoxReading get_reading() {
		return currentReading;
	}

	@Override
	public NoxReading[] get_reading_log() {
		return noxReadingLog.toArray(new NoxReading[noxReadingLog.size()]);
	}
}
