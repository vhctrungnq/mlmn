package vn.com.vhc.alarm.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.util.AL_Attribute;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.monitor.AL_SystemMonitor;


@Stateless
public class AL_CoreService implements AL_CoreServiceLocal {

	private static Logger logger = Logger.getLogger(AL_CoreService.class.getName());

	private static final ExecutorService mainPool = Executors.newFixedThreadPool(2);
	
	//private static int delay_dnu = 2;

	private static int delay = 5; // Delay 300s
	private static int monitorDelay = 60; // Delay 300s

	private static DataSource dataSource = null;
	private static String[] monitorMountpoints = null;
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss";

	public AL_CoreService() {
	}

	/*@Resource(mappedName = "java:/vmsc6_alarm")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}*/
	static {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:/vmsc6_alarm");
			logger.info("Init Datasource ALARM success.");
		} catch (NamingException e) {
			logger.error("Cannot init Datasource: " + e.getMessage());
		}
	}
	
	public void start() {
		try {
			this.loadSystemConfig();
		} catch (SQLException e) {
			logger.warn("Load system config failure: " + e.getMessage() + "; Service halt");
		}
		
		try {
			// Thoi gian nghi giua cac phien lam viec tien trinh core
			delay = Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty(AL_Setting.CORE_DELAY_KEY));
		} catch (Exception e) {
			logger.warn("Not found '" + AL_Setting.CORE_DELAY_KEY + "' in C_SYSTEM_CONFIGS_MLMN");
		}
		
		AL_CoreServiceThread.startService(mainPool, dataSource, delay);
	}
	
	
	public void startMonitor() {
		try {
			monitorDelay = Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty(AL_Setting.MONITOR_DELAY_KEY));
		} catch (Exception e) {
			logger.warn("Not found '" + AL_Setting.MONITOR_DELAY_KEY + "' in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			monitorMountpoints = AL_Global.SYSTEM_CONFIG.getProperty(AL_Setting.MONITOR_HDD_MOUNTPOINTS_KEY).split(",");
		} catch (Exception e) {
			logger.warn("Not found '" + AL_Setting.MONITOR_HDD_MOUNTPOINTS_KEY + "' in C_SYSTEM_CONFIGS_MLMN");
		}

		AL_SystemMonitor.startMonitor(mainPool, monitorDelay, dataSource, "Select 1 from dual", monitorMountpoints);
	}

	public void registerMonitorEvent() {
		AL_SystemMonitorService.registerMonitorEvent();
	}

	public void stopMonitor() {
		AL_SystemMonitor.stopMonitor();
	}

	public void stop() { 
	
		AL_CoreServiceThread.stopService();
		
	}

	public void switchToNextService() {
		AL_CoreServiceThread.switchToNextService();
	}

	public boolean serviceStarted() {
		return (AL_CoreServiceThread.isStarted());
	}

	public List<AL_Attribute> checkServiceInfo() {
		
		List<AL_Attribute> coreInfo = AL_CoreServiceThread.getRunningServiceInfo();

		return coreInfo;
	}

	private void loadSystemConfig() throws SQLException {
		Connection con = null; 

		try {
			con = dataSource.getConnection();
			Statement st = con.createStatement();
			ResultSet result = st.executeQuery("Select Param_Name, Param_Value from C_SYSTEM_CONFIGS_MLMN");
			String key = null;
			String value = null;
			AL_Global.SYSTEM_CONFIG.clear();
			while (result.next()) {
				key = result.getString(1);
				value = result.getString(2);
				if (key != null && value != null) {
					AL_Global.SYSTEM_CONFIG.setProperty(key, value);
				}
			}
			result.close();
			st.close();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
}
