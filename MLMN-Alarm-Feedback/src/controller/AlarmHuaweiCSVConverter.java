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

import com.csvreader.CsvReader;
  
import vo.RAlarmLogTemp; 

public class AlarmHuaweiCSVConverter{
	private static final Logger logger = Logger.getLogger(AlarmHuaweiCSVConverter.class.getName()); 
	
	private DateFormat df_full = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
	
	List<RAlarmLogTemp> alarmLogList = new ArrayList<RAlarmLogTemp>();
	BufferedWriter writer = null; 
	public List<RAlarmLogTemp> convertFile(InputStream pathFile, String userName){ 
		try {
			//Tao mot con tro de connect den pathFile  
		    // FileInputStream pathFile1 = new FileInputStream(pathFile);
			//writer = new BufferedWriter(new FileWriter(pathOut+"/"+"AlarmHuawei"));
			Reader reader = new InputStreamReader(pathFile); 
			//Doc tung byte
			CsvReader buffReader = new CsvReader(reader); 
			//buffReader.readHeaders(); 
			String line = "";
			String network = "";
			String  _Ne = "", _Bts = ""; 
			boolean block = false;
			int count = 0;
			while (buffReader.readRecord())
			{
				if(buffReader.get(1).equalsIgnoreCase("Severity")){
					String[] header = buffReader.getValues();
					buffReader.setHeaders(header);
					block = true;
					continue;
				}
				if(block){  
					String severity = buffReader.get("Severity").replace("-", "");
					String ackStatus = buffReader.get(3).replace("-", "");
					String ackTime = buffReader.get(4).replace("-", "");
					String ackUser = buffReader.get(5).replace("-", "");
					String alarmId = buffReader.get(6).replace("-", "");
					String neList = buffReader.get(7).replace("-", "");
					String sdate = buffReader.get(8).replace("-", "");
					String clrStatus = buffReader.get(10).replace("-", "");
					String edate = buffReader.get(11).replace("-", "");
					String alarmInfo = buffReader.get(19).replace("-", ""); 
					String fmAlarmid = buffReader.get(20).replace("-", ""); 
					String alarmName = buffReader.get(26).replace("-", "");
					String neType =  buffReader.get(27).replace("-", "");
					String evttype = buffReader.get(35).replace("-", "");
					
					/*String severity = buffReader.get("Severity").replace("-", "");
					String ackStatus = buffReader.get("Acknowledgement Status").replace("-", "");
					String ackTime = buffReader.get("Acknowledgement Time(ST)").replace("-", "");
					String ackUser = buffReader.get("Acknowledgement User").replace("-", "");
					String alarmId = buffReader.get("Alarm ID").replace("-", "");
					String neList = buffReader.get("Alarm Source").replace("-", "");
					String sdate = buffReader.get("Arrive Time(ST)").replace("-", "");
					String clrStatus = buffReader.get("Clearance Status").replace("-", "");
					String edate = buffReader.get("Clearance Time(NT)").replace("-", "");
					String alarmInfo = buffReader.get("Location Information").replace("-", ""); 
					String fmAlarmid = buffReader.get("Log Serial Number").replace("-", ""); 
					String alarmName = buffReader.get("Name").replace("-", "");
					String neType =  buffReader.get("NE Type").replace("-", "");
					String evttype = buffReader.get("Type").replace("-", "");*/
					
					//Lay thong tin muc do canh bao
					if(severity.equalsIgnoreCase("critical")){
						severity = "A1";
					}else if(severity.equalsIgnoreCase("major")){
						severity = "A2";
					}else if(severity.equalsIgnoreCase("minor")){
						severity = "A3";
					}else{
						severity = "Indeterminate";
					}
					
					//Lay thong tin NE
					_Ne = neList.split("/")[0];
					try {
						_Bts = neList.split("/")[1];
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
					if(neType.contains("BSC")){
						neType = "BSC";
					}else if(neType.equals("GBTS")){
						neType = "BTS";
					}
					
					RAlarmLogTemp alarmLogTemp = new RAlarmLogTemp(); 
					if(!ackTime.equals("")){
						alarmLogTemp.setAckTime(df_full.parse(ackTime));
					}
					if(!sdate.equals("")){
						alarmLogTemp.setSdate(df_full.parse(sdate));				
					}
					if(!edate.equals("")){
						alarmLogTemp.setEdate(df_full.parse(edate));
					}
					if(!alarmId.equals("")){
						alarmLogTemp.setAlarmId(Integer.parseInt(alarmId));
					}
					if(!fmAlarmid.equals("")){
						alarmLogTemp.setFmAlarmid(Integer.parseInt(fmAlarmid));
					} 
					
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
	    			
	    			alarmLogList.add(alarmLogTemp);
	    			
	    			line = ackTime+";"+sdate+";"+alarmId+";"+fmAlarmid+";"+edate+";"+_Ne+";"+_Bts+";"+network+";"+
	    					severity+";"+userName+";"+ackStatus+";"+ackUser+";"+alarmName+";"+neType+";"+alarmInfo;
	    			count++;
	    			System.out.println(line); 
	    			System.out.println(count); 
				}
			}
			buffReader.close();
		}catch (Exception e) {
			logger.warn("Khong tim thay file " + e);
		}
		return alarmLogList;
	} 
} 