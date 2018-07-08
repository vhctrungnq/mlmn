package vn.com.vhc.alarm.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.ArrayList; 
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger; 

import vn.com.vhc.alarm.core.AL_Global;
import vn.com.vhc.alarm.dnu.entity.AL_PatternInfo; 

public class AL_Setting {
	
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss";
	public static final String TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	public static final String DB_TIME_FORMAT = "MM/dd/yyyy HH24:mi:ss";

	public static final String DATE_SEPARATOR = "/";
	public static final String TIME_SEPARATOR = ":";
	public static final String FILE_EXTENSION = ".txt";

	public static final byte SEPARATOR_KEY = 0;
	public static final byte COMMENT_CHAR_KEY = 1;
	public static final byte NODE_NAME_KEY = 2;
	public static final byte FILE_ID_KEY = 3;
	public static final byte FILE_PATTERN_KEY = 4;
	public static final byte RAW_TABLE_KEY = 5;

	public static final String MAX_DOWNLOAD_THREAD_KEY = "max.download.thread";
	public static final String MAX_DOWNLOAD_RETRY_KEY = "max.download.retry";

	public static final String MAX_SELECTION_THREAD_KEY = "max.selection.thread";
	public static final String MAX_SELECTION_RETRY_KEY = "max.selection.retry";
	public static final String MAX_SELECTION_QUEUE_SIZE_KEY = "max.selection.queue.size";

	public static final String MAX_CONVERT_THREAD_KEY = "max.convert.thread";
	public static final String MAX_CONVERT_RETRY_KEY = "max.convert.retry";

	public static final String MAX_IMPORT_THREAD_KEY = "max.import.thread";
	public static final String MAX_IMPORT_RETRY_KEY = "max.import.retry"; 
	
	public static final String CONVERT_ERROR_DIR = "raw.convert.error.dir";
	public static final String CONVERT_DIR = "raw.convert.dir";
	public static final String ILLEGAL_DIR = "raw.illegal.dir";
	public static final String SELECTION_DIR = "raw.selection.dir";
	public static final String BASE_LOCAL_DIR = "raw.download.dir";

	public static final String UNKNOWN_STATUS = "UNKNOWN";
	public static final String SEARCHING_STATUS = "SEARCHING";
	public static final String DOWNLOADING_STATUS = "DOWNLOADING";
	public static final String SELECTING_STATUS = "SELECTING";
	public static final String CONVERTING_STATUS = "CONVERTING";
	public static final String IMPORTING_STATUS = "IMPORTING";
	public static final String SLEEPING_STATUS = "SLEEPING";
	public static final String STOPING_STATUS = "STOPING";
	public static final String STOPED_STATUS = "STOPED";
	public static final String DONE_STATUS = "DONE";
	// Get node of server
	// Update by ThangPX 20140218
	public static final String SERVER_KEY = "SERVER_NODE";
	// core module
	public static final String ALARM_TELNET_DELAY_HOUR = "alarm.delaytelnet.hour";
	public static final String CORE_DELAY_KEY = "core.delay.second";
	public static final String DOWNLOAD_DELAY_KEY = "download.delay.second";
	public static final String MONITOR_DELAY_KEY = "monitor.delay.second";
	public static final String MONITOR_HDD_MOUNTPOINTS_KEY = "monitor.hdd.mountpoints";
	public static final String UPLOAD_DELAY_KEY = "upload.delay.second";
	
	//add them so gio lay du lieu. 30/05/2017.AnhCTV
	public static final byte LAST_HOUR = 12;

	private static Logger logger = Logger.getLogger(AL_Setting.class.getName());

	public static List<AL_PatternInfo> getListPatternInfo() {
		List<AL_PatternInfo> patterns = new ArrayList<AL_PatternInfo>();
		
		String node = getNode(SERVER_KEY);
		Connection conn = null;
		try {
			conn = AL_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();

			String querySQL = "select PATTERN_ID,FILE_PATTERN,NODE_TYPE,RAW_TABLE," + "CONVERT_CLASS,STATUS,SUB_DIR,NODE_PATTERN_GROUP,TIME_PATTERN_GROUP,"
					+ "TIME_PATTERN, MODULE, SERVER_NODE,LAST_HOUR from C_FILE_PATTERNS where upper(MODULE) = 'ALARM' and upper(STATUS) = 'Y' and SERVER_NODE = '"+ node +"' order by PATTERN_ID";
			ResultSet rs = st.executeQuery(querySQL);
			AL_PatternInfo info = null;
			while (rs.next()) {
				info = new AL_PatternInfo();
				info.setPatternId(rs.getInt("PATTERN_ID"));
				info.setFilePattern(rs.getString("FILE_PATTERN"));
				info.setNodeType(rs.getString("NODE_TYPE"));
				info.setRawTable(rs.getString("RAW_TABLE"));
				info.setConvertClass(rs.getString("CONVERT_CLASS"));
				info.setStatus(rs.getString("STATUS"));
				info.setSubDir(rs.getString("SUB_DIR"));
				info.setNodePatternGroup(rs.getInt("NODE_PATTERN_GROUP"));
				info.setTimePatternGroup(rs.getInt("TIME_PATTERN_GROUP"));
				info.setTimeFormat(rs.getString("TIME_PATTERN"));
				info.setModule(rs.getString("MODULE"));
				
				info.setServerNode(rs.getString("SERVER_NODE"));
				
				info.setLastHour(rs.getInt("LAST_HOUR"));
				
				patterns.add(info);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			logger.warn("Get pattern information with error: " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException f) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
		return patterns;
	}

	public static Connection getDBConnection() {
		String DRIVER_CLASS_NAME = "oracle.jdbc.pool.OracleDataSource";
		String DBCONN_STRING = "jdbc:oracle:thin:@14.160.91.174:1521:vhc";
		String USERNAME = "mlmn_alarm";
		String PASSWORD = "alarm123";

		Connection conn = null;
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
			conn = DriverManager.getConnection(DBCONN_STRING, USERNAME, PASSWORD);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conn;
	}
	// Get node Server
	public static String getNode(String key)
	{
		String value = "ALL";
		String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
		InputStream propsStream = null;
		try {
			propsStream = new FileInputStream(propFileName);
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			return value = props.getProperty(key);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return value;
	}
}
