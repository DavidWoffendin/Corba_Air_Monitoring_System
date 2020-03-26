package com.u1654949.corba.common;

/**
 * Generated from IDL alias "Alarms".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 26, 2020, 11:28:20 AM
 */

public abstract class AlarmsHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, com.u1654949.corba.common.Alarm[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static com.u1654949.corba.common.Alarm[] extract (final org.omg.CORBA.Any any)
	{
		if ( any.type().kind() == org.omg.CORBA.TCKind.tk_null)
		{
			throw new org.omg.CORBA.BAD_OPERATION ("Can't extract from Any with null type.");
		}
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(AlarmsHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(com.u1654949.corba.common.AlarmsHelper.id(), "Alarms",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(com.u1654949.corba.common.AlarmHelper.id(),"Alarm",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("data", org.omg.CORBA.ORB.init().create_struct_tc(com.u1654949.corba.common.TLSDataHelper.id(),"TLSData",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("tls_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("stationData", org.omg.CORBA.ORB.init().create_struct_tc(com.u1654949.corba.common.MSDataHelper.id(),"MSData",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("region", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("station_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("alarm_level", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}), null)}), null),new org.omg.CORBA.StructMember("reading", org.omg.CORBA.ORB.init().create_struct_tc(com.u1654949.corba.common.NoxReadingHelper.id(),"NoxReading",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("time", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(23)), null),new org.omg.CORBA.StructMember("reading_value", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}), null)})));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:com/u1654949/corba/common/Alarms:1.0";
	}
	public static com.u1654949.corba.common.Alarm[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		com.u1654949.corba.common.Alarm[] _result;
		int _l_result1 = _in.read_long();
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result1 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result1);
				}
		}
		catch (java.io.IOException e)
		{
		}
		_result = new com.u1654949.corba.common.Alarm[_l_result1];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=com.u1654949.corba.common.AlarmHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, com.u1654949.corba.common.Alarm[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			com.u1654949.corba.common.AlarmHelper.write(_out,_s[i]);
		}

	}
}
