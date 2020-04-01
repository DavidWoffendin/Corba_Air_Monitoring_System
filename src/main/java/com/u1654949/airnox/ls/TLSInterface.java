package com.u1654949.airnox.ls;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.u1654949.corba.common.NoxReading;

public class TLSInterface {

    private static TLSDriver tlsDriver;
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");

    public static void main(String[] args) throws Exception {
        tlsDriver = new TLSDriver(args);
        processInput();
    }

    private static void processInput() {
        Scanner scanner = new Scanner(System.in);

        // display menu
        System.out.println("");
        System.out.println("Choose from one of the commands below:");
        System.out.println("- exit, exits the sensor");
        System.out.println("- name, return the name of the sensor");
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
        }
    }

}