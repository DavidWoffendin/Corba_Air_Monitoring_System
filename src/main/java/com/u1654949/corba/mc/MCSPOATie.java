package com.u1654949.corba.mc;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "MCS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 8, 2020, 11:56:17 AM
 */

public class MCSPOATie
	extends MCSPOA
{
	private MCSOperations _delegate;

	private POA _poa;
	public MCSPOATie(MCSOperations delegate)
	{
		_delegate = delegate;
	}
	public MCSPOATie(MCSOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.u1654949.corba.mc.MCS _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.u1654949.corba.mc.MCS __r = com.u1654949.corba.mc.MCSHelper.narrow(__o);
		return __r;
	}
	public com.u1654949.corba.mc.MCS _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.u1654949.corba.mc.MCS __r = com.u1654949.corba.mc.MCSHelper.narrow(__o);
		return __r;
	}
	public MCSOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(MCSOperations delegate)
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
	public boolean register_tls_connection(java.lang.String name)
	{
		return _delegate.register_tls_connection(name);
	}

	public java.lang.String name()
	{
		return _delegate.name();
	}

	public boolean remove_tls_connection(java.lang.String name)
	{
		return _delegate.remove_tls_connection(name);
	}

	public void receive_alarm(com.u1654949.corba.common.Alarm new_alarm)
	{
_delegate.receive_alarm(new_alarm);
	}

	public void cancel_alarm(com.u1654949.corba.common.TLSData tls_data)
	{
_delegate.cancel_alarm(tls_data);
	}

}
