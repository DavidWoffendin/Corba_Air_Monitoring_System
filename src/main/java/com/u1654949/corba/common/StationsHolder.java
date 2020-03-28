package com.u1654949.corba.common;

/**
 * Generated from IDL alias "Stations".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 28, 2020, 11:31:38 AM
 */

public final class StationsHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.u1654949.corba.common.MSData[] value;

	public StationsHolder ()
	{
	}
	public StationsHolder (final com.u1654949.corba.common.MSData[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return StationsHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StationsHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		StationsHelper.write (out,value);
	}
}
