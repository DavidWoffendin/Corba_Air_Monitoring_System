package com.u1654949.corba.common;

/**
 * Generated from IDL struct "MSData".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 2, 2020, 3:12:27 PM
 */

public final class MSDataHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.u1654949.corba.common.MSData value;

	public MSDataHolder ()
	{
	}
	public MSDataHolder(final com.u1654949.corba.common.MSData initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return com.u1654949.corba.common.MSDataHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = com.u1654949.corba.common.MSDataHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		com.u1654949.corba.common.MSDataHelper.write(_out, value);
	}
}
