package com.u1654949.corba.mc;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "MCS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 26, 2020, 11:28:20 AM
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
	public java.lang.String name()
	{
		return _delegate.name();
	}

	public boolean ping()
	{
		return _delegate.ping();
	}

	public boolean remove_tls_connection(java.lang.String name)
	{
		return _delegate.remove_tls_connection(name);
	}

	public boolean remove_mcc(java.lang.String id)
	{
		return _delegate.remove_mcc(id);
	}

	public boolean register_mcc(java.lang.String id)
	{
		return _delegate.register_mcc(id);
	}

	public com.u1654949.corba.common.Alarm[] get_region_state(java.lang.String region)
	{
		return _delegate.get_region_state(region);
	}

	public com.u1654949.corba.ls.TLS get_connected_tls(java.lang.String name)
	{
		return _delegate.get_connected_tls(name);
	}

	public com.u1654949.corba.common.Alarm[] get_alarms(java.lang.String id)
	{
		return _delegate.get_alarms(id);
	}

	public void cancel_alarm(com.u1654949.corba.common.TLSData tls_data)
	{
_delegate.cancel_alarm(tls_data);
	}

	public void receive_alarm(com.u1654949.corba.common.Alarm new_alarm)
	{
_delegate.receive_alarm(new_alarm);
	}

	public java.lang.String[] get_known_stations()
	{
		return _delegate.get_known_stations();
	}

	public boolean register_tls_connection(java.lang.String name)
	{
		return _delegate.register_tls_connection(name);
	}

}
