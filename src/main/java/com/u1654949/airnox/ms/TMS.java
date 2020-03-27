package com.u1654949.airnox.ms;

public class TMS {

    private static TMSDriver tlsDriver;

    public static void main(String[] args) throws Exception {
        tlsDriver = new TMSDriver(args);
        tlsDriver.getEmbeddedOrb().run();
    }
}
