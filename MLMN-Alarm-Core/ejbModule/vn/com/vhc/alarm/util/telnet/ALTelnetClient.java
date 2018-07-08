package vn.com.vhc.alarm.util.telnet; 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.utils.log.AL_TelnetLog;

import com.jscape.inet.ssh.SshException;
import com.jscape.inet.ssh.SshSession;
import com.jscape.inet.telnet.TelnetException;
import com.jscape.inet.telnet.TelnetSession;

public class ALTelnetClient {
	private static final Logger logger = Logger.getLogger(ALTelnetClient.class.getName());
	
	private String _description = "";
	private String _isConect = "";
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
	private int _ReadTimeout = 150000;
	private int _ReadWaitTimeout = 150000;
	TelnetSession telnetSession = null;
	SshSession sshSession = null;
	private String _ShellFirstPrompt;
	AL_TelnetLog alTelnetLog = new AL_TelnetLog();

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

	private synchronized boolean sshConnect() {
		_isConect = "";
		_description = ""; 

		try {
			// creates new SshShession instance providing hostname, username and
			// password arguments
			sshSession = new SshSession(_HostName, _Username, _Password);
			// set expected shell prompt from SSH server
			sshSession.setShellPrompt(_ShellFirstPrompt); 
			// establish connection
			sshSession.connect(); 
			return true;
		} catch (SshException e) {
			_isConect = "DISCONNECTED";
			_description = _HostName +": "+e;
			alTelnetLog.set_isConnect(_isConect);
			alTelnetLog.set_description(_description);
			logger.warn(_HostName +": "+e);
			return false;
		}
	}
	
	@SuppressWarnings("unused")
	private synchronized String sshExecute(String shellPrompt, String command, int readTimeout) { 
		String response = "";
		_isConect = "";
		_description = "";
		try {
			if (shellPrompt.length() >= 1){
				sshSession.setShellPrompt(shellPrompt);
			}
			sshSession.pause(1000); 
			response = sshSession.send(command, readTimeout); 
			_isConect = "CONNECTED";
			alTelnetLog.set_isConnect(_isConect);
		} catch (Exception e) { 
			response = "ERROR";
			_isConect = "ERROR TELNET";
			_description = _HostName +": "+e;
			alTelnetLog.set_isConnect(_isConect);
			alTelnetLog.set_description(_description);
		} 
		return response;
	}
	
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
			alTelnetLog.set_isConnect(_isConect);
			alTelnetLog.set_description(_description);
			logger.warn(_HostName +": "+e);
			return false;
		}
	}

	private synchronized String execute(String shellPrompt, String command, int readTimeout) { 
		String response = "";
		_isConect = "";
		_description = "";
		try {
			if (shellPrompt.length() >= 1){
				telnetSession.setShellPrompt(shellPrompt);
			}
			telnetSession.pause(1000); 
			response = telnetSession.send(command, readTimeout); 
			_isConect = "CONNECTED";
			alTelnetLog.set_isConnect(_isConect);
		} catch (Exception e) { 
			response = "ERROR";
			_isConect = "ERROR TELNET";
			_description = _HostName +": "+e;
			alTelnetLog.set_isConnect(_isConect);
			alTelnetLog.set_description(_description); 
			try {
				shellPrompt = ">";
				telnetSession.setShellPrompt(shellPrompt);
				response = telnetSession.send(command, readTimeout);
			} catch (TelnetException e1) {
				// TODO Auto-generated catch block
				logger.warn(_HostName +": "+e);
				e1.printStackTrace();
			} 
		} 
		return response;
	}

	public void disconnect() {
		try {
			if (telnetSession != null) {
				telnetSession.disconnect(); 
			}
		} catch (TelnetException e) {}
	}

	public synchronized AL_TelnetLog CommandNSN_Active(String p_ServerName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password) {

		try { 
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate); 

			if(connect()){
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/NSN-Active");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				
				//Tao file chua alarm active BSC
				String strFileName = "ACTIVE." + p_ServerName + "." + "ZAHO" + "-"
						+ p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "."
						+ strHour; 
				File outFile = new File(dir, strFileName + ".txt"); 
				BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile));
				
				try {
					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZAHO;", _ReadWaitTimeout);
					if (resp.length() > 1)
						Writer.write(resp);
				} catch (Exception e) {
					System.out.println("Can't close file " + outFile + "\r\n"
							+ e.getMessage());
				} finally {
					Writer.close();
				}
				
				//Tao file chua alarm active BTS
				String strFileNameZEOL = "ACTIVE" + "." + p_ServerName + "."
						+ "ZEOL" + "-" + p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "."
						+ strHour;
				File outFileZEOL = new File(dir,strFileNameZEOL + ".txt");
				BufferedWriter WriterZEOL = new BufferedWriter(new FileWriter(outFileZEOL));

				try {
					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZEOL;", _ReadWaitTimeout);
					if (resp.length() > 1)
						WriterZEOL.write(resp);
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					WriterZEOL.close();
				} 
				disconnect();
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return alTelnetLog;
	}

	public synchronized AL_TelnetLog CommandNSN_Active_3G(String p_ServerName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password) {
		
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate); 
 
			if(connect()){
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/NSN-Active");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				
				String strFileNameZAAP = "ACTIVE." + p_ServerName + "." + "ZAAP" + "-"
						+ p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" 
						+ strDay + "."+ strHour; 
				File outFileZAAP = new File(dir, strFileNameZAAP + ".txt"); 
				BufferedWriter WriterZAAP = new BufferedWriter(new FileWriter(outFileZAAP));
				
				try {
					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZAAP;", _ReadWaitTimeout);
					if (resp.length() > 1)
						WriterZAAP.write(resp);
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileZAAP + "\r\n"
							+ e.getMessage());

				} finally {
					WriterZAAP.close();
				} 
				disconnect();
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return alTelnetLog;
	}
	public synchronized AL_TelnetLog CommandNSN_Active_SGSN(String p_ServerName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password) {
		
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate); 
 
			if(connect()){
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/NSN-Active");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				
				String strFileNameZAAP = "ACTIVE." + p_ServerName + "." + "ZAHO" + "-"
						+ p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" 
						+ strDay + "."+ strHour; 
				File outFileZAHO = new File(dir, strFileNameZAAP + ".txt"); 
				BufferedWriter WriterZAHO = new BufferedWriter(new FileWriter(outFileZAHO));
				
				try {
					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZAHO;", _ReadWaitTimeout);
					if (resp.length() > 1)
						WriterZAHO.write(resp);
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileZAHO + "\r\n"
							+ e.getMessage());

				} finally {
					WriterZAHO.close();
				} 
				disconnect();
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return alTelnetLog;
	}
	public synchronized AL_TelnetLog CommandNSN_History(String p_ServerName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password) {

		try { 
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);  
 
			if(connect()){
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/NSN-2G");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				
				//Tao file chua alarm history BSC
				String strFileNameZAHP = "NOKIA_HISTORY." + p_ServerName + ".ZAHP"
						+ "-" + p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "."
						+ strHour;

				File outFileZAHP = new File(dir, strFileNameZAHP + ".txt");
				BufferedWriter WriterZAHP = new BufferedWriter(new FileWriter( outFileZAHP));
				
				try {

					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZAHP:::" + p_SDate + ","
							+ p_STime + ";", _ReadWaitTimeout);
					if (resp.length() > 1) {
						WriterZAHP.write(resp);
					}
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileZAHP + "\r\n"
							+ e.getMessage());

				} finally {
					WriterZAHP.close();
				}

				//Tao file chua alarm history BTS
				String strFileNameZEOH = "NOKIA_HISTORY." + p_ServerName + "."
						+ "ZEOH" + "-" + p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "."
						+ strHour; 
				File outFileZEOH = new File(dir,strFileNameZEOH + ".txt");
				BufferedWriter WriterZEOH = new BufferedWriter(new FileWriter(outFileZEOH)); 
				
				try { 
					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZEOH:" + p_SDate + ","
							+ p_STime + ";", _ReadWaitTimeout);
					if (resp.length() > 1) {
						WriterZEOH.write(resp);
					}
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileZEOH + "\r\n"
							+ e.getMessage()); 
				} finally {
					WriterZEOH.close();
				} 
				disconnect();
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return alTelnetLog;
	}

	public synchronized AL_TelnetLog CommandNSN_History_3G(String p_ServerName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password) {

		try {

			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);

			dfDay = new SimpleDateFormat("yyyy-MM-dd");
			dfHour = new SimpleDateFormat("HH-mm-ss");

			String p_EDate = dfDay.format(pDate);
			String p_ETime = dfHour.format(pDate); 
 
			if(connect()){
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/NSN-3G");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				
				String strFileNameZAHP = "NOKIA_HISTORY." + p_ServerName + ".ZAHP"
						+ "-" + p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "."
						+ strHour;

				File outFileZAHP = new File(dir, strFileNameZAHP + ".txt");
				BufferedWriter WriterZAHP = new BufferedWriter(new FileWriter( outFileZAHP));
				
				try { 
					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZAHP:::" + p_SDate + ","
							+ p_STime + "," + p_EDate + "," + p_ETime + ";",
							_ReadWaitTimeout);
					if (resp.length() > 1) {
						WriterZAHP.write(resp);
					}
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileZAHP + "\r\n"
							+ e.getMessage()); 
				} finally {
					WriterZAHP.close();
				}

				disconnect();
			} 
		} catch (Exception e) {
			System.out.println("Can't creater file :"
					+ e.getMessage()); 
		}
		return alTelnetLog;
	}

	// Telnet lay du lieu Ericsson Active 2G va Active STP
	public synchronized boolean CommandERS_Active_2G(String p_ServerName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password,
			int p_Count) {

		try {

			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "Windows NT Domain";
			String loginPrompt = "login name:";
			String passwordPrompt = "password:";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);// "10.21.129.132";

			BufferedWriter Writer;
			
			//Tao thu muc chua 
			File dir = new File(_DestinationPath + "/ERICSSON-Active");
			
			if(!dir.exists())
			{
				dir.mkdir();
			}
			
			String fileName = "ERICSSON_ACTIVE." + p_ServerName + "-"
					+ p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "."
					+ strHour + "." + p_Count;

			File outFile = new File(dir,fileName + ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			connect();
			String resp = "";
			try {
				/* System.out.println("Connect OK"); */

				String shellPrompt = ">";
				resp = execute(shellPrompt, "\n", _ReadTimeout);
				if (resp.length() > 1)
					Writer.write(resp);

				shellPrompt = "<";
				resp = execute(shellPrompt, "mml", _ReadTimeout);
				if (resp.length() > 1)
					Writer.write(resp);

				resp = execute(shellPrompt, "allip;", _ReadWaitTimeout);
				if (resp.length() > 1)
					Writer.write(resp);  

			} catch (Exception e) {
				System.out.println("Can't close file " + outFile + "\r\n"
						+ e.getMessage());

			} finally {
				Writer.close();
				if (resp == "ERROR")
					outFile.deleteOnExit();

			}

			disconnect();
			/* System.out.println("Disconnect: " + p_IP + "---" + p_SeverName); */

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Error Telnet: " + p_IP + "---" + p_ServerName
					+ "\r\n" + e.getMessage());
			return false;
		}

		return true;

	}

	// Telnet lay du lieu Active Ericsson 3G va MGW
	public synchronized boolean CommandActiveE3GCore(String p_Server_array, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password,
			int p_Count) {
		try {
			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);
			String shellFirstPrompt = ">";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			TelnetSession telnetSession = new TelnetSession(p_IP);
			telnetSession.setHostname(p_IP);
			telnetSession.setPort(23);
			telnetSession.setLoginPrompt(loginPrompt);
			telnetSession.setPasswordPrompt(passwordPrompt);
			telnetSession.setCommandTerminator(_CommandTerminator);
			telnetSession.setDebug(false);
			telnetSession.setShellPrompt(shellFirstPrompt);
			telnetSession.connect(p_Username, p_Password, _ConnectTimeout);
			try {
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/ERICSSON-Active");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				
				String[] p_Server_RNC_ERICSSON = p_Server_array.split(",");
				for (int iIP = 0; iIP < p_Server_RNC_ERICSSON.length; iIP++) {
					String p_SeverName = p_Server_RNC_ERICSSON[iIP].toString();

					BufferedWriter Writer;
					String fileName = "ERICSSON_ACTIVE." + p_SeverName + "-"
							+ p_SDate.replaceAll("-", "") + "."
							+ p_STime.replaceAll("-", "") + "-" + strDay + "."
							+ strHour + "." + p_Count;

					File outFile = new File(dir, fileName + ".txt");
					Writer = new BufferedWriter(new FileWriter(outFile));

					telnetSession.setShellPrompt("> ", false);
					String resp = telnetSession.send("amos " + p_SeverName,
							_ReadWaitTimeout);
					// telnetSession.pause(1000);
					if (resp.length() > 1)
						Writer.write(resp);

					resp = telnetSession.send("alt", _ReadWaitTimeout);
					// /telnetSession.pause(1000);
					if (resp.length() > 1)
						Writer.write(resp);
					Writer.close();

					// / send lenh quit de thuc hien cho server tiep theo

					telnetSession.setShellPrompt("> ", false);
					resp = telnetSession.send("q", _ReadWaitTimeout);
					/* System.out.println(resp); */
				}

			} catch (TelnetException e) {

				System.out.println("Error Telnet: " + p_IP + "---"
						+ e.getMessage());

			} finally {
				telnetSession.disconnect();
			}

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Can't close file " + e.getMessage());
			return false;
		}

		return true;
	}

	public synchronized boolean CommandMBL(String p_IP, String p_ServerNameArray,
			String p_Username, String p_Password) {
		try {
			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			String strDay = dfDay.format(pDate);
			String shellFirstPrompt = ">";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			TelnetSession telnetSession = new TelnetSession(p_IP);
			telnetSession.setHostname(p_IP);
			telnetSession.setPort(23);
			telnetSession.setLoginPrompt(loginPrompt);
			telnetSession.setPasswordPrompt(passwordPrompt);
			telnetSession.setCommandTerminator(_CommandTerminator);
			telnetSession.setDebug(false);
			telnetSession.setShellPrompt(shellFirstPrompt);
			telnetSession.connect(p_Username, p_Password, _ConnectTimeout);
			try {
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/ERICSSON-MBL");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				
				String[] p_Server_RNC_ERICSSON = p_ServerNameArray.split(",");

				for (int i = 0; i < p_Server_RNC_ERICSSON.length; i++) {

					String p_SeverName = p_Server_RNC_ERICSSON[i].toString();

					BufferedWriter Writer;

					String fileName = "ERICSSON_MBL_ABL." + p_SeverName + "-"
							+ strDay;

					File outFile = new File(dir,fileName + ".txt");
					Writer = new BufferedWriter(new FileWriter(outFile));

					telnetSession.setShellPrompt(">", false);
					String resp = telnetSession.send("\n ", _ReadWaitTimeout);
					if (resp.length() > 1)
						Writer.write(resp);

					telnetSession.setShellPrompt("<", false);
					resp = telnetSession.send("eaw " + p_SeverName,
							_ReadWaitTimeout);
					if (resp.length() > 1)
						Writer.write(resp);

					resp = telnetSession.send("rxmsp:moty=rxotrx;",
							_ReadWaitTimeout);
					if (resp.length() > 1)
						Writer.write(resp);

					// telnetSession.pause(1000);
					if (resp.length() > 1)
						Writer.write(resp);

					Writer.close();

					// / send lenh quit de thuc hien cho server tiep theo

					telnetSession.setShellPrompt("> ", false);
					resp = telnetSession.send("quit", _ReadWaitTimeout);
					/* System.out.println(resp); */
				}
				

			} catch (TelnetException e) {

				System.out.println("Error Telnet: " + p_IP + "---"
						+ e.getMessage());

			} finally {
				telnetSession.disconnect();
			}

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Can't close file " + e.getMessage());
			return false;
		}

		return true;
	}

	public synchronized boolean CommandMBL_CELL(String p_IP, String p_ServerName,
			List<String> listMo, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = ">";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String strDay = dfDay.format(pDate);
			BufferedWriter Writer;
			
			//Tao thu muc chua 
			File dir = new File(_DestinationPath + "/ERICSSON-MBL-CELL");
			
			if(!dir.exists())
			{
				dir.mkdir();
			}
			
			String fileName = "ERICSSON_MBL_ABL_CELL." + p_ServerName + "-"+ strDay;
			File outFile = new File(dir,fileName + ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			connect();
			String resp = "";
			try {

				//System.out.println("Connect OK");
				String shellPrompt = ">";
				resp = execute(shellPrompt, "\n", _ReadTimeout);
				if (resp.length() > 1)
					Writer.write(resp);

				shellPrompt = "<";
				resp = execute(shellPrompt, "eaw " + p_ServerName, _ReadTimeout);
				if (resp.length() > 1)
					Writer.write(resp);
				//System.out.println(resp);

				for (int i = 0; i < listMo.size(); i++) {

					resp = execute(shellPrompt, "RXMOP:MO=" + listMo.get(i)
							+ ";", _ReadWaitTimeout);
					if (resp.length() > 1)
						Writer.write(resp);
					//System.out.println(resp);
				}
			} catch (Exception e) {
				System.out.println("Can't close file " + outFile + "\r\n"
						+ e.getMessage());

			} finally {
				Writer.close();
				if (resp == "ERROR")
					outFile.deleteOnExit();

			}
			Writer.close();
			disconnect();
			//System.out.println("Disconnect: " + p_IP + "---" + p_ServerName);

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Error Telnet: " + p_IP + "---" + p_ServerName
					+ "\r\n" + e.getMessage());
			return false;
		}

		return true;
	}

	public synchronized void CommandSGSN_Active(String p_ServerName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password,
			int p_Count) {

		try {

			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);
			
			//Tao thu muc chua 
			File dir = new File(_DestinationPath + "/sgsnActive");
			
			if(!dir.exists())
			{
				dir.mkdir();
			}
			
			String strFileName = "ACTIVE." + p_ServerName + "." + "AHO" + "-"
					+ p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "."
					+ strHour + "." + p_Count;

			File outFile = new File(dir, strFileName + ".txt");

			BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile));

			connect();
			/*
			 * System.out.println( "Connect ok: " + _HostName +"; Server: "
			 * +p_ServerName ) ;
			 */
			try {

				String shellPrompt = "<";
				String resp = execute(shellPrompt, "AHO;", _ReadWaitTimeout);
				if (resp.length() > 1)
					Writer.write(resp);

			} catch (Exception e) {
				System.out.println("Can't close file " + outFile + "\r\n"
						+ e.getMessage());

			} finally {
				Writer.close();
			}

			disconnect();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// demo
	public synchronized void CommandGGSN_Active(String p_ServerName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password,
			String p_Command, String destinationPath) {

		try {

			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "]";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);
			
			//Tao thu muc chua 
			File dir = new File(_DestinationPath + "/GGSN");
			
			if(!dir.exists())
			{
				dir.mkdir();
			}
			
			String strFileName = "ACTIVE_GGSN." + p_ServerName + "_"
					+ p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "");

			File outFile = new File(dir, strFileName+ ".txt");
			BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile));

			connect();

			String strReturn = "";

			String shellPrompt = "#";
			strReturn = execute(shellPrompt, "\r\n", _ReadWaitTimeout);
			if (strReturn.length() > 1)
				Writer.write(strReturn.replaceAll("--More--", ""));

			shellPrompt = ">";
			strReturn = execute(shellPrompt, "clish", _ReadWaitTimeout);
			if (strReturn.length() > 1)
				Writer.write(strReturn.replaceAll("--More--", ""));

			shellPrompt = "--More--";
			strReturn = execute(shellPrompt, "show fm alarms", _ReadWaitTimeout);
			if (strReturn.length() > 1)
				Writer.write(strReturn.replaceAll("--More--", ""));

			//System.out.println(strReturn);

			shellPrompt = "--More--";
			telnetSession.setShellPrompt(shellPrompt);

			if (strReturn.length() > 1)
				Writer.write(strReturn.replaceAll("--More--", ""));

			boolean bReturn = true;
			while (bReturn) {
				if (strReturn.contains("--More--")) {
					strReturn = telnetSession.send("\r\n", 1000);
					//System.out.println(strReturn);
					if (strReturn.length() > 1)
						Writer.write(strReturn.replaceAll("--More--", ""));

				} else
					bReturn = false;

			}

			Writer.close();

		//	System.out.println(strReturn);

			telnetSession.disconnect();

		} catch (Exception e) {
			System.out.println("Error write file" + e.getMessage());
		}
	}

	public synchronized boolean CommandNSN_GGSN_SSH(String p_Alarm_type,
			String p_ServerName, String p_IP, String p_Username,
			String p_Password, String p_SDate, String p_STime, int p_Count) {

		try {
			//Tao thu muc chua 
			File dir = new File(_DestinationPath + "/GGSN-2");
			
			if(!dir.exists())
			{
				dir.mkdir();
			}
			
			String strFileName = p_Alarm_type + "_GGSN_SSH." + p_ServerName
					+ "_" + p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "." + p_Count;

			File outFile = new File(dir, strFileName+ ".txt");
			BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile));

			String shellPrompt = "#";
			String strReturn = "";

			// creates new SshShession instance providing hostname, username and
			// password arguments
			SshSession session = new SshSession(p_IP, p_Username, p_Password);

			// set expected shell prompt from SSH server
			session.setShellPrompt(shellPrompt);

			// establish connection
			session.connect();

			// send command to SSH shell and wait for shell prompt
			shellPrompt = ">";

			session.setShellPrompt(shellPrompt);
			strReturn = session.send("fsclish");
			if (strReturn.length() > 1)
				Writer.write(strReturn.replaceAll("--More--", ""));

			//System.out.println(strReturn);
			// send command to SSH shell and wait for shell prompt
			shellPrompt = "--More--";
			session.setShellPrompt(shellPrompt);
			if (p_Alarm_type.equals("ACTIVE"))
				strReturn = session.send("show alarm active");
			else
				strReturn = session.send("show alarm history");

			if (strReturn.length() > 1)
				Writer.write(strReturn.replaceAll("--More--", ""));

			boolean bReturn = true;
			while (bReturn) {
				if (strReturn.contains("--More--")) {
					strReturn = session.send("\r\n", 1000);
					// System.out.println(strReturn);
					if (strReturn.length() > 1)
						Writer.write(strReturn.replaceAll("--More--", ""));

				} else
					bReturn = false;

			}

			Writer.close();

			//System.out.println(strReturn);
			// send command to SSH shell and DO NOT wait for shell prompt
			session.sendNoWait("exit");

			// close connection with SSH server
			session.disconnect();

		} catch (Exception e) {
	
			return false;

		}

		return true;
	}
	
	//@Author AnhNT
	//@Date 2013-10-22
	//Lay du lieu alarm History SGSN Nokia Siemens
	public synchronized AL_TelnetLog CommandSGSN_History(String p_ServerName, String p_SDate,String p_STime, String p_IP, 
			String p_Username, String p_Password) {

		try { 
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);
			
			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");
			
			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);  
			
			//Tao ket noi telnet va gui lenh lay thong tin Alarm History 
			if(connect()){
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/sgsnHistory");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				
				//Tao file chua thong tin alarm History SGSN Nokia Siemens
				String fileNameZAHP = "HISTORY_SGSN." + p_ServerName + "." + "ZAHP" + "-"
						+ p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "."
						+ strHour; 
				File outFileZAHP = new File(dir, fileNameZAHP + ".txt");
				BufferedWriter WriterZAHP = new BufferedWriter(new FileWriter(outFileZAHP)); 
				
				try {  
					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZAHP:::" + p_SDate + ","+ p_STime + ":;", _ReadWaitTimeout); 
					if (resp.length() > 1)
						WriterZAHP.write(resp); 
					 
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileZAHP + "\r\n"
							+ e.getMessage()); 
				} finally { 
					disconnect();
					WriterZAHP.close();
				}
			}  
		} catch (Exception e) {
			// TODO: handle exception
		}
		return alTelnetLog;
	}

	//@Author AnhNT
	//@Date 2013-11-20
	//Lay du lieu Alarm History, Active Nokia Siemens 2G
	public synchronized String TelnetAlarmNSN2G(String p_ServerName, String p_SDate,String p_STime, 
			String p_IP, String p_Username, String p_Password, int p_count) { 
		try { 
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);  
			
			//Ket noi voi may chu
			//connect();
			
			if(connect()){
				//Tao thu muc chua 
				File dirHistory = new File(_DestinationPath + "/NSN-2G");
				
				if(!dirHistory.exists())
				{
					dirHistory.mkdir();
				}
				
				//Tao file chua du lieu nokia history 
				String strFileNameZAHP = "NOKIA_HISTORY." + p_ServerName + ".ZAHP"
						+ "-" + p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "."
						+ strHour;
				File outFileZAHP = new File(dirHistory,strFileNameZAHP + ".txt");
				BufferedWriter WriterZAHP = new BufferedWriter(new FileWriter(outFileZAHP)); 
				
				try { 
					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZAHP:::" + p_SDate + ","+ p_STime + ";", _ReadWaitTimeout);
					if (resp.length() > 1) WriterZAHP.write(resp);
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileZAHP + "\r\n"+ e.getMessage()); 
				} finally {
					WriterZAHP.close();
				}
				
				//Tao file chua du lieu nokia history 
				String strFileNameZEOH = "NOKIA_HISTORY." + p_ServerName + "."
						+ "ZEOH" + "-" + p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "."
						+ strHour; 
				File outFileZEOH = new File(dirHistory,strFileNameZEOH + ".txt");
				BufferedWriter WriterZEOH = new BufferedWriter(new FileWriter(outFileZEOH));

				try { 
					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZEOH:" + p_SDate + ","+ p_STime + ";", _ReadWaitTimeout);
					if (resp.length() > 1) WriterZEOH.write(resp);
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileZEOH + "\r\n" + e.getMessage()); 
				} finally {
					WriterZEOH.close();
				} 
				
				//Tao thu muc chua 
				File dirActive = new File(_DestinationPath + "/NSN-Active");
				
				if(!dirActive.exists())
				{
					dirActive.mkdir();
				}
				
				//Tao file chua du lieu nokia active
				String strFileNameZAHO = "ACTIVE." + p_ServerName + "." + "ZAHO" + "-"
						+ p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "."
						+ strHour + "." + p_count;
				File outFileZAHO = new File(dirActive,strFileNameZAHO + ".txt");
				BufferedWriter WriterZAHO = new BufferedWriter(new FileWriter(outFileZAHO));
				
				try {
					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZAHO;", _ReadWaitTimeout);
					if (resp.length() > 1)WriterZAHO.write(resp);
				} catch (Exception e) {
					System.out.println("Can't close file " + WriterZAHO + "\r\n" + e.getMessage()); 
				} finally {
					WriterZAHO.close();
				}
				//Ngat ket noi
				disconnect();
			}   
		} catch (Exception e) {
			// TODO: handle exception
		}
		return _isConect;
	}
	
	//@Author AnhNT
	//@Date 2013-11-20
	//Lay du lieu Alarm History, Active Nokia Siemens 3G
	public synchronized String TelnetAlarmNSN3G(String p_ServerName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password, int p_Count) { 
		try { 
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);

			dfDay = new SimpleDateFormat("yyyy-MM-dd");
			dfHour = new SimpleDateFormat("HH-mm-ss");

			String p_EDate = dfDay.format(pDate);
			String p_ETime = dfHour.format(pDate); 
			
			//Tao ket noi
			//connect();
			if(connect()){
				
				//Tao thu muc chua 
				File dirHistory = new File(_DestinationPath + "/NSN-3G");
				
				if(!dirHistory.exists())
				{
					dirHistory.mkdir();
				}
				//Tao file lay thong tin alarm history 3G
				String strFileNameZAHP = "NOKIA_HISTORY." + p_ServerName + ".ZAHP"
						+ "-" + p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "."
						+ strHour;
				File outFileZAHP = new File(dirHistory,strFileNameZAHP + ".txt");
				BufferedWriter WriterZAHP = new BufferedWriter(new FileWriter(outFileZAHP));
				try {

					String shellPrompt = "COMMAND EXECUTED";
					String resp = execute(shellPrompt, "ZAHP:::" + p_SDate + ","
							+ p_STime + "," + p_EDate + "," + p_ETime + ";", _ReadWaitTimeout);
					if (resp.length() > 1) WriterZAHP.write(resp);
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileZAHP + "\r\n" + e.getMessage()); 
				} finally {
					WriterZAHP.close();
				}
				
				//Tao thu muc chua 
				File dirActive = new File(_DestinationPath + "/NSN-Active");
				
				if(!dirActive.exists())
				{
					dirActive.mkdir();
				}
				
				//Tao file chua thong tin alarm active 3G
				String strFileNameZAAP = "ACTIVE." + p_ServerName + "." + "ZAAP" + "-"
						+ p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "."
						+ strHour + "." + p_Count; 
				File outFileZAAP = new File(dirActive,strFileNameZAAP + ".txt"); 
				BufferedWriter WriterZAAP = new BufferedWriter(new FileWriter(outFileZAAP)); 
				
				try { 
					String shellPrompt = "<";
					String resp = execute(shellPrompt, "ZAAP;", _ReadWaitTimeout);
					if (resp.length() > 1) WriterZAAP.write(resp); 
				} catch (Exception e) {
					System.out.println("Can't close file " + outFileZAAP + "\r\n" + e.getMessage()); 
				} finally {
					WriterZAAP.close();
				}
				
				//Ngat ket noi 
				disconnect(); 
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return _isConect;
	}
	
	//@Author: AnhNT
	//Update: 27/11/2013
	//Telnet lay thong tin TRX 2G, ABL-MBL
	public synchronized AL_TelnetLog TelnetTRXEricsson2G(String p_IP, String p_Ne, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = ">";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);
			
			Date pDate = new Date(); 
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfTime = new SimpleDateFormat("HHmmss");
			String strDay = dfDay.format(pDate);
			String strTime = dfTime.format(pDate);
			
			if(this.connect()){
				boolean error  = false; 
				String fileName = null;
				File outFile = null; 
				BufferedWriter Writer = null;
				
				//Lay loi ABL, MBL
				try {
					//Tao thu muc chua 
					File dirAblMbl = new File(_DestinationPath + "/ericssonAblMbl");
					
					if(!dirAblMbl.exists())
					{
						dirAblMbl.mkdir();
					}
					
					fileName = "ERICSSON_MBL_ABL." + p_Ne + "-" + strDay+ "-" + strTime; 
					outFile = new File(dirAblMbl,fileName + ".txt");
					Writer = new BufferedWriter(new FileWriter(outFile));
					
					//Ky tu hoac chuoi cuoi cung cua du lieu tra ve
					String shellPrompt = "";  
					//Gui command 1: Gui BSC can lay du lieu TRX
					shellPrompt = "<";
					String resp = execute(shellPrompt, "eaw " + p_Ne, _ReadWaitTimeout); 
					if(resp.equalsIgnoreCase("ERROR")){
						error = true;
					}
					/*Ghi du lieu tra ve vao file*/
					if (resp.length() > 1) Writer.write(resp); 
					
					//Gui commad 2: Lay thong tin A thuoc BSC da gui o command 1 neu command 1 khong loi
					if(error == false){ 
						shellPrompt = "<";
						resp = execute(shellPrompt, "rxmsp:moty=rxotrx;", _ReadWaitTimeout); 
						/*Ghi du lieu tra ve vao file*/
						if (resp.length() > 1) Writer.write(resp);
					} 
				} catch (Exception e) { 
					// TODO: handle exception 
					System.out.println("Can't close file " + e.getMessage()); 
				} finally {
					//Dong file ghi
					Writer.close();  
				}
				
				//Lay danh sach TRX
				try { 
					//Tao thu muc chua 
					File dirTrx = new File(_DestinationPath + "/ericssonTrx");
					
					if(!dirTrx.exists())
					{
						dirTrx.mkdir();
					}
					
					fileName = "ERICSSON_TRX." + p_Ne + "-" + strDay+ "-" + strTime;
					outFile = new File(dirTrx, fileName + ".txt");
					Writer = new BufferedWriter(new FileWriter(outFile));
					
					//Gui commad : Lay thong tin TRX thuoc BSC da gui o command 1 neu command 1 khong loi
					if(error == false	){ 
						Writer.write("eaw " + p_Ne);
						Writer.newLine();
						String shellPrompt = "<";
						String resp = execute(shellPrompt, "rxmop:moty=rxotrx;", _ReadWaitTimeout); 
						/*Ghi du lieu tra ve vao file*/
						if (resp.length() > 1) Writer.write(resp);
						
						//Gui commad 2: quit ---> Thoat khoi BSC
						shellPrompt = ">"; 
						resp = execute(shellPrompt, "quit", _ReadWaitTimeout);
					}  
				}catch (Exception e) {
					// TODO: handle exception 
					System.out.println("Can't close file " + e.getMessage()); 
				} finally {
					//Dong file ghi
					Writer.close();  
				}
				
				this.disconnect();
			} 
		} catch (Exception e) {  
		}
		return alTelnetLog;
	} 

	//@Author: AnhNT
	//Update: 03/12/2013
	//Telnet lay thong tin Alarm Active Ericsson 2G va Active STP
	public synchronized AL_TelnetLog TelnetAlarmActiveEricssonSTP2G(String p_IP, String p_Ne, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = ">";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);
			
			if(connect()){
				boolean error  = false;
				//Tao thu muc chua 
				File dirActive = new File(_DestinationPath + "/ericssonActive");
				
				if(!dirActive.exists())
				{
					dirActive.mkdir();
				}
				//Tao file chua thong tin Alarm Active 
				String fileName = "ERICSSON_ACTIVE." + p_Ne + "-"+strDay+"."+strHour; 
				File outFile = new File(dirActive,fileName + ".txt");
				BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile));
				try { 
					//Ky tu hoac chuoi cuoi cung cua du lieu tra ve
					String shellPrompt = "";  
					
					//Gui command 1:
					 shellPrompt = "<";
					 String resp = execute(shellPrompt, "eaw "+p_Ne, _ReadTimeout);
					if(resp.equalsIgnoreCase("ERROR")){
						error = true;
					}
					/*Ghi du lieu tra ve vao file*/
					if (resp.length() > 1) Writer.write(resp); 
					
					//Gui commad 2:
					if(error == false	){ 
						shellPrompt = "<";
						resp = execute(shellPrompt, "allip;", _ReadWaitTimeout);
						/*Ghi du lieu tra ve vao file*/
						if (resp.length() > 1) Writer.write(resp);
					}   
				} finally {
					//Dong file ghi
					Writer.close();
					this.disconnect();
				}
			} 
		} catch (Exception e) {
			// TODO: handle exception 
			System.out.println("Can't close file " + e.getMessage());  
		}
		return alTelnetLog;
	}
	//@Author: AnhNT
	//Update: 03/12/2013
	//Telnet lay thong tin Alarm Active Ericsson 3G va Active MGW
	public synchronized AL_TelnetLog TelnetAlarmActiveEricssonMGW3G(String p_IP, String p_Ne, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = ">";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);
			
			if(connect()){
				boolean error  = false;
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/ericssonActive");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				//Tao file chua thong tin Alarm Active 
				String fileName = "ERICSSON_ACTIVE." + p_Ne + "-"+strDay+"."+strHour; 
				File outFile = new File(dir,fileName + ".txt");
				BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile)); 
				try { 
					//Ky tu hoac chuoi cuoi cung cua du lieu tra ve
					String shellPrompt = "";  
					//Gui command 1: 
					shellPrompt = ">";
					String resp = execute(shellPrompt, "amos " + p_Ne, _ReadTimeout);
					if(resp.equalsIgnoreCase("ERROR")){
						error = true;
					}
					/*Ghi du lieu tra ve vao file*/
					if (resp.length() > 1) Writer.write(resp);
					
					//Gui commad 2:
					if(error == false	){ 
						shellPrompt = ">";
						resp = execute(shellPrompt, "alt", _ReadTimeout);
						/*Ghi du lieu tra ve vao file*/
						if (resp.length() > 1) Writer.write(resp);
					}    
				} catch(Exception e){
					
				}finally {
					//Dong file ghi
					Writer.close();
					this.disconnect();
				}
			} 
		} catch (Exception e) {
			// TODO: handle exception 
			System.out.println("Can't close file " + e.getMessage());  
		}
		return alTelnetLog;
	}
	//@Author: AnhNT
	//Update: 05/12/2013
	//Telnet lay thong tin Alarm Active GGSN
	public synchronized AL_TelnetLog TelnetAlarmActiveGGSN(String p_Ne, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password) {
		try { 
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = "]";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt); 
 
			if(connect()){
				boolean error = false;
				boolean bReturn = true;
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/ggsnActive");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				String strFileName = "ACTIVE_GGSN." + p_Ne + "-" + p_SDate.replaceAll("-", "") + "." + p_STime.replaceAll("-", "");
				File outFile = new File(_DestinationPath + "/ggsnActive", strFileName+ ".txt");
				BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile));
				try {  
					String strReturn = "";
					String shellPrompt = "";
					
					//Gui cau lenh 1: Gui dau enter de go lenh tiep theo 
					shellPrompt = "#";
					strReturn = execute(shellPrompt, "\r\n", _ReadWaitTimeout);
					if (strReturn.length() > 1)
						Writer.write(strReturn.replaceAll("--More--", "")); 
					
					//Gui cau lenh 2: 
					shellPrompt = ">";
					strReturn = execute(shellPrompt, "clish", _ReadWaitTimeout);
					if (strReturn.length() > 1)
						Writer.write(strReturn.replaceAll("--More--", ""));
					
					//Kiem tra neu du lieu tra ve ERROR thi khong gui lenh tiep theo
					if(strReturn.equalsIgnoreCase("ERROR")){
						error = true;
					}
					
					if(error == false){
						//Gui cau lenh 3: 
						shellPrompt = "--More--";
						strReturn = execute(shellPrompt, "show fm alarms", _ReadWaitTimeout);
						if (strReturn.length() > 1)
							Writer.write(strReturn.replaceAll("--More--", ""));
						
						//Gui cau lenh 4: Neu gia tri gui ve co chua "--More--" se gui dau enter de tiep tuc lay thong tin
						while (bReturn) {
							if (strReturn.contains("--More--")) {
								strReturn = execute(shellPrompt, "\r\n", 1000); 
								if (strReturn.length() > 1)
									Writer.write(strReturn.replaceAll("--More--", ""));
							} else
								bReturn = false;
						}
					}   
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Error write file" + e.getMessage());
				}finally{
					Writer.close(); 
					disconnect();
				}
			} 
		} catch (Exception e) {}
		return alTelnetLog;
	}
	//@Author: AnhNT
	//Update: 05/12/2013
	//Ssh lay thong tin Alarm Active GGSN
	public synchronized AL_TelnetLog SshAlarmActiveGGSN(String p_Ne, String p_IP, String p_Username,
			String p_Password, String p_SDate, String p_STime) {

		try { 
			set_Username(p_Username);
			set_Password(p_Password); 
			set_HostName(p_IP);

			String shellFirstPrompt = "#";  
			set_ShellFirstPrompt(shellFirstPrompt); 
			
			String strReturn = "";
			String shellPrompt = "";
			
			if(sshConnect()){
				boolean bReturn = true;
				boolean error = false;
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/ggsnActive");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				String strFileName = "ACTIVE_GGSN_SSH." + p_Ne + "-" + p_SDate.replaceAll("-", "") + "." + p_STime.replaceAll("-", "");
				File outFile = new File(dir, strFileName+ ".txt");
				BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile)); 
				
				try {
					// send command to SSH shell and wait for shell prompt
					shellPrompt = ">"; 
					strReturn = execute(shellPrompt, "fsclish", _ReadWaitTimeout); 
					if (strReturn.length() > 1)
						Writer.write(strReturn.replaceAll("--More--", ""));
					
					if(strReturn.equalsIgnoreCase("ERROR")){
						error = true;
					}

					if(error == false){
						// send command to SSH shell and wait for shell prompt
						shellPrompt = "--More--";
						strReturn = execute(shellPrompt, "show alarm active", _ReadWaitTimeout);  
						if (strReturn.length() > 1)
							Writer.write(strReturn.replaceAll("--More--", ""));  
						
						while (bReturn) {
							if (strReturn.contains("--More--")) {
								strReturn = execute(shellPrompt, "\r\n", 1000);  
								if (strReturn.length() > 1)
									Writer.write(strReturn.replaceAll("--More--", ""));
							} else
								bReturn = false;
						}
					}

					Writer.close();
				} catch (Exception e) { 
					// TODO: handle exception
					System.out.println("Error write file" + e.getMessage());
				}finally{ 
					// send command to SSH shell and DO NOT wait for shell prompt
					sshSession.sendNoWait("exit");  
					// close connection with SSH server
					sshSession.disconnect();
				}
			} 
		} catch (Exception e) {}
		return alTelnetLog;
	} 
	//@Author: AnhNT
	//Update: 05/12/2013
	//Ssh lay thong tin Alarm History GGSN
	public synchronized AL_TelnetLog SshAlarmHistoryGGSN(String p_Ne, String p_IP, String p_Username,
			String p_Password, String p_SDate, String p_STime) {

		try { 
			set_Username(p_Username);
			set_Password(p_Password); 
			set_HostName(p_IP);

			String shellFirstPrompt = "#";  
			set_ShellFirstPrompt(shellFirstPrompt); 
			
			String strReturn = "";
			String shellPrompt = "";
			
			if(sshConnect()){
				boolean bReturn = true;
				boolean error = false;
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/ggsnHistory");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}	
				String strFileName = "HISTORY_GGSN_SSH." + p_Ne + "-" + p_SDate.replaceAll("-", "") + "." + p_STime.replaceAll("-", "");
				File outFile = new File(dir, strFileName+ ".txt");
				BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile)); 
				
				try {
					// send command to SSH shell and wait for shell prompt
					shellPrompt = ">"; 
					strReturn = execute(shellPrompt, "fsclish", _ReadWaitTimeout); 
					if (strReturn.length() > 1)
						Writer.write(strReturn.replaceAll("--More--", ""));
					
					if(strReturn.equalsIgnoreCase("ERROR")){
						error = true;
					}

					if(error == false){
						// send command to SSH shell and wait for shell prompt
						shellPrompt = "--More--";
						strReturn = execute(shellPrompt, "show alarm history", _ReadWaitTimeout);  
						if (strReturn.length() > 1)
							Writer.write(strReturn.replaceAll("--More--", ""));  
						
						while (bReturn) {
							if (strReturn.contains("--More--")) {
								strReturn = execute(shellPrompt, "\r\n", 1000);  
								if (strReturn.length() > 1)
									Writer.write(strReturn.replaceAll("--More--", ""));
							} else
								bReturn = false;
						}
					} 
				} catch (Exception e) { 
					// TODO: handle exception
					System.out.println("Error write file" + e.getMessage());
				}finally{ 
					Writer.close();
					// send command to SSH shell and DO NOT wait for shell prompt
					sshSession.sendNoWait("exit");  
					// close connection with SSH server
					sshSession.disconnect();
				}
			} 
		} catch (Exception e) {}
		return alTelnetLog;
	} 

	//@Author AnhNT
	//@Date 2013-12-11
	
	//Telnet lay du lieu TRX Nokia Siemens 2G
	public AL_TelnetLog TelnetNokiaTRX2G(String neType, String p_ServerName, String p_SDate, String p_STime, String p_IP, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP); 

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);    
			
			//Tao thu muc chua 
			File dir = new File(_DestinationPath + "/portCard_NSN");
			
			if(!dir.exists())
			{
				dir.mkdir();
			}
			
			//Tao file chua thong tin port card Nokia Bsc
			String fileNamePortNSNBSC = "R_N_"+neType+"_PORT." + p_ServerName + "."+ p_SDate.replaceAll("-", "")+"."+ p_STime.replaceAll("-", "");
			File outFilePortNSNBSC = new File(dir, fileNamePortNSNBSC + ".txt");
			BufferedWriter WriterPortNSNBSC = new BufferedWriter(new FileWriter(outFilePortNSNBSC)); 
			
			try {
				//Tao ket noi telnet 
				if(connect()){ 
					//Gui lenh lay thong tin port card NSN BSC 
					String shellPrompt_Port = ">"; 
					String respPortNSNBSC = execute(shellPrompt_Port, "ZEEI:;", _ReadWaitTimeout);
					if (respPortNSNBSC.length() > 1)
						WriterPortNSNBSC.write(respPortNSNBSC);
				}
			} catch (Exception e) { 
				// TODO: handle exception
			}finally{ 
				WriterPortNSNBSC.close();
				disconnect();
			} 
		} catch (Exception e) {
			// TODO: handle exception 
			System.out.println("Not path : "+e);
		}
		return alTelnetLog;
	} 
	
	
	//@Author AnhNT
	//@Date 2013-12-13
	//Telnet lay du lieu Cell Ericsson 3G
	public synchronized AL_TelnetLog TelnetTRXEricsson3G(String p_IP, String p_Ne, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = ">";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);
			
			Date pDate = new Date(); 
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfTime = new SimpleDateFormat("HHmmss");
			String strDay = dfDay.format(pDate);
			String strTime = dfTime.format(pDate);
			
			if(this.connect()){
				boolean error = false;
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/ericssonTrx");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				//Tao file chua thong tin Cell Ericsson RNC
				String fileNameCell3G = "ERICSSON_TRX." + p_Ne + "-" + strDay+ "-"+strTime;
				File outFileCell3G = new File(dir, fileNameCell3G + ".txt");
				BufferedWriter WriterCell3G = new BufferedWriter(new FileWriter(outFileCell3G)); 
				try {
					String respCell3G = "";
					String shellPrompt = "";
					
					//Lenh 1: amos + ten RNC ---> Truy cap vao RNC
					shellPrompt = ">"; 
					respCell3G = execute(shellPrompt, "amos "+ p_Ne, _ReadWaitTimeout);
					if (respCell3G.length() > 1)
						WriterCell3G.write(respCell3G); 
					if(respCell3G.equals("ERROR")){
						error = true;
					}
					
					if(error == false){
						//Lenh 2: lt all ---> Try cap vao RNC
						shellPrompt = ">"; 
						respCell3G = execute(shellPrompt, "lt all", _ReadWaitTimeout);
						if (respCell3G.length() > 1)
							WriterCell3G.write(respCell3G); 
						
						//Lenh 3: st utrancell ---> Lay du lieu Cell RNC
						shellPrompt = ">"; 
						respCell3G = execute(shellPrompt, "st utrancell", _ReadWaitTimeout);
						if (respCell3G.length() > 1)
							WriterCell3G.write(respCell3G);
						
						//Lenh 4: quit ---> Thoat khoi RNC
						shellPrompt = ">"; 
						respCell3G = execute(shellPrompt, "quit", _ReadWaitTimeout);
					} 
				} finally {
					//Dong file ghi
					WriterCell3G.close(); 
					this.disconnect();
				}
			} 
		} catch (Exception e) {
			// TODO: handle exception 
			System.out.println("Can't close file " + e.getMessage());  
		}
		return alTelnetLog;
	}
	
	//@Author AnhNT
	//@Date 2013-12-16
	//Telnet lay du lieu TRX Nokia Siemens 3G
	public AL_TelnetLog TelnetNokiaTRX3G(String neType, String p_ServerName, String p_SDate, String p_STime, String p_IP, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP); 

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);    
			
			//Tao thu muc chua 
			File dir = new File(_DestinationPath + "/portCard_NSN");
			
			if(!dir.exists())
			{
				dir.mkdir();
			}
			
			//Tao file chua thong tin Cell Nokia RNC
			String fileNamePortNSN = "NOKIA_TRX." + p_ServerName + "-"+ p_SDate.replaceAll("-", "")+"-"+ p_STime.replaceAll("-", ""); 
			File outFilePortNSN = new File(dir, fileNamePortNSN + ".txt");
			BufferedWriter WriterPortNSN = new BufferedWriter(new FileWriter(outFilePortNSN)); 
			
			try {
				//Tao ket noi telnet 
				if(connect()){ 
					boolean error = false;
					//Gui lenh lay thong tin Cell Nokia RNC
					String shellPrompt = ""; 
					String respPortNSN = "";

					//Command 1: <ZDDS; : 
					shellPrompt = ">";
					respPortNSN = execute(shellPrompt, "ZDDS;", _ReadWaitTimeout); 
					if (respPortNSN.length() > 1)
						WriterPortNSN.write(respPortNSN);
					if(respPortNSN.equals("ERROR")){
						error = true;
					}
					
					if(error == false){
						//Command 2: >1: Neu da set gia tri 1 de vao 0000-RUO>
						shellPrompt = ">";
						respPortNSN = execute(shellPrompt, "1", _ReadWaitTimeout); 
						if (respPortNSN.length() > 1)
							WriterPortNSN.write(respPortNSN); 
						
						//Command 3: >SA;: Lenh lay danh sach Cell 3G Nokia
						shellPrompt = ">";
						respPortNSN = execute(shellPrompt, "SA;", _ReadWaitTimeout); 
						if (respPortNSN.length() > 1)
							WriterPortNSN.write(respPortNSN);
						
						//Command 4: >ZE;: Lenh lay danh sach Cell 3G Nokia
						shellPrompt = ">";
						respPortNSN = execute(shellPrompt, "ZE", _ReadWaitTimeout); 
						if (respPortNSN.length() > 1)
							WriterPortNSN.write(respPortNSN); 
					}
				}
			} catch (Exception e) { 
				// TODO: handle exception
			}finally{ 
				WriterPortNSN.close();
				disconnect();
			} 
		} catch (Exception e) {
			// TODO: handle exception 
		}
		return alTelnetLog;
	} 
	
	//@Author: AnhNT
	//Update: 19/12/2013
	//Telnet lay thong tin tai truc tuyen 2G
	public synchronized AL_TelnetLog TelnetEricssonLoad2G(String p_IP, String p_Ne, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = ">";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);
			
			Date pDate = new Date(); 
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfTime = new SimpleDateFormat("HHmmss");
			String strDay = dfDay.format(pDate);
			String strTime = dfTime.format(pDate);
			
			if(this.connect()){
				boolean error  = false; 
				String fileName = null;
				File outFile = null; 
				BufferedWriter Writer = null;
				
				//Lay thong tin tai truc tuyen
				try {
					//Tao thu muc chua 
					File dir = new File(_DestinationPath + "/ericssonLoad");
					
					if(!dir.exists())
					{
						dir.mkdir();
					}
					fileName = "ERICSSON_LOAD." + p_Ne + "-" + strDay+ "-" + strTime; 
					outFile = new File(dir,fileName + ".txt");
					Writer = new BufferedWriter(new FileWriter(outFile));
					
					//Ky tu hoac chuoi cuoi cung cua du lieu tra ve
					String shellPrompt = "";  
					//Gui command 1: Gui ten BSC can lay du lieu tai
					shellPrompt = "<";
					String resp = execute(shellPrompt, "eaw " + p_Ne, _ReadWaitTimeout); 
					if(resp.equalsIgnoreCase("ERROR")){
						error = true;
					}
					/*Ghi du lieu tra ve vao file*/
					if (resp.length() > 1) Writer.write(resp); 
					
					//Gui commad 2: Lay thong tin A thuoc BSC da gui o command 1 neu command 1 khong loi
					if(error == false){ 
						shellPrompt = "<";
						resp = execute(shellPrompt, "plldp;", _ReadWaitTimeout); 
						/*Ghi du lieu tra ve vao file*/
						if (resp.length() > 1) Writer.write(resp);
					} 
				} catch (Exception e) { 
					// TODO: handle exception 
					System.out.println("Can't close file " + e.getMessage()); 
				} finally {
					//Dong file ghi
					Writer.close();  
				} 
				this.disconnect();
			} 
		} catch (Exception e) {  
		}
		return alTelnetLog;
	} 
	
	//@Author: AnhNT
	//Update: 19/12/2013
	//Telnet lay thong tin tai truc tuyen 3G
	public synchronized AL_TelnetLog TelnetEricssonLoad3G(String p_IP, String p_Ne, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP);

			String shellFirstPrompt = ">";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);
			
			Date pDate = new Date(); 
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfTime = new SimpleDateFormat("HHmmss");
			String strDay = dfDay.format(pDate);
			String strTime = dfTime.format(pDate);
			
			if(this.connect()){
				boolean error = false;
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/ericssonLoad");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				//Tao file chua thong tin tai Ericsson 3G
				String fileName = "ERICSSON_LOAD." + p_Ne + "-" + strDay+ "-"+strTime;
				File outFile = new File(dir, fileName + ".txt");
				BufferedWriter _writer = new BufferedWriter(new FileWriter(outFile)); 
				try {
					String resp = "";
					String shellPrompt = "";
					
					//Lenh 1: amos + ten RNC ---> Truy cap vao RNC
					shellPrompt = ">"; 
					resp = execute(shellPrompt, "amos "+ p_Ne, _ReadWaitTimeout); 
					_writer.write("> amos "+ p_Ne); 
					_writer.newLine();
					if(resp.equals("ERROR")){
						error = true;
					}
					
					if(error == false){
						//Lenh 2: lt all ---> Try cap vao RNC
						shellPrompt = ">"; 
						resp = execute(shellPrompt, "lt all", _ReadWaitTimeout);
						_writer.write("> lt all"); 
						_writer.newLine();
						
						//Lenh 3: pmr -m 0,25
						shellPrompt = "Password:"; 
						resp = execute(shellPrompt, "pmr -m 0.25", _ReadWaitTimeout);
						_writer.write("> pmr -m 0.25"); 
						_writer.newLine();
						
						//Lenh 4: 
						shellPrompt = "Your Choice:"; 
						resp = execute(shellPrompt, "rnc", _ReadWaitTimeout);
						_writer.write("Password:"+ "rnc"); 
						_writer.newLine();
						
						//Lenh 5: 
						shellPrompt = "Your Choice:"; 
						resp = execute(shellPrompt, "7", _ReadWaitTimeout);
						if (resp.length() > 1)
							_writer.write("Your Choice:" +resp);
						
						//Lenh 6: 
						shellPrompt = "Your Choice:"; 
						resp = execute(shellPrompt, "9", _ReadWaitTimeout);
						if (resp.length() > 1)
							_writer.write("Your Choice:" +resp);
						
						//Lenh 6: 
						shellPrompt = ">"; 
						resp = execute(shellPrompt, "exit", _ReadWaitTimeout);
						_writer.write("exit"); 
						_writer.newLine();
						
						//Lenh 7: quit ---> Thoat khoi RNC
						shellPrompt = ">"; 
						resp = execute(shellPrompt, "quit", _ReadWaitTimeout);
						_writer.write(shellPrompt+ "quit"); 
						_writer.newLine();
					} 
				} finally {
					//Dong file ghi
					_writer.close(); 
					this.disconnect();
				}
			} 
		} catch (Exception e) {
			// TODO: handle exception 
			System.out.println("Can't close file " + e.getMessage());  
		}
		return alTelnetLog;
	}

	//@Author AnhNT
	//@Date 19/12/2013
	//Telnet lay du lieu tai Nokia 3G
	public AL_TelnetLog TelnetNokiaLoad3G(String p_ServerName, String p_IP, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP); 

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);  
			
			Date pDate = new Date(); 
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfTime = new SimpleDateFormat("HHmmss");
			String strDay = dfDay.format(pDate);
			String strTime = dfTime.format(pDate);  
			
			String fileName = null;
			File outFile = null;
			BufferedWriter _writer = null;
			
			//Tao ket noi telnet 
			if(connect()){ 
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/nokiaLoad");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				//Tao file chua thong tin tai Nokia 3G
				fileName = "NOKIA_LOAD." + p_ServerName + "-"+ strDay+"-"+ strTime; 
				outFile = new File(dir, fileName + ".txt");
				_writer = new BufferedWriter(new FileWriter(outFile));
				
				try { 
					//Gui lenh lay thong tin tai nokia 3G
					String shellPrompt = "<"; 
					String resp = execute(shellPrompt, "ZDOI;", _ReadWaitTimeout);
					if (resp.length() > 1)
						_writer.write(resp);
				} catch (Exception e) {
					// TODO: handle exception
				}finally{ 
					_writer.close();
				}
				
				//Tao file chua thong tin card Nokia 3G
				fileName = "NOKIA_CARD_LOAD." + p_ServerName + "-"+ strDay+"-"+ strTime; 
				outFile = new File(dir, fileName + ".txt");
				_writer = new BufferedWriter(new FileWriter(outFile));
				
				try { 
					//Gui lenh lay thong tin hoat dong card Nokia 3G
					String shellPrompt = "<"; 
					String resp = execute(shellPrompt, "ZUSI;", _ReadWaitTimeout);
					if (resp.length() > 1)
						_writer.write(resp);
				} catch (Exception e) {
					// TODO: handle exception
				}finally{ 
					_writer.close();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception 
		}
		return alTelnetLog;
	} 
	
	//@Author AnhNT
	//@Date 19/12/2013
	//Telnet lay du lieu tai Nokia 2G
	public AL_TelnetLog TelnetNokiaLoad2G(String p_ServerName, String p_IP, String p_Username, String p_Password) {
		try {
			set_Username(p_Username);
			set_Password(p_Password);
			set_Port(23);
			set_HostName(p_IP); 

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";

			set_ShellFirstPrompt(shellFirstPrompt);
			set_PasswordPrompt(passwordPrompt);
			set_LoginPrompt(loginPrompt);  
			
			Date pDate = new Date(); 
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfTime = new SimpleDateFormat("HHmmss");
			String strDay = dfDay.format(pDate);
			String strTime = dfTime.format(pDate);  
			
			String fileName = null;
			File outFile = null;
			BufferedWriter _writer = null;
			
			//Tao ket noi telnet 
			if(connect()){ 
				//Tao thu muc chua 
				File dir = new File(_DestinationPath + "/nokiaLoad");
				
				if(!dir.exists())
				{
					dir.mkdir();
				}
				//Tao file chua thong tin tai Nokia 2G
				fileName = "NOKIA_LOAD." + p_ServerName + "-"+ strDay+"-"+ strTime; 
				outFile = new File(dir, fileName + ".txt");
				_writer = new BufferedWriter(new FileWriter(outFile));
				
				try { 
					//Gui lenh lay thong tin tai nokia 2G
					String shellPrompt = "<"; 
					String resp = execute(shellPrompt, "ZDOI;", _ReadWaitTimeout);
					if (resp.length() > 1)
						_writer.write(resp);
				} catch (Exception e) {
					// TODO: handle exception
				}finally{ 
					_writer.close();
				}
				
				//Tao file chua thong tin card Nokia 2G
				fileName = "NOKIA_CARD_LOAD." + p_ServerName + "-"+ strDay+"-"+ strTime; 
				outFile = new File(dir, fileName + ".txt");
				_writer = new BufferedWriter(new FileWriter(outFile));
				
				try { 
					//Gui lenh lay thong tin hoat dong card Nokia 3G
					String shellPrompt = "<"; 
					String resp = execute(shellPrompt, "ZUSI:COMP;", _ReadWaitTimeout);
					if (resp.length() > 1)
						_writer.write(resp);
				} catch (Exception e) { 
				}finally{ 
					_writer.close();
				}
			}
		} catch (Exception e) { 
		}
		return alTelnetLog;
	} 
		//@Author ThangPX
		//@Date 26/05/2014
		//Telnet lay du lieu operation logs Nokia
		public AL_TelnetLog TelnetNokiaOperationLogs(String p_ServerName, String p_IP, String p_Username, String p_Password) {
			try {
				//Set username, password, server information
				set_Username(p_Username);
				set_Password(p_Password);
				set_Port(23);
				set_HostName(p_IP); 
				//Start 
				String shellFirstPrompt = "<";
				String loginPrompt = "ENTER USERNAME";
				String passwordPrompt = "ENTER PASSWORD";

				set_ShellFirstPrompt(shellFirstPrompt);
				set_PasswordPrompt(passwordPrompt);
				set_LoginPrompt(loginPrompt);  
				
				Date pDate = new Date(); 
				DateFormat dfDayForFileName = new SimpleDateFormat("yyyyMMdd");
				DateFormat dfDayInFile = new SimpleDateFormat("yyyy-MM-dd");
			 
				DateFormat dfTime = new SimpleDateFormat("HHmmss");
				String strDay = dfDayForFileName.format(pDate);
				String strTime = dfTime.format(pDate);  
				
				String fileName = null;
				File outFile = null;
				BufferedWriter _writer = null;
				
				//Tao ket noi telnet 
				if(connect()){ 
					//Tao thu muc chua 
					File dir = new File(_DestinationPath + "/operationslogNSN");
					
					if(!dir.exists())
					{
						dir.mkdir();
					}
					//Tao file chua thong tin tai Nokia 2G
					fileName = "NOKIA_OPERATION_LOGS." + p_ServerName + "-"+ strDay+"-"+ strTime; 
					outFile = new File(dir, fileName + ".txt");
					_writer = new BufferedWriter(new FileWriter(outFile));
					
					try { 
						//Gui lenh lay thong tin tai nokia 2G
						String shellPrompt = "<"; 
						String resp = execute(shellPrompt, "ZIGO:"+dfDayInFile.format(minusDay(pDate))+":USERID=ALL;", _ReadWaitTimeout);
						
						
						if (resp.length() > 1)
							_writer.write(resp);
					} catch (Exception e) { 
					}finally{ 
						_writer.close();
					}
					
					 
				}
			} catch (Exception e) {
			}
			return alTelnetLog;
		} 
		
		//@Author AnhNT
		//@Date 02/12/2015
		//Telnet lay du lieu tai thiet bi truyen dan 
		public void TelnetLoadTransmission(String p_ServerName, String p_IP, String p_Username, String p_Password) {
			try {
				//Set username, password, server information
				set_Username(p_Username);
				set_Password(p_Password);
				set_Port(23);
				set_HostName(p_IP); 
				//Start 
				String shellFirstPrompt = "<";
				String loginPrompt = "user name:";
				String passwordPrompt = "password:";

				set_ShellFirstPrompt(shellFirstPrompt);
				set_PasswordPrompt(passwordPrompt);
				set_LoginPrompt(loginPrompt);  
				
				Date pDate = new Date(); 
				DateFormat dfDayForFileName = new SimpleDateFormat("yyyyMMdd"); 
			 
				DateFormat dfTime = new SimpleDateFormat("HHmmss");
				String strDay = dfDayForFileName.format(pDate);
				String strTime = dfTime.format(pDate);  
				
				String fileName = null;
				File outFile = null;
				BufferedWriter _writer = null;
				
				boolean b = connect();
				//Tao ket noi telnet 
				if(b){ 
					//Tao thu muc chua 
					File dir = new File(_DestinationPath + "/loadTransmission");
					
					if(!dir.exists())
					{
						dir.mkdir();
					}
					
					//Tao file chua thong tin tai thiet bi truyen dan
					fileName = "LOAD_TRANS." + p_ServerName + ".slot1-"+ strDay+"-"+ strTime; 
					outFile = new File(dir, fileName + ".txt");
					_writer = new BufferedWriter(new FileWriter(outFile));
					
					try { 
						//Gui lenh lay thong tin tai thiet bi truyen dan
						String shellPrompt = ">"; 
						String resp = execute(shellPrompt, "enable", _ReadWaitTimeout);
						if (resp.length() > 1) _writer.write(resp);
						
						shellPrompt = "#"; 
						resp = execute(shellPrompt, "show cpu load days 1 slot 1", _ReadWaitTimeout);
						if (resp.length() > 1) _writer.write(resp);
						
						boolean bReturn = true;
						while (bReturn) {
							if (resp.contains("--More--")) {
								shellPrompt = "--More--";
								resp = execute(shellPrompt, "\r\n", 1000);
								if (resp.length() > 1)
									_writer.write(resp.replaceAll("--More--", ""));

							} else
								bReturn = false;
						}
					} catch (Exception e) {
						System.out.println("Telnet Transmission 1 --"+e.getMessage());
					}finally{ 
						_writer.close();
					}
					
					//Tao file chua thong tin tai thiet bi truyen dan
					fileName = "LOAD_TRANS." + p_ServerName + ".slot14-"+ strDay+"-"+ strTime; 
					outFile = new File(dir, fileName + ".txt");
					_writer = new BufferedWriter(new FileWriter(outFile));
					
					try {
						String shellPrompt = "#"; 
						String resp = execute(shellPrompt, "show cpu load days 1 slot 14", _ReadWaitTimeout);
						if (resp.length() > 1) _writer.write(resp);
						
						boolean bReturn = true;
						while (bReturn) {
							if (resp.contains("--More--")) {
								shellPrompt = "--More--";
								resp = execute(shellPrompt, "\r\n", 1000);   
								if (resp.length() > 1)
									_writer.write(resp.replaceAll("--More--", ""));

							} else
								bReturn = false;
						}
					} catch (Exception e) {
						System.out.println("Telnet Transmission 2 --"+e.getMessage());
					}finally{ 
						_writer.close();
					}
					
					//Tao file chua thong tin tai thiet bi truyen dan
					fileName = "LOAD_TRANS." + p_ServerName + ".slot-"+ strDay+"-"+ strTime; 
					outFile = new File(dir, fileName + ".txt");
					_writer = new BufferedWriter(new FileWriter(outFile));
					
					try {
						String shellPrompt = "#"; 
						String resp = execute(shellPrompt, "show cpu load days 1", _ReadWaitTimeout);
						if (resp.length() > 1) _writer.write(resp);
						
						boolean bReturn = true;
						while (bReturn) {
							if (resp.contains("--More--")) {
								shellPrompt = "--More--";
								resp = execute(shellPrompt, "\r\n", 1000);   
								if (resp.length() > 1)
									_writer.write(resp.replaceAll("--More--", ""));

							} else
								bReturn = false;
						}
					} catch (Exception e) {
						System.out.println("Telnet Transmission 3 --"+e.getMessage());
					}finally{ 
						_writer.close();
					}
				}
			} catch (Exception e) {
				System.out.println("Telnet Transmission 4 --"+e.getMessage());
			}
			
		} 
		
		private Date minusDay(Date sDate)
		{
			Date dateBefore = new Date(sDate.getTime() - 24 * 3600 * 1000 );
			return dateBefore;
		}
}
