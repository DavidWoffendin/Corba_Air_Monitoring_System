package com.u1654949.corba.mc;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "MCC".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 26, 2020, 11:28:20 AM
 */

public class MCCPOATie
	extends MCCPOA
{
	private MCCOperations _delegate;

	private POA _poa;
	public MCCPOATie(MCCOperations delegate)
	{
		_delegate = delegate;
	}
	public MCCPOATie(MCCOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
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
	public MCCOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(MCCOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		return super._default_POA();
	}
	public void remove_alarm(com.u1654949.corba.common.TLSData tls_data)
	{
_delegate.remove_alarm(tls_data);
	}

	public java.lang.String[] get_tls_list()
	{
		return _delegate.get_tls_list();
	}

	public java.lang.String id()
	{
		return _delegate.id();
	}

	public void add_alarm(com.u1654949.corba.common.Alarm new_alarm)
	{
_delegate.add_alarm(new_alarm);
	}

}
