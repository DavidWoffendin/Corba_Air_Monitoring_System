package com.u1654949.corba.common;


/**
 * Generated from IDL struct "TLSData".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 1, 2020, 2:00:22 PM
 */

public abstract class TLSDataHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(TLSDataHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(com.u1654949.corba.common.TLSDataHelper.id(),"TLSData",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("tls_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("stationData", org.omg.CORBA.ORB.init().create_struct_tc(com.u1654949.corba.common.MSDataHelper.id(),"MSData",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("region", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("station_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("alarm_level", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.u1654949.corba.common.TLSData s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.u1654949.corba.common.TLSData extract (final org.omg.CORBA.Any any)
	{
		org.omg.CORBA.portable.InputStream in = any.create_input_stream();
		try
		{
			return read (in);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (java.io.IOException e)
			{
			throw new RuntimeException("Unexpected exception " + e.toString() );
			}
		}
	}

	public static String id()
	{
		return "IDL:com/u1654949/corba/common/TLSData:1.0";
	}
	public static com.u1654949.corba.common.TLSData read (final org.omg.CORBA.portable.InputStream in)
	{
		com.u1654949.corba.common.TLSData result = new com.u1654949.corba.common.TLSData();
		result.tls_name=in.read_string();
		result.stationData=com.u1654949.corba.common.MSDataHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final com.u1654949.corba.common.TLSData s)
	{
		java.lang.String tmpResult4 = s.tls_name;
out.write_string( tmpResult4 );
		com.u1654949.corba.common.MSDataHelper.write(out,s.stationData);
	}
}
