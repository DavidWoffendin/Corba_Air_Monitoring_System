package com.u1654949.corba.mc;

/**
 * Generated from IDL interface "MCC".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 26, 2020, 11:28:20 AM
 */

public final class MCCHolder	implements org.omg.CORBA.portable.Streamable{
	 public MCC value;
	public MCCHolder()
	{
	}
	public MCCHolder (final MCC initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MCCHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MCCHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MCCHelper.write (_out,value);
	}
}
