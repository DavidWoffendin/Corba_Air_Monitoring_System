package com.u1654949.corba.common;

/**
 * Generated from IDL struct "TLSData".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 4, 2020, 12:42:21 PM
 */

public final class TLSData
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public TLSData(){}
	public java.lang.String tls_name = "";
	public java.lang.String tls_location = "";
	public com.u1654949.corba.common.MSData stationData;
	public TLSData(java.lang.String tls_name, java.lang.String tls_location, com.u1654949.corba.common.MSData stationData)
	{
		this.tls_name = tls_name;
		this.tls_location = tls_location;
		this.stationData = stationData;
	}
}
