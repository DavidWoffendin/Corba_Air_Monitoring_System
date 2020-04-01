package com.u1654949.corba.ls;


/**
 * Generated from IDL interface "TLS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 1, 2020, 12:42:52 PM
 */

public interface TLSOperations
{
	/* constants */
	/* operations  */
	java.lang.String name();
	com.u1654949.corba.common.Alarm[] alarm_log();
	boolean ping();
	com.u1654949.corba.common.Alarm[] get_current_state();
	java.lang.String[] get_known_stations();
	com.u1654949.corba.common.MSData register_tms(java.lang.String region);
	com.u1654949.corba.common.NoxReading[] take_readings();
	void receive_alarm(com.u1654949.corba.common.Alarm new_alarm);
	boolean remove_tms(com.u1654949.corba.common.MSData data);
}
