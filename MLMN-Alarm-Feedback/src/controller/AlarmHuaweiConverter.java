package controller;

import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger; 
  
import vo.RAlarmLogTemp; 

public class AlarmHuaweiConverter{
	private static final Logger logger = Logger
			.getLogger(AlarmHuaweiConverter.class.getName()); 
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	
	List<RAlarmLogTemp> alarmLogList = new ArrayList<RAlarmLogTemp>();
	BufferedWriter writer = null; 
	public List<RAlarmLogTemp> convertFile(InputStream pathFile, String userName, String pathOut){ 
		try {
			//Tao mot con tro de connect den pathFile  
		    // FileInputStream pathFile1 = new FileInputStream(pathFile);
			//writer = new BufferedWriter(new FileWriter(pathOut+"/"+"AlarmHuawei"));
			Reader reader = new InputStreamReader(pathFile); 
			//Doc tung byte
			BufferedReader buffReader = new BufferedReader(reader); 
			
			String strLine = null; 
			boolean block = false; 
			String line = "";
			String network = "";
			String  _Ne = "", _Bts = "";
			while ((strLine = buffReader.readLine()) != null)
			{
				if(strLine.contains("Severity")){  
					block = true;
					continue;
				} 
				
				if(block == true){ 
					String[] strList = strLine.split(",");
					
					//Lay thong tin muc do canh bao
					if(strList[1].equalsIgnoreCase("critical")){
						strList[1] = "A1";
					}else if(strList[1].equalsIgnoreCase("major")){
						strList[1] = "A2";
					}else if(strList[1].equalsIgnoreCase("minor")){
						strList[1] = "A3";
					}else{
						strList[1] = "Indeterminate";
					}
					
					//Lay thong tin NE
					_Ne = strList[7].split("/")[0];
					try {
						_Bts = strList[7].split("/")[1];
					} catch (Exception e) {} 
					
					//Lay thong tin network
					if(_Ne.substring(0,1).equalsIgnoreCase("B")){
						network = "2G";
					}else if(_Ne.substring(0,1).equalsIgnoreCase("R")){
						network = "3G";
					}else if(_Ne.substring(0,1).equalsIgnoreCase("S")||_Ne.substring(0,1).equalsIgnoreCase("G")){
						network = "PS_CORE";
					}else{
						network = "CS_CORE";
					}
					
					//Lay thong tin NE_TYPE
					if(strList[27].contains("BSC")){
						strList[27] = "BSC";
					}else if(strList[27].equals("GBTS")){
						strList[27] = "BTS";
					}
					
					String severity = strList[1].trim().replace("-", "");
					String ackStatus = strList[3].trim().replace("-", ""); 
					String ackTime = strList[4].trim().replace("-", "");
					String ackUser = strList[5].trim().replace("-", "");
					String alarmId = strList[6].trim().replace("-", ""); 
					String sdate = strList[8].trim().replace("-", "");
					String clrStatus = strList[10].trim().replace("-", "");
					String edate = strList[11].trim().replace("-", "");
					String alarmInfo = strList[19].trim().replace("-", ""); 
					String fmAlarmid = strList[20].trim().replace("-", "");  
					String alarmName = strList[26].trim().replace("-", ""); 
					String neType =  strList[27].trim().replace("-", "");
					String evttype = strList[35].trim().replace("-", ""); 
					
					RAlarmLogTemp alarmLogTemp = new RAlarmLogTemp();
					
					alarmLogTemp.setAckTime(df_full.parse(ackTime));
    				alarmLogTemp.setSdate(df_full.parse(sdate));
    				alarmLogTemp.setAlarmId(Integer.parseInt(alarmId));
    				alarmLogTemp.setFmAlarmid(Integer.parseInt(fmAlarmid));
    				alarmLogTemp.setEdate(df_full.parse(edate));
    				
    				alarmLogTemp.setNe(_Ne);
    				alarmLogTemp.setSite(_Bts);
    				alarmLogTemp.setNetwork(network);
        			alarmLogTemp.setSeverity(severity);
        			alarmLogTemp.setUsername(userName);
        			alarmLogTemp.setVendor("HUAWEI");
        			alarmLogTemp.setAckStatus(ackStatus);
        			alarmLogTemp.setAckUser(ackUser);
        			alarmLogTemp.setAlarmName(alarmName);
        			alarmLogTemp.setNeType(neType);
        			alarmLogTemp.setEvttype(evttype);
        			alarmLogTemp.setClrStatus(clrStatus);
        			alarmLogTemp.setAlarmInfo(alarmInfo);
        			
        			line = ackTime+";"+sdate+";"+alarmId+";"+fmAlarmid+";"+edate+";"+_Ne+";"+_Bts+";"+network+";"+
        					severity+";"+userName+";"+ackStatus+";"+ackUser+";"+alarmName+";"+neType+";"+alarmInfo;
        			alarmLogList.add(alarmLogTemp);
        			writer.write(line);
        			writer.newLine();
				} 
			}
			reader.close();
		}catch (Exception e) {
			logger.warn("Khong tim thay file " + e);
		}
		return alarmLogList;
	} 
} 