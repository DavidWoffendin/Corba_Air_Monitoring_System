package com.u1654949.corba.common;

/**
 * Generated from IDL struct "Alarm".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 8, 2020, 11:56:17 AM
 */

public final class Alarm
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public Alarm(){}
	public com.u1654949.corba.common.TLSData data;
	public com.u1654949.corba.common.NoxReading reading;
	public Alarm(com.u1654949.corba.common.TLSData data, com.u1654949.corba.common.NoxReading reading)
	{
		this.data = data;
		this.reading = reading;
	}
}
