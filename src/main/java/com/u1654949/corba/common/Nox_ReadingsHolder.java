package com.u1654949.corba.common;

/**
 * Generated from IDL alias "Nox_Readings".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 1, 2020, 3:30:59 PM
 */

public final class Nox_ReadingsHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.u1654949.corba.common.NoxReading[] value;

	public Nox_ReadingsHolder ()
	{
	}
	public Nox_ReadingsHolder (final com.u1654949.corba.common.NoxReading[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return Nox_ReadingsHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = Nox_ReadingsHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		Nox_ReadingsHelper.write (out,value);
	}
}
