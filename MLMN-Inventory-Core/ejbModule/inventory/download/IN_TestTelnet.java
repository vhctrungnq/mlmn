package inventory.download;

import inventory.cni.info.IN_CSystemConfig;
import inventory.cni.info.IN_TelnetServers;
import inventory.utils.log.IN_TelnetLog;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

	public class IN_TestTelnet {  
		private static IN_TelnetClient cls;
		private static String RAW_DOWNLOAD_DIR = "test.raw.download.dir";
		private static String destinationPath = getSystemConfig(RAW_DOWNLOAD_DIR).getParamValue();
		private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss";

	public static void main(String arg[]) throws IOException, Exception { 
		
		try {
			initTaskQueues(); 
			for(int i = 0; i < initTaskQueues().size(); i++){
				doTask(initTaskQueues().get(i));
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static List<IN_TelnetServers> initTaskQueues() {
		List<IN_TelnetServers> addTask = new ArrayList<IN_TelnetServers>();
		Connection conn = null;
		try {
			conn = getDBConnection();
			Statement st = conn.createStatement();

			String queryStr = " select ID,VENDOR,NETWORK,NE_TYPE,NE,IP_ADDRESS,TELNET_PORT,TELNET_USER, " +
					" TELNET_PASSWORD,TELNET_TYPE,LOGIN_TIME,TELNET_COMMAND,STATUS,MODULE,DESCRIPTION,TELNET_COUNT " +
					" from C_TELNET_SERVERS where upper(STATUS) = 'N' and upper(MODULE) = 'INVENTORY' order by ORDERING asc";

			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) { 
				IN_TelnetServers downloadInfo = new IN_TelnetServers();
				
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
	 
	public static void doTask(IN_TelnetServers input) {
		IN_TelnetServers beanInfo = (IN_TelnetServers) input;

		String p_Vendor, p_Network, p_NeType, p_Ne, p_IpAddress, p_TelnetUser, p_TelnetPass,
		p_TelnetType, p_TelnetCommand, p_Status, p_Module, p_Description; 

		Integer p_TelnetPort,p_Id, p_TelnetCount;
		Date p_LoginTime;
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String SysDate = sdf.format(new Date());
		
		String p_SDate = "", p_STime = "", p_Edate = "";
		Date Edate = new Date();
		try {
			Edate = sdf.parse(SysDate);
			p_Edate = sdf.format(Edate).split(" ")[0];
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		p_Status = beanInfo.getStatus();
		p_Module = beanInfo.getModule();
		p_Description = beanInfo.getDescription();
		p_TelnetCount = beanInfo.getCount();
		
		p_SDate = sdf.format(p_LoginTime).split(" ")[0];
		p_STime = sdf.format(p_LoginTime).split(" ")[1];
		String strMessage = "";
		strMessage = "MODULE: "+p_Module+"VENDOR: " + p_Vendor + " NE_TYPE:" + p_NeType
						+ " IP: " + p_IpAddress + " USER_NAME:" + p_TelnetUser;
		System.out.println(strMessage);   
		try { 
			IN_TelnetLog inTelnetLog = null;
			if(p_Vendor.contains("NOKIA")){
				inTelnetLog = new IN_TelnetLog();
				
				cls = new IN_TelnetClient();
				cls.set_DestinationPath(destinationPath);
				cls.set_Debug(false);
				
				if(p_NeType.equalsIgnoreCase("BSC")){
					//Telnet lay thong tin license va port,card NSN BSC
					inTelnetLog = cls.TelnetNSN_BSC(p_NeType, p_Ne, p_SDate,p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass);
				}else if(p_NeType.equalsIgnoreCase("RNC")){
					//Telnet lay thong tin license va port,card NSN RNC
					inTelnetLog = cls.TelnetNSN_RNC(p_NeType, p_Ne, p_SDate,p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass);
				}else if(p_NeType.equalsIgnoreCase("SGSN")){
					//Telnet lay thong tin license va port,card NSN SGSN
					inTelnetLog = cls.TelnetNSN_SGSN(p_NeType, p_Ne, p_SDate,p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass);
				}
			}else if(p_Vendor.contains("ERICSSON")){
				inTelnetLog = new IN_TelnetLog();
				
				cls = new IN_TelnetClient();
				cls.set_DestinationPath(destinationPath);
				cls.set_Debug(false);
				if(p_NeType.equalsIgnoreCase("RNC")){
					//Telnet lay thong tin license software va port card Ericsson RNC
					inTelnetLog = cls.TelnetEricsson_RNC(p_NeType, p_Ne, p_SDate,p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass);
				} 
			}
			//Update thoi gian login time voi nhung server telnet khong loi
			upTime(p_Id,inTelnetLog.get_isConnect(),inTelnetLog.get_description());  
		} catch (Exception ce) { 
			System.out.println("Error Thead Download: " + ce.getMessage());
		} finally {
		}
	}
	
	private static void upTime(int p_Id, String _isConnect, String _description) {

		Connection conn = null; 
		try {
			conn = getDBConnection(); 
			Statement st = conn.createStatement();
			String queryStr = "";
			
			if(_isConnect.equalsIgnoreCase("CONNECTED")){
				queryStr = "update C_TELNET_SERVERS set LOGIN_TIME =  SYSDATE " + 
						", LAST_CONNECT = SYSDATE"+
						", MONITER_STATUS = '"+_isConnect+"'" + 
						", DESCRIPTION = '"+_description+"'" +
						" where ID = '" + p_Id + "'";
			}else{
				queryStr = "update C_TELNET_SERVERS set LAST_CONNECT =  SYSDATE " +
						", MONITER_STATUS = '"+_isConnect+"'" + 
						", DESCRIPTION = '"+_description+"'" +
						" where ID = '" + p_Id + "'";
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

	public static IN_CSystemConfig getSystemConfig(String paramName) {
		
		IN_CSystemConfig systemCofigList = new IN_CSystemConfig();
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
		
		/*String DBCONN_STRING = "jdbc:oracle:thin:@192.168.1.224:1521:vhc";
		String USERNAME = "VMSC6_ALARM";
		String PASSWORD = "ALARM";*/

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
