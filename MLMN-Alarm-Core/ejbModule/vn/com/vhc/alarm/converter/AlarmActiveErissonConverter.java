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

// Su dung de convert file du lieu Active Ericsson 2G, STP
public class AlarmActiveErissonConverter extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmActiveErissonConverter.class.getName());

	@SuppressWarnings("unused")
	private int g = 0;
	private String network = "";
	private String _phancach = ",";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 	
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			wAlarmLog.write("#VENDOR,NETWORK,ALARM_CLASS,NE,ALARM_LOG_ID,SDATE,ALARM_NAME,MO,SITE,CELL,DIP,CHTYPE,ALARM_INFO,NE_TYPE\n");
			
			boolean blockAlarm = false;//Danh dau bat dau khoi Alarm
			boolean block = false;
			boolean block1 = false; // Danh dau bat dau khoi canh bao "RADIO X-CEIVER ADMINISTRATION"
			boolean block2 = false; // Danh dau bat dau khoi canh bao "CELL LOGICAL CHANNEL AVAILABILITY SUPERVISION"
			boolean block3 = false; // Danh dau bat dau khoi canh bao chua "DIGITAL PATH QUALITY SUPERVISION"
			boolean block4 = false; // Cac truong hop khac
			
			
			boolean block_Alarm_Type = false; // Phan tach loi trong khoi canh bao chua "DIGITAL PATH QUALITY SUPERVISION" 
 
			String strLine = "";
			String bscName = "";
			int count = 0;
			String alarmInfo = "";
			String vendor = "ERICSSON";
			String neType = "";
			String site = "";
			String sologan = "";
			
			bscName = outFile.getName().substring(outFile.getName().indexOf(".")+1, outFile.getName().indexOf(".")+8);
			
			while ((strLine = reader.readLine()) != null){
				
				while(strLine.indexOf("  ")>= 0)
				{
					strLine = strLine.replaceAll("  ", " ");
				}
				strLine = strLine.replaceAll(_phancach, ".");
				strLine = strLine.toString().trim();
				//Type
				if(bscName.substring(0,1).equalsIgnoreCase("B")){
					network = "2G";
				}else{
					network = "CS_CORE";
				}
				
				if(strLine.contains("ALARM LIST")){
					block = true;
				}
				
				if(block == true){
					if(strLine.contains(bscName) && strLine.split(" ").length > 4){
						if(!alarmInfo.equalsIgnoreCase("")){
							if(network.equals("2G")){
								if(site.equals("")){
									neType = "BSC";
								}else{
									neType = "BTS";
								}
							}else{
								neType = "STP";
							}
							
							wAlarmLog.write(alarmInfo+_phancach+neType);
							wAlarmLog.newLine();
						}
						neType = "";
						alarmInfo = ""; 
						count = 0; 
						blockAlarm = true;
					}		
					if(strLine.equalsIgnoreCase("END")){
						if(network.equals("2G")){
							if(site.equals("")){
								neType = "BSC";
							}else{
								neType = "BTS";
							}
						}else{
							neType = "STP";
						}
						
						wAlarmLog.write(alarmInfo+_phancach+neType); 
						blockAlarm = false;
					}
				}
				 
				if(blockAlarm == true){		
					count = count + 1;
					if(count > 0){
						if(strLine.length() > 1)
							alarmInfo = alarmInfo+"<->"+strLine.replace("\t", " ");
						//System.out.println("alarmInfo= "+alarmInfo);
					}
					if(count == 1){
						try{
							String[] str = strLine.split(" ");
							//type
							wAlarmLog.write(vendor+_phancach+network+_phancach);
							//alarm Class
							String alarmClass = str[0].substring(0,str[0].indexOf("/"));
							//bsc
							String bsc = str[1].substring(1,8);
							//alarm log id
							String alarmLogId = str[str.length - 3].toString().trim();
							//time
							String time = str[str.length-2]+" "+str[str.length-1];// yyMMdd HHmm
							
							wAlarmLog.write(alarmClass+_phancach+bsc+_phancach+alarmLogId+_phancach+time+_phancach);
							
						}catch(Exception e){
							//logger.warn("Try line 1 : "+e);
						}
						
					}else if(count == 2){
						//sologan
						sologan = strLine.toString().trim();
						if(sologan.equalsIgnoreCase("RADIO X-CEIVER ADMINISTRATION")){
							wAlarmLog.write(sologan+" ");
							block1 = true;
							block2 = false;
							block3 = false;
							block4 = false;
						}else if(sologan.equalsIgnoreCase("CELL LOGICAL CHANNEL AVAILABILITY SUPERVISION")){
							wAlarmLog.write(sologan+_phancach);
							block1 = false;
							block2 = true;
							block3 = false;
							block4 = false;
						}else if(sologan.contains("DIGITAL PATH QUALITY SUPERVISION")){
							wAlarmLog.write(sologan+_phancach);
							block1 = false;
							block2 = false;
							block3 = true;
							block4 = false;
						}else if(sologan.contains("DIGITAL PATH FAULT SUPERVISION")){
							wAlarmLog.write(sologan+_phancach);
							block1 = false;
							block2 = false;
							block3 = false;
							block4 = true;
						}else{
							// Cac truong hop khac
							wAlarmLog.write(sologan+_phancach);
							
							//mo
							String str0 = "";
							//site
							site = "";
							//cell
							String str2 = "";
							//dip
							String str3 = "";
							//chtype
							String str4 = "";
							
							wAlarmLog.write(str0+_phancach+site+_phancach+str2+_phancach+str3+_phancach+str4+_phancach);
							
							block1 = false;
							block2 = false;
							block3 = false;
							block4 = false;
						}
					}else if(count > 2){
						if(block1 == true){
							if(count == 3){
								wAlarmLog.write(strLine+_phancach);
							}
							if(count == 6){
								try{
									String[] str = strLine.split(" ");
									//mo
									String str0 = str[0].toString().trim();
									//site
									site = str[1].toString().trim();
									//cell
									String str2 = "";
									//dip
									String str3 = "";
									//chtype
									String str4 = "";
									wAlarmLog.write(str0+_phancach+site+_phancach+str2+_phancach+str3+_phancach+str4+_phancach);
								}catch(Exception e){
									logger.warn("Try line 6 : "+e);
								}								
							}
						}
						if(block2 == true){
							if(count == 3){
								 g = strLine.indexOf("S");
							}
							if(count == 4){
								try{
									String[] str = strLine.split(" ");
									//mo
									String str0 = "";
									//site
									site = str[0].toString().trim().substring(0, str[0].length()-1);
									//cell
									String str2 = str[0].toString().trim();
									//dip
									String str3 = "";
									//chtype
									String str4 = str[1].toString().trim();
									if(str4.contains("UL")||str4.contains("OL")){
										str4 = str[2].toString().trim();
									}	
									wAlarmLog.write(str0+_phancach+site+_phancach+str2+_phancach+str3+_phancach+str4+_phancach);
								}catch(Exception e){
									logger.warn("Try line 4 : "+e);
								}
							}			
						}
						
						if(block3 ==  true){
							if(count == 4){
								if(strLine.trim().equalsIgnoreCase("ESR")){
									block_Alarm_Type = true;
								}
							}
							
							if(count == 7 && block_Alarm_Type == true){
								String[] str = strLine.split(" ");
								//mo
								String str0 = "";
								//site
								site = "";
								//cell
								String str2 = "";
								//dip
								String str3 = str[0].toString().trim();
								
								/*@Author:AnhNT
								 * @Update:14-12-2013
								 */ 
								if(sologan.equalsIgnoreCase("DIGITAL PATH QUALITY SUPERVISION") && !str3.equals("")){
									site = str3.substring(0, str3.length()-1);
									str2 = str3;
								}
								
								//chtype
								String str4 = "";
								
								wAlarmLog.write(str0+_phancach+site+_phancach+str2+_phancach+str3+_phancach+str4+_phancach);
								block_Alarm_Type = false;
							}
							
							if(count == 6 && block_Alarm_Type == false){
								String[] str = strLine.split(" ");
								//mo
								String str0 = "";
								//site
								site = "";
								//cell
								String str2 = "";
								//dip
								String str3 = str[0].toString().trim();
								
								/*@Author:AnhNT
								 * @Update:14-12-2013
								 */ 
								if(sologan.equalsIgnoreCase("DIGITAL PATH QUALITY SUPERVISION") && !str3.equals("")){
									site = str3.substring(0, str3.length()-1);
									str2 = str3;
								}
								
								//chtype
								String str4 = "";
								
								wAlarmLog.write(str0+_phancach+site+_phancach+str2+_phancach+str3+_phancach+str4+_phancach);
							}					
						}
						
						if(block4 ==  true){
							try {
								if(count == 5){
									String[] str = strLine.split(" ");
									//mo
									String str0 = "";
									//site
									site = "";
									//cell
									String str2 = "";
									//dip
									String str3 = str[0].toString().trim();
									
									/*@Author:AnhNT
									 * @Update:14-12-2013
									 */ 
									if(sologan.equalsIgnoreCase("DIGITAL PATH FAULT SUPERVISION") && !str3.equals("")){
										site = str3.substring(0, str3.length()-1);
										str2 = str3;
									}
									
									//chtype
									String str4 = "";
									
									wAlarmLog.write(str0+_phancach+site+_phancach+str2+_phancach+str3+_phancach+str4+_phancach);
								}
							} catch (Exception e) {
								// TODO: handle exception
							}					
						}
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
}