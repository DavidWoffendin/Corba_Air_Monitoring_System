package com.u1654949.corba.mc;


/**
 * Generated from IDL interface "MCC".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 26, 2020, 11:28:20 AM
 */

public abstract class MCCPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.u1654949.corba.mc.MCCOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "remove_alarm", Integer.valueOf(0));
		m_opsHash.put ( "get_tls_list", Integer.valueOf(1));
		m_opsHash.put ( "_get_id", Integer.valueOf(2));
		m_opsHash.put ( "add_alarm", Integer.valueOf(3));
	}
	private String[] ids = {"IDL:com/u1654949/corba/mc/MCC:1.0"};
	public com.u1654949.corba.mc.MCC _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.u1654949.corba.mc.MCC __r = com.u1654949.corba.mc.MCCHelper.narrow(__o);
		return __r;
	}
	public com.u1654949.corba.mc.MCC _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.u1654949.corba.mc.MCC __r = com.u1654949.corba.mc.MCCHelper.narrow(__o);
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
			case 0: // remove_alarm
			{
				com.u1654949.corba.common.TLSData _arg0=com.u1654949.corba.common.TLSDataHelper.read(_input);
				_out = handler.createReply();
				remove_alarm(_arg0);
				break;
			}
			case 1: // get_tls_list
			{
				_out = handler.createReply();
				com.u1654949.corba.common.TLS_ListHelper.write(_out,get_tls_list());
				break;
			}
			case 2: // _get_id
			{
			_out = handler.createReply();
			java.lang.String tmpResult9 = id();
_out.write_string( tmpResult9 );
				break;
			}
			case 3: // add_alarm
			{
				com.u1654949.corba.common.Alarm _arg0=com.u1654949.corba.common.AlarmHelper.read(_input);
				_out = handler.createReply();
				add_alarm(_arg0);
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
