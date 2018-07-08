package vn.com.vhc.alarm.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class RouterLogConvert extends AL_BasicConverter {
	private static final Logger logger = Logger.getLogger(RouterLogConvert.class.getName());
	
	private String year = "";
	private String createDate = "";
	private String local = "";
	private String ip = "";
	private String alarmLogId = "";
	private String time = "";
	private String routerName = "";
	private String alarmInfo = "";
	
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
			wAlarmLog.write("#CREATE_DATE;LOCAL;IP;ALARM_LOG_ID;ROUTER_NAME;TIME;ALARM_INFO");

			String strLine = "";
			while ((strLine = reader.readLine()) != null){
				strLine = strLine.trim().replace(";", ".");
				
				while(strLine.indexOf("  ") > 0){
					strLine = strLine.replace("  ",  " ");
				}
				
				if(!strLine.contains("HCSFG")){
					if(strLine.contains("%")){
						try{
							String[] str = strLine.split("%");
							
							String[] str1 = str[0].toString().trim().split(" ");
							
							int str1Length = str1.length;
							
							  year = str1[0].toString().trim().substring(0, 4);
							  createDate = str1[0].toString().trim()+" "+str1[1].toString().trim();
							  local = str1[2].toString().trim();
							  ip = str1[3].toString().trim();
							  alarmLogId = str1[4].toString().trim().substring(0,str1[4].lastIndexOf(":"));
							  
							  if(str1Length == 10 || str1Length == 9){
								  routerName = str1[5].toString().trim().substring(0,str1[5].lastIndexOf(":"));
							  }else{
								  routerName ="";
							  }
							
							  time = year+" "+str1[str1Length-3].toString().trim().replace(".", "")+" "+
									str1[str1Length-2].toString().trim()+" "+
									str1[str1Length-1].toString().trim().substring(0,8); 

							  alarmInfo = str[1].toString().trim();
							  
							  wAlarmLog.newLine();
							  wAlarmLog.write(createDate+";"+local+";"+ip+";"+alarmLogId+";"+routerName+";"+time+";"+alarmInfo);
							  this.refresh();
						}
						catch(Exception e){ 
						}
					}else{
						try{ 
							String[] str = strLine.split(" ");
							
							createDate = str[0] + " " +str[1];
							local = str[2];
							ip = str[3];
							time = str[6]+ " " + str[4]+ " " + str[5]+ " " + str[7];
							routerName = str[8];
							
							for(int i = 9; i< str.length; i++){
								alarmInfo += str[i]+" ";
							}
							
							wAlarmLog.newLine();
							wAlarmLog.write(createDate+";"+local+";"+ip+";"+alarmLogId+";"+routerName+";"+time+";"+alarmInfo);
							this.refresh();
						}
						catch(Exception e){ 
						}
					}
				}else{
					try{
						String[] str1 = strLine.split(" ");
						
						  year = str1[0].toString().trim().substring(0, 4);
						  createDate = str1[0].toString().trim()+" "+str1[1].toString().trim();
						  local = str1[2].toString().trim();
						  ip = "";
						  alarmLogId = "";
						  routerName = str1[3].toString().trim().substring(0,str1[3].lastIndexOf(":"));
						  
						  SimpleDateFormat full_date = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
					    	
						  SimpleDateFormat full_date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						  
						  Date date = null;
							try {
								date = full_date1.parse(createDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						  
						  time = full_date.format(date);
						  alarmInfo = str1[4].toString().trim();
						   for(int i=5; i< str1.length; i++){
							   alarmInfo = alarmInfo+" "+str1[i].toString().trim();
						   }
						   
						  wAlarmLog.newLine();
						  wAlarmLog.write(createDate+";"+local+";"+ip+";"+alarmLogId+";"+routerName+";"+time+";"+alarmInfo); 
						  this.refresh();
					}catch(Exception e){ 
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
	
	private void refresh(){
		year = "";
		createDate = "";
		local = "";
		ip = "";
		alarmLogId = "";
		time = "";
		routerName = "";
		alarmInfo = "";
	}
}
