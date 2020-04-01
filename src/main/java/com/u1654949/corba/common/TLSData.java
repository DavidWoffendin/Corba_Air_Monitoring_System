package com.u1654949.corba.common;

/**
 * Generated from IDL struct "TLSData".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 1, 2020, 12:42:52 PM
 */

public final class TLSData
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public TLSData(){}
	public java.lang.String tls_name = "";
	public com.u1654949.corba.common.MSData stationData;
	public TLSData(java.lang.String tls_name, com.u1654949.corba.common.MSData stationData)
	{
		this.tls_name = tls_name;
		this.stationData = stationData;
	}
}
