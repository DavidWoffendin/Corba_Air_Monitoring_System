package com.u1654949.corba.ms;


/**
 * Generated from IDL interface "TMS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 28, 2020, 11:31:38 AM
 */

public interface TMSOperations
{
	/* constants */
	/* operations  */
	java.lang.String station_name();
	java.lang.String location();
	boolean deactivate();
	boolean activate();
	void reset();
	void send_alarm(int measurement);
	com.u1654949.corba.common.NoxReading get_reading();
	com.u1654949.corba.common.NoxReading[] get_reading_log();
}
