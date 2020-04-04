package com.u1654949.corba.mc;


/**
 * Generated from IDL interface "MCS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 4, 2020, 12:42:21 PM
 */

public abstract class MCSHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(MCSHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/u1654949/corba/mc/MCS:1.0", "MCS");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.u1654949.corba.mc.MCS s)
	{
			any.insert_Object(s);
	}
	public static com.u1654949.corba.mc.MCS extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:com/u1654949/corba/mc/MCS:1.0";
	}
	public static MCS read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(com.u1654949.corba.mc._MCSStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.u1654949.corba.mc.MCS s)
	{
		_out.write_Object(s);
	}
	public static com.u1654949.corba.mc.MCS narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.u1654949.corba.mc.MCS)
		{
			return (com.u1654949.corba.mc.MCS)obj;
		}
		else if (obj._is_a("IDL:com/u1654949/corba/mc/MCS:1.0"))
		{
			com.u1654949.corba.mc._MCSStub stub;
			stub = new com.u1654949.corba.mc._MCSStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static com.u1654949.corba.mc.MCS unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.u1654949.corba.mc.MCS)
		{
			return (com.u1654949.corba.mc.MCS)obj;
		}
		else
		{
			com.u1654949.corba.mc._MCSStub stub;
			stub = new com.u1654949.corba.mc._MCSStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
