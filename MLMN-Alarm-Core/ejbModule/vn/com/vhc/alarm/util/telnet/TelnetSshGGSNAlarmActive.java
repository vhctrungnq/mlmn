 package vn.com.vhc.alarm.util.telnet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList; 
import java.util.Date;
import java.util.List;

import vn.com.vhc.alarm.cni.info.AL_CSystemConfig;
import vn.com.vhc.alarm.cni.info.Al_TelnetServers;

	public class TelnetSshGGSNAlarmActive {
		
		public static final String ALARM_TELNET_DELAY_HOUR = "alarm.delaytelnet.hour"; 
		
		private static ALTelnetClient cls;
		//private static String RAW_DOWNLOAD_DIR = "raw.download.telnet.dir";
		private static String RAW_DOWNLOAD_DIR = "test.raw.download.dir";
		private static String destinationPath = getSystemConfig(RAW_DOWNLOAD_DIR).getParamValue();
		public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss"; 
		private static List<Al_TelnetServers> listNe = new ArrayList<Al_TelnetServers>(); 

	public static void main(String arg[]) throws IOException, Exception { 
		
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
					" from C_TELNET_SERVERS where upper(STATUS) = 'Y' and upper(MODULE) = 'ALARM_INVENTORY'";

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
		p_TelnetType, p_TelnetCommand, p_status, p_Module, p_Description; 

		Integer p_Id, p_TelnetCount;
		Date p_LoginTime;
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		
		p_Id = beanInfo.getId();
		p_Vendor = beanInfo.getVendor();
		p_Network = beanInfo.getNetwork();
		p_NeType = beanInfo.getNeType();
		p_Ne = beanInfo.getNe();
		p_IpAddress = beanInfo.getIpAddress(); 
		p_TelnetUser = beanInfo.getTelnetUser();
		p_TelnetPass = beanInfo.getTelnetPassword();
		p_TelnetType = beanInfo.getTelnetType();
		p_LoginTime = beanInfo.getLoginTime();
		p_TelnetCommand = beanInfo.getTelnetCommand();
		p_status = beanInfo.getStatus();
		p_Module = beanInfo.getModule();
		p_Description = beanInfo.getDescription();
		p_TelnetCount = beanInfo.getCount();
		
		String p_SDate = sdf.format(p_LoginTime).split(" ")[0];
		String p_STime = sdf.format(p_LoginTime).split(" ")[1]; 
		
		String strMessage = "";
		strMessage = "MODULE: "+p_Module+"/ VENDOR: " + p_Vendor + "/ NE_TYPE:" + p_NeType
						+ "/ IP: " + p_IpAddress + "/ USER_NAME:" + p_TelnetUser;
		System.out.println(strMessage);  
		
		//@Author: AnhNT
		//Update: 2013/12/05
		try { 
			if(p_Vendor.contains("NOKIA")){ 
				cls = new ALTelnetClient();
				cls.set_DestinationPath(destinationPath);
				cls.set_Debug(false);
				 
				if(p_NeType.equalsIgnoreCase("GGSN")){
					//telnet lay du lieu Active GGSN
					cls.TelnetAlarmActiveGGSN(p_Ne, p_SDate, p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass); 
					//ssh lay du lieu Active GGSN
					cls.SshAlarmActiveGGSN(p_Ne, p_IpAddress, p_TelnetUser, p_TelnetPass, p_SDate, p_STime);
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
	private static void upTime(int  count, int serverId, String _isConect, String _desCription) {
		Connection conn = null;
		
		count++;
		try { 
			conn = getDBConnection();
			Statement st = conn.createStatement();

			String queryStr = "update C_TELNET_SERVERS set  TELNET_COUNT ='" + count+"'" + 
							",MONITER_STATUS = '"+_isConect+"'" +
							",DESCRIPTION = '"+_desCription+"'" +
							" where ID = '" + serverId + "'";

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
		String DRIVER_CLASS_NAME = "oracle.jdbc.pool.OracleDataSource"; 
		String DBCONN_STRING = "jdbc:oracle:thin:@10.18.18.66:1521:ddh";
		String USERNAME = "ALARM";
		String PASSWORD = "ALARM201309";
		
		Connection conn = null;
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
			conn = DriverManager.getConnection(DBCONN_STRING, USERNAME, PASSWORD);  
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error connect DB 10.18.18.66.");
		}
		return conn;
	}
} 