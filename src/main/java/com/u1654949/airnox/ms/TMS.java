package com.u1654949.airnox.ms;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TMS {

    private static TMSDriver tlsDriver;
    private static final Logger logger = LoggerFactory.getLogger(TMS.class);

    public static void main(String[] args) throws Exception {
        tlsDriver = new TMSDriver(args);
        processInput();
    }

    private static void processInput(){
        Scanner scanner = new Scanner(System.in);
        
        // display menu
        System.out.println("");
        System.out.println("Enter a reading, or choose from one of the commands below:");
        System.out.println("- exit, exits the sensor");
        System.out.println("- return the location of the sensor");
        System.out.println("- name, return the name of the sensor");
        System.out.println("- history, views Sensor reading history");
        System.out.println("- poweroff, turns off the Sensor");
        System.out.println("- poweron, turns on the Sensor");
        System.out.println("- reset, resets the Sensor logs");
        System.out.println("- status, views current Sensor alert status");
        System.out.println("");

        while(true){
            System.out.print("Please select an option: ");
            String input = scanner.next();
            if (input == null) {
                System.err.println("Invalid measurement provided!" + "\n");
                continue;
            }
            // user wishes to exit
            if (input.equals("exit")) {
                return;
            }            
            if (input.equals("name")) {
                System.out.println(tlsDriver.station_name());                
                System.out.println("\n");
                continue;
            }
            // measurement provided, send an alert
            if (input.equals("100")) {
                tlsDriver.send_alarm(Integer.parseInt(input));
            } else {
                System.err.println("Invalid measurement provided!");
            }
            
            System.out.println("");
        }
    }        
}
