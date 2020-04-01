package com.u1654949.corba.common;

/**
 * Generated from IDL alias "TMS_List".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 1, 2020, 12:42:52 PM
 */

public final class TMS_ListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public java.lang.String[] value;

	public TMS_ListHolder ()
	{
	}
	public TMS_ListHolder (final java.lang.String[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return TMS_ListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = TMS_ListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		TMS_ListHelper.write (out,value);
	}
}
