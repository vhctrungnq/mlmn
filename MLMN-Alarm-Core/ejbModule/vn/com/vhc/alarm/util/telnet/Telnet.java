package vn.com.vhc.alarm.util.telnet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.download.AL_DyMblAblLog;
import vn.com.vhc.alarm.cni.info.AL_CServersTelnet;
import vn.com.vhc.alarm.cni.info.AL_CSystemConfig;

import com.jscape.inet.telnet.TelnetException;
import com.jscape.inet.telnet.TelnetSession;

public class Telnet {

	/**
	 * @param args
	 * @throws TelnetException
	 * @throws InterruptedException
	 */
	private static Logger logger = Logger.getLogger(Telnet.class.getName()); 
	
	public static String _HostName;
	private static List<String> listMo = new ArrayList<String>();
	
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss";
	public static final String TELNET_DELAY_HOUR = "telnet.delay.hour"; 
	@SuppressWarnings("unused")
	private static final String RAW_DOWNLOAD_DIR = "raw.download.dir";
	
	//private static String destinationPath = getSystemConfig(RAW_DOWNLOAD_DIR).getParamValue();
	private static String destinationPath = "/home/thanhlv/oracle/u02/alarm/rawfile/download";
	private static Integer delayTime = Integer.parseInt(getSystemConfig(TELNET_DELAY_HOUR).getParamValue());
	private static String pServerNamABL ="";

	public static int _Port;
	public static String _Username;
	public static String _Password;
	public static String _PasswordPrompt;
	public static String _ShellPrompt;
	public static String _LoginPrompt;
	public static int _ConnectTimeout = 10000;
	public static int _ReadTimeout = 1200;
	public static int _ReadWaitTimeout = 15000;
	public static TelnetSession telnetSession = null;

	public static String _ShellFirstPrompt;

	/**
	 * @param args
	 * @throws TelnetException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws TelnetException,
			InterruptedException {
		try {
			Date pDate = new Date();
			while (true) {
				Date d = new Date();
				int iMinute = d.getMinutes();
				if (iMinute % 3 == 0) {

					DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
					DateFormat dfHour = new SimpleDateFormat("HHmmss");

					String strDay = dfDay.format(pDate);
					String strHour = dfHour.format(pDate);

					initTaskQueues();
					System.out.println("initTaskQueues().size() = "+initTaskQueues().size());
					for(int i = 0; i < initTaskQueues().size(); i++){
						doTask(initTaskQueues().get(i));
					} 
					
					dfDay = new SimpleDateFormat("yyyy-MM-dd");
					dfHour = new SimpleDateFormat("HH-mm-ss");

					String strDayE = dfDay.format(pDate);
					String strHourE = dfHour.format(pDate);

					String resp = " Thoi gian thuc hien" + strDay + " "
							+ strHour + " to " + strDayE + " " + strHourE;
					System.out.println(resp); 
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	//
	@SuppressWarnings("unused")
	private static void TelnetNSN_RNC_History(String p_Username,
			String p_Password, String p_SDate, String p_STime,
			String p_Ip, String p_ServerName,
			String p_AlarmTypeID,String p_Command) {
		
		String p_EDate;
		String p_ETime;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Calendar cal_End = Calendar.getInstance();
		String SysDate_End = sdf.format(cal_End.getTime());

		p_EDate = SysDate_End.substring(0, SysDate_End.indexOf(" ")).toString()
				.trim();
		p_ETime = SysDate_End
				.substring(SysDate_End.indexOf(" ") + 1, SysDate_End.length())
				.toString().trim();
		
		CommandNSN_3G(p_ServerName, p_AlarmTypeID, p_SDate, p_STime,
				p_EDate, p_ETime, p_Ip, p_Username, p_Password,p_Command);
	}

	@SuppressWarnings("unused")
	private static boolean TelnetNokia2G(String loginPrompt,
			String passwordPrompt, String shellPrompt, String p_Username,
			String p_Password, int p_Count, String p_AlarmType,
			String p_SubFolder, String p_SDate, String p_STime, String p_IP,
			String p_ServerName,String p_Command) {
		try {

			TelnetSession telnetSession = null;

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);

			String strFileName = p_AlarmType + "." + p_ServerName + "."
					+ "ZAHO" + "-" + p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "."
					+ strHour + "." + p_Count;
			File outFile = new File(destinationPath + "/" + p_SubFolder,
					strFileName + ".txt");

			BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile));

			String strFileNameZEOL = p_AlarmType + "." + p_ServerName + "."
					+ "ZEOL" + "-" + p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "."
					+ strHour + "." + p_Count;
			File outFileZEOL = new File(destinationPath + "/" + p_SubFolder,
					strFileNameZEOL + ".txt");
			BufferedWriter WriterZEOL = new BufferedWriter(new FileWriter(
					outFileZEOL));

			telnetSession = new TelnetSession(p_IP);
			telnetSession.setHostname(p_IP);
			telnetSession.setPort(23);
			telnetSession.setLoginPrompt(loginPrompt);
			telnetSession.setPasswordPrompt(passwordPrompt);
			telnetSession.setCommandTerminator(p_Command);
			telnetSession.setDebug(true);
			try {
				telnetSession.setShellPrompt(shellPrompt);
				telnetSession.connect(p_Username, p_Password, _ConnectTimeout);

				shellPrompt = "<";

				telnetSession.setShellPrompt(shellPrompt);
				telnetSession.pause(2000);
				String resp = telnetSession.send("ZAHO;", 10000);

				if (resp.length() > 1)
					Writer.write(resp);
				Writer.close();

				resp = telnetSession.send("ZEOL;", 10000);
				if (resp.length() > 1)
					WriterZEOL.write(resp);
				WriterZEOL.close();

				telnetSession.disconnect();
				System.out.println("Download OK");
				// DbUtils.setStateConnect2OSS(true);
			} catch (TelnetException e) {
				outFile.deleteOnExit();
				outFileZEOL.deleteOnExit();
				System.out.println("Error Telnet: " + e.getMessage());
				return false;

			}

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Error close file " + e.getMessage());
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private static boolean TelnetNSN_Active_3G(String loginPrompt,
			String passwordPrompt, String shellPrompt, String p_Username,
			String p_Password, int p_Count, String p_AlarmType,
			String p_SubFolder, String p_SDate, String p_STime, String p_IP,
			String p_ServerName,String p_Command) {
		try {

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);

			String strFileName = p_AlarmType + "." + p_ServerName + "."
					+ "ZAAP" + "-" + p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "."
					+ strHour + "." + p_Count;
			File outFile = new File(destinationPath + "/" + p_SubFolder,
					strFileName + ".txt");

			BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile));
			TelnetSession telnetSession = null;

			telnetSession = new TelnetSession(p_IP);
			telnetSession.setHostname(p_IP);
			telnetSession.setPort(23);
			telnetSession.setLoginPrompt(loginPrompt);
			telnetSession.setPasswordPrompt(passwordPrompt);
			telnetSession.setCommandTerminator(p_Command);
			telnetSession.setDebug(true);
			try {
				telnetSession.setShellPrompt(shellPrompt);
				telnetSession.connect(p_Username, p_Password, _ConnectTimeout);
				shellPrompt = "<";

				telnetSession.setShellPrompt(shellPrompt);
				telnetSession.pause(2000);
				String resp = telnetSession.send("ZAAP;", 10000);

				if (resp.length() > 1)
					Writer.write(resp);
				Writer.close();
				telnetSession.disconnect();

				// DbUtils.setStateConnect2OSS(true);
			} catch (TelnetException e) {

				outFile.deleteOnExit();
				System.out.println("Error Telnet: " + e.getMessage());
				return false;

			}

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Error close file " + e.getMessage());
			return false;
		}
		return true;
	}

	public static boolean CommandActiveEricsson(String p_SeverName,
			String p_SDate, String p_STime, String p_IP, String p_Username,
			String p_Password, int p_Count,String p_Command) {
		// WsC1.SendData ("ZEOH:" & SD & "," & st & ":NR=7704;" & Chr$(13))

		try {

			String shellFirstPrompt = "Windows NT Domain";
			String loginPrompt = "login name:";
			String passwordPrompt = "password:";

			TelnetSession telnetSession = new TelnetSession(p_IP);
			telnetSession.setHostname(p_IP);
			telnetSession.setPort(23);
			telnetSession.setLoginPrompt(loginPrompt);
			telnetSession.setPasswordPrompt(passwordPrompt);
			telnetSession.setCommandTerminator(p_Command);
			telnetSession.setDebug(true);

			Date pDate = new Date();
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);// "10.21.129.132";

			BufferedWriter Writer;

			String fileName = "ERICSSON_ACTIVE." + p_SeverName + "-"
					+ p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "."
					+ strHour + "." + p_Count;

			File outFile = new File(destinationPath + "/ERICSSON-Active",
					fileName + ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));

			String resp = "";
			try {
				/* System.out.println("Connect OK"); */

				telnetSession.setShellPrompt(shellFirstPrompt);
				telnetSession.connect(p_Username, p_Password, _ConnectTimeout);

				String shellPrompt = ">";
				telnetSession.setShellPrompt(shellPrompt);
				resp = telnetSession.send("\n", _ReadTimeout);

				shellPrompt = "<";
				telnetSession.setShellPrompt(shellPrompt);
				resp = telnetSession.send("mml", _ReadTimeout);
				if (resp.length() > 1)
					Writer.write(resp);
				resp = telnetSession.send("allip;", _ReadWaitTimeout);
				if (resp.length() > 1)
					Writer.write(resp);

				Writer.close();
				telnetSession.disconnect();
				System.out.println("Disconnect: " + p_IP + "---" + p_SeverName);
			} catch (TelnetException e) {
				outFile.deleteOnExit();
				System.out.println("Error Telnet: " + p_IP + "---"
						+ p_SeverName + e.getMessage());

			} finally {

			}

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Can't close file " + e.getMessage());
			return false;
		}

		return true;

	}

	// Telnet lay du lieu Active Ericsson 3G va MGW
	public static boolean CommandActiveE3GCore(String p_Server,
			String p_SDate, String p_STime, String p_IP, String p_Username,
			String p_Password, int p_Count, String destinationPath,String p_Command) {
		try {
			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);
			
			String[] p_Server_RNC_ERICSSON = p_Server.replace(";", ",").split(",");

			try {

				String shellFirstPrompt = ">";
				String loginPrompt = "login:";
				String passwordPrompt = "Password:";

				TelnetSession telnetSession = new TelnetSession(p_IP);
				telnetSession.setHostname(p_IP);
				telnetSession.setPort(23);
				telnetSession.setLoginPrompt(loginPrompt);
				telnetSession.setPasswordPrompt(passwordPrompt);
				telnetSession.setCommandTerminator(p_Command);
				telnetSession.setDebug(true);
				telnetSession.setShellPrompt(shellFirstPrompt);
				telnetSession.connect(p_Username, p_Password, _ConnectTimeout);

				for (int i = 0; i < p_Server_RNC_ERICSSON.length; i++) {
					String p_SeverName = p_Server_RNC_ERICSSON[i].toString();

					BufferedWriter Writer;
					String fileName = "ERICSSON_ACTIVE." + p_SeverName + "-"
							+ p_SDate.replaceAll("-", "") + "."
							+ p_STime.replaceAll("-", "") + "-" + strDay + "."
							+ strHour + "." + p_Count;

					File outFile = new File(destinationPath
							+ "/ERICSSON-Active", fileName + ".txt");
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

				}
				telnetSession.disconnect();

			} catch (TelnetException e) {

				System.out.println("Error Telnet: " + p_IP + "---"
						+ e.getMessage());

			} finally {

			}

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Can't close file " + e.getMessage());
			return false;
		}

		return true;
	}

	public static boolean CommandNSN_3G(String p_ServerName,
			String p_AlarmTypeID, String p_SDate, String p_STime,
			String p_EDate, String p_ETime, String p_IP, String p_Username,
			String p_Password,String p_Command) {

		try {

			String shellFirstPrompt = "<";
			String loginPrompt = "ENTER USERNAME";
			String passwordPrompt = "ENTER PASSWORD";
			String strCommand = "ZAHP::NR";

			TelnetSession telnetSession = new TelnetSession(p_IP);
			telnetSession.setHostname(p_IP);
			telnetSession.setPort(23);
			telnetSession.setLoginPrompt(loginPrompt);
			telnetSession.setPasswordPrompt(passwordPrompt);
			telnetSession.setCommandTerminator(p_Command);
			telnetSession.setDebug(true);
			telnetSession.setShellPrompt(shellFirstPrompt);
			BufferedWriter Writer;

			try {
				telnetSession.connect(p_Username, p_Password, _ConnectTimeout);
				String[] p_AlarmArray = p_AlarmTypeID.replace(";", ",").split(
						",");
				for (int iAlarmID = 0; iAlarmID < p_AlarmArray.length; iAlarmID++) {
					try {
						strCommand = "ZAHP::NR";
						String str_AlarmID = p_AlarmArray[iAlarmID];

						String strFileName = "NOKIA_" + p_ServerName + "_"
								+ strCommand.replaceAll(":", "") + "."
								+ str_AlarmID + "_"
								+ p_SDate.replaceAll("-", "") + "."
								+ p_STime.replaceAll("-", "") + "-"
								+ p_EDate.replaceAll("-", "") + "."
								+ p_ETime.replaceAll("-", "");

						File outFile = new File(destinationPath + "/NSN-3G",
								strFileName + ".txt");

						Writer = new BufferedWriter(new FileWriter(outFile));

						strCommand = strCommand + "=" + str_AlarmID + ",:"
								+ p_SDate + "," + p_STime + "," + p_EDate + ","
								+ p_ETime + ";";

						String resp = telnetSession.send(strCommand,
								_ReadWaitTimeout);
						// telnetSession.pause(1000);
						if (resp.length() > 1)
							Writer.write(resp);
						Writer.close();

					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				telnetSession.disconnect();

			} catch (TelnetException e) {

				System.out.println("Error Telnet: " + p_IP + "---"
						+ e.getMessage());

			} finally {

			}

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Can't close file " + e.getMessage());
			return false;
		}

		return true;
	}

	public static boolean CommandMBL(String p_IP, String[] p_ArrayIP_BSC,
			String p_Username, String p_Password, String p_Command) {
		try {

			String shellFirstPrompt = ">";
			String loginPrompt = "login:";
			String passwordPrompt = "Password:";

			TelnetSession telnetSession = new TelnetSession(p_IP);
			telnetSession.setHostname(p_IP);
			telnetSession.setPort(23);
			telnetSession.setLoginPrompt(loginPrompt);
			telnetSession.setPasswordPrompt(passwordPrompt);
			telnetSession.setCommandTerminator(p_Command);
			telnetSession.setDebug(true);
			telnetSession.setShellPrompt(shellFirstPrompt);

			String resp = "";

			telnetSession.connect(p_Username, p_Password, _ConnectTimeout);
			/* System.out.println("Connect OK"); */
			String shellPrompt = ">";
			telnetSession.setShellPrompt(shellPrompt);

			resp = telnetSession.send("\n", _ReadTimeout);
			shellPrompt = "<";
			telnetSession.setShellPrompt(shellPrompt);

			try {

				for (int iIP = 0; iIP < p_ArrayIP_BSC.length; iIP++) {

					String p_ServerName = p_ArrayIP_BSC[iIP].toString();

					Date pDate = new Date();
					DateFormat dfDay = new SimpleDateFormat("yyyyMMdd_HHmmss");
					String strDay = dfDay.format(pDate);

					String fileName = "ERICSSON_MBL_ABL." + p_ServerName + "-"
							+ strDay;
					File outFile = new File(destinationPath + "/ERICSSON-MBL",
							fileName + ".txt");
					BufferedWriter Writer = new BufferedWriter(new FileWriter(
							outFile));

					

					resp = telnetSession.send("eaw " + p_ServerName,
							_ReadTimeout);
					if (resp.length() > 1)
						Writer.write(resp);
					resp = telnetSession.send("rxmsp:moty=rxotrx;",
							_ReadWaitTimeout);
					if (resp.length() > 1)
						Writer.write(resp);
					Writer.close();

				}

			} catch (Exception e) {
				// TODO: handle exception
			}

			telnetSession.disconnect();

			System.out.println("Disconnect: " + p_IP);

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Error Telnet: " + p_IP + "\r\n"
					+ e.getMessage());
			return false;
		}

		return true;
	}
	
	public static List<AL_CServersTelnet> initTaskQueues() {
		List<AL_CServersTelnet> addTask = new ArrayList<AL_CServersTelnet>();
		Connection conn = null;
		try {
			conn = getDBConnection();
			Statement st = conn.createStatement();

			String queryStr = "select * from C_SERVERS_TELNET where upper(status) = 'Y' order by ORDERING asc";

			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) {

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
				cal.add(Calendar.HOUR_OF_DAY, 0);
				String SysDate = sdf.format(cal.getTime());
				String Edate = SysDate.substring(0, SysDate.indexOf(" "))
						.toString().trim();
				String Etime = SysDate
						.substring(SysDate.indexOf(" ") + 1, SysDate.length())
						.toString().trim();

				AL_CServersTelnet downloadInfo = new AL_CServersTelnet();
				
				
				pServerNamABL = rs.getString("SERVER_NAME");
				downloadInfo.setPromptUser(rs.getString("PROMPT_USER"));
				downloadInfo.setPromptPass(rs.getString("PROMPT_PASS"));
				downloadInfo.setPromptCommand(rs.getString("PROMPT_COMMAND"));
				downloadInfo.setServerId(rs.getInt("SERVER_ID"));
				downloadInfo.setCount(rs.getInt("COUNT"));
				downloadInfo.setType(rs.getString("TYPE"));
				downloadInfo.setIpAddress(rs.getString("IP_ADDRESS"));
				downloadInfo.setServerName(rs.getString("SERVER_NAME"));
				downloadInfo.setTelnetUser(rs.getString("TELNET_USER"));
				downloadInfo.setTelnetPassword(rs.getString("TELNET_PASSWORD"));
				downloadInfo.setAlarmName(rs.getString("ALARM_NAME"));
				downloadInfo.setAlarmType(rs.getString("ALARM_TYPE"));
				downloadInfo.setPort(23);
				downloadInfo.seteDate(Edate);
				downloadInfo.seteTime(Etime);
				downloadInfo.setSysDate(SysDate);
				downloadInfo.setsDate(rs.getString("LOGIN_TIME")
						.substring(0, rs.getString("LOGIN_TIME").indexOf(" "))
						.toString().trim());
				downloadInfo.setsTime(rs
						.getString("LOGIN_TIME")
						.substring(rs.getString("LOGIN_TIME").indexOf(" ") + 1,
								rs.getString("LOGIN_TIME").length()).toString()
						.trim());

				downloadInfo.setLoginTime(rs.getString("LOGIN_TIME"));
				downloadInfo.setNode(rs.getString("NODE"));
				downloadInfo.setCommand(rs.getString("COMMAND"));
				downloadInfo.setTaskInfo("Server:"
						+ rs.getString("SERVER_NAME") + "----TYPE:"
						+ rs.getString("TYPE") + "----Alarm_name:"
						+ rs.getString("ALARM_NAME") + "\r\n");

				addTask.add(downloadInfo);
			} 
			rs.close();
			st.close();
		} catch (SQLException e) {
			logger.warn("File list to convert is empty" + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
		return addTask;
	}
	
	@SuppressWarnings("unused")
	public static void doTask(AL_CServersTelnet input) {
		AL_CServersTelnet beanInfo = (AL_CServersTelnet) input;

		String p_AlarmTypeID, p_SDate, p_STime, p_EDate, p_ETime, p_IP, p_Username, p_Password, p_ServerName, p_Command;
		int p_ServerId;
		int p_Count;
		p_ServerName = beanInfo.getServerName();
		p_AlarmTypeID = beanInfo.getAlarmName();
		p_SDate = beanInfo.getsDate();
		p_STime = beanInfo.getsTime();
		p_IP = beanInfo.getIpAddress();
		p_Username = beanInfo.getTelnetUser();
		p_Password = beanInfo.getTelnetPassword();
		p_Count = beanInfo.getCount();
		p_EDate = beanInfo.geteDate();
		p_ETime = beanInfo.geteTime();
		p_ServerId = beanInfo.getServerId(); 
		p_Command = beanInfo.getCommand();
		
	 	if (p_Command.equalsIgnoreCase("n"))
			p_Command = "\n";
		else
			p_Command = "\r\n"; 
	 	
		String strMessage = "";
		strMessage = "Alarm type " + beanInfo.getNode() + " "
				+ beanInfo.getAlarmType() + " TYPE:" + beanInfo.getType()
				+ " IP " + p_IP + " Username  " + p_Username + " ALARM TYPE: ";
		logger.info(strMessage);

		TCPClient send = new TCPClient();

		try {

			send.openTelnet(p_IP, 23);
			if (beanInfo.getAlarmType().equals("ALARM_RNC")) {

				if (beanInfo.getNode().equals("NOKIA")) {
					if (beanInfo.getType().contains("ACTIVE")) {
							
						// Nokia Active 3G
						String loginPrompt = "ENTER USERNAME <";
						String passwordPrompt = "ENTER PASSWORD <";
						String shellPrompt = "<";
						String p_AlarmType = "ACTIVE";
						String p_SubFolder = "NSN-Active";

						/*TelnetNSN_Active_3G(loginPrompt, passwordPrompt,
								shellPrompt, p_Username, p_Password, p_Count,
								p_AlarmType, p_SubFolder, p_SDate, p_STime,
								p_IP, p_ServerName, p_Command);*/
					} else if (beanInfo.getType().contains("HISTORY")) {
						
						// telnet lay du lieu NOKIA HISTORY 3G
						/*TelnetNSN_RNC_History(p_Username, p_Password, p_SDate,
								p_STime, p_IP, p_ServerName,p_AlarmTypeID,p_Command);*/
					}
				} else if (beanInfo.getNode().equals("ERICSSON")) {
					if (beanInfo.getType().contains("ACTIVE")) {
						
						// telnet lay du lieu ERICSSON ACTIVE 3G VA ACTIVE MGW(MGH011E,...)
						/*CommandActiveE3GCore(p_ServerName, p_SDate, p_STime, p_IP, p_Username,
								p_Password, p_Count, destinationPath,p_Command);*/
					}
					// HISTORY DOWN LOAD BANG CRONTAB
					// continue;
				}

			} else if (beanInfo.getAlarmType().equals("ALARM_BSC")) {
				if (beanInfo.getNode().equals("NOKIA")) {
					if (beanInfo.getType().contains("HISTORY")) {
						
						// telnet lay du lieu NOKIA HISTORY 2G
						/*String[] AlarmTypeId = p_AlarmTypeID.replaceAll(";", ",").split(",");
						for(int i = 0; i< AlarmTypeId.length; i++){
							send.Command2G(p_ServerName, AlarmTypeId[i], p_SDate,
									p_STime, p_IP, p_Username, p_Password,
									p_Command, destinationPath);
						}*/
					} else if (beanInfo.getType().contains("ACTIVE")) {
						
						// Nokia Active 2G					
						String loginPrompt = "ENTER USERNAME <";
						String passwordPrompt = "ENTER PASSWORD <";
						String shellPrompt = "<";
						String p_AlarmType = "ACTIVE";
						String p_SubFolder = "NSN-Active";
						/*
						TelnetNokia2G(loginPrompt, passwordPrompt, shellPrompt,
								p_Username, p_Password, p_Count, p_AlarmType,
								p_SubFolder, p_SDate, p_STime, p_IP,
								p_ServerName,p_Command);*/
					}
				} else if (beanInfo.getNode().equals("ERICSSON")) {
					if (beanInfo.getType().contains("ACTIVE")) {
						
						//telnet lay du lieu ERICSSON ACTIVE 2G va ACTIVE STP 
						/*CommandActiveEricsson(p_ServerName, p_SDate, p_STime,
								p_IP, p_Username, p_Password, p_Count,p_Command);*/

					} else if (beanInfo.getType().contains("ABL_MBL")) {
						if (!p_SDate.equals(p_EDate)) {

							// Telnet lay du lieu Ericsson ABL_MBL
							// download 1 lan trong ngay
							String[] p_ArrayIP_BSC = pServerNamABL.replace(";",
									",").split(",");

							for (int iIP = 0; iIP < p_ArrayIP_BSC.length; iIP++) {
								
								CommandMBL(p_IP, p_ArrayIP_BSC, p_Username, p_Password,p_Command);						
							}

						}else{
							String[] p_ArrayIP_BSC = pServerNamABL.replace(";",",").split(",");
							for(int i = 0; i < p_ArrayIP_BSC.length; i++){
								getMo(p_ArrayIP_BSC[i]);
								
								if(listMo.size() > 0){
									
									// telnet lay du lieu Ericsson CELL ABL_MBL( chay sau khi telnet ABL_MBL)	
									send.CommandMBLCELL(p_IP, p_ArrayIP_BSC[i], listMo, p_Username, 
											p_Password, p_Command, destinationPath);									
								}							
							}
						}										
					}
				}
			} else if (beanInfo.getAlarmType().equals("ALARM_SGSN")) {
				
				//telnet lay du lieu ERICSSON SGSN
				/*send.CommandActiveNokia(p_ServerName, p_AlarmTypeID, p_SDate,
						p_STime, p_IP, p_Username, p_Password, p_Count,
						p_Command, destinationPath);*/
			}
			
			upTime(p_Count, p_ServerId,delayTime);
			
		} catch (Exception ce) {
			// Xu ly cac truong hop loi o day

			logger.info("Error Thead Download: " + ce.getMessage());

		} finally { 
			send.CloseTelnet();

		}

	}
	
	public static void getMo(String bscid) {
		Connection conn = null;
		try {
			conn = getDBConnection();
			
			Statement st = conn.createStatement();

			String queryStr = "select MO from al_dy_mbl_abl_log "
					+ " where BSCID = '"+ bscid +"' and CELLID IS NULL" + " and trunc(day) = to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd')";

			ResultSet rs = st.executeQuery(queryStr);
			
			while (rs.next()) {
				
				AL_DyMblAblLog getMoByBsc = new AL_DyMblAblLog();
				getMoByBsc.setMo(rs.getString("MO"));

				listMo.add(getMoByBsc.getMo());
			}
			
			rs.close();
			st.close();
		} catch (SQLException e) {
			logger.warn("File list to convert is empty" + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
	}
	
	private static void upTime(int count, int serverId, int delayTime) {

		Connection conn = null;

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		cal.add(Calendar.HOUR_OF_DAY, -delayTime);
		String SysDate = sdf.format(cal.getTime());

		count = count + 1;
		try {
			conn = getDBConnection();
			Statement st = conn.createStatement();

			String queryStr = "update C_SERVERS_TELNET set  COUNT ='" + count
					+ "'" + "," + "LOGIN_TIME = '" + SysDate + "'" + " "
					+ "where SERVER_ID = '" + serverId + "'";

			st.execute(queryStr); 
			st.close();

		} catch (SQLException e) {
			logger.warn("Try upTime: " + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
	}

	public static AL_CSystemConfig getSystemConfig(String paramName) {
		
		AL_CSystemConfig systemCofigList = new AL_CSystemConfig();
		Connection conn = null;
		
		try {
			conn = getDBConnection();
			
			Statement st = conn.createStatement();

			String queryStr = "select PARAM_NAME,PARAM_VALUE,DESCRIPTION,CONFIG_TYPE from C_SYSTEM_CONFIGS_MLMN " +
					" where  PARAM_NAME = '"+paramName+"'";
			
			ResultSet rs = st.executeQuery(queryStr);
			
			while (rs.next()) { 
				
				systemCofigList.setConfigType(rs.getString("CONFIG_TYPE"));
				systemCofigList.setDescription(rs.getString("DESCRIPTION")); 
				systemCofigList.setParamName(rs.getString("PARAM_NAME"));
				systemCofigList.setParamValue(rs.getString("PARAM_VALUE"));
			}
			
			rs.close();
			st.close();
		} catch (SQLException e) {
			logger.warn("File list to convert is empty" + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
		return systemCofigList;
	}

	public static Connection getDBConnection() {
		String DRIVER_CLASS_NAME = "oracle.jdbc.pool.OracleDataSource"; 
		String DBCONN_STRING = "jdbc:oracle:thin:@10.151.73.73:1522:orcl";
		String USERNAME = "ALARM";
		String PASSWORD = "ALARM";

		Connection conn = null;
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
			conn = DriverManager.getConnection(DBCONN_STRING, USERNAME, PASSWORD);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conn;
	}

}