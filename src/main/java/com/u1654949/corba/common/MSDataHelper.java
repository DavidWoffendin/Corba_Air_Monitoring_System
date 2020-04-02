package com.u1654949.corba.common;


/**
 * Generated from IDL struct "MSData".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 2, 2020, 3:12:27 PM
 */

public abstract class MSDataHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(MSDataHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(com.u1654949.corba.common.MSDataHelper.id(),"MSData",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("region", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("station_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("alarm_level", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.u1654949.corba.common.MSData s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.u1654949.corba.common.MSData extract (final org.omg.CORBA.Any any)
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
		return "IDL:com/u1654949/corba/common/MSData:1.0";
	}
	public static com.u1654949.corba.common.MSData read (final org.omg.CORBA.portable.InputStream in)
	{
		com.u1654949.corba.common.MSData result = new com.u1654949.corba.common.MSData();
		result.region=in.read_string();
		result.station_name=in.read_string();
		result.alarm_level=in.read_long();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final com.u1654949.corba.common.MSData s)
	{
		java.lang.String tmpResult3 = s.region;
out.write_string( tmpResult3 );
		java.lang.String tmpResult4 = s.station_name;
out.write_string( tmpResult4 );
		out.write_long(s.alarm_level);
	}
}
