package com.u1654949.airnox.mc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import com.u1654949.corba.common.NoxReading;

public class TMCInterface {

    private static TMCDriver tmcDriver;
    static Scanner scanner = new Scanner(System.in);
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");

    public static void main(String[] args) throws Exception {
        tmcDriver = new TMCDriver(args);
        processInput();
    }

    private static void processInput() {
        System.out.println("");
        System.out.println("Choose from one of the commands below:");
        System.out.println("- exit, exits the server");
        System.out.println("- name, return the name of the server");
        System.out.println("- poll, return the last reading of all connected TMS's");
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
                System.out.println(tmcDriver.name());
                continue;
            }
            if (input.equals("servers")) {
                System.out.println(Arrays.toString(tmcDriver.get_known_servers()));
                continue;
            }
            if (input.equals("poll")) {
                NoxReading[][] readings = tmcDriver.get_local_station_readings();
                int temp;
                int temp2;
                for (temp = 0; temp < readings.length; temp++) {
                    NoxReading[] tempReadings = readings[temp];
                    for (temp2 = 0; temp2 < tempReadings.length; temp2++) {
                        NoxReading tempReading2 = tempReadings[temp2];
                        Date date = new Date(tempReading2.time);
                        String time = simpleDateFormat.format(date);
                        System.out.println(tempReading2.server_name + " server reports monitoring Station - ID:"
                                + tempReading2.station_name + " - Region:" + tempReading2.region
                                + ", reading with a value of " + tempReading2.reading_value + " at " + time);
                    }
                }
                continue;
            }
        }
    }
}