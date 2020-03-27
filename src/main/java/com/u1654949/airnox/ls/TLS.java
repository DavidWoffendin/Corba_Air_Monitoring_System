package com.u1654949.airnox.ls;

public class TLS {

    private static TLSDriver tlsDriver;

    public static void main(String[] args) throws Exception {
        tlsDriver = new TLSDriver(args);
        tlsDriver.getEmbeddedOrb().run();
    }

}