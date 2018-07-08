package inventory.download;

public class TelnetThread extends Thread {

	private String type;
	private String sv;
	private String sd;
	private String ip;
	private String us;
	private String ps;
	IN_TelnetClient telnet;

	public TelnetThread(String type, String sv, String sd, String ip,
			String us, String ps) {
		this.type = type;
		this.sv = sv;
		this.sd = sd;
		this.ip = ip;
		this.us = us;
		this.ps = ps;

		telnet = new IN_TelnetClient();
		telnet.set_DestinationPath("D:/inventorydata/download/");
		
	}
	public void run() {
		/*if(type.equals("BSC")){
			telnet.TelnetNSN_BSC(type, sv, sd, ip, us, ps);
		}else if(type.equals("RNC"))
			telnet.TelnetNSN_RNC(type, sv, sd, ip, us, ps);*/
	}
	
}
