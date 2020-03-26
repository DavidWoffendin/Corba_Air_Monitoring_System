package com.u1654949.corba.mc;


/**
 * Generated from IDL interface "MCC".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 26, 2020, 11:28:20 AM
 */

public interface MCCOperations
{
	/* constants */
	/* operations  */
	java.lang.String id();
	void add_alarm(com.u1654949.corba.common.Alarm new_alarm);
	void remove_alarm(com.u1654949.corba.common.TLSData tls_data);
	java.lang.String[] get_tls_list();
}
