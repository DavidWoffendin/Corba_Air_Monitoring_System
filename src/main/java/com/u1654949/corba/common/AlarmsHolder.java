package com.u1654949.corba.common;

/**
 * Generated from IDL alias "Alarms".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 4, 2020, 12:42:21 PM
 */

public final class AlarmsHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.u1654949.corba.common.Alarm[] value;

	public AlarmsHolder ()
	{
	}
	public AlarmsHolder (final com.u1654949.corba.common.Alarm[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AlarmsHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AlarmsHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AlarmsHelper.write (out,value);
	}
}
