package com.u1654949.corba.ms;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "TMS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 28, 2020, 11:31:38 AM
 */

public class TMSPOATie
	extends TMSPOA
{
	private TMSOperations _delegate;

	private POA _poa;
	public TMSPOATie(TMSOperations delegate)
	{
		_delegate = delegate;
	}
	public TMSPOATie(TMSOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
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
	public TMSOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(TMSOperations delegate)
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
	public java.lang.String station_name()
	{
		return _delegate.station_name();
	}

	public boolean activate()
	{
		return _delegate.activate();
	}

	public boolean deactivate()
	{
		return _delegate.deactivate();
	}

	public com.u1654949.corba.common.NoxReading get_reading()
	{
		return _delegate.get_reading();
	}

	public void send_alarm(int measurement)
	{
_delegate.send_alarm(measurement);
	}

	public com.u1654949.corba.common.NoxReading[] get_reading_log()
	{
		return _delegate.get_reading_log();
	}

	public void reset()
	{
_delegate.reset();
	}

	public java.lang.String location()
	{
		return _delegate.location();
	}

}
