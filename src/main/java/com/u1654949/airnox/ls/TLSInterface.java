package com.u1654949.airnox.ls;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.u1654949.corba.common.NoxReading;

public class TLSInterface {

    private static TLSDriver tlsDriver;
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("Please enter a name for the server: ");
        String input = scanner.next();
        tlsDriver = new TLSDriver(args, input);
        processInput();
    }

    private static void processInput() {
        System.out.println("");
        System.out.println("Choose from one of the commands below:");
        System.out.println("- exit, exits the server");
        System.out.println("- name, return the name of the server");
        System.out.println("- poll, return the last reading of all connected TMS's");
        System.out.println("- activate, activates a sensor");
        System.out.println("- deactivate, deactivate a sensor");
        System.out.println("- reset, reset a sensor");
        System.out.println("");

        while (true) {
            System.out.print("Please select an option: ");
            String input = scanner.next();
            if (input == null) {
                System.err.println("Invalid command provided!" + "\n");
                continue;
            }
            if (input.equals("exit")) {
                return;
            }
            if (input.equals("name")) {
                System.out.println(tlsDriver.name());
                System.out.println("\n");
                continue;
            }
            if (input.equals("poll")) {
                NoxReading[] noxReadings = tlsDriver.take_readings();
                int temp;
                for (temp = 0; temp < noxReadings.length; temp++) {
                    NoxReading tempReading = noxReadings[temp];
                    Date date = new Date(tempReading.time);
                    String time = simpleDateFormat.format(date);
                    System.out.println("Last reading from Monitoring Station - ID:" + tempReading.station_name + 
                                       " - Region:"+tempReading.region+", with a value of "+tempReading.reading_value+
                                       " at " + time);
  
                } 
                System.out.println("\n");
                continue;
            }            
            if (input.equals("activate")) {
                String[] stations = tlsDriver.get_known_stations();
                System.out.println("Current connect stations are:");
                for (String station : stations) {
                    System.out.println(station);
                }
                System.out.println("Please choose a station to activate:");
                String station = scanner.next();
                boolean status = tlsDriver.activateTMS(station);
                if (status){
                    System.out.println("Station activated");
                } else {
                    System.out.println("Station failed to activate or is already activated");
                }
                continue;
            }
            if (input.equals("deactivate")) {
                String[] stations = tlsDriver.get_known_stations();
                System.out.println("Current connect stations are:");
                for (String station : stations) {
                    System.out.println(station);
                }
                System.out.println("Please choose a station to deactivate:");
                String station = scanner.next();
                boolean status = tlsDriver.deactivateTMS(station);
                if (status){
                    System.out.println("Station deactivated");
                } else {
                    System.out.println("Station failed to deactivate or is already deactivated");
                }
                continue;
            }
            if (input.equals("reset")) {
                String[] stations = tlsDriver.get_known_stations();
                System.out.println("Current connect stations are:");
                for (String station : stations) {
                    System.out.println(station);
                }
                System.out.println("Please choose a station to reset:");
                String station = scanner.next();
                boolean status = tlsDriver.resetTMS(station);
                if (status){
                    System.out.println("Station reset");
                } else {
                    System.out.println("Station failed to reset");
                }
                continue;
            }
        }
    }

}