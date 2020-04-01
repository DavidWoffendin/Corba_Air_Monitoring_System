package com.u1654949.corba.ls;


/**
 * Generated from IDL interface "TLS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 1, 2020, 3:30:59 PM
 */

public abstract class TLSPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.u1654949.corba.ls.TLSOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "_get_name", Integer.valueOf(0));
		m_opsHash.put ( "_get_alarm_log", Integer.valueOf(1));
		m_opsHash.put ( "ping", Integer.valueOf(2));
		m_opsHash.put ( "get_current_state", Integer.valueOf(3));
		m_opsHash.put ( "register_tms", Integer.valueOf(4));
		m_opsHash.put ( "receive_alarm", Integer.valueOf(5));
		m_opsHash.put ( "take_readings", Integer.valueOf(6));
		m_opsHash.put ( "remove_tms", Integer.valueOf(7));
		m_opsHash.put ( "get_known_stations", Integer.valueOf(8));
	}
	private String[] ids = {"IDL:com/u1654949/corba/ls/TLS:1.0"};
	public com.u1654949.corba.ls.TLS _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.u1654949.corba.ls.TLS __r = com.u1654949.corba.ls.TLSHelper.narrow(__o);
		return __r;
	}
	public com.u1654949.corba.ls.TLS _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.u1654949.corba.ls.TLS __r = com.u1654949.corba.ls.TLSHelper.narrow(__o);
		return __r;
	}
	public org.omg.CORBA.portable.OutputStream _invoke(String method, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.ResponseHandler handler)
		throws org.omg.CORBA.SystemException
	{
		org.omg.CORBA.portable.OutputStream _out = null;
		// do something
		// quick lookup of operation
		java.lang.Integer opsIndex = (java.lang.Integer)m_opsHash.get ( method );
		if ( null == opsIndex )
			throw new org.omg.CORBA.BAD_OPERATION(method + " not found");
		switch ( opsIndex.intValue() )
		{
			case 0: // _get_name
			{
			_out = handler.createReply();
			java.lang.String tmpResult12 = name();
_out.write_string( tmpResult12 );
				break;
			}
			case 1: // _get_alarm_log
			{
			_out = handler.createReply();
			com.u1654949.corba.common.AlarmsHelper.write(_out,alarm_log());
				break;
			}
			case 2: // ping
			{
				_out = handler.createReply();
				_out.write_boolean(ping());
				break;
			}
			case 3: // get_current_state
			{
				_out = handler.createReply();
				com.u1654949.corba.common.AlarmsHelper.write(_out,get_current_state());
				break;
			}
			case 4: // register_tms
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				com.u1654949.corba.common.MSDataHelper.write(_out,register_tms(_arg0));
				break;
			}
			case 5: // receive_alarm
			{
				com.u1654949.corba.common.Alarm _arg0=com.u1654949.corba.common.AlarmHelper.read(_input);
				_out = handler.createReply();
				receive_alarm(_arg0);
				break;
			}
			case 6: // take_readings
			{
				_out = handler.createReply();
				com.u1654949.corba.common.Nox_ReadingsHelper.write(_out,take_readings());
				break;
			}
			case 7: // remove_tms
			{
				com.u1654949.corba.common.MSData _arg0=com.u1654949.corba.common.MSDataHelper.read(_input);
				_out = handler.createReply();
				_out.write_boolean(remove_tms(_arg0));
				break;
			}
			case 8: // get_known_stations
			{
				_out = handler.createReply();
				com.u1654949.corba.common.TMS_ListHelper.write(_out,get_known_stations());
				break;
			}
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
