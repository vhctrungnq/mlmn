// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.vhc.model.AlarmType;
import vn.com.vhc.model.CellN;
import vn.com.vhc.model.CellN3G;
import vn.com.vhc.model.Message;
import vn.com.vhc.model.StructAlarm;
import vn.com.vhc.util.DbUtil;
import vn.com.vhc.util.StringUtil;

/**
 * Message service implement
 * @author datnh
 */
public class MessageServiceImpl implements MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	private static final String alarmTag 	= "#E##S#";
	private static final String alarmRNC 	= "PLMN-PLMN/RNC";
	private static final String alarmBSC 	= "PLMN-PLMN/BSC";
	private static final String alarmOMC 	= "PLMN-PLMN/OMC";
	private static final String alarmOMS 	= "PLMN-PLMN/OMS";
	private static final String alarmSGSN 	= "PLMN-PLMN/SGSN";
	private static final String alarmGGSN 	= "PLMN-PLMN/GPBB";
	private static final String alarmGGSN2 	= "PLMN-PLMN/FING";
	private static final String alarmSGSN2 	= "PLMN-PLMN/FLEXINS";
	private static final String RAN_4G = "PLMN-PLMN/MRBTS";
	private static int count = 0;
	private static String siteNameCore = "";
	
	protected StructAlarm structAlarm;
	
	@Override
	public void processMessage(Message message) {
		
		String data = message.getData();
		
		String[] alarmMessage = data.split(message.getDelimiter());
		
		if (alarmMessage.length <= 3) return;
		
		//logger.info("Processing message: " + data);		// test
		
		analysis(alarmMessage);
	}
	
	/**
	 * analysis alarm message
	 */
	private void analysis(String[] alarmMessage) {
		
		structAlarm = new StructAlarm();
		structAlarm.alarmInfo = "";
		String[] temp; // dung de lay bsc, site, cell 4g
		for (String line: alarmMessage) {
			structAlarm.alarmInfo += line;
			structAlarm.alarmInfo += "<->";
			
			if (line.contains(alarmTag)) {
				String[] arrValue = line.split(" ");
				
				// alarmNumber
				structAlarm.alarmNumber = arrValue[0].replace(alarmTag, "");
				
				// alarmClass - Loai canh bao
				structAlarm.alarmClass = getAlarmClass(arrValue[1]);
				
				// alarmId
				structAlarm.alarmId = arrValue[0].substring(arrValue[0].length() - 4);
				
				//dem dong de lay du lieu sitename tai dong 2
				count = 1;
			} else {
				
				if(count == 1){
					siteNameCore = line.trim();
					count = 0;
				}
				
				//NodeB & RNC la canh bao 3G
				if (line.contains(alarmRNC)) {
					structAlarm.networkType = "3G";
					
					structAlarm.objectName = line;
					getBSCId(structAlarm);
				} 
				// BTS & BSC la canh bao 2G
				else if (line.contains(alarmBSC)) {
					structAlarm.networkType = "2G";
					
					structAlarm.objectName = line;
					getBSCId(structAlarm);
				}
				
				// ngoai 2 canh bao tren con co mot so canh bao khac can duoc luu lai
				else if (line.contains(alarmOMC)) {
					String[] arrObject = line.split("/");
					
					structAlarm.networkType = "OMC";
					structAlarm.node = "OMC";
					structAlarm.objectName = line;
					structAlarm.bscId = arrObject[1];
				}
				else if (line.contains(alarmOMS)) {
					String[] arrObject = line.split("/");
					
					structAlarm.networkType = "OMS";
					structAlarm.node = "OMS";
					structAlarm.objectName = line;
					structAlarm.bscId = arrObject[1];
				}
				// SGSN
				else if(line.contains(alarmSGSN) || line.contains(alarmSGSN2)) {
					structAlarm.networkType = "PS_CORE";
					structAlarm.node = "SGSN";
					structAlarm.objectName = line;
					structAlarm.bscId = getPsCoreName(alarmMessage[1]);
					structAlarm.site = siteNameCore;
					siteNameCore = "";
				}
				else if(line.contains(alarmGGSN) || line.contains(alarmGGSN2) ) {
					structAlarm.networkType = "PS_CORE";
					structAlarm.node = "GGSN";
					structAlarm.objectName = line;
					structAlarm.bscId = getPsCoreName(alarmMessage[1]);
					structAlarm.site = siteNameCore;
					siteNameCore = "";
				} else if (line.contains(RAN_4G)) {
					structAlarm.networkType = "RAN_4G";
					structAlarm.objectName = line;
					temp = line.split("/");
					structAlarm.bscId = temp[1];
					structAlarm.site = temp[1];
					structAlarm.cellid = temp[2];
				}
				
				
				if (line.contains("User info")) {
					structAlarm.userInfo = line.replace("User info", "").trim();
				}
				
				if (line.contains("IntId")) {
					structAlarm.intId = StringUtil.getValueByField("IntId", line, " ");
					structAlarm.notifId = StringUtil.getValueByField("NotifId", line, " ");
				}
				
				else if (line.contains("Cancelled")) {
					structAlarm.sDate = "";
					structAlarm.eDate = line.substring(line.indexOf("Cancelled") + 9).trim();
					structAlarm.alarmStatus = "UPDATE";
				}
				
				else if (line.contains("Started") && !line.contains("Cancelled")) {
					//structAlarm.sDate = StringUtil.getValueByField("Started", line, " ");
					String str = line.substring(line.indexOf("Started") + 7).trim();
					if (str.length() >= 20) {
						str = str.substring(0, 20).trim();
					}
					structAlarm.sDate = str;
					structAlarm.eDate = "";
					
					structAlarm.alarmStatus = "INSERT";
				}
			}
		}
		
		// skip RNC, BSC, OMC, RNS node
		/*if (structAlarm.node.equals("RNC") || structAlarm.node.equals("BSC") || structAlarm.node.equals("OMC") || structAlarm.node.equals("OMS")) {
			logger.debug("[Skip Message] " + structAlarm.node);
			return;
		}*/
		
		// alarmName
		structAlarm.alarmName = alarmMessage[3].toUpperCase();
		
		// filter alarmtype
		getAlarmType(structAlarm);
		structAlarm.alarmInfo=structAlarm.alarmInfo.replace(",", ".");
		/*structAlarm.info = structAlarm.alarmStatus
				+ "," +  structAlarm.node
				+ "," +  structAlarm.alarmNumber
				+ "," +  structAlarm.intId
				+ "," +  structAlarm.notifId
				+ "," +  structAlarm.bscId
				+ "," +  structAlarm.site
				+ "," +  structAlarm.cellid
				+ "," +  structAlarm.sDate
				+ "," +  structAlarm.eDate
				+ "," +  structAlarm.objectName
				+ "," +  structAlarm.alarmClass
				+ "," +  structAlarm.alarmName
				+ "," +  structAlarm.alarmId
				+ "," +  structAlarm.alarmType
				+ "," +  structAlarm.alarmMappingName
				+ "," +  structAlarm.alarmMappingId
				+ "," +  structAlarm.userInfo
				+ "," +  structAlarm.alarmInfo
				+ "," +  structAlarm.isMonitor
				+ "," +  structAlarm.isSendSms;*/
		
		// ghi log
		logger.info(structAlarm.toString());
		
		// insert to database
		// 2014.05.27
		DbUtil.insertAlarm(structAlarm);
	}
	
	private String getPsCoreName(String msg) {
		if (msg != null) {
			int p = msg.indexOf(" ");
			if (p >= 0)
				return msg.substring(0, p);
		}
		return msg;
	}
	
	/**
	 * Get alarm class by code
	 * 
	 * @param alarmCode
	 * @return
	 */
	private String getAlarmClass(String alarmCode) {
		if (alarmCode.equals("W")) {
			return "Warnning";
		} else if (alarmCode.equals("*")) {
			return "A3";
		} else if (alarmCode.equals("**")) {
			return "A2";
		} else if (alarmCode.equals("***")) {
			return "A1";
		}
		
		return "";
	}
	
	/**
	 * Get bscid, site, cellid
	 * 
	 * @param structAlarm
	 */
	private void getBSCId(StructAlarm structAlarm) {
		
		String[] arrAll = structAlarm.objectName.split("/");
		
		// 2G
		if (structAlarm.networkType.equals("2G")) {
			
			// BTS - Alarm CELL
			if (structAlarm.objectName.contains("BTS")) {
				structAlarm.node = "SITE";
				
				CellN cellN = DbUtil.getCellN(1, arrAll[0] + "/" + arrAll[1] + "/" + arrAll[2]+ "/" + arrAll[3]);
				
				if (cellN != null) {
					structAlarm.bscId = cellN.getBscName();
					structAlarm.site = cellN.getBtsName();
					structAlarm.cellid = cellN.getSegmentName();
				}
			}
			// BCF - Alarm SITE
			else if (structAlarm.objectName.contains("BCF")) {
				structAlarm.node = "SITE";
				
				CellN cellN = DbUtil.getCellN(2, arrAll[0] + "/" + arrAll[1] + "/" + arrAll[2]);
				if (cellN != null) {
					structAlarm.bscId = cellN.getBscName();
					structAlarm.site = cellN.getBtsName();
				}
			}
			// RNC - ALARM MUC BSC
			else {
				
				structAlarm.node = "BSC";
				
				CellN cellN = DbUtil.BSCID.get(arrAll[0] + "/" + arrAll[1]);
				if (cellN != null) {
					structAlarm.bscId = cellN.getBscName();
				}
			}
		}
		
		// 3G
		else if (structAlarm.networkType.equals("3G")) {
			
			//PLMN-PLMN/RNC-61/WBTS-377/WCEL-1
			if (structAlarm.objectName.contains("WCEL")) {
				structAlarm.node = "NODEB";		// node B
				
				// filter by objectname
				//where = " objectname = '" + arrAll[0] + "/" + arrAll[1] + "/" + arrAll[2] + "/" + arrAll[3] + "'";
				CellN3G cellN3G = DbUtil.getCellN3G(1, arrAll[0] + "/" + arrAll[1] + "/" + arrAll[2] + "/" + arrAll[3]);
				
				if (cellN3G != null) {
					structAlarm.bscId = cellN3G.getRncname();
					structAlarm.site = cellN3G.getWbtsname();
					structAlarm.cellid = cellN3G.getCellname();
				}
			}
			
			else if (structAlarm.objectName.contains("WBTS")) {
				structAlarm.node = "NODEB";
				
				// filter by objectname
				//where = " bts_objectname = '" + arrAll[0] + "/" + arrAll[1] + "/" + arrAll[2] + "'";
				CellN3G cellN3G = DbUtil.getCellN3G(2, arrAll[0] + "/" + arrAll[1] + "/" + arrAll[2]);
				
				if (cellN3G != null) {
					structAlarm.bscId = cellN3G.getRncname();
					structAlarm.site = cellN3G.getWbtsname();
				}
			}
			
			else {
				structAlarm.node = "RNC";
				
				CellN3G cellN3G = DbUtil.RNCID.get(arrAll[0] + "/" + arrAll[1]);
				
				if (cellN3G != null) {
					structAlarm.bscId = cellN3G.getRncname();
				}
			}
		}
	}
	
	/**
	 * Get alarm type
	 * 
	 * @param structAlarm
	 */
	private void getAlarmType(StructAlarm structAlarm) {
		
		AlarmType alarmConfig = null;
		
		alarmConfig = DbUtil.getAlarmConfig(structAlarm.networkType, "ALARM_NAME", structAlarm.alarmName);
		
		if (alarmConfig == null && structAlarm.alarmId != null && structAlarm.alarmId.length() >= 1) {
			alarmConfig = DbUtil.getAlarmConfig(structAlarm.networkType, "ALARM_ID", structAlarm.alarmId);
		}
		
		if (alarmConfig != null) {
			structAlarm.alarmType 			= alarmConfig.getAlarmType();
			structAlarm.alarmMappingName 	= alarmConfig.getAlarmMappingName();
			structAlarm.alarmMappingId 		= alarmConfig.getAlarmMappingId();
			structAlarm.isMonitor 			= alarmConfig.getIsMonitor();
			structAlarm.isSendSms 			= alarmConfig.getIsSendSms();
		}
		
		// SERVICE
		if (structAlarm.alarmName.contains("BTS O&M LINK FAILURE")
				|| structAlarm.alarmName.contains("BTS FAULTY")
				|| structAlarm.alarmName.contains("WCDMA BASE STATION OUT OF USE")
				|| structAlarm.alarmName.contains("WCDMA CELL OUT OF USE")) {
			
			structAlarm.alarmType = "SERVICE";
		} 
		// POWER
		else if (structAlarm.alarmName.contains("BSC AC FAILURE")
				|| structAlarm.alarmName.contains("BSC RECTIFIER FAILURE")
				|| structAlarm.alarmName.contains("TRANSMISSION NODE AC FAILURE")
				|| structAlarm.alarmName.contains("BTS RECTIFIER FAILURE")
				|| structAlarm.alarmName.contains("TRANSMISSION NODE RECTIFIER FAILURE")
				|| structAlarm.alarmName.contains("GENERATOR IS RUNNING")
				|| structAlarm.alarmName.contains("BTS MAINS FAILURE")) {
			
			structAlarm.alarmType = "POWER";
		}
	}

}
