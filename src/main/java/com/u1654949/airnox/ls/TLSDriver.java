package com.u1654949.airnox.ls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;

import com.u1654949.airnox.common.Constants;
import com.u1654949.airnox.common.Levels;
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

    private static final Logger logger = LoggerFactory.getLogger(TLS.class);

    private final ConcurrentSkipListMap<String, ConcurrentSkipListMap<String, NoxReading>> regionMapping = new ConcurrentSkipListMap<>();
    private final ConcurrentSkipListMap<String, Alarm> alarmStates = new ConcurrentSkipListMap<>();
    private final HashSet<String> theMonitoringStations = new HashSet<>();        
    private static final List<Alarm> alarmLog = new ArrayList<>();
    private NamingContextExt nameService;
    private static HashMap<String, Levels> levels;
    private static MCS server;
    private static String name;
    
    Scanner scanner = new Scanner(System.in);

    private final ORB orb;

    public TLSDriver(String[] args, String sName) throws Exception {      
        levels = setLevel();

        name = sName;      

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
        server = MCSHelper.narrow(nameService.resolve_str(Constants.THE_MONITORING_CENTRE));

        // bind the Count object in the Naming service
        NameComponent[] countName = nameService.to_name(name);
        nameService.rebind(countName, server_ref);

        // test out the RMC connection
        if (!server.register_tls_connection(name)) {
            throw new IllegalStateException("TMC Connection failed!");
        } else {
            logger.info("Made successful connection to TMC");
        }
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
     * Simple accessor method to check connection.
     *
     * @return true
     */
    @Override
    public Alarm[] alarm_log() {
        return alarmLog.toArray(new Alarm[alarmLog.size()]);
    }

    @Override
    public boolean ping() {   
        return true;
    }

    @Override
    public Alarm[] get_current_state() {
        List<Alarm> currentStates = new ArrayList<>();
        for (Map.Entry<String, Alarm> region : alarmStates.entrySet()) {
            currentStates.add(region.getValue());
        }
        return currentStates.toArray(new Alarm[currentStates.size()]);
    }

    @Override
    public String[] get_known_stations() {
        return theMonitoringStations.toArray(new String[theMonitoringStations.size()]);
        
    }

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
            regionMapping.put(region, new ConcurrentSkipListMap<String, NoxReading>() {{
                put(id, noxReading);
            }});
        }
        logger.info("Added Sensor #{} to {}", id, region);

        Levels sensorLevels;
        if(levels.containsKey(region)){
            sensorLevels = levels.get(region);
        } else {
            sensorLevels = levels.get("default");
        }

        String stationName = id + "_" + region;

        theMonitoringStations.add(stationName);
        return new MSData(region, id, sensorLevels.getAlarmLevel());
    }

    @Override
    public void receive_alarm(Alarm new_alarm) {
        logger.info("Received alert from sensor #{} in region `{}`", new_alarm.data.stationData.station_name, new_alarm.data.stationData.region);
        ConcurrentSkipListMap<String, NoxReading> region = regionMapping.get(new_alarm.data.stationData.region);
        region.put(new_alarm.data.stationData.station_name, new_alarm.reading);
        alarmLog.add(new_alarm);
        int alarm_level = getLevelsForRegion(levels, new_alarm.data.stationData.region).getAlarmLevel();

        if(new_alarm.reading.reading_value > alarm_level){
            logger.warn("Registered alarm {} from Sensor #{} in region {}", new_alarm.reading.reading_value, new_alarm.data.stationData.station_name, new_alarm.data.stationData.region);
        } else {
            logger.info("Registered reading {} from Sensor #{} in region {}", new_alarm.reading.reading_value, new_alarm.data.stationData.station_name, new_alarm.data.stationData.region);
        }

        int avg = 0;
        for(Map.Entry<String, NoxReading> regionMap : region.entrySet()){
            avg += regionMap.getValue().reading_value;
        }     
        int size = region.size();
        System.out.println(size);
        avg = Math.round((avg / size) * 100) / 100;
        logger.info("" + avg);

        try {
            logger.info("ping"); 
			server.ping();
			logger.info("pong");   
        } catch(Exception e) {
            System.err.println(server.name() + "` is unreachable!");
            return;
        }

        if((avg >= alarm_level && size > 2) || (avg > alarm_level && size > 1)){
            logger.warn("Average above alert level in region `{}`, forwarding to TMC...", new_alarm.data.stationData.region);
            new_alarm.reading.reading_value = avg;
            server.receive_alarm(new_alarm);
            alarmStates.put(new_alarm.data.stationData.region, new Alarm(new_alarm.data, new NoxReading(new_alarm.reading.time, avg, new_alarm.data.stationData.region, new_alarm.data.stationData.station_name)));
        } else {
            server.cancel_alarm(new TLSData(name, new_alarm.data.stationData));
            
            if(alarmStates.containsKey(new_alarm.data.stationData.region)){
                logger.info("Removed alarm state for region `{}`", new_alarm.data.stationData.region);
                alarmStates.remove(new_alarm.data.stationData.region);
            }
        }
    }

    @Override
    public boolean remove_tms(MSData data) {
        logger.info("Removed Sensor #{} from region `{}`", data.station_name, data.region);
        if (regionMapping.containsKey(data.region)) {
            regionMapping.get(data.region).remove(data.station_name);
            return true;
        }
        String stationName = data.station_name + "_" + data.region;
        theMonitoringStations.remove(stationName);
        return false;
    }

    @Override
    public NoxReading[] take_readings() {
        NoxReading[] noxReadings  = new NoxReading[theMonitoringStations.size()];
        int size = 0;
        for (String station : theMonitoringStations) {
            TMS tempServer = get_connected_tms(station);
            NoxReading tempReading = tempServer.get_reading();
            System.out.println(tempReading.reading_value);
            noxReadings[size] = tempReading;
            size++;
        }
        return noxReadings;
    }

    public boolean activateTMS(String name) {
        TMS tempServer = get_connected_tms(name);
        boolean status = tempServer.activate();
        return status;
    }
    public boolean deactivateTMS(String name) {
        TMS tempServer = get_connected_tms(name);
        boolean status = tempServer.deactivate();
        return status;
    } 
    public boolean resetTMS(String name) {
        TMS tempServer = get_connected_tms(name);
        boolean status = tempServer.reset();
        return status;
    } 

    public TMS get_connected_tms(String name) {
        TMS tempServer = null;
        try {
            tempServer = TMSHelper.narrow(nameService.resolve_str(name));
        } catch (CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName | org.omg.CosNaming.NamingContextPackage.NotFound e) {
            logger.error("nameServiceObj = null" + e);
        }
        return tempServer;
    }
    
    private HashMap<String, Levels> setLevel(){
        final Levels def = new Levels(Constants.DEFAULT_ALERT_LEVEL, Constants.DEFAULT_WARNING_LEVEL);
        
        logger.info("Set default warning level to {}, alert level to {}", def.getWarningLevel(), def.getAlarmLevel());

        return new HashMap<String, Levels>(){{
            put("default", def);
        }};
    }

    public static Levels getLevelsForRegion(HashMap<String, Levels> levels, String zone){
        return !levels.containsKey(zone) ? levels.get("default") : levels.get(zone);
    }
}

