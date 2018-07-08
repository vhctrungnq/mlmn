package vn.com.vhc.alarm.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

/**
 * @author AnhNT
 * @Update by: ThangPX_26032014
 * Convert file du lieu History Alcater 2G
 * */ 
public class AlarmHistoryHuawei2GConvert extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmHistoryHuawei2GConvert.class.getName());
	private String vendor = "HUAWEI";
	private String sep = ";";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 	
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			wAlarmLog.write("#ATYPE;VENDOR;NE_SN;SN;ANETWORK;NE_TYPE;OBJ_TYPE;SITE;ADD_BOARD;CELL;CELL_INDEX;CGI;NE_NAME;ALARM_ID;ALARM_NAME;ALARM_CLASS;" +
					"ACK_STATUS;CLR_STATUS;EVENT_TYPE;SDATE;EDATE;ACK_TIME;ALARM_INFO;OPERATOR;CABINET_NO;SUBRACK;SLOT;PORT_NO;BOARD_TYPE;BOARD_NO;SITE_NO;" +
					"SITE_TYPE;PASS_NO;CELL_NO;RACK;BOX_NO\n");
			
			boolean blockAlarm = false;//Danh dau bat dau khoi Alarm 
			String strLine = ""; 
			String line="";
			String strSite = "";
			String addBoard = "",strObjName = "",strType = "";
			String strCell= "",CellIndex = "",CGI = "",strObjType="", ackStatus="", clrStatus="";
			String strClearTime = "",strAckTime = "", strOperator = "", strLocation = "",
					 strNeName="",strNeType="",strAlarmID="",strAlarmName="",
					 strSeverity="",strEventType="",strOccurtime="",network = "",strSn="", strNeSn="",
					strCabinet = "", strSubrack = "", strSlot ="", strPort="", strBoardType="", strBoardNo = "",
					strSiteType = "", strSiteNo = "", strPassNo="", strCellNo="", strRackNo="", strBoxNo = "";
			while ((strLine = reader.readLine()) != null){
				if(strLine.toString().trim().equalsIgnoreCase("<+++>")){
					blockAlarm = true; 
				}
				
				if(blockAlarm == true){
					if(!strLine.toString().trim().contains("<--->")){
						 if(strLine.contains("ObjName")){
							strObjName = subStrLine(strLine);
						}else if(strLine.contains("ObjType")){
							strObjType = subStrLine(strLine);
							
							if(strObjType.equalsIgnoreCase("BTS")){
								strSite = strObjName;
							}else if(strObjType.equalsIgnoreCase("Board")){
								addBoard = strObjName;
							}else if(strObjType.equalsIgnoreCase("Cell")){
								String[] str1 = strObjName.split(",");
								strCell = str1[0].substring(str1[0].indexOf("=")+1,str1[0].length());
								CellIndex = str1[1].substring(str1[1].indexOf("=")+1,str1[1].length());
								CGI = str1[2].substring(str1[2].indexOf("=")+1,str1[2].length());
								strSite = strCell.substring(0,6);
							}
						} 
						else if(strLine.contains("NeSn  =")){
							strSn = subStrLine(strLine);
						}
						else if(strLine.contains("Sn  =")){
							strNeSn = subStrLine(strLine);
						}
						else if(strLine.contains("NeName  =")){
							strNeName = subStrLine(strLine);
						}else if(strLine.contains("NeType  =")){
							strNeType = subStrLine(strLine);
						}else if(strLine.contains("AlarmID  =")){
							strAlarmID = subStrLine(strLine);
						}else if(strLine.contains("AlarmName  =")){
							strAlarmName = subStrLine(strLine);
						}else if(strLine.contains("Severity  =")){
							strSeverity = subStrLine(strLine);
							if(strSeverity.equalsIgnoreCase("critical")){
								strSeverity = "A1";
							}else if(strSeverity.equalsIgnoreCase("major")){
								strSeverity = "A2";
							}else if(strSeverity.equalsIgnoreCase("minor")){
								strSeverity = "A3";
							}else{
								strSeverity = "Indeterminate";
							}
						}else if(strLine.contains("State  =")){
							String str = subStrLine(strLine);
							try{
								ackStatus = str.substring(0, str.indexOf("&")-2);
								clrStatus = str.substring(str.indexOf("&")+2, str.length());
							}catch(Exception e){
								ackStatus = str.substring(0, str.indexOf(" ")-1);
							}
							
						}else if(strLine.contains("EventType  =")){
							strEventType = subStrLine(strLine);
						}else if(strLine.contains("Occurtime  =")){
							strOccurtime = subStrLine(strLine);
						}else if(strLine.contains("ClearTime  =")){
							strClearTime = subStrLine(strLine);
						}else if(strLine.contains("AckTime  =")){
							strAckTime = subStrLine(strLine);
						}else if(strLine.contains("Location  =")){
							strLocation = subStrLine(strLine);
							 
								String[] str = strLocation.split(",");
						 
								for(int i = 0; i < str.length ; i++){
									 
									if(str[i].contains("BTS Name")) 
										strSite = subStrLine(str[i]); 
									if(str[i].contains("Cabinet No"))
										strCabinet = subStrLine(str[i]); 
									if(str[i].contains("Subrack No"))
										strSubrack = subStrLine(str[i]); 
									if(str[i].contains("Slot No"))
										strSlot = subStrLine(str[i]); 
									if(str[i].contains("Port No")||str[i].contains("SubLink No."))
										strPort = subStrLine(str[i]); 
									if(str[i].contains("Board Type"))
										strBoardType = subStrLine(str[i]); 
									if(str[i].contains("Board No"))
										strBoardNo = subStrLine(str[i]); 
									if(str[i].contains("Site No"))
										strSiteNo = subStrLine(str[i]); 
									if(str[i].contains("Site Type"))
										strSiteType = subStrLine(str[i]); 
									if(str[i].contains("PassNo"))
										strPassNo = subStrLine(str[i]); 
									if(str[i].contains("CellNo"))
										strCellNo = subStrLine(str[i]); 
									if(str[i].contains("RackNo"))
										strRackNo = subStrLine(str[i]); 
									if(str[i].contains("BoxNo"))
										strBoxNo = subStrLine(str[i]); 
									
								}
							 
						}else if(strLine.contains("Operator  =")){
							strOperator = subStrLine(strLine);
						}
					}else{ 
						if(strClearTime.equalsIgnoreCase("")){
							strType = "ALARM FROM";
						}else{
							strType = "ALARM CLEARING";
						}
						try {
							if(strNeName.substring(0, 1).equalsIgnoreCase("B")){
								network = "2G";
							}else if(strNeName.substring(0, 1).equalsIgnoreCase("R")){
								network = "3G";
							}else{
								network = "CS_CORE";
							}
						} catch (Exception e) { 
						} 
						
						line += strType+sep+ vendor +sep + strSn + sep + strNeSn + sep+network+sep+strNeType+sep+strObjType+sep+strSite+sep+addBoard+sep+strCell+sep+CellIndex+sep+CGI+sep
								+strNeName+sep+strAlarmID+sep+strAlarmName+sep
								+strSeverity+sep+ackStatus+sep+clrStatus+sep+strEventType+sep
								+strOccurtime+sep+strClearTime+sep+strAckTime+sep+strLocation+sep+strOperator+sep
								+strCabinet+sep+strSubrack+sep+strSlot+sep+strPort+sep+strBoardType+sep
								+strBoardNo+sep+strSiteNo+sep+strSiteType+sep+strPassNo+sep+strCellNo+sep+strRackNo+sep+strBoxNo ;
						wAlarmLog.write(line);
						wAlarmLog.newLine();
						
						strNeName="";strNeType="";strAlarmID="";CGI="";strType="";
						strAlarmName="";strSeverity="";ackStatus="";clrStatus="";strEventType="";
						strSite="";addBoard="";strCell="";CellIndex="";CellIndex="";strOccurtime="";
						strClearTime="";strAckTime="";strLocation="";strOperator="";strSn="";strNeSn="";
						line="";strObjName=""; strObjType="";strCabinet = ""; strSubrack = ""; strSlot =""; 
						strPort=""; strBoardType=""; strBoardNo = "";strSiteType = ""; strSiteNo = ""; 
						strPassNo=""; strCellNo=""; strRackNo=""; strBoxNo = "";
						blockAlarm = false;
					}
				}
			}
		}catch (Exception e) {
			throw new AL_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					wAlarmLog.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
	
	}
	
	private String subStrLine(String strLine){
		String str = strLine.substring(strLine.indexOf("=")+1,strLine.length()).toString().trim();
		return str;
	}
}
