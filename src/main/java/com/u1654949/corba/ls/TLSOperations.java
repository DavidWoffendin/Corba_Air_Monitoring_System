package com.u1654949.corba.ls;


/**
 * Generated from IDL interface "TLS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 28, 2020, 11:31:38 AM
 */

public interface TLSOperations
{
	/* constants */
	/* operations  */
	java.lang.String name();
	com.u1654949.corba.common.Alarm[] alarm_log();
	boolean ping();
	com.u1654949.corba.common.Alarm[] get_current_state();
	com.u1654949.corba.common.MSData[] get_registered_tms();
	com.u1654949.corba.common.MSData register_tms(java.lang.String region);
	void receive_alarm(com.u1654949.corba.common.Alarm new_alarm);
	boolean remove_tms(com.u1654949.corba.common.MSData data);
}
