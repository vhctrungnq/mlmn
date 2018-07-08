package vn.com.vhc.sts.converter.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.Hashtable;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;


public class DongBoAlarmConverterTest extends STS_BasicConverter {
	
	private static final Logger logger = Logger
			.getLogger(DongBoAlarmConverterTest.class.getName());
	private String sep= ";";
	private int fileId = -1;
	 
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		//Tao mot con tro de connect den pathFile
		//FileInputStream fileInPutStream = new FileInputStream(pathFile);
		prepareParams(params);
		makeDirectory(direcPath);
		BufferedReader reader = null;
		BufferedWriter writer = null;
		//Doc tung byte
		
		File outFile = new File(direcPath, file.getName());
		
		//List<RAlarmLogTemp> alarmLogList = new ArrayList<RAlarmLogTemp>();
		String strLine = "";
		int count = 0 ; 
		boolean block = false;   
		String 		alarmId = "";
		String 		severity = "";
		String 		bts = "";
		String 		ne = ""; 
		String 		alarmName = "";
		String 		sdate = "";
		String 		edate = ""; 
		String 		ackDate = "";
		String 		ackUser = ""; 
		String 		alarmInfo = "";
		String      fmAlarmId = "";
	try {
		reader = new BufferedReader(new FileReader(file));
		writer = new BufferedWriter(new FileWriter(outFile));
		String temp = "";
		while ((strLine = reader.readLine()) != null)
		{ 
			strLine = strLine.trim(); 
			
			if(strLine.contains("*") ||  isValidNumber(strLine.split(" ")[0])){
				block = true;
				count = 0; 
			} 
			 
			if(block == true){
				count ++;
				if(count ==1){
				strLine = unSpace(strLine.trim());
				String[] str = strLine.split(" ");
				alarmId = str[0];
				severity = str[1];
				if(severity.equals("***")){
					severity = "A1";
				}else if(severity.equals("**")){
					severity = "A2";
				}else if(severity.equals("*")){
					severity = "A3";
				}
				temp  = alarmId + sep + severity + sep;
				}
				 
			else  if(count == 2){
				strLine = unSpace(strLine);
				bts = strLine.split(" ")[0];
				alarmInfo += strLine+">>>"; 
				temp += bts + sep  ;
			}
			if(count == 3){
				ne =  strLine.trim();
				alarmInfo += strLine+">>>"; 
				temp += ne + sep  ;
			}
			if(count == 4){
				alarmName = strLine.trim();  
				alarmInfo += strLine+">>>"; 
				temp += alarmName + sep  ;
			}
			if(count == 5){ 
				strLine = unSpace(strLine.trim());
				alarmInfo += strLine+">>>";
				String[] str = strLine.split(" ");
				try { 
					sdate = str[1]+" "+str[2];
					edate = str[4]+" "+str[5];
				} catch (Exception e) { 
				}   
				temp += sdate + sep + edate + sep;
			}
			
			if(strLine.contains("Notif ID")){
				strLine = strLine.trim();
				fmAlarmId = strLine.substring(strLine.lastIndexOf(" ")+1);
				temp +=   fmAlarmId + sep;
			}
			
			
			if(strLine.contains("Acked by")){
				strLine = strLine.trim();
				ackDate = strLine.substring(strLine.indexOf("Acked")+6, strLine.lastIndexOf("Acked") - 1);
				ackUser = strLine.substring(strLine.lastIndexOf(" ")+1);
				alarmInfo += strLine+">>>"; 
				temp +=   ackDate + sep + ackUser + sep;
			} 
			if(strLine.contains("Started"))
				block = false;
			} 
			if(block == false && count > 0 && !strLine.equals(""))
			{
				temp +=   alarmInfo ; 
				writer.write(temp);
				writer.newLine();  
				temp = "";
				alarmInfo = "";
			}
		}
		
		 
	}catch (Exception e) {
		System.out.println("strLine: "+strLine); 
	} 
	 finally {
			if (reader != null) {
				try {
					reader.close();
					writer.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
	} 
	private static boolean isValidNumber(String number) {
		
		try {
			Integer.parseInt(number);
			return true;
		} catch(Exception ex) {
			return false;
		}

	}
	private static String unSpace(String strLine){
		while(strLine.indexOf("  ") > 0){
			strLine = strLine.replace("  ", " ");
		}
		return strLine;
	}
	 
}
