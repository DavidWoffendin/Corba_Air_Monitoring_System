package com.u1654949.corba.ls;


/**
 * Generated from IDL interface "TLS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 28, 2020, 11:31:38 AM
 */

public abstract class TLSHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(TLSHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/u1654949/corba/ls/TLS:1.0", "TLS");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.u1654949.corba.ls.TLS s)
	{
			any.insert_Object(s);
	}
	public static com.u1654949.corba.ls.TLS extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:com/u1654949/corba/ls/TLS:1.0";
	}
	public static TLS read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(com.u1654949.corba.ls._TLSStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.u1654949.corba.ls.TLS s)
	{
		_out.write_Object(s);
	}
	public static com.u1654949.corba.ls.TLS narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.u1654949.corba.ls.TLS)
		{
			return (com.u1654949.corba.ls.TLS)obj;
		}
		else if (obj._is_a("IDL:com/u1654949/corba/ls/TLS:1.0"))
		{
			com.u1654949.corba.ls._TLSStub stub;
			stub = new com.u1654949.corba.ls._TLSStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static com.u1654949.corba.ls.TLS unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.u1654949.corba.ls.TLS)
		{
			return (com.u1654949.corba.ls.TLS)obj;
		}
		else
		{
			com.u1654949.corba.ls._TLSStub stub;
			stub = new com.u1654949.corba.ls._TLSStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
