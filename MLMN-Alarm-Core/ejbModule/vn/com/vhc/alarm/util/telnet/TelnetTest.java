package vn.com.vhc.alarm.util.telnet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelnetTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DateFormat dfDay = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
		// TODO Auto-generated method stub
		ALTelnetClient telnet = new ALTelnetClient();
		
		String path="C:/Users/vhcsoft/Desktop";
		
		telnet.set_DestinationPath(path);
						
		String p_ServerName = "TLH120141";
		String p_IP="10.19.241.188"; 
		String p_Username = "truyendan";
		String p_Password = "ddh@vms2";
		
		Date sdate = new Date();
		System.out.println("sdate = "+dfDay.format(sdate)+" "+dfTime.format(sdate));
		
		telnet.TelnetLoadTransmission(p_ServerName, p_IP, p_Username, p_Password);
		
		Date edate = new Date();
		System.out.println("edate = "+dfDay.format(edate)+" "+dfTime.format(edate));
	} 
}
