package com.u1654949.airnox.ms;

import java.util.Date;
import java.util.Scanner;

import com.u1654949.corba.common.NoxReading;

public class TMSInterface {

    private static TMSDriver tmsDriver;

    static Scanner scanner = new Scanner(System.in);

    
    /** 
     * Main function to launch the tms
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Please enter the local station name: ");
        String tlsName = scanner.next();
        System.out.println("Please enter the monitoring station zone: ");
        String zoneName = scanner.next();
        tmsDriver = new TMSDriver(args, tlsName, zoneName);
        processInput();
    }

    private static void processInput() {
        System.out.println("");
        System.out.println("Choose from one of the commands below:");
        System.out.println("- reading, exits the sensor");
        System.out.println("- exit, exits the sensor");
        System.out.println("- name, return the name of the sensor");
        System.out.println("- location, returns the location of the sensor");
        System.out.println("- current_reading, returns the last reading of the sensor");
        System.out.println("- history, views Sensor reading history");
        System.out.println("- poweroff, turns off the Sensor");
        System.out.println("- poweron, turns on the Sensor");
        System.out.println("- reset, resets the Sensor logs");
        System.out.println("- status, views current Sensor alert status");
        System.out.println("");

        while (true) {
            System.out.print("Please select an option: ");
            String input = scanner.next();
            if (input == null) {
                System.err.println("Invalid command provided!" + "\n");
                continue;
            }
            if (input.equals("reading")) {
                System.out.println("Please enter a value");
                String input2 = scanner.next();
                if (input2.matches("\\d{1,2}")) {
                    tmsDriver.send_alarm(Integer.parseInt(input2));
                } else {
                    System.err.println("Invalid measurement provided!");
                }

            }
            if (input.equals("exit")) {
                return;
            }
            if (input.equals("name")) {
                System.out.println(tmsDriver.station_name());
                System.out.println("\n");
                continue;
            }
            if (input.equals("location")) {
                System.out.println(tmsDriver.location());
                System.out.println("\n");
                continue;
            }
            if (input.equals("current_reading")) {
                NoxReading reading = tmsDriver.get_reading();
                System.out.println("Current Reading value at " + reading.reading_value);
                System.out.println("\n");
                continue;
            }
            if (input.equals("history")) {
                NoxReading[] readings = tmsDriver.get_reading_log();
                String readingLog = "\n";
                for (int i = 0, j = readings.length; i < j; i++) {
                    if (i != 0) {
                        readingLog += "\n";
                    }
                    readingLog += "[" + new Date(readings[i].time) + " => " + readings[i].reading_value + "]";
                }
                if (readings.length != 0) {
                    System.out.println(readingLog + "\n");
                } else {
                    System.err.println("No readings have been registered yet!" + "\n");
                }

                continue;
            }
            if (input.equals("poweroff")) {
                if (tmsDriver.deactivate()) {
                    System.out.println("Turned sensor off.\n");
                } else {
                    System.err.println("System is already powered off!\n");
                }
                continue;
            }
            if (input.equals("poweron")) {
                if (tmsDriver.activate()) {
                    System.out.println("Turned sensor on.\n");
                } else {
                    System.err.println("System is already switched on!\n");
                }
                continue;
            }
            if (input.equals("reset")) {
                tmsDriver.reset();
                System.out.println("System reset.\n");
                continue;
            }
            if (input.equals("status")) {
                NoxReading current = tmsDriver.get_reading();
                if (current != null) {
                    System.out.print("Previous reading made at " + new Date(current.time) + ", registering at "
                            + current.reading_value + ". ");
                    if (current.reading_value > 40) {
                        System.out.println("This is above safety levels.\n");
                    } else {
                        System.out.println("\n");
                    }
                } else {
                    System.err.println("No readings have been registered yet!\n");
                }
                continue;
            }
            System.out.println("");
        }
    }
}
