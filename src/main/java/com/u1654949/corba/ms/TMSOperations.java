package com.u1654949.corba.ms;


/**
 * Generated from IDL interface "TMS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 8, 2020, 11:56:17 AM
 */

public interface TMSOperations
{
	/* constants */
	/* operations  */
	java.lang.String station_name();
	java.lang.String location();
	boolean deactivate();
	boolean activate();
	boolean reset();
	com.u1654949.corba.common.NoxReading get_reading();
	com.u1654949.corba.common.NoxReading[] get_reading_log();
}
