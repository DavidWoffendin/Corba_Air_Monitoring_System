package com.u1654949.corba.ms;


/**
 * Generated from IDL interface "TMS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 8, 2020, 11:56:17 AM
 */

public abstract class TMSHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(TMSHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/u1654949/corba/ms/TMS:1.0", "TMS");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.u1654949.corba.ms.TMS s)
	{
			any.insert_Object(s);
	}
	public static com.u1654949.corba.ms.TMS extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:com/u1654949/corba/ms/TMS:1.0";
	}
	public static TMS read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(com.u1654949.corba.ms._TMSStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.u1654949.corba.ms.TMS s)
	{
		_out.write_Object(s);
	}
	public static com.u1654949.corba.ms.TMS narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.u1654949.corba.ms.TMS)
		{
			return (com.u1654949.corba.ms.TMS)obj;
		}
		else if (obj._is_a("IDL:com/u1654949/corba/ms/TMS:1.0"))
		{
			com.u1654949.corba.ms._TMSStub stub;
			stub = new com.u1654949.corba.ms._TMSStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static com.u1654949.corba.ms.TMS unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.u1654949.corba.ms.TMS)
		{
			return (com.u1654949.corba.ms.TMS)obj;
		}
		else
		{
			com.u1654949.corba.ms._TMSStub stub;
			stub = new com.u1654949.corba.ms._TMSStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
