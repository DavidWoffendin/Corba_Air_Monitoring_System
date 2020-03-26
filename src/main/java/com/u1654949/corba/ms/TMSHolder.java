package com.u1654949.corba.ms;

/**
 * Generated from IDL interface "TMS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 26, 2020, 11:28:20 AM
 */

public final class TMSHolder	implements org.omg.CORBA.portable.Streamable{
	 public TMS value;
	public TMSHolder()
	{
	}
	public TMSHolder (final TMS initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return TMSHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = TMSHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		TMSHelper.write (_out,value);
	}
}
