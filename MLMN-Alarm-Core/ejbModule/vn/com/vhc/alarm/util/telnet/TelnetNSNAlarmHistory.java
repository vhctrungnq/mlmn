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
import java.util.Date;
import java.util.List;
import java.util.Properties;

import vn.com.vhc.alarm.cni.info.AL_CSystemConfig;
import vn.com.vhc.alarm.cni.info.Al_TelnetServers;
import vn.com.vhc.alarm.utils.log.AL_TelnetLog;

	public class TelnetNSNAlarmHistory {
		
		public static final String ALARM_TELNET_DELAY_HOUR = "alarm.delaytelnet.hour"; 
		
		private static ALTelnetClient cls;
		private static String RAW_DOWNLOAD_DIR = "inventory.raw.download.telnet.dir";
		private static String destinationPath = "";
		public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss"; 
		private static List<Al_TelnetServers> listNe = new ArrayList<Al_TelnetServers>(); 

		private static Properties prop = new Properties();
		private static AppConfig appConfig = new AppConfig(); 
		
		private static String DRIVER_CLASS_NAME = "";
		private static String DBCONN_STRING = "";
		private static String USERNAME = "";
		private static String PASSWORD = "";
	/**
	 * Gets {@link String} of connect DB.
	 * 
	 * @return {@link String} object.
	 * @throws AppConfigException
	 */
	public static void loadconfigDB() throws AppConfigException{ 
		prop = appConfig.getPropertiesConfig();
		/**
		 * Key name in file config.
		 */
		DRIVER_CLASS_NAME = prop.getProperty("classLoader","oracle.jdbc.pool.OracleDataSource");
		DBCONN_STRING = prop.getProperty("jdbcUrl");
		USERNAME = prop.getProperty("username");
		PASSWORD = prop.getProperty("password");
	}	
		
	public static void main(String arg[]) throws IOException, Exception {  
		loadconfigDB();
		destinationPath = getSystemConfig(RAW_DOWNLOAD_DIR).getParamValue();
		try {
			listNe = initTaskQueues(); 
			for(int i = 0; i < listNe.size(); i++){
				doTask(listNe.get(i));
			}  
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static List<Al_TelnetServers> initTaskQueues() {
		List<Al_TelnetServers> addTask = new ArrayList<Al_TelnetServers>();
		Connection conn = null;
		try {
			conn = getDBConnection();
			Statement st = conn.createStatement();

			String queryStr = " select ID,VENDOR,NETWORK,NE_TYPE,NE,IP_ADDRESS,TELNET_PORT,TELNET_USER, " +
					" TELNET_PASSWORD,TELNET_TYPE,LOGIN_TIME,TELNET_COMMAND,STATUS,MODULE,DESCRIPTION,TELNET_COUNT " +
					" from C_TELNET_SERVERS where upper(STATUS) = 'Y' and upper(VENDOR) like '%NOKIA%' and upper(MODULE) = 'ALARM_INVENTORY'";

			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) { 
				Al_TelnetServers downloadInfo = new Al_TelnetServers();
				
				downloadInfo.setId(rs.getInt("ID"));
				downloadInfo.setVendor(rs.getString("VENDOR"));
				downloadInfo.setNetwork(rs.getString("NETWORK"));
				downloadInfo.setNeType(rs.getString("NE_TYPE"));
				downloadInfo.setNe(rs.getString("NE"));
				downloadInfo.setIpAddress(rs.getString("IP_ADDRESS"));
				downloadInfo.setTelnetPort(rs.getInt("TELNET_PORT"));
				downloadInfo.setTelnetUser(rs.getString("TELNET_USER"));
				downloadInfo.setTelnetPassword(rs.getString("TELNET_PASSWORD"));
				downloadInfo.setTelnetType(rs.getString("TELNET_TYPE"));
				downloadInfo.setLoginTime(rs.getTimestamp("LOGIN_TIME")); 
				downloadInfo.setTelnetCommand(rs.getString("TELNET_COMMAND"));
				downloadInfo.setStatus(rs.getString("STATUS"));
				downloadInfo.setModule(rs.getString("MODULE"));
				downloadInfo.setDescription(rs.getString("DESCRIPTION"));
				downloadInfo.setCount(rs.getInt("TELNET_COUNT"));
				
				downloadInfo.setTaskInfo("Server:"
						+ rs.getString("NE") + "----IP:"
						+ rs.getString("IP_ADDRESS")+ "\r\n");

				addTask.add(downloadInfo);
			} 
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("File list to convert is empty" + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					System.out.println("Cannot close connection to database");
				}
			}
		}
		return addTask;
	} 
	
	public static void doTask(Al_TelnetServers beanInfo) {  
		String p_Vendor, p_Network, p_NeType, p_Ne, p_IpAddress, p_TelnetUser, p_TelnetPass,
		p_TelnetType, p_TelnetCommand, p_isConect, p_Module, p_Description; 

		Integer p_TelnetPort,p_Id, p_TelnetCount;
		Date p_LoginTime;
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String SysDate = sdf.format(new Date());
		
		p_Id = beanInfo.getId();
		p_Vendor = beanInfo.getVendor();
		p_Network = beanInfo.getNetwork();
		p_NeType = beanInfo.getNeType();
		p_Ne = beanInfo.getNe();
		p_IpAddress = beanInfo.getIpAddress();
		p_TelnetPort = beanInfo.getTelnetPort();
		p_TelnetUser = beanInfo.getTelnetUser();
		p_TelnetPass = beanInfo.getTelnetPassword();
		p_TelnetType = beanInfo.getTelnetType();
		p_LoginTime = beanInfo.getLoginTime();
		p_TelnetCommand = beanInfo.getTelnetCommand();
		p_isConect = beanInfo.getStatus();
		p_Module = beanInfo.getModule();
		p_Description = beanInfo.getDescription();
		p_TelnetCount = beanInfo.getCount();
		
		String p_SDate = sdf.format(p_LoginTime).split(" ")[0];
		String p_STime = sdf.format(p_LoginTime).split(" ")[1];
		String p_EDate = SysDate.split(" ")[0];
		String p_ETime = SysDate.split(" ")[1];
		
		String strMessage = "";
		strMessage = "MODULE: "+p_Module+"/ VENDOR: " + p_Vendor + "/ NE_TYPE:" + p_NeType
						+ "/ IP: " + p_IpAddress + "/ USER_NAME:" + p_TelnetUser;
		System.out.println(strMessage);  
		
		//@Author: AnhNT
		//Update: 2013/10/30
		try { 
			int delayTime = Integer.parseInt(getSystemConfig(ALARM_TELNET_DELAY_HOUR).getParamValue());
			if(p_Vendor.contains("NOKIA")){
				AL_TelnetLog alTelnetLog = new AL_TelnetLog();
				cls = new ALTelnetClient();
				cls.set_DestinationPath(destinationPath);
				cls.set_Debug(false);
				
				if(p_Network.equalsIgnoreCase("3G")){
					//telnet lay du lieu History Nokia Siemens 3G
					alTelnetLog = cls.CommandNSN_History_3G(p_Ne, p_SDate, p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass); 
					
					String _isConect = alTelnetLog.get_isConnect();
					String _desCription = alTelnetLog.get_description(); 
					upTime(p_Id,delayTime,_isConect,_desCription);
				}else if(p_Network.equalsIgnoreCase("2G")){
					//telnet lay du lieu History Nokia Siemens 2G
					alTelnetLog = cls.CommandNSN_History(p_Ne, p_SDate, p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass); 
					
					String _isConect = alTelnetLog.get_isConnect();
					String _desCription = alTelnetLog.get_description(); 
					upTime(p_Id,delayTime,_isConect,_desCription);
				} 
			}
			
		} catch (Exception ce) {
			// Xu ly cac truong hop loi o day
			System.out.println("Error Thead Download: " + ce.getMessage());
		} finally { 
			//disconnect();
		}

	} 
	 
	// Update time in database
	private synchronized static void upTime(int serverId, int delayTime, String _isConect, String _desCription) {
		Connection conn = null;

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		cal.add(Calendar.HOUR_OF_DAY, -delayTime);
		String SysDate = sdf.format(cal.getTime());  

		try { 
			conn = getDBConnection();
			Statement st = conn.createStatement();
			String queryStr = "";
			
			if(_isConect.equalsIgnoreCase("CONNECTED")){
				queryStr = "update C_TELNET_SERVERS set LOGIN_TIME = to_date('" + SysDate +"','yyyy-MM-dd hh24-mi-ss')" +
							",LAST_CONNECT = to_date('" + SysDate +"','yyyy-MM-dd hh24-mi-ss')"+ 
							",DESCRIPTION = '"+_desCription+"'" +
							",MONITER_STATUS = '"+_isConect+"'" +
							" where ID = '" + serverId + "'";
			}else{
				queryStr = "update C_TELNET_SERVERS set LAST_CONNECT = SYSDATE"+ 
							",DESCRIPTION = '"+_desCription+"'" +
							",MONITER_STATUS = '"+_isConect+"'" +
							" where ID = '" + serverId + "'";
			}

			st.execute(queryStr); 
			st.close();

		} catch (SQLException e) {
			System.out.println("Try upTime: " + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					System.out.println("Cannot close connection to database");
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
			System.out.println("File list to convert is empty" + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					System.out.println("Cannot close connection to database");
				}
			}
		}
		return systemCofigList;
	}

	public static Connection getDBConnection() {
		Connection conn = null;
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
			conn = DriverManager.getConnection(DBCONN_STRING, USERNAME, PASSWORD);  
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception "+ex);
		}
		return conn;
	}
} 