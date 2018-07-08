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


public class AlarmLog3GConverter extends STS_BasicConverter {
	
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
/*		BufferedWriter writerStartAlarm = null;
		BufferedWriter writerClearingAlarm = null;*/
		BufferedWriter writerClearingAlarmOF = null;
		BufferedWriter writerStartAlarmOF = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
/*			writerStartAlarm = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarm-3G_"+getFileName(outFile.getName())));
			writerClearingAlarm = new BufferedWriter(new FileWriter(outFile.getParent()+"/ClearingAlarm-3G_"+getFileName(outFile.getName())));*/
			writerClearingAlarmOF = new BufferedWriter(new FileWriter(outFile.getParent()+"/ClearingAlarmOF-3G_"+getFileName(outFile.getName())));
			writerStartAlarmOF = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarmOF-3G_"+getFileName(outFile.getName())));
/*			writerClearingAlarm.write("#DAY,OJB-CLASS,MO,RSITE,ALARM,SLOGAN,ID\n");
			writerStartAlarm.write("#DAY,OJB-CLASS,MO,RSITE,ALARM,SLOGAN,ID\n");*/
			writerStartAlarmOF.write("#ALARMNUMBER,DAY\n");
			writerClearingAlarmOF.write("#ALARMNUMBER,DAY,MO,RSITE,ALARM,SLOGAN\n");
			String strLine = "";
			boolean blockAlarmClearing = false;
			boolean blockStartAlarm = false;
			boolean blockAlamCeasingContent = false; 
			boolean blockAlamStartContent = false;
			int clearing = 0;
			int start = 0;
			while ((strLine = reader.readLine()) != null){
				//Lay du lieu tu khoi (--ALARM CLEARING--....--ALARM END--)
				if(strLine.contains("ALARM CLEARING")){
					blockAlarmClearing = true;
					blockStartAlarm = false;
				}
				if(blockAlarmClearing == true){
					if(!strLine.contains("ALARM END")){
						// LAY DU LIEU ALAM "RADIO X-CEIVER ADMINISTRATION MANAGED OBJECT FAULT"
						if(blockAlarmClearing == true && strLine.contains("RADIO X-CEIVER ADMINISTRATION MANAGED OBJECT FAULT")){
							blockAlamCeasingContent = true;
							clearing = 0;
						}
						if(blockAlamCeasingContent == true){
							if(!strLine.contains("END")){
								clearing++;
								ClearConvertDate(clearing,strLine,writerClearingAlarmOF);
								if(clearing == 9){
									String[] str = strLine.split(" ");
									int j=0;
									for(int i=0; i< str.length; i++){
										if(str[i].length() > 0 ){
											j++;
											if(j>0 && j<4){
												writerClearingAlarmOF.write(str[i].trim()+",");
											}
											if(j==4){
												writerClearingAlarmOF.write(str[i].trim());
											}
										}
									}
									writerClearingAlarmOF.newLine();
								}
							}else{
								blockAlamCeasingContent = false;
							}
						}
					}else{
						blockAlarmClearing = false;
					}
				}
				//Lay du lieu tu khoi (--ALARM FROM--....--ALARM END--)
				if(strLine.contains("ALARM FROM")){
					blockAlarmClearing = false;
					blockStartAlarm = true;
				}
				if(blockStartAlarm == true){
					if(!strLine.contains("ALARM END")){
						// LAY DU LIEU ALAM "RADIO X-CEIVER ADMINISTRATION MANAGED OBJECT FAULT"
						if(blockStartAlarm == true && strLine.contains("RADIO X-CEIVER ADMINISTRATION MANAGED OBJECT FAULT")){
							blockAlamStartContent = true;
							start = 0;
						}
						if(blockAlamStartContent == true){
							if(!strLine.contains("END")){
								start++;
								StartConvertDate(start,strLine,writerStartAlarmOF);
								}
							}else{
								blockAlamStartContent = false;
							}
						}
					}else{
						blockStartAlarm = false;
					}
			}
			this.genarateDBRecord("FromAlarmOF-3G_"+getFileName(outFile.getName()), "R_LOGS_CLEARINGOF_3G");
			this.genarateDBRecord("ClearingAlarmOF-3G_"+getFileName(outFile.getName()), "R_LOGS_CLEARINGOF_3G");
			/*this.genarateDBRecord("StartAlarm-3G_"+getFileName(outFile.getName()), "R_LOGS_ALARM_START_3G");
			this.genarateDBRecord("ClearingAlarm-3G_"+getFileName(outFile.getName()), "R_LOGS_CLEARING_3G");*/
			this.updateRecordStatus();
		}catch (Exception e) {
			throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					/*writerStartAlarm.close();
					writerClearingAlarm.close();*/
					writerClearingAlarmOF.close();
					writerStartAlarmOF.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}

		logger.info("Convert file: " + file.getName() + " success");
	}
	private static void ClearConvertDate(int count,String strLine,BufferedWriter file){
		try{
			if(count == 4){
				String[] str = strLine.split(" ");
				int j=0;
				for(int i=0; i < str.length; i++){
					if(str[i].length() > 0){
						j++;
						String temp = str[i].substring(0,1);
						//lay alam number
						if(j == 4){
							if(temp.equals("0")== true){
								String str1= str[i].substring(1,3);
								file.write(str1+",");
							}
							else
								file.write(str[i]+",");
						}
						//lay ngay thang nam
						if(j==8){
								file.write("20"+str[i].substring(0,2)+"-"+str[i].substring(2,4)+"-"+str[i].substring(4,6)+" ");
						}
						//lay gio phut
						if(j==9){
							file.write(str[i].substring(0,2)+":"+str[i].substring(2,4)+" ");
						}
					}
				}
				file.write(",");	
			}
		}catch(Exception e){
		}
	}
	//Convert file lan 2
	public void convertFile2(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}
		
		BufferedReader reader = null;
		BufferedWriter writerClearingAlarmOF2 = null;
		BufferedWriter writerClearingAlarmOF3 = null;
		BufferedWriter writerStartAlarmOF2 = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerClearingAlarmOF2 = new BufferedWriter(new FileWriter(outFile.getParent()+"/ClearingAlarmOF2-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
			writerStartAlarmOF2 = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarmOF2-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
			writerClearingAlarmOF3 = new BufferedWriter(new FileWriter(outFile.getParent()+"/ClearingAlarmOF3-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
			writerClearingAlarmOF2.write("#ALARMNUMBER,DAY,MO,RSITE,ALARM,SLOGAN\n");
			writerClearingAlarmOF3.write("#ALARMNUMBER,DAY,MO,RSITE,ALARM,SLOGAN\n");
			String strLine = "";
			while ((strLine = reader.readLine()) != null){
				if(strLine.contains("RXOTX-55-4")){
					writerClearingAlarmOF2.write(strLine);
					writerClearingAlarmOF2.newLine();
				}
				if(strLine.contains("RXORX-106")){
					writerClearingAlarmOF3.write(strLine);
					writerClearingAlarmOF3.newLine();
				}
			}
			this.genarateDBRecord("ClearingAlarmOF3-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_CLEARINGOF3_3G");
			this.genarateDBRecord("FromAlarmOF2-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_CLEARINGOF2_3G");
			this.genarateDBRecord("ClearingAlarmOF2-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_CLEARINGOF2_3G");
			this.updateRecordStatus();
		}catch (Exception e) {
			throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					writerClearingAlarmOF2.close();
					writerClearingAlarmOF3.close();
					writerStartAlarmOF2.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}

		logger.info("Convert file: " + file.getName() + " success");
	}
	private static void StartConvertDate(int count,String strLine,BufferedWriter file){
		try{
			if(count == 4){
				String[] str = strLine.split(" ");
				int j=0;
				for(int i=0; i < str.length; i++){
					if(str[i].length() > 0){
						j++;
						String temp = str[i].substring(0,1);
						//lay alam number
						if(j == 3){
							if(temp.equals("0")== true){
								String str1= str[i].substring(1,3);
								file.write(str1+",");
							}
							else
								file.write(str[i]+",");
						}
						//lay ngay thang nam
						if(j==7){
								file.write("20"+str[i].substring(0,2)+"-"+str[i].substring(2,4)+"-"+str[i].substring(4,6)+" ");
						}
						//lay gio phut
						if(j==8){
							file.write(str[i].substring(0,2)+":"+str[i].substring(2,4)+" ");
						}
					}
				}
				file.newLine();	
			}
		}catch(Exception e){
		}
	}
	private void updateRecordStatus() throws SQLException {
		Connection conn = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();
			// update state
			String queryStr = "update /*+NOLOGGING */ C_RAW_FILES_MLMN set \n" + "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" + "where FILE_ID = " + fileId;
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
