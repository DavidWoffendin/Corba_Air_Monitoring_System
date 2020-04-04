package com.u1654949.corba.common;

/**
 * Generated from IDL struct "TLSData".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 4, 2020, 12:42:21 PM
 */

public final class TLSDataHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.u1654949.corba.common.TLSData value;

	public TLSDataHolder ()
	{
	}
	public TLSDataHolder(final com.u1654949.corba.common.TLSData initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return com.u1654949.corba.common.TLSDataHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = com.u1654949.corba.common.TLSDataHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		com.u1654949.corba.common.TLSDataHelper.write(_out, value);
	}
}
