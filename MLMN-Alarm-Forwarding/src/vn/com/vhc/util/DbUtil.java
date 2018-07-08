// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.vhc.common.AppConfig;
import vn.com.vhc.common.AppEnv;
import vn.com.vhc.model.AlarmType;
import vn.com.vhc.model.CellN;
import vn.com.vhc.model.CellN3G;
import vn.com.vhc.model.StructAlarm;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * Database utilities
 * 
 * @author datnh
 *
 */
public class DbUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DbUtil.class);

	private static BoneCP db1ConnectionPool = null;
	private static BoneCP db2ConnectionPool = null;
	
	private static int queryTimeout = 30; // seconds
	
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public static List<CellN> BTS = new ArrayList<CellN>();
	public static List<CellN3G> BTS_3G = new ArrayList<CellN3G>();
	
	public static ConcurrentMap<String, CellN> BSCID = new ConcurrentHashMap<String, CellN>();
	public static ConcurrentMap<String, CellN3G> RNCID = new ConcurrentHashMap<String, CellN3G>();
	public static List<AlarmType> ALARM_CONFIG_2G = new ArrayList<AlarmType>();
	public static List<AlarmType> ALARM_CONFIG_3G = new ArrayList<AlarmType>();
	public static int databaseNo = 1;

	static {
		// database 1
		try {
			BoneCPConfig boneCPConfigDb1 = new BoneCPConfig(AppConfig.getBoneCPConfigProperties(1));
			db1ConnectionPool = new BoneCP(boneCPConfigDb1);
			
			logger.info("[INFO] Connection to the database 1 successful");
		} catch (Exception e) {
			logger.error("Can not establish connection to the database.", e);
		}
		
		// database 2
		try {
			PropertiesConfiguration config = AppConfig.getPropertiesConfig();
			databaseNo = config.getInt(AppEnv.DATABASE_NO, AppEnv.DEFAULT_DATABASE_NO);
			
			if (databaseNo > 1) {
				BoneCPConfig boneCPConfigDb1 = new BoneCPConfig(AppConfig.getBoneCPConfigProperties(1));
				db1ConnectionPool = new BoneCP(boneCPConfigDb1);
				
				BoneCPConfig boneCPConfigDb2 = new BoneCPConfig(AppConfig.getBoneCPConfigProperties(2));
				db2ConnectionPool = new BoneCP(boneCPConfigDb2);
				
				logger.info("[INFO] Connection to the database 2 successful");
			}
		} catch (Exception e) {
			logger.error("Can not establish connection to the database.", e);
		}
		
		// Reload
		/*Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DAY_OF_MONTH,1);
	    cal.set(Calendar.HOUR_OF_DAY, 3);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);

		long initialDelay = cal.getTimeInMillis() - System.currentTimeMillis();*/
		long period = 12*3600*1000;
		
		final Runnable reset = new Runnable() {
			public void run() {
				logger.info("[INFO] " + new Date() + ": reload from database!");
				init();
			}
		};
		scheduler.scheduleAtFixedRate(reset, period, period, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Get connect by dbNo
	 * @param dbNo
	 * @return
	 * @throws SQLException
	 */
	private static Connection getConnection(int dbNo) throws SQLException {
		if (dbNo == 1)
			return db1ConnectionPool.getConnection();
		return db2ConnectionPool.getConnection();
	}
	
	/**
	 * Initial
	 */
	public static void init() {		
		// load BTS
		loadBTS();
		
		// load BTS
		loadBTS_3G();
		
		// load BSC
		loadBSCID();
		
		// load RNC list
		loadRNCID();
		
		// load alarm config 2G
		loadAlarmConfig2G();
		
		// load alarm config 3G
		loadAlarmConfig3G();
	}
	
	/**
	 * Load BTS
	 */
	private static void loadBTS() {
		String mSQL = "select SUBSTR(objectname,0,INSTR(t.OBJECTNAME,'/BTS')-1) bts_objectname, T.objectname, T.bscname, T.btsname, segmentname from H_TMP_CELL_N T";
		
		Connection conn = null;
		try {
			conn = getConnection(1);
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(queryTimeout);
			
			ResultSet rs = stmt.executeQuery(mSQL);
			
			// clear 
			BTS.clear();
			
			CellN cellN = null;
			while(rs.next()) {
				cellN = new CellN(rs.getString("bts_objectname"), rs.getString("objectname"), null,
						rs.getString("bscname"), rs.getString("btsname"),
						rs.getString("segmentname"));
				BTS.add(cellN);
			}
			
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			//conn.close();
			logger.info("[INFO] Load BTS succesful");
		} catch (Exception e) {
			logger.error("Can not load BTS", e);
		} finally {
            try {
            	conn.close();
    			conn = null;
            } catch (Exception e) {
            	logger.error("close({}) error", "Connectiton", e);
            }
        }
	}
	
	/**
	 * Load BTS 3G
	 */
	private static void loadBTS_3G() {
		String mSQL = "select SUBSTR(objectname,0,INSTR(t.OBJECTNAME,'/WCEL')-1) bts_objectname, T.objectname, t.rncname, t.wbtsname, t.cellname from H_CELL_N_3G T";
		
		Connection conn = null;
		try {
			conn = getConnection(1);
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(queryTimeout);
			
			ResultSet rs = stmt.executeQuery(mSQL);
			
			// clear
			BTS_3G.clear();
			
			CellN3G cellN3G = null;
			while(rs.next()) {
				cellN3G = new CellN3G(rs.getString("bts_objectname"), rs.getString("objectname"), null,
						rs.getString("rncname"), rs.getString("wbtsname"),
						rs.getString("cellname"));
				BTS_3G.add(cellN3G);
			}
			
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			//conn.close();
			
			logger.info("[INFO] Load BTS 3G succesful");
		} catch (Exception e) {
			logger.error("Can not load BTS 3G", e);
		} finally {
            try {
            	conn.close();
    			conn = null;
            } catch (Exception e) {
            	logger.error("close({}) error", "Connectiton", e);
            }
        }
	}
	
	/**
	 * Load BSC
	 */
	private static void loadBSCID() {
		String mSQL = "select distinct SUBSTR(objectname,0,INSTR(t.OBJECTNAME,'/BCF')-1) bscid, bscname  from H_TMP_CELL_N T";
		
		Connection conn = null;
		try {
			conn = getConnection(1);
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(queryTimeout);
			
			ResultSet rs = stmt.executeQuery(mSQL);
			
			// clear
			BSCID.clear();
			
			CellN cellN = null;
			while(rs.next()) {
				cellN = new CellN(null, null, rs.getString("bscid"),
						rs.getString("bscname"), null,
						null);
				BSCID.putIfAbsent(cellN.getBscId(), cellN);
			}
			
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			//conn.close();
			
			logger.info("[INFO] Load BSCID succesful");
		} catch (Exception e) {
			logger.error("Can not load BSCID", e);
		} finally {
            try {
            	conn.close();
    			conn = null;
            } catch (Exception e) {
            	logger.error("close({}) error", "Connectiton", e);
            }
        }
	}
	
	/**
	 * Load RNC
	 */
	private static void loadRNCID() {
		String mSQL = "select distinct SUBSTR(objectname, 0, INSTR(t.OBJECTNAME,'/WBTS')-1) rncid, rncname from H_CELL_N_3G t";
		
		Connection conn = null;
		try {
			conn = getConnection(1);
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(queryTimeout);
			
			ResultSet rs = stmt.executeQuery(mSQL);
			
			// clear
			RNCID.clear();
			
			CellN3G cellN3G = null;
			while(rs.next()) {
				cellN3G = new CellN3G(null, null, rs.getString("rncid"),
						rs.getString("rncname"), null, null);
				RNCID.putIfAbsent(cellN3G.getRncid(), cellN3G);
			}
			
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			//conn.close();
			
			logger.info("[INFO] Load RNCID succesful");
			
		} catch (Exception e) {
			logger.error("Can not load RNCID", e);
		} finally {
            try {
            	conn.close();
    			conn = null;
            } catch (Exception e) {
            	logger.error("close({}) error", "Connectiton", e);
            }
        }
	}
	
	/**
	 * Load alarm config 2G
	 */
	private static void loadAlarmConfig2G() {
		String mSQL = "select block_column_name, block_value, alarm_type, alarm_mapping_name, alarm_mapping_id, is_monitor, is_send_sms from c_config_alarm_type A where vendor='NOKIA SIEMENS' and is_enable='Y' and node ='2G' and search='SINGLE' and RAW_TABLE='R_ALARM_LOG_ACTIVE'";
		
		Connection conn = null;
		try {
			conn = getConnection(1);
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(queryTimeout);
			
			ResultSet rs = stmt.executeQuery(mSQL);
			
			// clear
			ALARM_CONFIG_2G.clear();
			
			AlarmType alarmType = null;
			while(rs.next()) {
				alarmType = new AlarmType(rs.getString("block_column_name"), rs.getString("block_value"), rs.getString("alarm_type"), 
						rs.getString("alarm_mapping_name"), rs.getString("alarm_mapping_id"), rs.getString("is_monitor"), rs.getString("is_send_sms"));
				ALARM_CONFIG_2G.add(alarmType);
			}
			
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			//conn.close();
			
			logger.info("[INFO] Load AlarmConfig2G succesful");
			
		} catch (Exception e) {
			logger.error("Can not load AlarmConfig2G", e);
		} finally {
            try {
            	conn.close();
    			conn = null;
            } catch (Exception e) {
            	logger.error("close({}) error", "Connectiton", e);
            }
        }
	}
	
	/**
	 * Load alarm config 3G
	 */
	private static void loadAlarmConfig3G() {
		String mSQL = "select block_column_name, block_value, alarm_type, alarm_mapping_name, alarm_mapping_id, is_monitor, is_send_sms from c_config_alarm_type A where vendor='NOKIA SIEMENS' and is_enable='Y' and node ='3G' and search='SINGLE' and RAW_TABLE='R_ALARM_LOG_ACTIVE'";
		
		Connection conn = null;
		try {
			conn = getConnection(1);
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(queryTimeout);
			
			ResultSet rs = stmt.executeQuery(mSQL);
			
			// clear
			ALARM_CONFIG_3G.clear();
			
			AlarmType alarmType = null;
			while(rs.next()) {
				alarmType = new AlarmType(rs.getString("block_column_name"), rs.getString("block_value"), rs.getString("alarm_type"), 
						rs.getString("alarm_mapping_name"), rs.getString("alarm_mapping_id"), rs.getString("is_monitor"), rs.getString("is_send_sms"));
				ALARM_CONFIG_3G.add(alarmType);
			}
			
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			//conn.close();
			
			logger.info("[INFO] Load AlarmConfig3G succesful");
		} catch (Exception e) {
			logger.error("Can not load AlarmConfig3G", e);
		} finally {
            try {
            	conn.close();
    			conn = null;
            } catch (Exception e) {
            	logger.error("close({}) error", "Connectiton", e);
            }
        }
	}
	
	/**
	 * Get cell by object name
	 * @param objectName
	 * @return
	 */
	public static CellN getCellN(int type, String name) {
		if (type == 1) {
			for (CellN item: BTS) {
				if (item.getObjectName().equals(name))
					return item;
			}
		}
		else {
			for (CellN item: BTS) {
				if (item.getBtsObjectname().equals(name))
					return item;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param type
	 * @param name
	 * @return
	 */
	public static CellN3G getCellN3G(int type, String name) {
		
		// objectname
		if (type == 1) {
			for (CellN3G item: BTS_3G) {
				if (item.getObjectname().equals(name)) {
					return item;
				}
			}
		} 
		// bts_objectname
		else {
			for (CellN3G item: BTS_3G) {
				if (item.getBtsObjectname().equals(name)) {
					return item;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Get alarm config
	 * 
	 * @param networkType
	 * @param blockColumnName
	 * @param blockValue
	 * @return
	 */
	public static AlarmType getAlarmConfig(String networkType, String blockColumnName, String blockValue) {
		
		try {
			if (networkType.equals("2G")) {
				for (AlarmType item: ALARM_CONFIG_2G) {
					if (item.getBlockColumnName().equals(blockColumnName)
							&& item.getBlockValue().contains(blockValue))
						return item;
						
				}
			} else if (networkType.equals("3G")) {
				for (AlarmType item: ALARM_CONFIG_3G) {
					if (item.getBlockColumnName().equals(blockColumnName)
							&& item.getBlockValue().contains(blockValue))
						return item;
						
				}
			}
		} catch (Exception e) {
			logger.error("Exception getAlarmConfig() : ", e);
			return null;
		}
		
		return null;
	}
	
	/**
	 * Insert alarm to database
	 * 
	 * @param structAlarm
	 */
	public static void insertAlarm(StructAlarm structAlarm) {
		insertAlarm(1, structAlarm);			// db 1
		
		if (databaseNo > 1) {
			insertAlarm(2, structAlarm);		// db 2
		}
	}
	
	/**
	 * Insert alarm to database
	 * @param dbNo
	 * @param structAlarm
	 */
	public static void insertAlarm(int dbNo, StructAlarm structAlarm) {
		String insertQuery = "{call PK_AL_CORE_NSN.pr_insert_NSN_FW(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection(dbNo);
			stmt = conn.prepareStatement(insertQuery);
			stmt.setQueryTimeout(queryTimeout);
			
			stmt.setString(1, structAlarm.cellid);
			stmt.setString(2, structAlarm.alarmType);
			stmt.setString(3, structAlarm.alarmClass);
			stmt.setString(4, structAlarm.alarmName);
			stmt.setString(5, structAlarm.site);
			stmt.setString(6, structAlarm.bscId);
			stmt.setString(7, structAlarm.alarmInfo);
			stmt.setString(8, structAlarm.alarmNumber);
			stmt.setString(9, structAlarm.alarmId);
			stmt.setString(10, structAlarm.alarmStatus);
			stmt.setString(11, structAlarm.intId);
			stmt.setString(12, structAlarm.notifId);
			stmt.setString(13, structAlarm.userInfo);
			stmt.setString(14, structAlarm.node);
			stmt.setString(15, structAlarm.eDate);
			stmt.setString(16, structAlarm.sDate);
			stmt.setString(17, structAlarm.objectName);
			stmt.setString(18, structAlarm.alarmMappingId);
			stmt.setString(19, structAlarm.alarmMappingName);
			stmt.setString(20, structAlarm.isMonitor);
			stmt.setString(21, structAlarm.isSendSms);
			
			stmt.executeUpdate();
			
			
			
		} catch (Exception e) {
			try {
				try {
					stmt.wait(10000);//wait 5s
				} catch (InterruptedException e1) {  
				}
				stmt.executeUpdate();//update repeat 1 
				
			} catch (SQLException e1) { 
				try {
					try {
						stmt.wait(10000);//wait 5s
						
					} catch (InterruptedException e11) {  
					}
					stmt.executeUpdate();//update repeat 2
				} catch (SQLException e2) { 
					logger.warn("{}", dbNo + " " + structAlarm);				// queue
				}
			}
			
			
			logger.error("insertAlarm({}) error", structAlarm, e);		// error
		} finally {
            try { 
            	if(stmt != null)
            		stmt.close();
            	if(conn != null)
            		conn.close();
            } catch (Exception e) {
            	logger.error("close({}) error", "Connectiton", e);
            }
        }
	}
}
