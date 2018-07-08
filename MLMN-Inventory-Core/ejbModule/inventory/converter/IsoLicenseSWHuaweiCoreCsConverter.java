package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.core.IN_Global;
import inventory.util.IN_Setting;
import inventory.util.IN_Util;
import inventory.util.exceptions.IN_ConvertException; 
import inventory.utils.IN_DateTools;

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
NAME:       IsoLicenseSWHuaweiCoreCsConverter
PURPOSE:	Convert data license software huawei core cs

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        28/09/2013       AnhNT      		1. Raw file format (R_H_MSC_LICENSE_SW).([0-9]{8}).(*)
1.1		   16/11/2013		AnhNT		Convert lay them thong tin ne. 
										Du lieu cua MSC,MGW duoc ghi vao 2 bang tho:
											Bang 1(H_LICENSE_CODES). Chua thong tin Day, Ne, License code.
											Bang 2(R_H_ISO_LICENSE_SOFT). Chua thong tin Day, Ne va cac thong tin chi tiet cua license. 
******************************************************************************/
public class IsoLicenseSWHuaweiCoreCsConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(IsoLicenseSWHuaweiCoreCsConverter.class.getName());
	 
	private String sep = ";"; 
	private String line = ""; 
	private String status = "";
	private int capacity = 0;
	private String featureName = ""; 
	private int usage = 0;
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
		
		//Chua thong tin license code MGW
		BufferedWriter w_LicenseCodeMgw = null;  
		//Chua thong tin chi tiet MGW
		BufferedWriter w_LicenseDataMgw = null;  
		//Chua thong tin chi tiet license MSC
		BufferedWriter w_LicenseDataMsc = null;  
		
		//Danh dau bat dau khoi thong tin license MSC
		boolean blockMsc = false;
		//Danh dau bat dau khoi thong tin license MGW
		boolean blockMgw = false;
		//Danh dau khoi tra ve chua thong tin license code
		boolean blockLicense = false;
		//Danh dau khoi tra ve chua thong tin chi tiet license
		boolean blockData = false;
		//Danh dau tung khoi con trong khoi thong tin chi tiet license
		boolean block2 = false;
		//Danh dau tung khoi con trong khoi thong tin chi tiet license
		boolean block3 = false;
		
		//Dem so dong cua tung khoi thong tin license
		int count = 0,count1 = 0,count2 = 0;
		
		String licenseCode = ""; 
		String ne = ""; 
		String neType = "";
		String day = "";
		String activeDate = "";
		
		//Mang int chua do dai cua tung cot trong dong head
		int[] lengthList = new int[4]; 
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			
			//Su dung de ghi thong tin license code MGW
			w_LicenseCodeMgw = new BufferedWriter(new FileWriter(outFile.getParent()+"/LicenseCodeMgw_"+outFile.getName()));
			//Su dung de ghi thong tin chi tiet MGW
			w_LicenseDataMgw = new BufferedWriter(new FileWriter(outFile.getParent()+"/LicenseDataMgw_"+outFile.getName()));
			//Su dung de ghi thong tin chi tiet license MSC
			w_LicenseDataMsc = new BufferedWriter(new FileWriter(outFile.getParent()+"/LicenseDataMsc_"+outFile.getName())); 
			
			
			// writer header
			w_LicenseCodeMgw.write("#VENDOR;INITIALIZE_DATE;NE;NE_TYPE;LICENSE_CODE;IS_CURRENT_LICENSE");
			w_LicenseDataMgw.write("#INITIALIZE_DATE;VENDOR;NE_TYPE;NE;FEATURE_NAME;CAPACITY;USAGE;STATUS");
			w_LicenseDataMsc.write("#INITIALIZE_DATE;ACTIVE_DATE;VENDOR;NE_TYPE;NE;LICENSE_CODE;FEATURE_NAME;CAPACITY;STATUS;NAME;VALUE");
			
			// Su dung doc du lieu tung dong de su ly, lay thong tin can thiet
			String strLine = "";
			 
			while ((strLine = reader.readLine()) != null){  
				if(strLine.length() == 0){
					continue;
				}  
				//Dong chua "Operation succeeded" se lay duoc Ne va Day
				if(strLine.contains("Operation succeeded") && !strLine.contains("RETCODE")){
					if(this.seach(strLine) != -1){
						//Vi tri cua ky tu BEL
						int t = this.seach(strLine);
						neType = "MSC";
						try {
							ne = strLine.substring(t+1,t+8);  
							day = strLine.substring(strLine.length()-25,strLine.lastIndexOf("+"));
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						blockMsc = true;
						blockMgw = false;
						count = 0;
						continue;
					}
				} 
				//Dong chua "accomplished" se lay duoc Ne va Day
				if(strLine.contains("accomplished") && !strLine.contains("RETCODE")){
					if(this.seach(strLine) != -1){
						//Vi tri cua ky tu BEL
						int t = this.seach(strLine);
						neType = "MGW";
						try {
							ne = strLine.substring(t+1,t+8);  
							day = strLine.substring(strLine.length()-19);
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						blockMgw = true;
						blockMsc = false;
						count = 0;
						continue;
					}
				} 
				
				//Lay thong tin chi tiet license MSC
				if(blockMsc){
					count++;
					//Chi lay du lieu trong khoi "Current license file basic information"
					if(count == 4 && !strLine.contains("Current license file basic information")){
						blockMsc = false;
						continue;
					} 
					
					//Bat dau lay du lieu tu file
					if(count >= 6){
						try {
							String[] str = strLine.split("=");
							String name = str[0].trim();
							String value = str[1].trim();
							//Lay gia tri license code
							if(name.equalsIgnoreCase("LicenseSerialNo")){
								licenseCode = value;
							} 
							//Lay gia tri creat time
							if(name.equalsIgnoreCase("CreatedTime")){
								activeDate = value;
							}  
							//Ket thuc khoi du lieu = "Number of results"
							if(strLine.contains("Number of results")){
								blockMsc = false; 
								continue;
							}
							//Lay gia tri feature name, status va capacity
							if(IN_DateTools.isValidNumber(value)){
								featureName = name;
								//Convert string to number
								int n_value = Integer.parseInt(value);
								if(n_value == 0){
									status = "OFF"; 
								}else if(n_value == 1){
									status = "ON"; 
								}else if(n_value > 1){
									status = "ON";
									capacity = n_value; 
								}
							}else{
								if(value.equalsIgnoreCase("NA")){
									featureName = name;
									status = "OFF";
								}
							}
							//Ghi du lieu doc duoc vao file luu tru
							line += day+sep+activeDate+sep+"HUAWEI"+sep+neType+sep+ne+sep+licenseCode+sep+featureName+sep+capacity+sep+status+sep+name+sep+value;
							w_LicenseDataMsc.newLine();
							w_LicenseDataMsc.write(line); 
							this.refresh();
						} catch (Exception e) {
							// TODO: handle exception
						}
					} 
				}
				
				//Lay thong tin license code MGW
				if(blockMgw){
					count++; 
					if(count == 2){
						//Danh dau la khoi chua du lieu license code
						if(strLine.contains("LST LICENSE")){
							blockLicense = true;
							blockData = false;
						} 
						//Danh dau la khoi chua du lieu chi tiet cua license code
						if(strLine.contains("DSP LICENSE")){
							blockData = true;
							blockLicense = false;
						} 
					} 
					//Lay thong tin license code
					if(blockLicense){
						if(count == 6){
							lengthList = new int[4];
							lengthList[0] = strLine.substring(0, strLine.indexOf("LicenseFileName")).length();
							lengthList[1] = lengthList[0]+strLine.substring(strLine.indexOf("LicenseFileName"), strLine.indexOf("IsCurrentLicense")).length(); 	
						} 
						if(count >= 7){
							//Ket thuc khoi du lieu = "Number of results"
							if(strLine.contains("Number of results")){ 
								blockLicense = false; 
								continue;
							}
							
							try{
								licenseCode = strLine.substring(lengthList[0], lengthList[1]).trim();
								licenseCode = licenseCode.substring(0, licenseCode.indexOf("."));
								String isCurrentLicense = strLine.substring(lengthList[1]).trim();
								w_LicenseCodeMgw.newLine();
								w_LicenseCodeMgw.write("HUAWEI"+sep+day+sep+ne+sep+neType+sep+licenseCode+sep+isCurrentLicense);
								refresh();
							}catch(Exception e){
								//System.out.println(strLine);
							} 
						}  
					}
					//Lay thong tin chi tiet license
					if(blockData){
						if(strLine.contains("Dynamic resource info") || strLine.contains("Static resource info")){
							block2 = true;
							count1 = 0;
							continue;
						}else if(strLine.contains("Function info")){
							block3 = true;
							count2 = 0;
							continue;
						}
						if(block2){
							count1 ++;
							if(count1 == 2){
								lengthList = new int[4];
								lengthList[0] = strLine.substring(0, strLine.indexOf("Authorized")).length();
								lengthList[1] = lengthList[0]+strLine.substring(strLine.indexOf("Authorized"), strLine.indexOf("Used num")).length(); 	
								lengthList[2] = lengthList[1]+strLine.substring(strLine.indexOf("Used num"), strLine.indexOf("Percent(p.c.)")).length(); 
								lengthList[3] = lengthList[2]+strLine.substring(strLine.indexOf("Percent(p.c.)"), strLine.indexOf("Status")).length(); 
							} 
							if(count1 >= 3){
								//Ket thuc khoi du lieu = "Number of results"
								if(strLine.contains("Number of results")){ 
									block2 = false; 
									continue;
								}
								
								try {
									featureName = strLine.substring(0, lengthList[0]).trim();
									capacity = Integer.parseInt(strLine.substring(lengthList[0], lengthList[1]).trim());
									usage = Integer.parseInt(strLine.substring(lengthList[1], lengthList[2]).trim()); 
									status = strLine.substring(lengthList[3]).trim(); 
									w_LicenseDataMgw.newLine();
									w_LicenseDataMgw.write(day+sep+"HUAWEI"+sep+neType+sep+ne+sep+featureName+sep+capacity+sep+usage+sep+status);
									refresh();
								} catch (Exception e) {
									// TODO: handle exception
								} 
							} 
						}
						
						if(block3){
							count2++;
							if(count == 2){
								lengthList = new int[3];
								lengthList[0] = strLine.substring(0, strLine.indexOf("Authorized")).length();
								lengthList[1] = lengthList[0]+strLine.substring(strLine.indexOf("Authorized"), strLine.indexOf("Currently")).length(); 	
								lengthList[2] = lengthList[1]+strLine.substring(strLine.indexOf("Currently"), strLine.indexOf("Status")).length();  
							} 
							if(count2 >= 3){
								//Ket thuc khoi du lieu = "Number of results"
								if(strLine.contains("Number of results")){ 
									block3 = false; 
									continue;
								}
								try{
									featureName = strLine.substring(0, lengthList[0]).trim();
									status = strLine.substring(lengthList[2]).trim();
									w_LicenseDataMgw.newLine();
									w_LicenseDataMgw.write(day+sep+"HUAWEI"+sep+neType+sep+ne+sep+featureName+sep+capacity+sep+usage+sep+status);
									refresh();
								}catch(Exception e){} 
							} 
						}  
					}
				}
			}
		this.genarateDBRecord("LicenseCodeMgw_"+getFileName(outFile.getName()), "H_LICENSE_CODES");
		this.genarateDBRecord("LicenseDataMgw_"+getFileName(outFile.getName()), "R_H_ISO_LICENSE_SOFT"); 
		this.genarateDBRecord("LicenseDataMsc_"+getFileName(outFile.getName()), "R_H_MSC_ISO_LICENSE_SOFT"); 
		this.updateRecordStatus();
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					w_LicenseDataMsc.close();
					w_LicenseCodeMgw.close();
					w_LicenseDataMgw.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
	} 
	//Refresh lai gia tri mac dinh
	private void refresh(){
		line = "";
		status = "";
		capacity = 0;
		featureName = ""; 
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
