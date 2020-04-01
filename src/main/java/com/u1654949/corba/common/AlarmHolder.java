package com.u1654949.corba.common;

/**
 * Generated from IDL struct "Alarm".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 1, 2020, 3:30:59 PM
 */

public final class AlarmHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.u1654949.corba.common.Alarm value;

	public AlarmHolder ()
	{
	}
	public AlarmHolder(final com.u1654949.corba.common.Alarm initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return com.u1654949.corba.common.AlarmHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = com.u1654949.corba.common.AlarmHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		com.u1654949.corba.common.AlarmHelper.write(_out, value);
	}
}
