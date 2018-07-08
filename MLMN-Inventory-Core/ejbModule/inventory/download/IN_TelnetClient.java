package inventory.download; 

import inventory.utils.log.IN_TelnetLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.log4j.Logger;

import com.jscape.inet.telnet.TelnetException;
import com.jscape.inet.telnet.TelnetSession;

/******************************************************************************
NAME:       IN_TelnetClient
PURPOSE:	Su ly ket noi va gui lenh telnet

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        06/11/2013       AnhNT      		
******************************************************************************/
public class IN_TelnetClient {
	private static final Logger logger = Logger.getLogger(IN_TelnetClient.class.getName());
	
	private String _description = "";
	private String _isConect = "";
	private String _serverName;
	private String _HostName;
	private int _Port;
	private String _CommandTerminator = "\r\n";
	private Boolean _Debug = false;
	private String _DestinationPath;
	private String _Username;
	private String _Password;
	private String _PasswordPrompt;
	private String _ShellPrompt;
	private String _LoginPrompt;
	private int _ConnectTimeout = 15000;
	private int _ReadTimeout = 12000;
	private int _ReadWaitTimeout = 100000;
	private TelnetSession telnetSession = null;
	private String _ShellFirstPrompt;
	IN_TelnetLog inTelnetLog = new IN_TelnetLog();

	public String get_HostName() {
		return _HostName;
	}

	public void set_HostName(String _HostName) {
		this._HostName = _HostName;
	}

	public int get_Port() {
		return _Port;
	}

	public void set_Port(int _Port) {
		this._Port = _Port;
	}

	public String get_CommandTerminator() {
		return _CommandTerminator;
	}

	public void set_CommandTerminator(String _CommandTerminator) {
		this._CommandTerminator = _CommandTerminator;
	}

	public Boolean get_Debug() {
		return _Debug;
	}

	public void set_Debug(Boolean _Debug) {
		this._Debug = _Debug;
	}

	public String get_DestinationPath() {
		return _DestinationPath;
	}

	public void set_DestinationPath(String _DestinationPath) {
		this._DestinationPath = _DestinationPath;
	}

	public String get_Username() {
		return _Username;
	}

	public void set_Username(String _Username) {
		this._Username = _Username;
	}

	public String get_Password() {
		return _Password;
	}

	public void set_Password(String _Password) {
		this._Password = _Password;
	}

	public String get_PasswordPrompt() {
		return _PasswordPrompt;
	}

	public void set_PasswordPrompt(String _PasswordPrompt) {
		this._PasswordPrompt = _PasswordPrompt;
	}

	public String get_ShellPrompt() {
		return _ShellPrompt;
	}

	public void set_ShellPrompt(String _ShellPrompt) {
		this._ShellPrompt = _ShellPrompt;
	}

	public String get_LoginPrompt() {
		return _LoginPrompt;
	}

	public void set_LoginPrompt(String _LoginPrompt) {
		this._LoginPrompt = _LoginPrompt;
	}

	public int get_ConnectTimeout() {
		return _ConnectTimeout;
	}

	public void set_ConnectTimeout(int _ConnectTimeout) {
		this._ConnectTimeout = _ConnectTimeout;
	}

	public int get_ReadTimeout() {
		return _ReadTimeout;
	}

	public void set_ReadTimeout(int _ReadTimeout) {
		this._ReadTimeout = _ReadTimeout;
	}

	public int get_ReadWaitTimeout() {
		return _ReadWaitTimeout;
	}

	public void set_ReadWaitTimeout(int _ReadWaitTimeout) {
		this._ReadWaitTimeout = _ReadWaitTimeout;
	}

	public String get_ShellFirstPrompt() {
		return _ShellFirstPrompt;
	}

	public void set_ShellFirstPrompt(String _ShellFirstPrompt) {
		this._ShellFirstPrompt = _ShellFirstPrompt;
	}

	//Tao ket noi telnet
	private synchronized boolean connect() {
		_isConect = "";
		_description = "";
		
		telnetSession = new TelnetSession(_HostName);
		telnetSession.setHostname(_HostName);
		telnetSession.setPort(_Port);
		telnetSession.setLoginPrompt(_LoginPrompt);
		telnetSession.setPasswordPrompt(_PasswordPrompt);
		telnetSession.setCommandTerminator(_CommandTerminator);
		telnetSession.setDebug(_Debug); 
		
		try {
			telnetSession.setShellPrompt(_ShellFirstPrompt);
			telnetSession.connect(_Username, _Password, _ConnectTimeout); 
			return true;
		} catch (TelnetException e) { 
			_isConect = "DISCONNECTED";
			_description = _HostName +": "+e;
			inTelnetLog.set_isConnect(_isConect);
			inTelnetLog.set_description(_description);
			logger.warn(_HostName +": "+e);
			return false;
		} 
	}

	//Return chuoi du lieu tu server telnet
	private synchronized  String execute(String shellPrompt, String command, int readTimeout) { 
		String response = "";
		_isConect = "";
		_description = "";
		try {
			if (shellPrompt.length() >= 1)
				telnetSession.setShellPrompt(shellPrompt);
			telnetSession.pause(3000);
			response = telnetSession.send(command, readTimeout); 
			_isConect = "CONNECTED";
			inTelnetLog.set_isConnect(_isConect);
		} catch (TelnetException e) { 
			_isConect = "ERROR TELNET";
			_description = _HostName +": "+e;
			inTelnetLog.set_isConnect(_isConect);
			inTelnetLog.set_description(_description);
			logger.warn(_HostName +": "+e);
			response = response+" \nERROR";
		}
		return response;
	}

	public void disconnect() {
		try {
			telnetSession.disconnect();
		} catch (TelnetException e) {
			logger.warn("TelnetException disconnect() : "+e);
		}
	}


	//@Author AnhNT
	//@Date 2013-10-23  
	
	//Telnet lay du lieu license va port card Nokia Siemens BSC
	public IN_TelnetLog TelnetNSN_BSC(String neType, String p_ServerName, String p_SDate, String p_STime, String p_IP, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);
			set_serverName(p_ServerName);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);   

			boolean errorPort = false;
			boolean errorLicense = false;
			
			//Tao file chua thong tin port card Nokia Bsc
			String fileNamePortNSNBSC = "R_N_"+neType+"_PORT." + p_ServerName + "."+ p_SDate.replaceAll("-", "")+"."+ p_STime.replaceAll("-", ""); 
			File outFilePortNSNBSC = new File(_DestinationPath + "/portCard_NSN", fileNamePortNSNBSC + ".txt");
			
			//Tao file chua thong tin license Nokia Bsc
			String fileNameLicenseNSNBSC = "R_N_"+neType+"_LICENSE_SW." + p_ServerName + "."+ p_SDate.replaceAll("-", "")+"."+ p_STime.replaceAll("-", ""); 
			File outFileLicenseNSNBSC = new File(_DestinationPath + "/licenseSW_NSN", fileNameLicenseNSNBSC + ".txt");
			
			//Tao ket noi telnet 
			if(connect()){
				//Mo file ghi thong tin port card NSN BSC
				BufferedWriter WriterPortNSNBSC = new BufferedWriter(new FileWriter(outFilePortNSNBSC));  
				//Gui lenh lay thong tin port card NSN BSC
				/*String shellPrompt_Port = "COMMAND EXECUTED"; */
				String shellPrompt_Port = ">"; 
				String respPortNSNBSC = execute(shellPrompt_Port, "ZEEI:;", _ReadWaitTimeout);
				if (respPortNSNBSC.length() > 1)
					WriterPortNSNBSC.write(respPortNSNBSC); 
				if(respPortNSNBSC.equalsIgnoreCase("ERROR")){
					errorPort = true;
					WriterPortNSNBSC.close();
				}

				WriterPortNSNBSC.close();
				//-------------------------------------
				 
				//Mo file ghi thong tin license NSN BSC
				BufferedWriter WriterLicenseNSNBSC = new BufferedWriter(new FileWriter(outFileLicenseNSNBSC)); 
				//Gui lenh lay thong tin license NSN BSC

				String shellPrompt_License = ">"; 
				String respLicenseNSNBSC = "";
				
				//Gui lenh lay thong tin license chua kich hoat
				respLicenseNSNBSC = execute(shellPrompt_License, "ZW7I:FEA,FULL:FSTATE=OFF;", _ReadWaitTimeout);
				if (respLicenseNSNBSC.length() > 1)
					WriterLicenseNSNBSC.write(respLicenseNSNBSC); 
				
				if(respLicenseNSNBSC.equalsIgnoreCase("ERROR")){
					errorLicense = true;
					WriterLicenseNSNBSC.close();
				}
				
				if(errorLicense == false){
					//Gui lenh lay thong tin license da kich hoat
					respLicenseNSNBSC = execute(shellPrompt_License, "ZW7I:FEA,FULL:FSTATE=ON;", _ReadWaitTimeout);
					WriterLicenseNSNBSC.newLine();
					if (respLicenseNSNBSC.length() > 1)
						WriterLicenseNSNBSC.write(respLicenseNSNBSC); 
					
					//Gui lenh lay thong tin ngay license active
					respLicenseNSNBSC = execute(shellPrompt_License, "ZW7I:LIC,FULL:STATE=LIM;", _ReadWaitTimeout);
					WriterLicenseNSNBSC.newLine();
					if (respLicenseNSNBSC.length() > 1)
						WriterLicenseNSNBSC.write(respLicenseNSNBSC); 
					
					//Gui lenh lay thong tin usage
					respLicenseNSNBSC = execute(shellPrompt_License, "ZW7I:UCAP,FULL:;", _ReadWaitTimeout);
					WriterLicenseNSNBSC.newLine();
					if (respLicenseNSNBSC.length() > 1)
						WriterLicenseNSNBSC.write(respLicenseNSNBSC); 
				}
				
				WriterLicenseNSNBSC.close();  
				disconnect();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Telnet_VHC : "+ e);
			logger.error("Telnet_VHC", e);
		}
		return inTelnetLog;
	} 
	
	//Telnet lay du lieu license va port card Nokia Siemens RNC
	public IN_TelnetLog TelnetNSN_RNC(String neType, String p_ServerName, String p_SDate, String p_STime, String p_IP, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);
			set_serverName(p_ServerName);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt); 
			
			//Tao file chua thong tin port card Nokia RNC
			String fileNamePortNSNRNC = "R_N_"+neType+"_PORT." + p_ServerName + "."+ p_SDate.replaceAll("-", "")+"."+ p_STime.replaceAll("-", ""); 
			File outFilePortNSNRNC = new File(_DestinationPath + "/portCard_NSN", fileNamePortNSNRNC + ".txt");  
			
			//Tao file chua thong tin license Nokia RNC
			String fileNameLicenseNSNRNC = "R_N_"+neType+"_LICENSE_SW." + p_ServerName + "."+ p_SDate.replaceAll("-", "")+"."+ p_STime.replaceAll("-", ""); 
			File outFileLicenseNSNRNC = new File(_DestinationPath + "/licenseSW_NSN", fileNameLicenseNSNRNC + ".txt");   
			
			boolean errorPort = false;
			boolean errorLicense = false;
			//TelnetSession telnetSession = connect();
			//Tao ket noi telnet
			if(connect()){
				//Mo file ghi thong tin port card NSN RNC
				BufferedWriter WriterPortNSNRNC = new BufferedWriter(new FileWriter(outFilePortNSNRNC));
				//Gui lenh lay thong tin port card NSN RNC
				String shellPrompt = ">";
				String respPortNSNRNC = execute(shellPrompt, "ZQRB:NPGEP,4:;", _ReadWaitTimeout);
				if (respPortNSNRNC.length() > 1)
					WriterPortNSNRNC.write(respPortNSNRNC); 
				
				if(respPortNSNRNC.equalsIgnoreCase("ERROR")){
					WriterPortNSNRNC.close();
					errorPort = true;
				}
				
				if(errorPort == false){
					respPortNSNRNC = execute(shellPrompt, "ZQRB:NPGEP,6:;", _ReadWaitTimeout);
					WriterPortNSNRNC.newLine();
					if (respPortNSNRNC.length() > 1)
						WriterPortNSNRNC.write(respPortNSNRNC);
				} 
				
				WriterPortNSNRNC.close();
				//-------------------------------------
				
				//Mo file ghi thong tin license NSN RNC
				BufferedWriter WriterLicenseNSNRNC = new BufferedWriter(new FileWriter(outFileLicenseNSNRNC));
				//Gui lenh lay thong tin license NSN RNC

				shellPrompt = ">"; 
				String respLicenseNSNRNC = ""; 
				//Gui lenh lay thong tin license chua kich hoat
				respLicenseNSNRNC = execute(shellPrompt, "ZW7I:FEA,FULL:FSTATE=OFF;", _ReadWaitTimeout);
				if (respLicenseNSNRNC.length() > 1)
					WriterLicenseNSNRNC.write(respLicenseNSNRNC); 
				
				if(respLicenseNSNRNC.equalsIgnoreCase("ERROR")){
					WriterLicenseNSNRNC.close();
					errorLicense = true;
				}
				
				if(errorLicense == false){
					//Gui lenh lay thong tin license da kich hoat
					respLicenseNSNRNC = execute(shellPrompt, "ZW7I:FEA,FULL:FSTATE=ON;", _ReadWaitTimeout);
					WriterLicenseNSNRNC.newLine();
					if (respLicenseNSNRNC.length() > 1)
						WriterLicenseNSNRNC.write(respLicenseNSNRNC); 
					
					//Gui lenh lay thong tin ngay license active
					respLicenseNSNRNC = execute(shellPrompt, "ZW7I:LIC,FULL:STATE=LIM;", _ReadWaitTimeout);
					WriterLicenseNSNRNC.newLine();
					if (respLicenseNSNRNC.length() > 1)
						WriterLicenseNSNRNC.write(respLicenseNSNRNC); 
					
					//Gui lenh lay thong tin usage
					respLicenseNSNRNC = execute(shellPrompt, "ZW7I:UCAP,FULL:;", _ReadWaitTimeout);
					WriterLicenseNSNRNC.newLine();
					if (respLicenseNSNRNC.length() > 1)
						WriterLicenseNSNRNC.write(respLicenseNSNRNC);  
				} 
				
				WriterLicenseNSNRNC.close();
				//-------------------------------------
				
				disconnect();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return inTelnetLog;
	}
	
	//Telnet lay du lieu license va port card Nokia Siemens SGSN
	public IN_TelnetLog TelnetNSN_SGSN(String neType, String p_ServerName, String p_SDate, String p_STime,String p_IP, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);
			set_serverName(p_ServerName);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt); 
			
			boolean errorLicense = false;
			//Tao file chua thong tin port card Nokia SGSN-BSC
			String fileNamePortBsc = "R_N_"+neType+"_BSC_PORT." + p_ServerName + "."+ p_SDate.replaceAll("-", "")+"."+ p_STime.replaceAll("-", ""); 
			File outFilePortBsc = new File(_DestinationPath + "/portCard_NSN", fileNamePortBsc + ".txt");  
			
			//Tao file chua thong tin port card SGSN-RNC
			String fileNamePortRnc = "R_N_"+neType+"_RNC_PORT." + p_ServerName + "." + p_SDate.replaceAll("-", "")+"."+ p_STime.replaceAll("-", ""); 
			File outFilePortRnc = new File(_DestinationPath + "/portCard_NSN", fileNamePortRnc + ".txt");
			
			//Tao file chua thong tin license Nokia SGSN
			String fileNameLicenseSGSN = "R_N_"+neType+"_LICENSE_SW." + p_ServerName + "."+ p_SDate.replaceAll("-", "")+"."+ p_STime.replaceAll("-", ""); 
			File outFileLicenseSGSN = new File(_DestinationPath + "/licenseSW_NSN", fileNameLicenseSGSN + ".txt");   
			
			//Tao ket noi telnet
			if(connect()){ 
				//Gui lenh lay thong tin port card NSN SGSN
				//Mo file ghi thong tin port card SGSN-BSC
				BufferedWriter WriterPortBsc = new BufferedWriter(new FileWriter(outFilePortBsc));
				try { 
					String shellPrompt = ">";
					//Gui lenh lay thong tin port card SGSN-BSC
					String respPortBsc = execute(shellPrompt, "ZFWO:PAPU=0&&16;", _ReadWaitTimeout);
					if (respPortBsc.length() > 1)
						WriterPortBsc.write(respPortBsc);
					WriterPortBsc.close();
				} catch (Exception e) {
					System.out.println("Can't close file " + outFilePortBsc + "\r\n"
							+ e.getMessage()); 
				} 
				
				//Mo file ghi thong tin port card SGSN-RNC
				BufferedWriter WriterPortRnc = new BufferedWriter(new FileWriter(outFilePortRnc)); 
				try {
					String shellPrompt = ">";
					//Gui lenh lay thong tin port card SGSN-RNC
					String respPortRnc = execute(shellPrompt, "ZE6I:;", _ReadWaitTimeout);
					if (respPortRnc.length() > 1)
						WriterPortRnc.write(respPortRnc); 
					WriterPortRnc.close();
				} catch (Exception e) {
					System.out.println("Can't close file " + outFilePortRnc + "\r\n"
							+ e.getMessage()); 
				} 
				//-------------------------------------
				
				//Gui lenh lay thong tin license NSN SGSN
				//Mo file ghi thong tin license SGSN-RNC
				BufferedWriter WriterLicenseSGSN = new BufferedWriter(new FileWriter(outFileLicenseSGSN));
				try {
					String shellPrompt = ">"; 
					String respLicenseSGSN = "";
					
					//Gui lenh lay thong tin license chua kich hoat
					respLicenseSGSN = execute(shellPrompt, "ZW7I:FEA,FULL:FSTATE=OFF;", _ReadWaitTimeout);
					if (respLicenseSGSN.length() > 1)
						WriterLicenseSGSN.write(respLicenseSGSN); 
					
					if(respLicenseSGSN.equalsIgnoreCase("ERROR")){
						errorLicense = true;
						WriterLicenseSGSN.close();
					}
					
					if(errorLicense == false){
						//Gui lenh lay thong tin license da kich hoat
						respLicenseSGSN = execute(shellPrompt, "ZW7I:FEA,FULL:FSTATE=ON;", _ReadWaitTimeout);
						WriterLicenseSGSN.newLine();
						if (respLicenseSGSN.length() > 1)
							WriterLicenseSGSN.write(respLicenseSGSN); 
						
						//Gui lenh lay thong tin ngay license active
						respLicenseSGSN = execute(shellPrompt, "ZW7I:LIC,FULL:STATE=LIM;", _ReadWaitTimeout);
						WriterLicenseSGSN.newLine();
						if (respLicenseSGSN.length() > 1)
							WriterLicenseSGSN.write(respLicenseSGSN);
					} 
					
					WriterLicenseSGSN.close();
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileLicenseSGSN + "\r\n"
							+ e.getMessage()); 
				} 
				//-------------------------------------
				
				disconnect();
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return inTelnetLog;
	}
	
	//Lay thong tin license software Ericsson RNC
	public IN_TelnetLog TelnetEricsson_RNC(String neType, String p_ServerName, String p_SDate, String p_STime, String p_IP,String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);
			set_serverName(p_ServerName);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);
			
			//Tao file chua thong tin license software Ericsson RNC
			String fileName = "R_E_"+neType+"_LICENSE_SW." + p_ServerName + "."+ p_SDate.replaceAll("-", "")+"."+ p_STime.replaceAll("-", "");

			File outFileLicenseERSRNC = new File(_DestinationPath + "/licenseSW_Ericsson", fileName + ".txt");
			BufferedWriter WriterLicenseERSRNC = new BufferedWriter(new FileWriter(outFileLicenseERSRNC)); 
			 
			if(connect()){
				try {
					String shellPrompt = "<";
					//Gui lenh lay thong tin license chua kich hoat
					String respLicenseERSRNC = execute(shellPrompt, "inv", _ReadWaitTimeout);
					if (respLicenseERSRNC.length() > 1)
						WriterLicenseERSRNC.write(respLicenseERSRNC); 
					WriterLicenseERSRNC.close();
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileLicenseERSRNC + "\r\n"
							+ e.getMessage()); 
				} 

				disconnect();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return inTelnetLog;
	}

	public String get_serverName() {
		return _serverName;
	}

	public void set_serverName(String _serverName) {
		this._serverName = _serverName;
	}
}
