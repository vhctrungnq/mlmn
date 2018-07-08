package vn.com.vhc.alarm.util.telnet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger; 

import com.jscape.inet.telnet.TelnetException;
import com.jscape.inet.telnet.TelnetSession;

import vn.com.vhc.alarm.cni.download.AL_DyMblAblLog;
import vn.com.vhc.alarm.cni.info.AL_CServersTelnet;
import vn.com.vhc.alarm.cni.info.AL_CSystemConfig; 

	public class TelnetAblMblCell {
		
		public static final String TELNET_DELAY_HOUR = "telnet.delay.hour"; 
		
		private static List<String> listMo = new ArrayList<String>();
		private static String pServerNamABL = "";
		private static ALTelnetClient cls;
		
		private static String RAW_DOWNLOAD_DIR = "raw.download.dir";
		
		private static String destinationPath = getSystemConfig(RAW_DOWNLOAD_DIR).getParamValue();
		
		public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss";
		private static Logger logger = Logger.getLogger(TelnetAblMblCell.class
				.getName());
		
		static TelnetSession telnetSession = null;
		
	private static void disconnect() {
			try {
				if (telnetSession != null) {
					telnetSession.disconnect();

				}
			} catch (TelnetException e) {

			}
		}

	public static void main(String arg[]) throws IOException, Exception { 
		
		try {
			while (true) {
				initTaskQueues(); 
				for(int i = 0; i < initTaskQueues().size(); i++){
					doTask(initTaskQueues().get(i));
				}  
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static List<AL_CServersTelnet> initTaskQueues() {
		List<AL_CServersTelnet> addTask = new ArrayList<AL_CServersTelnet>();
		Connection conn = null;
		try {
			conn = getDBConnection();
			Statement st = conn.createStatement();

			String queryStr = "select * from C_SERVERS_TELNET where upper(status) = 'Y' and alarm_type = 'ALARM_BSC' and type = 'ABL_MBL' order by ORDERING asc";

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

		String p_SDate, p_EDate, p_IP, p_Username, p_Password;
		int p_ServerId;
		int p_Count;
		p_SDate = beanInfo.getsDate(); 
		p_IP = beanInfo.getIpAddress();
		p_Username = beanInfo.getTelnetUser();
		p_Password = beanInfo.getTelnetPassword();
		p_Count = beanInfo.getCount();
		p_EDate = beanInfo.geteDate();
		p_ServerId = beanInfo.getServerId(); 
		
		String strMessage = "";
		strMessage = "Alarm type " + beanInfo.getNode() + " "
				+ beanInfo.getAlarmType() + " TYPE:" + beanInfo.getType()
				+ " IP " + p_IP + " Username  " + p_Username + " ALARM TYPE: ";
		logger.info(strMessage); 

		try { 
			cls = new ALTelnetClient();
			cls.set_DestinationPath(destinationPath);
			cls.set_Debug(false);
			cls.set_ReadWaitTimeout(70000);
			cls.set_ConnectTimeout(5000);
			
			String[] p_ArrayIP_BSC = pServerNamABL.replace(";",",").split(",");
			for(int i = 0; i < p_ArrayIP_BSC.length; i++){
				getMo(p_ArrayIP_BSC[i]);
				if(listMo.size() > 0){
					// telnet lay du lieu Ericsson CELL ABL_MBL( chay sau khi telnet ABL_MBL)	
					cls.CommandMBL_CELL(p_IP, 
							p_ArrayIP_BSC[i].toString(), listMo, p_Username, p_Password);							
				}				
			}
			/*int delayTime = Integer.parseInt(getSystemConfig(TELNET_DELAY_HOUR).getParamValue());
			upTime(p_Count, p_ServerId,delayTime);*/

		} catch (Exception ce) {
			// Xu ly cac truong hop loi o day

			logger.info("Error Thead Download: " + ce.getMessage());

		} finally { 
			disconnect();
		}

	}
	
	public static void getMo(String bscid) {
		listMo = new ArrayList<String>();
		Connection conn = null;
		
		try {
			conn = getDBConnection();
			
			Statement st = conn.createStatement();

			String queryStr = "select MO from al_dy_mbl_abl_log "
					+ " where BSCID = '"+ bscid +"' and CELLID IS NULL" + " and trunc(day) = to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') order by MO";

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
	
	@SuppressWarnings("unused")
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
