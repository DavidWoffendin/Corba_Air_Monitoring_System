package com.u1654949.corba.ls;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "TLS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 4, 2020, 12:42:21 PM
 */

public class TLSPOATie
	extends TLSPOA
{
	private TLSOperations _delegate;

	private POA _poa;
	public TLSPOATie(TLSOperations delegate)
	{
		_delegate = delegate;
	}
	public TLSPOATie(TLSOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
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
	public TLSOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(TLSOperations delegate)
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

	public com.u1654949.corba.common.Alarm[] alarm_log()
	{
		return _delegate.alarm_log();
	}

	public com.u1654949.corba.common.Alarm[] get_current_state()
	{
		return _delegate.get_current_state();
	}

	public com.u1654949.corba.common.MSData register_tms(java.lang.String region)
	{
		return _delegate.register_tms(region);
	}

	public void receive_alarm(com.u1654949.corba.common.Alarm new_alarm)
	{
_delegate.receive_alarm(new_alarm);
	}

	public com.u1654949.corba.common.NoxReading[] take_readings()
	{
		return _delegate.take_readings();
	}

	public boolean remove_tms(com.u1654949.corba.common.MSData data)
	{
		return _delegate.remove_tms(data);
	}

	public java.lang.String location()
	{
		return _delegate.location();
	}

	public java.lang.String[] get_known_stations()
	{
		return _delegate.get_known_stations();
	}

}
