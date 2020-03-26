package com.u1654949.corba.mc;


/**
 * Generated from IDL interface "MCC".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 26, 2020, 11:28:20 AM
 */

public abstract class MCCHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(MCCHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/u1654949/corba/mc/MCC:1.0", "MCC");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.u1654949.corba.mc.MCC s)
	{
			any.insert_Object(s);
	}
	public static com.u1654949.corba.mc.MCC extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:com/u1654949/corba/mc/MCC:1.0";
	}
	public static MCC read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(com.u1654949.corba.mc._MCCStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.u1654949.corba.mc.MCC s)
	{
		_out.write_Object(s);
	}
	public static com.u1654949.corba.mc.MCC narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.u1654949.corba.mc.MCC)
		{
			return (com.u1654949.corba.mc.MCC)obj;
		}
		else if (obj._is_a("IDL:com/u1654949/corba/mc/MCC:1.0"))
		{
			com.u1654949.corba.mc._MCCStub stub;
			stub = new com.u1654949.corba.mc._MCCStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static com.u1654949.corba.mc.MCC unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.u1654949.corba.mc.MCC)
		{
			return (com.u1654949.corba.mc.MCC)obj;
		}
		else
		{
			com.u1654949.corba.mc._MCCStub stub;
			stub = new com.u1654949.corba.mc._MCCStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
