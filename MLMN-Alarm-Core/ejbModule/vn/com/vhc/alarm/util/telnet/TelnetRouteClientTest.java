package vn.com.vhc.alarm.util.telnet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TelnetRouteClientTest {
	public static void main(String arg[]) throws IOException, Exception{

		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf1 = new SimpleDateFormat("MMM dd");	
	    String SysDate = sdf1.format(cal.getTime());
	    String p_Time = "Jan 11";
	    System.out.println("Time = "+SysDate);
		String[] p_IP = {"10.20.245.1"};
		String[] p_RouteName = {"ACC30-3C"};
		String p_Username = "trucca";
		String p_Password = "trucca@vms2";
		
		TelnetRouteClient Telnet_IPBB = new TelnetRouteClient();
		for(int i = 0; i < p_IP.length; i++){
			Telnet_IPBB.TelnetRoute(p_Time, p_RouteName[i], p_IP[i], p_Username, p_Password);
		}
		
	}
}
