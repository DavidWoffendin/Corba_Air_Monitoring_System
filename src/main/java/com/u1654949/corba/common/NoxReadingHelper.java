package com.u1654949.corba.common;


/**
 * Generated from IDL struct "NoxReading".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 4, 2020, 12:42:21 PM
 */

public abstract class NoxReadingHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(NoxReadingHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(com.u1654949.corba.common.NoxReadingHelper.id(),"NoxReading",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("time", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(23)), null),new org.omg.CORBA.StructMember("reading_value", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("region", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("station_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("server_name", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.u1654949.corba.common.NoxReading s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.u1654949.corba.common.NoxReading extract (final org.omg.CORBA.Any any)
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
		return "IDL:com/u1654949/corba/common/NoxReading:1.0";
	}
	public static com.u1654949.corba.common.NoxReading read (final org.omg.CORBA.portable.InputStream in)
	{
		com.u1654949.corba.common.NoxReading result = new com.u1654949.corba.common.NoxReading();
		result.time=in.read_longlong();
		result.reading_value=in.read_long();
		result.region=in.read_string();
		result.station_name=in.read_string();
		result.server_name=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final com.u1654949.corba.common.NoxReading s)
	{
		out.write_longlong(s.time);
		out.write_long(s.reading_value);
		java.lang.String tmpResult0 = s.region;
out.write_string( tmpResult0 );
		java.lang.String tmpResult1 = s.station_name;
out.write_string( tmpResult1 );
		java.lang.String tmpResult2 = s.server_name;
out.write_string( tmpResult2 );
	}
}
