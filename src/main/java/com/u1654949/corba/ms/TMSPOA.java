package com.u1654949.corba.ms;


/**
 * Generated from IDL interface "TMS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 8, 2020, 11:56:17 AM
 */

public abstract class TMSPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.u1654949.corba.ms.TMSOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "_get_station_name", Integer.valueOf(0));
		m_opsHash.put ( "activate", Integer.valueOf(1));
		m_opsHash.put ( "deactivate", Integer.valueOf(2));
		m_opsHash.put ( "get_reading", Integer.valueOf(3));
		m_opsHash.put ( "get_reading_log", Integer.valueOf(4));
		m_opsHash.put ( "reset", Integer.valueOf(5));
		m_opsHash.put ( "_get_location", Integer.valueOf(6));
	}
	private String[] ids = {"IDL:com/u1654949/corba/ms/TMS:1.0"};
	public com.u1654949.corba.ms.TMS _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.u1654949.corba.ms.TMS __r = com.u1654949.corba.ms.TMSHelper.narrow(__o);
		return __r;
	}
	public com.u1654949.corba.ms.TMS _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.u1654949.corba.ms.TMS __r = com.u1654949.corba.ms.TMSHelper.narrow(__o);
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
			case 0: // _get_station_name
			{
			_out = handler.createReply();
			java.lang.String tmpResult10 = station_name();
_out.write_string( tmpResult10 );
				break;
			}
			case 1: // activate
			{
				_out = handler.createReply();
				_out.write_boolean(activate());
				break;
			}
			case 2: // deactivate
			{
				_out = handler.createReply();
				_out.write_boolean(deactivate());
				break;
			}
			case 3: // get_reading
			{
				_out = handler.createReply();
				com.u1654949.corba.common.NoxReadingHelper.write(_out,get_reading());
				break;
			}
			case 4: // get_reading_log
			{
				_out = handler.createReply();
				com.u1654949.corba.common.Nox_ReadingsHelper.write(_out,get_reading_log());
				break;
			}
			case 5: // reset
			{
				_out = handler.createReply();
				_out.write_boolean(reset());
				break;
			}
			case 6: // _get_location
			{
			_out = handler.createReply();
			java.lang.String tmpResult11 = location();
_out.write_string( tmpResult11 );
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
