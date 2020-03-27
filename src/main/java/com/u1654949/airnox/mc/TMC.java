package com.u1654949.airnox.mc;

public class TMC {

    private static TMCDriver tmcDriver;

    public static void main(String[] args) throws Exception {
        tmcDriver = new TMCDriver(args);
        tmcDriver.getEmbeddedOrb().run();
    }
}