package com.u1654949.corba.mc;


/**
 * Generated from IDL interface "MCS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 1, 2020, 3:30:59 PM
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
}
