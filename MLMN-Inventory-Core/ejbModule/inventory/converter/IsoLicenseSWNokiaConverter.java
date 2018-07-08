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
NAME:       IsoLicenseSWNokiaConverter
PURPOSE:	Convert data license software nokia 

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        10/01/2013       AnhNT      		1. Raw file format (R_N_BSC_LICENSE_SW).(.*).([0-9]{8}).(*)
											2. Raw file format (R_N_RNC_LICENSE_SW).(.*).([0-9]{8}).(*)
											3. Raw file format (R_N_SGSN_LICENSE_SW).(.*).([0-9]{8}).(*)
											Khi telnet dat dinh dang ten file nhu tren bao gom
											telnet license Nokia BSC, RNC, SGSN.
NOTE: Lenh telnet

Doi voi BSC,RNC,SGSN
<ZW7I:FEA,FULL:FSTATE=OFF;	---> Liet ke nhung license chua kich hoat
<ZW7I:FEA,FULL:FSTATE=ON;	---> Liet ke nhung license da kich hoat,total Capacity , Feature code , Feature name
<ZW7I:LIC,FULL:STATE=LIM;	---> Liet ke ngay license duoc Active Date, License capacity, License Code

Doi voi BSC,RNC
<ZW7I:UCAP,FULL:;			---> Liet ke Feature Usage, phai dua vao Feature code de tim ra Usage

Doi voi SGSN
<ZW7I:ZMMN:;				---> Lay thong tin Usage cua Feature name = “SGSN Total Attach capacity”
								nhung Feature khac khong co Usage 
******************************************************************************/ 
public class IsoLicenseSWNokiaConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(IsoLicenseSWNokiaConverter.class.getName());
	
	private String sep = ";";
	private int fileId = -1;
	private String line = "";
	private String _FEATURE_CODE = "";
	private String _FEATURE_NAME = "";
	private String _FEATURE_STATE = "";
	private String _FEATURE_CAPACITY = "";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(IN_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(IN_Setting.FILE_ID_KEY));
		}
		
		BufferedReader reader = null;
		BufferedWriter writer = null; 
		BufferedWriter writerActive = null; 
		BufferedWriter writerUsage = null; 
		 
		String _DAY = "";
		String _NE = "";
		String _NE_TYPE = "";
		String _VENDOR = "NOKIA SIEMENS";
		
		//Danh dau bat dau khoi du lieu tra ve cua lenh FEA,FULL:FSTATE=ON;
		boolean block1 = false;
		//Danh dau bat dau khoi du lieu tra ve cua lenh FEA,FULL:FSTATE=OFF;
		boolean block2 = false;
		//Danh dau bat dau khoi du lieu tra ve cua lenh ZW7I:LIC,FULL:STATE=LIM;
		boolean block3 = false;
		//Danh dau bat dau khoi du lieu tra ve cua lenh UCAP,FULL:;
		boolean block4 = false; 
		//Danh dau bat dau khoi du lieu tra ve cua lenh ZMMN:;
		boolean block5 = false; 
		//Danh dau khoi du lieu can phan tich(bat dau va ket thuc = "------")
		boolean block6 = false;
		int count = 0,count3 = 0;
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			 
			writer = new BufferedWriter(new FileWriter(outFile.getParent()+"/LICENSE_"+getFileName(outFile.getName()))); 
			writer.write("#DAY;VENDOR;NE_TYPE;NE;FEATURE_CODE;FEATURE_NAME;FEATURE_STATE;FEATURE_CAPACITY\n"); 
			
			writerActive = new BufferedWriter(new FileWriter(outFile.getParent()+"/ACTIVE_"+getFileName(outFile.getName()))); 
			writerActive.write("#DAY;VENDOR;NE;LICENCE_CODE;LICENCE_NAME;LICENCE_FILE_NAME;SERIAL_NUMBER;" +
					"ORDER_IDENTIFIER;CUSTOMER_ID;CUSTOMER_NAME;TARGET_NE_TYPE;TARGET_ID;LICENCE_STATE;" +
					"START_DATE;EXPIRATION_WARNING_PERIOD;LICENCE_CAPACITY\n"); 
			
			writerUsage = new BufferedWriter(new FileWriter(outFile.getParent()+"/USAGE_"+getFileName(outFile.getName()))); 
			writerUsage.write("#DAY;VENDOR;NE_TYPE;NE;FEATURE_CODE;USAGE\n"); 
			
			String strLine = ""; 
			while ((strLine = reader.readLine()) != null){ 
				if(strLine.length() == 0){
					continue;
				}
				if(strLine.contains("ZW7I:FEA,FULL:FSTATE=ON;")){
					count = 0;
					block1 = true;
					continue;
				}else if(strLine.contains("ZW7I:FEA,FULL:FSTATE=OFF;")){
					count = 0;
					block2 = true;
					continue;
				}else if(strLine.contains("ZW7I:LIC,FULL:STATE=LIM;")){
					count = 0;
					block3 = true;
					continue;
				}else if(strLine.contains("ZW7I:UCAP,FULL:;")){
					count = 0;
					block4 = true;
					continue;
				}else if(strLine.contains("ZMMN:;")){
					count = 0;
					block5 = true;
					continue;
				}
				
				//Lay thong tin du lieu license da kich hoat
				if(block1 == true){
					count++;
					if(count < 2){
						continue;
					}
					// Lay thong tin NE va DAY
					if(count == 2){
						while(strLine.indexOf("  ") > 0){
							strLine = strLine.replace("  ", " ");
						}
						
						String[] str = strLine.split(" ");
						_NE = str[1];
						_DAY = str[3]+" "+str[4];
						
						if(str[0].contains("FlexiBSC")){
							_NE_TYPE = "BSC";
						}else if(str[0].contains("RNC")){
							_NE_TYPE = "RNC";
						}else if(str[0].contains("SGSN")){
							_NE_TYPE = "SGSN";
						}
						continue;
					}
					
					if((strLine.contains("---") && _FEATURE_CODE.equals("") == false)){
						line +=_DAY+sep+_VENDOR+sep+_NE_TYPE+sep+_NE+sep+_FEATURE_CODE+sep+_FEATURE_NAME+sep+_FEATURE_STATE+sep+_FEATURE_CAPACITY;
						writer.write(line);
						writer.newLine();
						this.reset();
						continue;
					}
					
					if(strLine.contains("FEATURE CODE")){
						_FEATURE_CODE = strLine.substring(strLine.lastIndexOf("..")+2, strLine.length()).trim(); 
						continue;
					}else if(strLine.contains("FEATURE NAME")){
						_FEATURE_NAME = strLine.substring(strLine.lastIndexOf("..")+2, strLine.length()).trim(); 
						continue;
					}else if(strLine.contains("FEATURE STATE")){
						_FEATURE_STATE = strLine.substring(strLine.lastIndexOf("..")+2, strLine.length()).trim(); 
						continue;
					}else if(strLine.contains("FEATURE CAPACITY")){
						_FEATURE_CAPACITY = strLine.substring(strLine.lastIndexOf("..")+2, strLine.length()).trim(); 
						continue;
					}
					
					if(strLine.contains("COMMAND EXECUTED")){
						if(_FEATURE_CODE.equals("") == false){
							line +=_DAY+sep+_VENDOR+sep+_NE_TYPE+sep+_NE+sep+_FEATURE_CODE+sep+_FEATURE_NAME+sep+_FEATURE_STATE+sep+_FEATURE_CAPACITY;
							writer.write(line);
							writer.newLine();
						}
						this.reset();
						block1 = false;
						continue; 
					}
				}
				
				//Lay thong tin du lieu license chua kich hoat 
				if(block2 == true){
					count++;
					if(count < 2){
						continue;
					}
					// Lay thong tin NE va DAY
					if(count == 2){
						while(strLine.indexOf("  ") > 0){
							strLine = strLine.replace("  ", " ");
						}
						
						String[] str = strLine.split(" ");
						_NE = str[1];
						_DAY = str[3]+" "+str[4];
						
						if(str[0].contains("FlexiBSC")){
							_NE_TYPE = "BSC";
						}else if(str[0].contains("RNC")){
							_NE_TYPE = "RNC";
						}else if(str[0].contains("SGSN")){
							_NE_TYPE = "SGSN";
						}
						continue;
					}
					
					if((strLine.contains("---") && _FEATURE_CODE.equals("") == false)){
						line +=_DAY+sep+_VENDOR+sep+_NE_TYPE+sep+_NE+sep+_FEATURE_CODE+sep+_FEATURE_NAME+sep+_FEATURE_STATE+sep+_FEATURE_CAPACITY;
						writer.write(line);
						writer.newLine();
						this.reset();
						continue;
					}
					
					if(strLine.contains("FEATURE CODE")){
						_FEATURE_CODE = strLine.substring(strLine.lastIndexOf("..")+2, strLine.length()).trim();
						continue;
					}else if(strLine.contains("FEATURE NAME")){
						_FEATURE_NAME = strLine.substring(strLine.lastIndexOf("..")+2, strLine.length()).trim();
						continue;
					}else if(strLine.contains("FEATURE STATE")){
						_FEATURE_STATE = strLine.substring(strLine.lastIndexOf("..")+2, strLine.length()).trim();
						continue;
					}else if(strLine.contains("FEATURE CAPACITY")){
						_FEATURE_CAPACITY = strLine.substring(strLine.lastIndexOf("..")+2, strLine.length()).trim();
						continue;
					}
					
					if(strLine.contains("COMMAND EXECUTED")){
						if(_FEATURE_CODE.equals("") == false){
							line +=_DAY+sep+_VENDOR+sep+_NE_TYPE+sep+_NE+sep+_FEATURE_CODE+sep+_FEATURE_NAME+sep+_FEATURE_STATE+sep+_FEATURE_CAPACITY;
							writer.write(line);
							writer.newLine();
						}
						
						this.reset();
						block2 = false;
						continue; 
					}
				}
				
				// Lay thong tin ngay active date
				if(block3 == true){
					count++;
					if(count < 2){
						continue;
					}
					// Lay thong tin NE va DAY
					if(count == 2){
						while(strLine.indexOf("  ") > 0){
							strLine = strLine.replace("  ", " ");
						}
						
						String[] str = strLine.split(" ");
						_NE = str[1];
						_DAY = str[3]+" "+str[4];
						continue;
					} 
					
					if(strLine.contains("---")){
						count3 = 0; 
						block6 = true;
						continue;
					}
					if(block6 == true){
						count3++;
						if(count3 > 0 && count3 <= 11){
							line += strLine.substring(strLine.lastIndexOf("..")+2, strLine.length()).trim()+sep;
							continue;
						}
						if(count3 == 12){
							line += strLine.substring(strLine.lastIndexOf(".")+2, strLine.length()).trim()+sep;
							continue;
						}
						if(count3 == 13){
							line += strLine.substring(strLine.lastIndexOf("..")+2, strLine.length()).trim();
							writerActive.write(_DAY+sep+_VENDOR+sep+_NE+sep+line);
							writerActive.newLine();
							line = "";
						}
						if(strLine.contains("COMMAND EXECUTED")){
							block3 = false;
							block6 = false;
						}
					}
				}
				
				// Lay thong tin Usage BSC/RNC
				if(block4 == true){
					count++;
					if(count < 2 || strLine.contains("SUCCESS")){
						continue;
					}
					// Lay thong tin NE va DAY
					if(count == 2){
						while(strLine.indexOf("  ") > 0){
							strLine = strLine.replace("  ", " ");
						}
						
						String[] str = strLine.split(" ");
						_NE = str[1];
						_DAY = str[3]+" "+str[4]; 
						
						if(str[0].contains("FlexiBSC")){
							_NE_TYPE = "BSC";
						}else if(str[0].contains("RNC")){
							_NE_TYPE = "RNC";
						}else if(str[0].contains("SGSN")){
							_NE_TYPE = "SGSN";
						}
						continue;
					}  
					if(strLine.contains("---")){  
						block6 = true;
						continue;
					} 
					if(strLine.contains("COMMAND EXECUTED")){
						block4 = false;
						block6 = false;
					}
					if(block6 == true){
						line = "";
						strLine = strLine.trim();
						//System.out.println(strLine);
						while(strLine.indexOf("  ") > 0){
							strLine = strLine.replace("  ", " ");
						}
						String[] str = strLine.split(" ");
						try{
							line += _DAY+sep+_VENDOR+sep+_NE_TYPE+sep+_NE+sep+str[0].trim()+sep+str[1].trim();
						}catch(Exception e){ 
						}
						writerUsage.write(line);
						writerUsage.newLine();
					}  
				}
				
				//Lay thong tin Usage SGSN
				if(block5 == true){
					count++;
					if(count < 2 || strLine.contains("SUCCESS")){
						continue;
					}
					// Lay thong tin NE va DAY
					if(count == 2){
						while(strLine.indexOf("  ") > 0){
							strLine = strLine.replace("  ", " ");
						}
						
						String[] str = strLine.split(" ");
						_NE = str[1];
						_DAY = str[2]+" "+str[3];
						
						if(str[0].contains("FlexiBSC")){
							_NE_TYPE = "BSC";
						}else if(str[0].contains("RNC")){
							_NE_TYPE = "RNC";
						}else if(str[0].contains("SGSN")){
							_NE_TYPE = "SGSN";
						}
						continue;
					}  
					if(strLine.contains("---")){
						block6 = true;
						continue;
					} 
					if(strLine.contains("COMMAND EXECUTED")){
						block5 = false;
						block6 = false;
					}
					if(block6 == true){
						line = "";
						strLine = strLine.trim();
						while(strLine.indexOf("  ") > 0){
							strLine = strLine.replace("  ", " ");
						}
						String[] str = strLine.split(" ");
						line += _DAY+sep+_VENDOR+sep+_NE_TYPE+sep+_NE+sep+""+sep+str[1].trim();
						writerUsage.write(line);
						writerUsage.newLine();
					}
				}
			} 
			this.genarateDBRecord("LICENSE_"+getFileName(outFile.getName()), "ISO_LICENSE_SOFT");
			this.genarateDBRecord("ACTIVE_"+getFileName(outFile.getName()), "R_N_ACTIVE_LICENSE");
			this.genarateDBRecord("USAGE_"+getFileName(outFile.getName()), "R_N_USAGE_LICENSE");
			this.updateRecordStatus();
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					writer.close();
					writerUsage.close();
					writerActive.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
	} 
	
	private void reset(){
		line = "";
		_FEATURE_CODE = "";
		_FEATURE_NAME = "";
		_FEATURE_STATE = "";
		_FEATURE_CAPACITY = ""; 
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
}
