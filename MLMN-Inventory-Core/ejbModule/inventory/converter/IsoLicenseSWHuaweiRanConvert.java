package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.core.IN_Global;
import inventory.util.IN_Setting;
import inventory.util.IN_Util;
import inventory.util.exceptions.IN_ConvertException;

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



/******************************************************************************
NAME:       IsoLicenseSWHuaweiRanConvert
PURPOSE:	Convert data license software huawei ran

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        27/08/2013       AnhNT      		
1.1		   16/11/2013		AnhNT		Convert lay them thong tin ne. Du lieu cua file duoc ghi vao 2 bang tho:
										Bang 1. Chua thong tin Day, Ne, License code.
										Bang 2. Chua thong tin Day, Ne va cac thong tin chi tiet cua license. 
******************************************************************************/
public class IsoLicenseSWHuaweiRanConvert extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(IsoLicenseSWHuaweiRanConvert.class.getName());
	 
	String sep = ";";
	private int fileId = -1;
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(IN_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(IN_Setting.FILE_ID_KEY));
		}
		
		BufferedReader reader = null;
		
		//Chua thong tin ma license
		BufferedWriter writerLicense = null; 	
		//Chua thong tin chi tiet cua license
		BufferedWriter writerData = null; 	
		
		//Bat dau khoi chua thong tin ma license code
		boolean blockLicense = false;
		//Bat dau khoi chua thong tin chi tiet license
		boolean blockData = false;
		//Danh dau cot head
		boolean blockHead = false;
		
		//Dem so dong trong tung khoi
		int count = 0;
		
		//Mang int chua do dai cua tung cot trong dong head
		int[] lengthList = new int[5]; 
		
		String ne ="";
		String licenseCode = "";
		String activeDate = "";
		//Ngay lay du lieu
		String day = ""; 
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			
			// create file writer
			writerLicense = new BufferedWriter(new FileWriter(outFile.getParent()+"/LicenseCode_"+outFile.getName()));
			writerData = new BufferedWriter(new FileWriter(outFile.getParent()+"/LicenseData_"+outFile.getName()));
			
			// writerData header
			writerLicense.write("#VENDOR;INITIALIZE_DATE;NE;LICENSE_CODE;ACTIVE_DATE;NE_TYPE\n");
			writerData.write("#INITIALIZE_DATE;VENDOR;NE_TYPE;NE;OPERATOR_INDEX;OPERATOR_NAME;FEATURE_CODE;FEATURE_NAME;CAPACITY;USAGE;STATUS\n");
			
			// Su dung doc du lieu tung dong de su ly, lay thong tin can thiet
			String strLine = "";
			 
			while ((strLine = reader.readLine()) != null){ 
				if(strLine.length() == 0){
					continue;
				}
				
				//Lay thong tin license code
				
				//Dong chua "Execution succeeded" se lay duoc Ne va Day
				if(strLine.contains("Execution succeeded") && !strLine.contains("RETCODE")){
					if(seach(strLine) != -1){
						//Vi tri cua ky tu BEL
						int t = seach(strLine);
						ne = strLine.substring(t+1,t+8);  
						day = strLine.substring(strLine.length()-19); 
						blockData = false;
						blockLicense = false;
						count = 0; 
					}
				}
				
				count++;
				
				if(count == 3 && strLine.contains("CHK DATA2LIC")){
					blockLicense = true;
				}
				
				if(count == 3 && strLine.contains("DSP LICUSAGE")){
					blockData = true;
				}
				
				//Khoi lay thong tin ma license 
				if(blockLicense){
					if(strLine.contains("LicenseSerialNo")){
						licenseCode = strLine.substring(strLine.indexOf("=")+1).trim();
						continue;
					} 
					
					if(strLine.contains("CreatedTime")){
						activeDate = strLine.substring(strLine.indexOf("=")+1).trim();
						writerLicense.write("HUAWEI;"+day+sep+ne+sep+licenseCode+sep+activeDate+";BSC");
						writerLicense.newLine();
						blockLicense = false;
					}
				}
				
				//Khoi lay thong tin chi tiet license
				if(blockData){
					//Dong co chua "Cn Operator Index" la head
					if(strLine.contains("Cn Operator Index")){ 
						lengthList = new int[5];
						lengthList[0] = strLine.substring(0, strLine.indexOf("Operator Name")).length();
						lengthList[1] = lengthList[0]+strLine.substring(strLine.indexOf("Operator Name"), strLine.indexOf("License Identifier")).length();
						lengthList[2] = lengthList[1]+strLine.substring(strLine.indexOf("License Identifier"), strLine.indexOf("License Item")).length();
						lengthList[3] = lengthList[2]+strLine.substring(strLine.indexOf("License Item"), strLine.indexOf("Allocated")).length();
						lengthList[4] = lengthList[3]+strLine.substring(strLine.indexOf("Allocated"), strLine.indexOf("Usage")).length();
						blockHead = true; 
						continue;
					}
					
					if(blockHead){
						if(strLine.contains("To be continued") || strLine.contains("Number of results")){
							blockHead = false;
							continue;
						}
						
						try{
							String operatorIndex = strLine.substring(0, lengthList[0]).trim();
							String operatorName = strLine.substring(lengthList[0], lengthList[1]).trim();
							String featureCode = strLine.substring(lengthList[1], lengthList[2]).trim();
							String featureName = strLine.substring(lengthList[2], lengthList[3]).trim();
							String capacity = strLine.substring(lengthList[3], lengthList[4]).trim();
							String usage = strLine.substring(lengthList[4]).trim();
							String status = "";
							
							if(capacity.contains("NULL")){
								status = "OFF";
								capacity = "0";
							}else{
								status = "ON";
							} 
							
							writerData.write(day+sep+"HUAWEI"+sep+"BSC"+sep+ne+sep+operatorIndex+sep+operatorName+sep+featureCode
									+sep+featureName+sep+capacity+sep+usage+sep+status);
							writerData.newLine(); 
						}catch(Exception e){
						}
					}
				}
			}
			
			this.genarateDBRecord("LicenseCode_"+getFileName(outFile.getName()), "H_LICENSE_CODES");
			this.genarateDBRecord("LicenseData_"+getFileName(outFile.getName()), "R_H_ISO_LICENSE_SOFT"); 
			this.updateRecordStatus();
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMS-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					writerData.close();
					writerLicense.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
	} 
	
	private void updateRecordStatus() throws SQLException {
		Connection conn = null;
		try {
			conn = IN_Global.DATA_SOURCE.getConnection();
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
			conn = IN_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();

			String queryStr = "select PATTERN_ID, \n" + "DAY, HOUR, NODE_NAME, MODULE, SERVER_NODE from C_RAW_FILES_MLMN \n" + "where FILE_ID = " + fileId;
			String patternId = "";
			String day = "";
			String hour = "";
			String nodeName = "";
			String module = "";
			String serverNode = "";
			//
			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) {
				patternId = rs.getString("PATTERN_ID");
				day = IN_Util.getTime(rs.getDate("DAY"));
				hour = rs.getString("HOUR");
				nodeName = rs.getString("NODE_NAME");
				module = rs.getString("MODULE");
				serverNode = rs.getString("SERVER_NODE");
				
			}
			if (patternId.length() > 0) {
				queryStr = "insert into C_RAW_FILES_MLMN(FILE_ID,\n" + "PATTERN_ID, FILE_NAME, DAY, HOUR, \n" + "CONVERT_FLAG, IMPORT_FLAG, \n"
						+ "NODE_NAME, RAW_TABLE, MODULE, SERVER_NODE) values(SEQ_CRF.NEXTVAL, " + patternId + ", '" + fileName + "', to_date('" + day + "', '"
						+ IN_Setting.DB_TIME_FORMAT + "'), " + hour + ", 1, 0, '" + nodeName + "', '" + tableName + "','" + module + "', '" + serverNode + "')";

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
	
	//Tim vi tri cua ky tu BEL trong dong du lieu su dung de cat lay ten NE
	private int seach(String strLine){
		int temp = -1;
		for(int i = 0; i < strLine.length(); i++){
			char c = strLine.charAt(i);
			int _temp = (int)c; 
			if(_temp == 7){
				temp = i;
				break;
			}
		}
		return temp;
	} 
}
