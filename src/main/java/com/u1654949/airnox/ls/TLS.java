package com.u1654949.airnox.ls;

import java.util.Scanner;

public class TLS {

    private static TLSDriver tlsDriver;

    public static void main(String[] args) throws Exception {
        tlsDriver = new TLSDriver(args);
        processInput();
    }

    private static void processInput(){
        Scanner scanner = new Scanner(System.in);
        
        // display menu
        System.out.println("");
        System.out.println("Choose from one of the commands below:");
        System.out.println("- exit, exits the sensor");        
        System.out.println("- name, return the name of the sensor");
        System.out.println("");

        while(true){
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
        }
    }

}