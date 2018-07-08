package vn.com.vhc.alarm.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.csvreader.CsvReader;

import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.model.AlarmZteModel;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

/**
 * @author galaxy
 * @createDate 11:11:09 AM   
 * AlarmHistoryZteMTConverter.java
 * Filename format (To_Web-Alarm)_([0-9]{17})_([0-9]{17})_(.*)(.csv)
 * ex: To_Web-Alarm_20151218150214948_20151218150715007_1_1.csv
 * 
 */
public class AlarmHistoryZteMTConverter  extends AL_BasicConverter{
	
	private static final Logger logger = Logger
			.getLogger(AlarmHistoryZteMTConverter.class.getName()); 
	
	String line="";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		 
		BufferedWriter writerAlarmLogs = null; 
		AlarmZteModel alarmZteModel = new AlarmZteModel();
		
		try {
			writerAlarmLogs = new BufferedWriter(new FileWriter(direcPath + "/" + file.getName()));
			writerAlarmLogs.write("#Vendor;Network;PerceivedSeverity;AlarmType;Location;Ne;AlarmCode;SpecificProblem;RaisedTime;NeType;AlarmId;ClearedTime;AckState;Severity;Site");
			
			FileInputStream fileInPutStream = new FileInputStream(file); 
			Reader reader = new InputStreamReader(fileInPutStream);
			CsvReader buffReader = new CsvReader(reader);
			boolean block = false;
			while (buffReader.readRecord()){
				
				if(!block){
					if(buffReader.get(0).equals("Perceived Severity")){
						String[] header = buffReader.getValues();
						buffReader.setHeaders(header);
						block = true;
						continue;
					}
				} else {
					if(buffReader.get(0).equals("Total Count:")){
						block = false;
						continue;
					}
					
					String perceivedSeverity = buffReader.get("Perceived Severity");
					String alarmType = buffReader.get("Alarm Type");
					String location = buffReader.get("Location");
					//String ne = buffReader.get("NE");
					String alarmCode = buffReader.get("Alarm Code");
					String specificProblem = buffReader.get("Specific Problem");
					String raisedTime = buffReader.get("Raised Time"); 
					String neType = buffReader.get("NE Type");
					String ackState = buffReader.get("ACK State");
					String alarmId = buffReader.get("Alarm ID"); 
					String neAgent = buffReader.get("NE Agent"); 
					String clearedTime = buffReader.get("Cleared Time");
					String site = buffReader.get("Custom Attribute 2");
					
					String severity = "";
					String network = "";
					String ne = "";
					
					// Lay thong tin muc do canh bao
					if(perceivedSeverity.equals("Major")|| perceivedSeverity.equals("Cleared")){
						severity = "A2"; 
					}else if(perceivedSeverity.equals("Critical")){
						severity = "A1"; 
					}else if(perceivedSeverity.equals("Warning")){
						severity = "O2"; 
					}else if(perceivedSeverity.equals("Minor")){
						severity = "A3"; 
					}else if(perceivedSeverity.equals("Indeterminate")){
						severity = "Indeterminate"; 
					}
					
					// Lay thong tin Site, Cell
					try {
						site = site.substring(0, site.indexOf("$"));
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					// Lay thong tin ne
					try {
						ne = neAgent.substring(0, neAgent.indexOf("-"));
					} catch (Exception e) {
						// TODO: handle exception
						ne = neAgent;
					}
					
					// Lay thong tin network
					if(neAgent.substring(0,1).equalsIgnoreCase("B")) network = "2G";
					else if(neAgent.substring(0,1).equalsIgnoreCase("R")) network = "3G";
					else if(neAgent.substring(0,1).equalsIgnoreCase("S")
							||neAgent.substring(0,1).equalsIgnoreCase("G")){
						network = "PS_CORE";
					}else{
						network = "CS_CORE";
					}
					
					alarmZteModel = new AlarmZteModel();
					
					alarmZteModel.setAckState(ackState);
					alarmZteModel.setAlarmCode(alarmCode);
					alarmZteModel.setAlarmId(alarmId);
					alarmZteModel.setAlarmType(alarmType);
					alarmZteModel.setClearedTime(clearedTime);
					alarmZteModel.setLocation(location);
					alarmZteModel.setNe(ne);
					alarmZteModel.setNetwork(network);
					alarmZteModel.setNeType(neType);
					alarmZteModel.setPerceivedSeverity(perceivedSeverity);
					alarmZteModel.setRaisedTime(raisedTime);
					alarmZteModel.setSeverity(severity);
					alarmZteModel.setSpecificProblem(specificProblem);
					alarmZteModel.setVendor("ZTE");
					alarmZteModel.setSite(site);
					
					writerAlarmLogs.newLine();
					writerAlarmLogs.write(alarmZteModel.getAlarmLine());
					System.out.println(alarmZteModel.getAlarmLine());
				}
			}
		}catch (Exception e) {
			throw new AL_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			try { 
				writerAlarmLogs.close();  
			} catch (IOException e) {
				logger.warn("Close IO stream failure " + e);
			}
		}

		logger.info("Convert file: " + file.getName() + " success");
	}
}
