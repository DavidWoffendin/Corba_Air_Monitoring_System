package com.u1654949.corba.mc;


/**
 * Generated from IDL interface "MCS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 28, 2020, 11:31:38 AM
 */

public interface MCSOperations
{
	/* constants */
	/* operations  */
	java.lang.String name();
	boolean ping();
	void cancel_alarm(com.u1654949.corba.common.TLSData tls_data);
	void receive_alarm(com.u1654949.corba.common.Alarm new_alarm);
	boolean register_tls_connection(java.lang.String name);
	boolean remove_tls_connection(java.lang.String name);
	com.u1654949.corba.common.Alarm[] get_alarms(java.lang.String id);
	com.u1654949.corba.common.Alarm[] get_County_state(java.lang.String county);
	java.lang.String[] get_known_stations();
	com.u1654949.corba.ls.TLS get_connected_tls(java.lang.String name);
}
