package com.u1654949.airnox.ms;

import com.u1654949.airnox.common.Constants;
import com.u1654949.corba.common.NoxReading;
import com.u1654949.corba.ls.TLS;
import com.u1654949.corba.ls.TLSHelper;
import com.u1654949.corba.ms.TMSPOA;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class TMSDriver extends TMSPOA {

	public static void main(String[] args) {
		try {
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// Get a reference to the Naming service
			org.omg.CORBA.Object nameServiceObj =
					orb.resolve_initial_references (Constants.NAME_SERVICE);
			if (nameServiceObj == null) {
				System.out.println("nameServiceObj = null");
				return;
			}

			// Use NamingContextExt instead of NamingContext. This is
			// part of the Interoperable naming Service.
			NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
			if (nameService == null) {
				System.out.println("nameService = null");
				return;
			}

			String name = "Relay";
			TLS tls = TLSHelper.narrow(nameService.resolve_str(name));

			// call the Relay
			System.out.println("Calling relay...");

			String result = tls.name();
			System.out.println("Result = " + result);

		} catch (Exception e) {
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		}
	}

	@Override
	public String station_name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String location() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deactivate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean activate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void send_alarm(int measurement) {
		// TODO Auto-generated method stub

	}

	@Override
	public NoxReading get_reading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoxReading[] get_reading_log() {
		// TODO Auto-generated method stub
		return null;
	}

}
