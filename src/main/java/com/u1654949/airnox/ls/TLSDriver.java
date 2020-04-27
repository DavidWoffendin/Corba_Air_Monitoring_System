package com.u1654949.airnox.ls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.u1654949.airnox.Constants;
import com.u1654949.corba.common.Alarm;
import com.u1654949.corba.common.MSData;
import com.u1654949.corba.common.NoxReading;
import com.u1654949.corba.common.TLSData;
import com.u1654949.corba.ls.TLS;
import com.u1654949.corba.ls.TLSHelper;
import com.u1654949.corba.ls.TLSPOA;
import com.u1654949.corba.mc.MCS;
import com.u1654949.corba.mc.MCSHelper;
import com.u1654949.corba.ms.TMS;
import com.u1654949.corba.ms.TMSHelper;

import org.omg.CORBA.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TLSDriver extends TLSPOA {   

    private final ORB orb;
    private final ConcurrentSkipListMap<String, ConcurrentSkipListMap<String, NoxReading>> regionMapping = new ConcurrentSkipListMap<>();
    private final ConcurrentSkipListMap<String, Alarm> alarmStates = new ConcurrentSkipListMap<>();
    private final HashSet<String> theMonitoringStations = new HashSet<>();

    private static final List<Alarm> readingLog = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(TLS.class);

    private static HashMap<String, Levels> levels;
    private static MCS tmc;
    private static String name;
    private static String location;
    private static NamingContextExt nameService;       

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
    public TLSDriver(String[] args, String sName, String sLocation) throws Exception {
        levels = setLevel();
        
        name = sName;
        location = sLocation;

        logger.info("Registered Local Monitoring Station: {}", name);

        // Initialise the ORB
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
        TLS server_ref = TLSHelper.narrow(ref);

        // resolve the server object reference in the Naming service
        tmc = MCSHelper.narrow(nameService.resolve_str(Constants.THE_MONITORING_CENTRE));

        // bind the Count object in the Naming service
        NameComponent[] countName = nameService.to_name(name);
        nameService.rebind(countName, server_ref);

        // test out the RMC connection
        if (!tmc.register_tls_connection(name)) {
            throw new IllegalStateException("TMC Connection failed!");
        } else {
            logger.info("Made successful connection to TMC");
        }

        // Shutdown hook to remove tms from tls
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					if (!tmc.remove_tls_connection(name)) {
						logger.error("Error: unable to unregister from TMC!");
					}
				} catch (Exception e) {
					logger.error("Error: unable to unregister from TMC!");
				}
			}
		});
    }

    /**
     * Retrieves the name of this TLS.
     *
     * @return the String name
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Retrieves the location of this TLS.
     *
     * @return the String location
     */
    @Override
    public String location() {
        return location;
    }

    /**
     * returns the log of stored alarms
     *
     * @return Alarm[] array of all known alarms
     */
    @Override
    public Alarm[] alarm_log() {
        return readingLog.toArray(new Alarm[readingLog.size()]);
    }
    
    /** 
     * Retrieve current active alarms on this tls
     * 
     * @return Alarm[] array of active alarms
     */
    @Override
    public Alarm[] get_current_state() {
        List<Alarm> currentStates = new ArrayList<>();
        for (Map.Entry<String, Alarm> region : alarmStates.entrySet()) {
            currentStates.add(region.getValue());
        }
        return currentStates.toArray(new Alarm[currentStates.size()]);
    }

    
    /** 
     * Returns an array of known connected tms's
     * 
     * @return String[] array of known station names
     */
    @Override
    public String[] get_known_stations() {
        return theMonitoringStations.toArray(new String[theMonitoringStations.size()]);

    }

    
    /** 
     * Method to regiser a tms and its assosiated sub region
     * This method also generates the monitoring stations unique name
     * This also assigns the monitoring station with the default alarm levels
     * 
     * 
     * @param region the region an monitoring station is occupying
     * @return MSData msdata object the monitoring stations need to know about itself
     */
    @Override
    public MSData register_tms(String region) {
        final NoxReading noxReading = new NoxReading();
        final String id;

        if (regionMapping.containsKey(region)) {
            ConcurrentSkipListMap<String, NoxReading> regionMap = regionMapping.get(region);

            id = (regionMap.size() + 1) + "";

            regionMap.put(id, noxReading);
        } else {
            id = "1";
            regionMapping.put(region, new ConcurrentSkipListMap<String, NoxReading>() {
                private static final long serialVersionUID = -3077591726878396738L;
                {
                    put(id, noxReading);
                }
            });
        }
        logger.info("Added Sensor #{} to {}", id, region);

        Levels sensorLevels;
        if (levels.containsKey(region)) {
            sensorLevels = levels.get(region);
        } else {
            sensorLevels = levels.get("default");
        }

        String stationName = id + "_" + region + "_" + name;

        theMonitoringStations.add(stationName);
        return new MSData(region, id, sensorLevels.getAlarmLevel());
    }

    
    /** 
     * Remote function to recieve an alarm called by a monitoring station
     * The method processes the alarm adding it to relevent arrays and hashmaps
     * The method then works out an average of all monitoring station within a sub region
     * if the alarm goes above the region alarm level it alerts the monitoring centre
     * if a reading causes the avg to go below the regions alarm level it cancels the monitoring centers alarm
     * 
     * @param new_alarm takes an alarm from the monitoring station
     */
    @Override
    public void receive_alarm(Alarm new_alarm) {
        logger.info("Received reading from Monitoring Station #{} in region `{}`",
                new_alarm.data.stationData.station_name, new_alarm.data.stationData.region);

        int alarm_level = getLevelsForRegion(levels, new_alarm.data.stationData.region).getAlarmLevel();
        ConcurrentSkipListMap<String, NoxReading> region = regionMapping.get(new_alarm.data.stationData.region);
        boolean alert = false;

        if (new_alarm.reading.reading_value > alarm_level) {
            logger.warn("Registered alarm {} from Monitoring Station #{} in region {}", new_alarm.reading.reading_value,
                    new_alarm.data.stationData.station_name, new_alarm.data.stationData.region);
            for (Map.Entry<String, NoxReading> regionMap : region.entrySet()) {
                if (regionMap.getValue().reading_value > alarm_level) {
                    long time = (regionMap.getValue().time - new_alarm.reading.time)/1000;
                    if (time < 30) {
                        System.out.println("Alerting Monitoring Centre");
                        alert = true;
                    }
                }     
            }
        } else {
            logger.info("Registered reading {} from Monitoring Station #{} in region {}",
                    new_alarm.reading.reading_value, new_alarm.data.stationData.station_name,
                    new_alarm.data.stationData.region);
        }

        readingLog.add(new_alarm);
        region.put(new_alarm.data.stationData.station_name, new_alarm.reading);  
        
        if ((alert)) {            
            logger.warn("Multiple alarms found in region: `{}`, forwarding to TMC...",
                    new_alarm.data.stationData.region);            
            tmc.receive_alarm(new_alarm);
            alarmStates.put(new_alarm.data.stationData.region,
                    new Alarm(new_alarm.data, new NoxReading(new_alarm.reading.time, new_alarm.reading.reading_value,
                            new_alarm.data.stationData.region, new_alarm.data.stationData.station_name, name)));
        } else {            
            if (alarmStates.containsKey(new_alarm.data.stationData.region)) {
                tmc.cancel_alarm(new TLSData(name, location, new_alarm.data.stationData));
                logger.info("Removed alarm state for region `{}`", new_alarm.data.stationData.region);
                alarmStates.remove(new_alarm.data.stationData.region);
            }
        }
    }

    
    /** 
     * method to remove a monitoring station from the tls
     * allows for the unique name to be reassigned
     * 
     * @param data takes an msdata from a monitoring station
     * @return boolean if successfully removed the station
     */
    @Override
    public boolean remove_tms(MSData data) {
        logger.info("Removed Sensor #{} from region `{}`", data.station_name, data.region);
        if (regionMapping.containsKey(data.region)) {
            regionMapping.get(data.region).remove(data.station_name);
            String stationName = data.station_name + "_" + data.region + "_" + name;
            theMonitoringStations.remove(stationName);
            return true;
        }
        return false;
    }

    
    /** 
     * Returns an array of alarms from all connected monitoring stations
     * individuall pings all connected stations
     * 
     * @return NoxReading[] array of reading from all connected monitoring stations
     */
    @Override
    public NoxReading[] take_readings() {
        NoxReading[] noxReadings = new NoxReading[theMonitoringStations.size()];
        int size = 0;
        for (String station : theMonitoringStations) {
            TMS tempServer = get_connected_tms(station);
            NoxReading tempReading = tempServer.get_reading();            
            noxReadings[size] = tempReading;
            size++;
        }
        return noxReadings;
    }

    
    /** 
     * Function to activate a connected monitoring station
     * 
     * @param name of a monitoring station
     * @return boolean true if successful
     */
    public boolean activateTMS(String name) {
        TMS tempServer = get_connected_tms(name);
        boolean status = tempServer.activate();
        return status;
    }

    
    /** 
     * Function to deactivate a connected monitoring station
     * 
     * @param name of a monitoring station
     * @return boolean true if successful
     */
    public boolean deactivateTMS(String name) {
        TMS tempServer = get_connected_tms(name);
        boolean status = tempServer.deactivate();
        return status;
    }

    
    /** 
     * Function to reset a connected monitoring station
     * 
     * @param name of a monitoring station
     * @return boolean true if successful
     */
    public boolean resetTMS(String name) {
        TMS tempServer = get_connected_tms(name);
        boolean status = tempServer.reset();
        return status;
    }

    
    /** 
     * Returns a retrieved tms object from a given name 
     * 
     * @param name of a connected tms
     * @return TMS object of a connected tms
     */
    public TMS get_connected_tms(String name) {
        TMS tempServer = null;
        try {
            tempServer = TMSHelper.narrow(nameService.resolve_str(name));
        } catch (CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName
                | org.omg.CosNaming.NamingContextPackage.NotFound e) {
            logger.error("nameServiceObj = null" + e);
        }
        return tempServer;
    }

    
    /** 
     * Createds a hashmap of allowed alarm levels
     * 
     * @return HashMap<String, Levels>
     */
    private HashMap<String, Levels> setLevel() {
        final Levels def = new Levels(Constants.DEFAULT_ALARM_LEVEL, Constants.DEFAULT_WARNING_LEVEL);

        logger.info("Set default warning level to {}, alert level to {}", def.getWarningLevel(), def.getAlarmLevel());

        return new HashMap<String, Levels>() {
            private static final long serialVersionUID = 3106482810716209290L;
            {
                put("default", def);
            }
        };
    }

    
    /** 
     * returns the given levels are a region
     * 
     * @param levels hashmap of levels
     * @param zone name of zone
     * @return Levels the given levels
     */
    public static Levels getLevelsForRegion(HashMap<String, Levels> levels, String zone) {
        return !levels.containsKey(zone) ? levels.get("default") : levels.get(zone);
    }
}

/**
 * Levels class to hold alarm level data
 */
class Levels {

    private Integer alarm_level;
    private Integer warning_level;

    public Levels(){
        // no-op
    }

    /** 
     * @param alarm_level int to set alarm level  
     * @param warning_level int to set warning level
     */
    public Levels(int alarm_level, int warning_level){
        this.alarm_level = alarm_level;
        this.warning_level = warning_level;
    }

    
    /** 
     * @return Integer of current alarm level
     */
    public Integer getAlarmLevel(){
        return alarm_level;
    }

    
    /** 
     * @return Integer of current warning level
     */
    public Integer getWarningLevel(){
        return warning_level;
    }

    
    /** 
     * @param alarm_level sets the alarm level
     */
    @JsonSetter("alarm_level")
    public void setAlarmLevel(int alarm_level){
        this.alarm_level = alarm_level;
    }

    
    /** 
     * @param warning_level set the warning level
     */
    @JsonSetter("warning_level")
    public void setWarningLevel(int warning_level){
        this.warning_level = warning_level;
    }

} 