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

//Su dung convert du lieu file Ericsson hsitory core
public class AlarmLogCoreConverter extends AL_BasicConverter {

	private static final Logger logger = Logger
			.getLogger(AlarmLogCoreConverter.class.getName());

	private String temp = "";
	private String temp1 = "";
	private String line = "";
	private int dem1 = 0;
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerAlarmLogsCore = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			System.out.println(outFile.getParent()+"/"+outFile.getName());
			writerAlarmLogsCore = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerAlarmLogsCore.write("#VENDOR;NETWORK;TYPE;OBJECT_OF_REPERENCE;NE_TYPE;MSCID;BC;LOG_RECORD_ID;SDATE;EDATE;EVENT_TYPE;OBJECT_CLASS;BACKUP_OBJECT;BACKUP_STATUS;TREND_INDICATION;ALARM_CLASS;PERCEIVED_SEVERITY;PROBABLE_CAUSE;ALARM_NAME;PROBLEM_TEXT;PROBLEM_DATA;CORRELATED_ID;PREVIOUS_SEVERITY;RECORD_TYPE;OBJECT_TYPE;LOGGING_TIME;REPEAT_COUNT;FM_ALARM_ID\n");
			String strLine = "";
			String vendor = "ERICSSON", network = "CS_CORE", neType = "";
			boolean blockAlarm = false;
			boolean blockProblemText = false;
			int dem = 0;
			boolean blockFmAlarmId = false; 
			boolean blockProblemData = false;
			int demProblem = 0; 
			String strMsc = "";
			while ((strLine = reader.readLine()) != null){
				//Giam so dau cach giua cac chu xuong 1
				while(strLine.indexOf("  ")>= 0)
				{
					strLine = strLine.replaceAll("  ", " ");
				}
				
				//Thay the dau ";" = "."(tranh truong hop bi sai khi cat theo dau ";" trong qua trinh import)
				strLine = strLine.replaceAll(";", ".");
				
				//Bat dau khoi loi
				if(strLine.contains("ALARM FROM") || strLine.contains("EVENT FROM")){
					try{
						//Danh dau ban ghi bat dau khoi loi
						line += vendor+";"+network+";"+"ALARM FROM;";
						
						String[] str = strLine.split(" ");
						String str2 = str[2].trim().substring(0, str[2].trim().indexOf("-"));
						line += str2+";";
						
						String[] strNode = str2.split(","); 
						
						//Doc ten MSC
						strMsc = strNode[2].trim().substring(strNode[2].trim().lastIndexOf("=")+1,
								strNode[2].length());
						try {
							if(strMsc.substring(0,2).equalsIgnoreCase("ST")){
								neType = "STP";
							}else if(strMsc.substring(0,2).equalsIgnoreCase("MS")){
								neType = "MSC";
							}else if(strMsc.substring(0,2).equalsIgnoreCase("MG")){
								neType = "MGW";
							}
						} catch (Exception e) {
							// TODO: handle exception
						} 
						
						line += neType+";"+strMsc+";";
						
						// Doc ten BC 
						if(strNode.length == 4)
							line += strNode[3].trim().substring(strNode[3].trim().lastIndexOf("=")+1,
									strNode[3].length())+";";
						else
							line += ";";
						
						blockAlarm = true;
					}catch(Exception e){
						logger.warn(strMsc+"Try line 1 : "+e);
					}
				} 
				
				//
				if(strLine.contains("ALARM CLEARING")){
					try{
						line += vendor+";"+network+";"+"ALARM CLEARING;";
						String[] str = strLine.split(" ");
						String str2 = str[3].trim().substring(0, str[3].trim().indexOf("-"));
						line += str2+";"; 
						String[] strNode = str2.split(","); 
						//Doc ten MSC
						strMsc = strNode[2].trim().substring(strNode[2].trim().lastIndexOf("=")+1,
								strNode[2].length());
						try {
							if(strMsc.substring(0,2).equalsIgnoreCase("ST")){
								neType = "STP";
							}else if(strMsc.substring(0,2).equalsIgnoreCase("MS")){
								neType = "MSC";
							}else if(strMsc.substring(0,2).equalsIgnoreCase("MG")){
								neType = "MGW";
							}
						} catch (Exception e) {
							// TODO: handle exception
						} 
						
						line += neType+";"+strMsc+";";
						
						if(strNode.length == 4)
							line += strNode[3].trim().substring(strNode[3].trim().lastIndexOf("=")+1,
									strNode[3].length())+";"; 
						else
							line += ";"; 			
						blockAlarm = false;
					}catch(Exception e){
						logger.warn(strMsc+"Try line 1 : "+e);
					}
				}
				
				//
				if(strLine.contains("Log Record ID:")){
					simple(strLine);
				}else if(strLine.contains("Event Time:")){
					String[] str = strLine.split(" ");
					if(blockAlarm == true){
						line += str[2].trim().toString()+" "+str[3].trim().toString()+";;";
						}else{
						line += ";"+str[2].trim().toString()+" "+str[3].trim().toString()+";";
					}				
				}else if(strLine.contains("Event Type:")){
					simple(strLine);
				}else if(strLine.contains("Object Class:")){
					simple(strLine);
				}else if(strLine.contains("Backup Object:")){
					simple(strLine);
				}else if(strLine.contains("Backup Status:")){
					simple(strLine);
				}else if(strLine.contains("Trend Indication:")){
					simple(strLine);
				}else if(strLine.contains("Perceived Severity:")){
					String[] str = strLine.split(":");
					String str1 = str[1].trim().toString();
					
					if(str1.equals("Major")|| str1.equals("Cleared")){
						line += "A2;";
					}else if(str1.equals("Critical")){
						line += "A1;";
					}else if(str1.equals("Warning")){
						line += "O2;";
					}else if(str1.equals("Minor")){
						line += "A3;";
					}else if(str1.equals("Indeterminate")){
						line += "Indeterminate;";
					}
					line += str1+";";
				}else if(strLine.contains("Probable Cause:")){
					simple(strLine);
				}else if(strLine.contains("Specific Problem:")){
					simple(strLine);
				}else if(strLine.contains("Problem Text:")){
					dem=0;
					blockProblemText = true;
				}
				if(blockProblemText == true){
					if(!strLine.contains("Problem Data:")){
						dem++;
						if(dem >= 2){
							if(!strLine.equals(""))
								temp = temp + strLine.trim().toString()+ "<->";
						}
					}else{
						while(temp.indexOf("<-><->")>= 0)
						{
							temp = temp.replaceAll("<-><->", "<->");
						}
						temp = temp.replaceAll("<->END<->","");
						line += temp+";";				
						blockProblemText = false;
						temp = "";
					}
				}
				
				if(blockProblemData == true){
					if(!strLine.contains("Correlated ID:")){
						demProblem++;
						if(demProblem == 2){
							if(!strLine.equals(""))
								line += strLine.trim().toString();
						}								
					}else{
						line += ";";
						blockProblemData = false;
						demProblem = 0;
					}
				}
				if(strLine.contains("Problem Data:")){
					simple(strLine);
				}else if(strLine.contains("Correlated ID:")){
					simple(strLine);
				}else if(strLine.contains("Previous Severity:")){
					simple(strLine);
				}else if(strLine.contains("Record Type:")){
					simple(strLine);
				}else if(strLine.contains("Object Type:")){
					simple(strLine);
				}else if(strLine.contains("Logging Time:")){
					try{
						String[] str = strLine.split(" ");
						if(str.length > 3){
							line += str[2].trim().toString()+" "+str[3].trim().toString()+";";
						}else{
							line += ";";
						}	
					}catch(Exception e){
					}		
				}else if(strLine.contains("FmAlarmId:")){ 
					dem1 = 0;
					blockFmAlarmId = true;
					String[] str = strLine.split(":");
					temp1 = temp1+str[1].trim().toString(); 
				} 
				if(blockFmAlarmId == true){
					if(!strLine.contains("ALARM END")){
						dem1++;
						if(dem1 == 2){
							if(strLine.trim().toString().equals("")){
								line += ";";
							}else{
								simple(strLine);
								//line += "repeat_count";
							}
						}
					}else{	
						if(!line.contains("repeat_count")){
							writerAlarmLogsCore.write(line+temp1);
							writerAlarmLogsCore.newLine(); 
						} 
						blockFmAlarmId = false;
						temp1 = "";
						line = "";
						dem1 = 0;
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
					writerAlarmLogsCore.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		} 
	}

	private void simple(String strLine) { 
		try{
			String[] str = strLine.split(":");
			line += str[1].toString().trim()+";";
		}catch(Exception e){
			line += ";";
		}		 
	}
}
