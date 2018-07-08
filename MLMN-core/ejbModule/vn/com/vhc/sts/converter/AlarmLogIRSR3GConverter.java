package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;


public class AlarmLogIRSR3GConverter extends STS_BasicConverter {
	
	private static final Logger logger = Logger
			.getLogger(AlarmLogIRSR3GConverter.class.getName());
	
	private int fileId = -1;
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}
		
		BufferedReader reader = null;
		BufferedWriter writerStartAlarm = null;
		BufferedWriter writerClearingAlarm = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerStartAlarm = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarm-3G_"+getFileName(outFile.getName())));
			writerClearingAlarm = new BufferedWriter(new FileWriter(outFile.getParent()+"/ClearingAlarm-3G_"+getFileName(outFile.getName())));
			writerStartAlarm.write("#START_TIME,BSCID,CELLID\n");
			writerClearingAlarm.write("#CLEARING_TIME,BSCID,CELLID\n");
			String strLine = "";
			boolean blockAlarmClearing = false;
			boolean blockStartAlarm = false;
			int start = 0;
			int clearing = 0;
			while ((strLine = reader.readLine()) != null) {
				if (strLine.contains("ALARM CLEARING FROM") && strLine.contains("UtranCell=")&& strLine.substring(strLine.lastIndexOf("=")-9, strLine.lastIndexOf("=")).equals("UtranCell")) {
					blockAlarmClearing = true;
					blockStartAlarm = false;
					clearing = 0;
				}
				if (blockAlarmClearing == true){
					if (!strLine.contains("ALARM END")) {
						clearing++;
						if (clearing == 2) {
							String[] str = strLine.split(" ");
							int  j = 0;
							for (int i = 0; i <str.length; i++) {
								if (str[i].length() >0) {
									j++;
									if (j == 3 || j== 4)
										writerClearingAlarm.write(str[i].trim()+" ");
								}
							}
							writerClearingAlarm.write(",");
						}
						if (clearing == 3) {
							String[] str = strLine.split(":");
							int j = 0;
							for (int i = 0; i <str.length; i++) {
								if (str[i].length() >0) {
									j ++;
									if (j == 2) {
										String[] strNode = str[i].split(","); 
										writerClearingAlarm.write(strNode[1].trim().substring(strNode[1].trim().lastIndexOf("=")+1,strNode[1].length())+",");
										writerClearingAlarm.write(str[i].trim().substring(str[i].trim().lastIndexOf("=")+1,str[i].trim().length())+"\n");
									}
								}
							}
						}
					}else {
						blockAlarmClearing = false;
					}
				}
				if (strLine.contains("ALARM FROM") && strLine.contains("UtranCell=")&& strLine.substring(strLine.lastIndexOf("=")-9, strLine.lastIndexOf("=")).equals("UtranCell")) {
					blockStartAlarm = true;
					blockAlarmClearing = false;
					start = 0;
				}
				if (blockStartAlarm == true) {
					if (!strLine.contains("ALARM END")) {
						start++;
						if (start == 2) {
							String[] str = strLine.split(" ");
							int  j = 0;
							for (int i = 0; i <str.length; i++) {
								if (str[i].length() >0) {
									j++;
									if (j == 3 || j== 4)
										writerStartAlarm.write(str[i].trim()+" ");
								}
							}
							writerStartAlarm.write(",");
						}
						if (start == 3) {
							String[] str = strLine.split(":");
							int j = 0;
							for (int i = 0; i <str.length; i++) {
								if (str[i].length() >0) {
									j ++;
									if (j == 2) {
										String[] strNode = str[i].split(","); 
										writerStartAlarm.write(strNode[1].trim().substring(strNode[1].trim().lastIndexOf("=")+1,strNode[1].length())+",");
										writerStartAlarm.write(str[i].trim().substring(str[i].trim().lastIndexOf("=")+1,str[i].trim().length())+"\n");
									}
								}
							}
						}
					} else {
						blockStartAlarm = false;
					}
				}
			}

			this.genarateDBRecord("StartAlarm-3G_"+getFileName(outFile.getName()), "R_IRSR_ALARM_START_3G");
			this.genarateDBRecord("ClearingAlarm-3G_"+getFileName(outFile.getName()), "R_IRSR_ALARM_CLEARING_3G");
			this.updateRecordStatus();
		}catch (Exception e) {
			throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					writerStartAlarm.close();
					writerClearingAlarm.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}

		logger.info("Convert file: " + file.getName() + " success");
	}
	
	private void updateRecordStatus() throws SQLException {
		Connection conn = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();
			// update state
			String queryStr = "update /*+ NOLOGGING */ C_RAW_FILES_MLMN set \n" + "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" + "where FILE_ID = " + fileId;
			st.execute(queryStr);
			st.close();
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
	
	private void genarateDBRecord(String fileName, String tableName) throws SQLException {
		Connection conn = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();

			String queryStr = "select PATTERN_ID, \n" + "DAY, HOUR, NODE_NAME, SERVER_NODE from C_RAW_FILES_MLMN \n" + "where FILE_ID = " + fileId;
			String patternId = "";
			String day = "";
			String hour = "";
			String nodeName = "";
			String serverNode = "";
			//
			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) {
				patternId = rs.getString("PATTERN_ID");
				day = STS_Util.getTime(rs.getDate("DAY"));
				hour = rs.getString("HOUR");
				nodeName = rs.getString("NODE_NAME");
				serverNode = rs.getString("SERVER_NODE");
			}
			if (patternId.length() > 0) {
				queryStr = "insert /*+ NOLOGGING */ into C_RAW_FILES_MLMN(FILE_ID,\n" + "PATTERN_ID, FILE_NAME, DAY, HOUR, \n" + "CONVERT_FLAG, IMPORT_FLAG, \n"
						+ "NODE_NAME, RAW_TABLE, SERVER_NODE) values(SEQ_CRF.NEXTVAL, " + patternId + ", '" + fileName + "', to_date('" + day + "', '"
						+ STS_Setting.DB_TIME_FORMAT + "'), " + hour + ", 1, 0, '" + nodeName + "', '" + tableName + "', '" + serverNode + "')";

				st.execute(queryStr);
			}

			rs.close();
			st.close();
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
}
