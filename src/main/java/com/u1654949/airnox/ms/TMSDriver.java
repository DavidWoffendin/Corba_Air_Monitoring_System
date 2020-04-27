package com.u1654949.airnox.ms;

import java.util.ArrayList;
import java.util.List;

import com.u1654949.airnox.Constants;
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

	private final ORB orb;
	private final List<NoxReading> noxReadingLog = new ArrayList<>();

	private static final Logger logger = LoggerFactory.getLogger(TMS.class);

	private static TLS tls;
	private static TLSData tlsData;
	private static NoxReading currentReading;
	private static Boolean status = true;
	private static String name;

	/**
     * This code is based upon the client/server code provided by Gary Allen
     * 
     * Name: Gary Allen
     * Source: https://github.com/GaryAllenGit/Jacorb_NamingServiceDemo/blob/master/src/CountPortableServer.java
     * Commit: 0559c3c
     * 
     * @param args program arguments passed from client
     * @throws Exception exception for orb
     */
	public TMSDriver(String[] args, String tlsName, String zoneName) throws Exception {
		// Logs the beginning of the connection process
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
		org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references(Constants.NAME_SERVICE);
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

		// Register the Sensor with the TLS
		final MSData msData = tls.register_tms(zoneName);

		// create and store the current metadata
		tlsData = new TLSData(tls.name(), tls.location(), msData);

		// set the station name
		name = msData.station_name + "_" + msData.region + "_" + tlsName;

		// bind the Count object in the Naming service
		NameComponent[] sName = nameService.to_name(name);
		nameService.rebind(sName, server_ref);

		// Log the successful connection
		logger.info("Connected and assigned name: " + name + " by TLS.");

		// Shutdown hook to remove tms from tls
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					if (!tls.remove_tms(msData)) {
						logger.error("Error: unable to unregister from TLS!");
					}
				} catch (Exception e) {
					logger.error("Error: unable to unregister from TLS!");
				}
			}
		});
	}

	
	
	/** 
	 * Function to return the station name
	 * 
	 * @return String
	 */
	@Override
	public String station_name() {
		return name;
	}

	
	/** 
	 * Function to return the station region
	 * 
	 * @return String
	 */
	@Override
	public String location() {
		return tlsData.stationData.region;
	}

	
	/** 
	 * Function to deactivate the station
	 * 
	 * @return boolean
	 */
	@Override
	public boolean deactivate() {
		if (status) {
			status = false;
			logger.info("Monitoring Station is now deactivated");
			return true;
		}
		return false;
	}

	
	/** 
	 * Function to activate the station
	 * 
	 * @return boolean
	 */
	@Override
	public boolean activate() {
		if (!status) {
			status = true;
			logger.info("Monitoring Station is now reactivated");
			return true;
		}
		return false;
	}

	
	/** 
	 * Function to reset the station log
	 * 
	 * @return boolean
	 */
	@Override
	public boolean reset() {
		currentReading = null;
		noxReadingLog.clear();
		logger.info("Monitoring Station is reset");
		return true;

	}

	
	/** 
	 * Function to send reading to the connected tls
	 * 
	 * @param measurement
	 */	
	public void send_Reading(int measurement) {
		// check is system is disconnected
		if (!status) {
			System.err.println("System is disconnected");
			return;
		}
		// generate a noxReading object from measurement
		NoxReading noxReading = new NoxReading(System.currentTimeMillis(), measurement, tlsData.stationData.region,
				tlsData.stationData.station_name, tlsData.tls_name);

		// generated tls object to send to add to alarm
		TLSData config = new TLSData(tls.name(), tls.location(), tlsData.stationData);
		// send an alarm to tls
		tls.receive_alarm(new Alarm(config, noxReading));

		// Determine whether the alarm is dangerous or not and add alarm to log.
		currentReading = noxReading;
		noxReadingLog.add(noxReading);
		if (measurement > tlsData.stationData.alarm_level) {
			System.err.println(
					"Reading is above alarm level of " + tlsData.stationData.alarm_level + " at " + measurement + "!");
		} else {
			System.out.println("Registered new reading: " + measurement);
		}
	}

	
	/** 
	 * Function to return the last reading
	 * 
	 * @return NoxReading
	 */
	@Override
	public NoxReading get_reading() {
		return currentReading;
	}

	
	/** 
	 * Function to return the log of readings
	 * 
	 * @return NoxReading[]
	 */
	@Override
	public NoxReading[] get_reading_log() {
		return noxReadingLog.toArray(new NoxReading[noxReadingLog.size()]);
	}
}
