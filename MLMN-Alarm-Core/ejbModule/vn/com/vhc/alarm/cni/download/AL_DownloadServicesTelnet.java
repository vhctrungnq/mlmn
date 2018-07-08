package vn.com.vhc.alarm.cni.download;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.core.AL_Global;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.task.AL_TaskInfo;
import vn.com.vhc.alarm.util.task.AL_TaskService;
import vn.com.vhc.alarm.util.telnet.ALTelnetClient;

public class AL_DownloadServicesTelnet extends AL_TaskService {

	private static Logger logger = Logger
			.getLogger(AL_DownloadServicesTelnet.class.getName());

	private int maxExecutor = -1;
	private int retryNumber = -1; 
	private int delayTime = 1;
	
	private String destinationPath = AL_Global.SYSTEM_CONFIG.getProperty(AL_Setting.BASE_LOCAL_DIR);  
	private DataSource ds = null; 
	ALTelnetClient cls; 
	private final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss";
	private List<String> listMo = new ArrayList<String>();
	private String status = "";
	
	public AL_DownloadServicesTelnet(DataSource ds) {
		this("Download Service", ds);
	}

	public AL_DownloadServicesTelnet(String name, DataSource ds) {
		super(name);
		this.ds = ds;
	}

	public void doInit() {
		try {
			String maxThread = AL_Global.SYSTEM_CONFIG
					.getProperty(AL_Setting.MAX_DOWNLOAD_THREAD_KEY);
			this.maxExecutor = Integer.parseInt(maxThread);
		} catch (NumberFormatException nfe) {
			logger.warn("Invalid '" + AL_Setting.MAX_DOWNLOAD_THREAD_KEY
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			String maxRetry = AL_Global.SYSTEM_CONFIG
					.getProperty(AL_Setting.MAX_DOWNLOAD_RETRY_KEY);
			this.retryNumber = Integer.parseInt(maxRetry);
		} catch (NumberFormatException nfe) {
			logger.warn("Invalid '" + AL_Setting.MAX_DOWNLOAD_RETRY_KEY
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		this.maxExecutor = (this.maxExecutor <= 0) ? 1 : this.maxExecutor;
		this.retryNumber = (this.retryNumber < 0) ? 0 : this.retryNumber;

		initTaskQueues();
	}

	public void doFinallize() {
		this.maxExecutor = -1;
		this.retryNumber = -1;
	}

	public int getMaxExecutorThread() {
		return this.maxExecutor;
	}

	public void initTaskQueues() {
		Connection conn = null;
		try {
			conn = this.ds.getConnection();
			Statement st = conn.createStatement();

			String queryStr = " select ID,VENDOR,NETWORK,NE_TYPE,NE,IP_ADDRESS,TELNET_PORT,TELNET_USER, " +
					" TELNET_PASSWORD,TELNET_TYPE,LOGIN_TIME,TELNET_COMMAND,STATUS,MODULE,DESCRIPTION,TELNET_COUNT " +
					" from C_TELNET_SERVERS where upper(STATUS) = 'Y' and upper(MODULE) = 'ALARM' order by ORDERING asc";

			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
				AL_DownloadInfo downloadInfo = new AL_DownloadInfo();
				
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
				logger.warn(rs.getString("NE") +" : "+sdf.format(rs.getDate("LOGIN_TIME")));
				addTask(downloadInfo);
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

	public void doTask(AL_TaskInfo input) {
		AL_DownloadInfo beanInfo = (AL_DownloadInfo) input;

		String p_Vendor, p_Network, p_NeType, p_Ne, p_IpAddress, p_TelnetUser, p_TelnetPass,
		p_TelnetType, p_TelnetCommand, p_Status, p_Module, p_Description; 

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
		p_Status = beanInfo.getStatus();
		p_Module = beanInfo.getModule();
		p_Description = beanInfo.getDescription();
		p_TelnetCount = beanInfo.getCount();
		
		String p_SDate = sdf.format(p_LoginTime).split(" ")[0];
		String p_STime = sdf.format(p_LoginTime).split(" ")[1];
		String p_EDate = SysDate.split(" ")[0];
		String p_ETime = SysDate.split(" ")[1];
		
		String strMessage = "";
		strMessage = "MODULE: "+p_Module+"VENDOR: " + p_Vendor + " NE_TYPE:" + p_NeType
						+ " IP: " + p_IpAddress + " USER_NAME:" + p_TelnetUser;
		logger.info(strMessage);  
		
		//@Author: AnhNT
		//Update: 2013/10/30
		try { 
			if(beanInfo.getVendor().contains("NOKIA")){
				cls = new ALTelnetClient();
				cls.set_DestinationPath(destinationPath);
				cls.set_Debug(false);
				if(beanInfo.getNeType().equalsIgnoreCase("RNC")){
					/*if(beanInfo.getTelnetType().contains("ACTIVE")){
						//telnet lay du lieu Nokia Siemens ACTIVE 3G
						cls.CommandNSN_Active_3G(p_Ne, p_SDate, p_STime,p_IpAddress, p_TelnetUser, p_TelnetPass, p_TelnetCount);
					}else if(beanInfo.getTelnetType().contains("HISTORY")){
						// telnet lay du lieu Nokia Siemens HISTORY 3G
						cls.CommandNSN_History_3G(p_Ne, p_SDate, p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass);
					}*/
				}else if(beanInfo.getNeType().equalsIgnoreCase("BSC")){
					/*if(beanInfo.getTelnetType().contains("ACTIVE")){
						//telnet lay du lieu Nokia Siemens ACTIVE 2G(ZAHO,ZEOL)
						cls.CommandNSN_Active(p_Ne, p_SDate, p_STime,p_IpAddress, p_TelnetUser, p_TelnetPass, p_TelnetCount);
					}else if(beanInfo.getTelnetType().contains("HISTORY")){
						// telnet lay du lieu Nokia Siemens HISTORY 2G
						cls.CommandNSN_History(p_Ne, p_SDate, p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass);
					}*/
				}else if(beanInfo.getNeType().equalsIgnoreCase("SGSN")){
					//Telnet lay du lieu Nokia Siemens History SGSN
					/*cls.CommandSGSN_History(p_Ne, p_SDate, p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass);
					if (AL_Global.SYSTEM_CONFIG.getProperty(AL_Setting.ALARM_TELNET_DELAY_HOUR)!= null)
						delayTime = Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty(AL_Setting.ALARM_TELNET_DELAY_HOUR));
					upTime(p_TelnetCount, p_Id, delayTime);*/
				}
			}else if(beanInfo.getVendor().contains("ERICSSON")){
				/*cls = new ALTelnetClient();
				cls.set_DestinationPath(destinationPath);
				cls.set_Debug(false);
				if(beanInfo.getNeType().equalsIgnoreCase("RNC") || beanInfo.getNeType().equalsIgnoreCase("MGW")){
					if(beanInfo.getTelnetType().contains("ACTIVE")){
						// telnet lay du lieu ERICSSON ACTIVE 3G VA ACTIVE MGW(MGH011E,...)
						cls.CommandActiveE3GCore(p_Ne, p_SDate, p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass, p_TelnetCount);
					} 
				}else if(beanInfo.getNeType().equalsIgnoreCase("BSC") || beanInfo.getNeType().equalsIgnoreCase("STP")){
					//telnet lay du lieu ERICSSON ACTIVE 2G va ACTIVE STP
					cls.CommandERS_Active_2G(p_Ne, p_SDate, p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass, p_TelnetCount);
				}else if(beanInfo.getNeType().equalsIgnoreCase("ABL_MBL")){
					if (!p_SDate.equals(p_EDate)){
						// Telnet lay du lieu Ericsson ABL_MBL 
						cls.CommandMBL(p_IpAddress, p_Ne, p_TelnetUser, p_TelnetPass);
					} 
				}else if(beanInfo.getNeType().equalsIgnoreCase("GGSN_SSH")){
					// Telnet lay du lieu Ericsson GGSN SSH
					cls.CommandNSN_GGSN_SSH(p_TelnetType, p_Ne, p_IpAddress, p_TelnetUser, p_TelnetPass, p_SDate, p_STime, p_TelnetCount);
				}*/
			} 
		} catch (Exception ce) { 
			logger.info("Error Thead Download: " + ce.getMessage());

		} finally {
			increaseDoneTasks();
		}
	}
// Update time in database
	private void upTime(int  count, int serverId, int delayTime ) {
		Connection conn = null;
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		cal.add(Calendar.HOUR_OF_DAY, -delayTime);
		String SysDate = sdf.format(cal.getTime());  

		count++;
		try { 
			conn = this.ds.getConnection();
			Statement st = conn.createStatement();

			String queryStr = "update C_TELNET_SERVERS set  TELNET_COUNT ='" + count+"'" + ",LOGIN_TIME = to_date('" + SysDate +
					"','yyyy-MM-dd hh24-mi-ss')" +"	where ID = '" + serverId + "'";

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
	public void getMo(String bscid) {
		listMo = new ArrayList<String>();
		Connection conn = null;
		try {
			conn = this.ds.getConnection();
			Statement st = conn.createStatement();

			String queryStr = "select distinct MO from al_dy_mbl_abl_log "
					+ " where BSCID = '"+ bscid +"' and CELLID IS NULL and trunc(DAY) = to_date(to_char(sysdate,'dd-MM-yyyy'),'dd-MM-yyyy') order by MO";

			ResultSet rs = st.executeQuery(queryStr);
			
			while (rs.next()) { 
				listMo.add(rs.getString("MO"));
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
	
	public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
