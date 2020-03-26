package com.u1654949.corba.common;

/**
 * Generated from IDL alias "Stations".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 26, 2020, 11:28:20 AM
 */

public abstract class StationsHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, com.u1654949.corba.common.MSData[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static com.u1654949.corba.common.MSData[] extract (final org.omg.CORBA.Any any)
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
			synchronized(StationsHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(com.u1654949.corba.common.StationsHelper.id(), "Stations",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(com.u1654949.corba.common.MSDataHelper.id(),"MSData",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("region", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("station_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("alarm_level", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)})));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:com/u1654949/corba/common/Stations:1.0";
	}
	public static com.u1654949.corba.common.MSData[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		com.u1654949.corba.common.MSData[] _result;
		int _l_result3 = _in.read_long();
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result3 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result3);
				}
		}
		catch (java.io.IOException e)
		{
		}
		_result = new com.u1654949.corba.common.MSData[_l_result3];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=com.u1654949.corba.common.MSDataHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, com.u1654949.corba.common.MSData[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			com.u1654949.corba.common.MSDataHelper.write(_out,_s[i]);
		}

	}
}
