package com.u1654949.corba.common;

/**
 * Generated from IDL alias "TLS_List".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 2, 2020, 3:12:27 PM
 */

public final class TLS_ListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public java.lang.String[] value;

	public TLS_ListHolder ()
	{
	}
	public TLS_ListHolder (final java.lang.String[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return TLS_ListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = TLS_ListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		TLS_ListHelper.write (out,value);
	}
}
