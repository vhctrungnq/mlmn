package inventory.core;
 
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

import inventory.util.IN_Attribute;
import inventory.util.IN_Setting;
import inventory.util.monitor.IN_SystemMonitor;


@Stateless
public class IN_CoreService implements IN_CoreServiceLocal {

	private static Logger logger = Logger.getLogger(IN_CoreService.class.getName());

	private static final ExecutorService mainPool = Executors.newFixedThreadPool(2);

	private static int delay = 300; // Delay 300s
	private static int monitorDelay = 60; // Delay 300s

	private static DataSource dataSource = null;
	private static String[] monitorMountpoints = null;

	public IN_CoreService() {
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
			logger.info("Init Datasource success.");
		} catch (NamingException e) {
			logger.error("Cannot init Datasource: " + e.getMessage());
		}
	}

	public void start() {
		try {
			loadSystemConfig();
		} catch (SQLException e) {
			logger.warn("Load system config failure: " + e.getMessage() + "; Service halt");
			return;
		}

		try {
			delay = Integer.parseInt(IN_Global.SYSTEM_CONFIG.getProperty(IN_Setting.CORE_DELAY_KEY));
		} catch (Exception e) {
			logger.warn("Not found '" + IN_Setting.CORE_DELAY_KEY + "' in C_SYSTEM_CONFIGS_MLMN");
		}

		//IN_DownloadServiceThread.startService(mainPool, dataSource, delay);
		IN_CoreServiceThread.startService(mainPool, dataSource, delay);
	}

	public void startMonitor() {
		try {
			monitorDelay = Integer.parseInt(IN_Global.SYSTEM_CONFIG.getProperty(IN_Setting.MONITOR_DELAY_KEY));
		} catch (Exception e) {
			logger.warn("Not found '" + IN_Setting.MONITOR_DELAY_KEY + "' in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			monitorMountpoints = IN_Global.SYSTEM_CONFIG.getProperty(IN_Setting.MONITOR_HDD_MOUNTPOINTS_KEY).split(",");
		} catch (Exception e) {
			logger.warn("Not found '" + IN_Setting.MONITOR_HDD_MOUNTPOINTS_KEY + "' in C_SYSTEM_CONFIGS_MLMN");
		}

		IN_SystemMonitor.startMonitor(mainPool, monitorDelay, dataSource, "Select 1 from dual", monitorMountpoints);
	}

	public void registerMonitorEvent() {
		IN_SystemMonitorService.registerMonitorEvent();
	}

	public void stopMonitor() {
		IN_SystemMonitor.stopMonitor();
	}

	public void stop() {
		//IN_DownloadServiceThread.stopService();
		IN_CoreServiceThread.stopService();
	}

	public void switchToNextService() {
		IN_CoreServiceThread.switchToNextService();
	}

	public boolean serviceStarted() {
		return (IN_CoreServiceThread.isStarted());
	}

	public List<IN_Attribute> checkServiceInfo() {
		//List<IN_Attribute> downloadInfo = IN_DownloadServiceThread.getRunningServiceInfo();

		List<IN_Attribute> coreInfo = IN_CoreServiceThread.getRunningServiceInfo();

		//downloadInfo.addAll(coreInfo);

		return coreInfo;
	}

	private void loadSystemConfig() throws SQLException {
		Connection con = null;
		IN_Global.SYSTEM_CONFIG.clear();

		try {
			con = dataSource.getConnection();
			Statement st = con.createStatement();
			ResultSet result = st.executeQuery("Select Param_Name, Param_Value from C_SYSTEM_CONFIGS_MLMN");
			String key = null;
			String value = null;
			while (result.next()) {
				key = result.getString(1);
				value = result.getString(2);
				if (key != null && value != null) {
					IN_Global.SYSTEM_CONFIG.setProperty(key, value);
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
