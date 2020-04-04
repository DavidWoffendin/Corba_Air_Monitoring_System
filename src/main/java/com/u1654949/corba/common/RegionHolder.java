package com.u1654949.corba.common;

/**
 * Generated from IDL alias "Region".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 4, 2020, 12:42:21 PM
 */

public final class RegionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public java.lang.String[] value;

	public RegionHolder ()
	{
	}
	public RegionHolder (final java.lang.String[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return RegionHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = RegionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		RegionHelper.write (out,value);
	}
}
