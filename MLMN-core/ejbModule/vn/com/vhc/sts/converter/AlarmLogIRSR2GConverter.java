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


public class AlarmLogIRSR2GConverter extends STS_BasicConverter {

	private static final Logger logger = Logger
			.getLogger(AlarmLogIRSR2GConverter.class.getName());
	
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
		BufferedWriter writerCeasingAlarm = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerStartAlarm = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarm-2G_"+getFileName(outFile.getName())));
			System.out.print(outFile.getParent()+"/"+getFileName(outFile.getName()));
			writerCeasingAlarm = new BufferedWriter(new FileWriter(outFile.getParent()+"/CeasingAlarm-2G_"+getFileName(outFile.getName())));
			writerStartAlarm.write("#BSCID,START_TIME,CELLID\n");
			writerCeasingAlarm.write("#BSCID,CEASING_TIME,CELLID\n");
			String strLine = "";
			String tmpStrStartAlarm ="";
			String tmpStrAlarmCeasing ="";
			boolean blockAlarmList = false;
			boolean blockAlarmCeasing = false;
			boolean blockStartAlarm = false;
			boolean blockAlamCeasingContent = false;
			boolean blockStartAlarmContent = false;
			int start = 0 ;
			int ceasing = 0;
			while ((strLine = reader.readLine()) != null) {
				if (strLine.contains("*** ALARM CEASING")) {
					tmpStrAlarmCeasing = strLine;
					blockAlarmCeasing = true;
					blockStartAlarm = false;
				}
				if (blockAlarmCeasing == true){
					if (!strLine.equals("END")) {
						if (blockAlarmCeasing == true && strLine.equals("CELL LOGICAL CHANNEL AVAILABILITY SUPERVISION")) {
							String [] str = tmpStrAlarmCeasing.split(" ");
							writerCeasingAlarm.write(str[5].substring(1)+","+"20"+str[7]+" "+str[8]+",");
							blockAlamCeasingContent = true;
							ceasing = 0;
						}
						else { 
							ceasing ++;
							if (blockAlamCeasingContent == true) {
								if (ceasing == 2) {
									String[] str = strLine.split(" ");
									writerCeasingAlarm.write(str[0]+"\n");
								}
							}
						}
					}else {
						blockAlarmCeasing = false;
						blockAlamCeasingContent = false;
					}
				}
				if (strLine.equals("ALARM LIST")) {
					blockAlarmList = true;
					blockAlarmCeasing = false;
				}
				if (blockAlarmList == true) {
					if (!strLine.equals("END")) {
						if (strLine.contains("A1/APT")||strLine.contains("A2/APT")) {
							tmpStrStartAlarm = strLine;
							blockStartAlarm = true;
						}
						else {
							if (blockStartAlarm == true && strLine.equals("CELL LOGICAL CHANNEL AVAILABILITY SUPERVISION")) {
								String [] str = tmpStrStartAlarm.split(" ");
								writerStartAlarm.write(str[1].substring(1)+","+"20"+str[4]+" "+str[7]+",");
								blockStartAlarmContent = true;
								blockStartAlarm = false;
								start =0;
							}else {
								start ++;
								if (strLine.equals("")) blockStartAlarmContent = false;
								if (blockStartAlarmContent == true) {
									if (start == 2) {
										String[] str = strLine.split(" ");
										writerStartAlarm.write(str[0]+"\n");
									}
								}
							}
						}
					} else {
						blockAlarmList = false;
					}
				}
			}
			this.genarateDBRecord("StartAlarm-2G_"+getFileName(outFile.getName()), "R_IRSR_ALARM_START_2G");
			this.genarateDBRecord("CeasingAlarm-2G_"+getFileName(outFile.getName()), "R_IRSR_ALARM_CEASING_2G");
			this.updateRecordStatus();
		}catch (Exception e) {
			throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					writerStartAlarm.close();
					writerCeasingAlarm.close();
				} catch (IOException e) {
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
