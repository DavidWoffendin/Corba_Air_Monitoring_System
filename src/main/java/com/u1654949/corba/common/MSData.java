package com.u1654949.corba.common;

/**
 * Generated from IDL struct "MSData".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 1, 2020, 12:42:52 PM
 */

public final class MSData
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public MSData(){}
	public java.lang.String region = "";
	public java.lang.String station_name = "";
	public int alarm_level;
	public MSData(java.lang.String region, java.lang.String station_name, int alarm_level)
	{
		this.region = region;
		this.station_name = station_name;
		this.alarm_level = alarm_level;
	}
}
