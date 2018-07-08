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
import vn.com.vhc.alarm.model.Alarm2GModel;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class AlarmHistoryEra2GMTConvert  extends AL_BasicConverter{
	
	private static final Logger logger = Logger
			.getLogger(AlarmHistoryEra2GMTConvert.class.getName()); 
	
	String line="";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerAlarmLogs = null; 
		Alarm2GModel alarm2gModel = new Alarm2GModel();
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			//khong thay doi duoi file 
			//writerAlarmLogs = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			writerAlarmLogs = new BufferedWriter(new FileWriter(outFile));
			writerAlarmLogs.write("#Vendor;Network;Type;NeType;BscId;BtsId;LogRecordId;StartDate;EndDate;EventType;ObjectOfReference;PerceivedSeverity;Severity;SpecificProblem;ProblemText;CorrelatedId;FmAlarmId");
			String vendor = "ERICSSON",network = "2G"; 
			String strLine = ""; 
			boolean blockFrom=false;
			boolean blockText=false;
			
			while ((strLine = reader.readLine()) != null){
				if(strLine.equals("")) continue;
				
				if(strLine.contains("ALARM CLEARING")) { 
					blockFrom = false; 
					alarm2gModel.setVendor(vendor);
					alarm2gModel.setNetwork(network);
					alarm2gModel.setType("ALARM CLEARING"); 
				}
				
				if(strLine.contains("ALARM FROM")) {  
					blockFrom = true;
					alarm2gModel.setVendor(vendor);
					alarm2gModel.setNetwork(network);
					alarm2gModel.setType("ALARM FROM");
				}
				
				if(strLine.contains("ALARM END")) blockFrom = false;
				
				if(strLine.contains("Log Record ID:")) alarm2gModel.setLogRecordId(simple(strLine,":"));
				else if(strLine.contains("Event Time:")){
					while(strLine.indexOf("  ")>= 0) strLine = strLine.replaceAll("  ", " ");
					if(blockFrom) alarm2gModel.setStartDate(strLine.substring(strLine.indexOf(":")+1).trim());
					else alarm2gModel.setEndDate(strLine.substring(strLine.indexOf(":")+1).trim());
				}else if(strLine.contains("Event Type:")) alarm2gModel.setEventType(simple(strLine,":"));
				else if(strLine.contains("Object of Reference:")){
					String str = simple(strLine,":");
					String[] strList = str.split(",");
					
					for(String i:strList){
						if(i.contains("ManagedElement")) alarm2gModel.setBscId(simple(i,"=")); 
						if(i.contains("BtsSiteMgr")) alarm2gModel.setBtsId(simple(i,"="));
					}
					
					alarm2gModel.setObjectOfReference(str);
				}else if (strLine.contains("Perceived Severity:")){
					String str1 = simple(strLine,":");
					String str2 ="";
					if(str1.equals("Major")|| str1.equals("Cleared")){
						str2 = "A2"; 
					}else if(str1.equals("Critical")){
						str2 = "A1"; 
					}else if(str1.equals("Warning")){
						str2 = "O2"; 
					}else if(str1.equals("Minor")){
						str2 = "A3"; 
					}else if(str1.equals("Indeterminate")){
						str2 = "Indeterminate"; 
					}
					alarm2gModel.setPerceivedSeverity(str1);
					alarm2gModel.setSeverity(str2);
				}else if (strLine.contains("Specific Problem:")) alarm2gModel.setSpecificProblem(simple(strLine,":"));
				else if (strLine.contains("Problem Text:")){
					blockText=true;
					continue;
				}else if (strLine.contains("Correlated ID:")) {
					alarm2gModel.setCorrelatedId(simple(strLine,":"));
					if(blockFrom) alarm2gModel.setFmAlarmId(alarm2gModel.getLogRecordId());
					else alarm2gModel.setFmAlarmId(alarm2gModel.getCorrelatedId());
				}
				
				if(blockText){
					while(strLine.indexOf("  ")>= 0) strLine = strLine.replaceAll("  ", " ");
					if(strLine.equals("END")) {
						blockText = false;
						alarm2gModel.setProblemText(line);
					}
					line+= strLine+"<->"; 
				}
				
				if(strLine.contains("ALARM END")) {
					writerAlarmLogs.newLine();
					writerAlarmLogs.write(alarm2gModel.getVendor()+";"+alarm2gModel.getNetwork()+";"+alarm2gModel.getType()+";"+alarm2gModel.getNeType()+";"+alarm2gModel.getBscId()+";"+alarm2gModel.getBtsId()+
							";"+alarm2gModel.getLogRecordId()+";"+alarm2gModel.getStartDate()+
							";"+alarm2gModel.getEndDate()+";"+alarm2gModel.getEventType()+";"+alarm2gModel.getObjectOfReference()+";"+alarm2gModel.getPerceivedSeverity()+";"+alarm2gModel.getSeverity()+";"
							+alarm2gModel.getSpecificProblem()+";"+alarm2gModel.getProblemText()+";"+alarm2gModel.getCorrelatedId()+";"+alarm2gModel.getFmAlarmId()); 
					alarm2gModel = new Alarm2GModel();
					line = "";
				}
			}
		}catch (Exception e) {
			throw new AL_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					writerAlarmLogs.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}

		logger.info("Convert file: " + file.getName() + " success");
	}
	
	private String simple(String strLine, String phancach){
		String line1 = "";
		try{
			String[] str = strLine.split(phancach); 
			line1 = str[1].toString().trim();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return line1;
	}
	
}
