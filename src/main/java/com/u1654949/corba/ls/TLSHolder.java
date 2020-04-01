package com.u1654949.corba.ls;

/**
 * Generated from IDL interface "TLS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 1, 2020, 3:30:59 PM
 */

public final class TLSHolder	implements org.omg.CORBA.portable.Streamable{
	 public TLS value;
	public TLSHolder()
	{
	}
	public TLSHolder (final TLS initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return TLSHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = TLSHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		TLSHelper.write (_out,value);
	}
}
