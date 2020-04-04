package com.u1654949.corba.common;

/**
 * Generated from IDL struct "NoxReading".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 4, 2020, 12:42:21 PM
 */

public final class NoxReading
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public NoxReading(){}
	public long time;
	public int reading_value;
	public java.lang.String region = "";
	public java.lang.String station_name = "";
	public java.lang.String server_name = "";
	public NoxReading(long time, int reading_value, java.lang.String region, java.lang.String station_name, java.lang.String server_name)
	{
		this.time = time;
		this.reading_value = reading_value;
		this.region = region;
		this.station_name = station_name;
		this.server_name = server_name;
	}
}
