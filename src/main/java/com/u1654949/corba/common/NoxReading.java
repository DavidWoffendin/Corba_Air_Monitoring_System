package com.u1654949.corba.common;

/**
 * Generated from IDL struct "NoxReading".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Mar 26, 2020, 11:28:20 AM
 */

public final class NoxReading
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public NoxReading(){}
	public long time;
	public int reading_value;
	public NoxReading(long time, int reading_value)
	{
		this.time = time;
		this.reading_value = reading_value;
	}
}
