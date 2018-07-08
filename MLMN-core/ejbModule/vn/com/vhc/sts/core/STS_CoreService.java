package vn.com.vhc.sts.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.util.STS_Attribute;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.monitor.SystemMonitor;

//import vn.com.vhc.vms.vmsc2.dnu.DownloadServiceThread;

@Stateless
public class STS_CoreService implements STS_CoreServiceLocal {

	private static Logger logger = Logger.getLogger(STS_CoreService.class.getName());

	private static final ExecutorService mainPool = Executors.newFixedThreadPool(2);

	private static int delay = 300; // Delay 300s
	private static int monitorDelay = 60; // Delay 300s

	private DataSource dataSource = null;
	private static String[] monitorMountpoints = null;

	public STS_CoreService() {
	}

	@Resource(mappedName = "java:/vmsc2DS")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void start() {
		try {
			loadSystemConfig();
		} catch (SQLException e) {
			logger.warn("Load system config failure: " + e.getMessage() + "; Service halt");
			return;
		}

		try {
			delay = Integer.parseInt(STS_Global.SYSTEM_CONFIG.getProperty(STS_Setting.CORE_DELAY_KEY));
		} catch (Exception e) {
			logger.warn("Not found '" + STS_Setting.CORE_DELAY_KEY + "' in C_SYSTEM_CONFIGS_MLMN");
		}

		//DownloadServiceThread.startService(dataSource);
		STS_CoreServiceThread.startService(mainPool, dataSource, delay);
	}

	public void startMonitor() {
		try {
			monitorDelay = Integer.parseInt(STS_Global.SYSTEM_CONFIG.getProperty(STS_Setting.MONITOR_DELAY_KEY));
		} catch (Exception e) {
			logger.warn("Not found '" + STS_Setting.MONITOR_DELAY_KEY + "' in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			monitorMountpoints = STS_Global.SYSTEM_CONFIG.getProperty(STS_Setting.MONITOR_HDD_MOUNTPOINTS_KEY).split(",");
		} catch (Exception e) {
			logger.warn("Not found '" + STS_Setting.MONITOR_HDD_MOUNTPOINTS_KEY + "' in C_SYSTEM_CONFIGS_MLMN");
		}

		SystemMonitor.startMonitor(mainPool, monitorDelay, dataSource, "Select 1 from dual", monitorMountpoints);
	}

	public void registerMonitorEvent() {
		STS_SystemMonitorService.registerMonitorEvent();
	}

	public void stopMonitor() {
		SystemMonitor.stopMonitor();
	}

	public void stop() {
		//DownloadServiceThread.stopService();
		STS_CoreServiceThread.stopService();
	}

	public void switchToNextService() {
		STS_CoreServiceThread.switchToNextService();
	}

	public boolean serviceStarted() {
		return (STS_CoreServiceThread.isStarted());
	}

	public List<STS_Attribute> checkServiceInfo() {
		//List<Attribute> downloadInfo =
		//DownloadServiceThread.getRunningServiceInfo();

		List<STS_Attribute> coreInfo = STS_CoreServiceThread.getRunningServiceInfo();

		//downloadInfo.addAll(coreInfo);

		return coreInfo;
	}

	private void loadSystemConfig() throws SQLException {
		Connection con = null;
		STS_Global.SYSTEM_CONFIG.clear();
		String sys_config = STS_Setting.getNode(STS_Setting.SYS_CONFIG);

		try {
			con = dataSource.getConnection();
			Statement st = con.createStatement();
			String sqlLine = "Select Param_Name, Param_Value from "+sys_config;
			ResultSet result = st.executeQuery(sqlLine);
			String key = null;
			String value = null;
			while (result.next()) {
				key = result.getString(1);
				value = result.getString(2);
				if (key != null && value != null) {
					STS_Global.SYSTEM_CONFIG.setProperty(key, value);
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
