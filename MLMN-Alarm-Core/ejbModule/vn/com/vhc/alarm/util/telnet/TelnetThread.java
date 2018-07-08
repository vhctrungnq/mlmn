package vn.com.vhc.alarm.util.telnet;

import vn.com.vhc.alarm.cni.info.Al_TelnetServers;

public class TelnetThread extends Thread {

	private String type;
	private String sv;
	private String sd;
	private String ip;
	private String us;
	private String ps;
	ALTelnetClient telnet;

	public TelnetThread(Al_TelnetServers telnetServers) {
		this.type = type;
		this.sv = sv;
		this.sd = sd;
		this.ip = ip;
		this.us = us;
		this.ps = ps;

		telnet = new ALTelnetClient();
		telnet.set_DestinationPath("D:/inventorydata/download/");
		
	}
	public void run() {
		/*if(type.equals("BSC")){
			telnet.TelnetNSN_BSC(type, sv, sd, ip, us, ps);
		}else if(type.equals("RNC"))
			telnet.TelnetNSN_RNC(type, sv, sd, ip, us, ps);*/
	}
	
}
