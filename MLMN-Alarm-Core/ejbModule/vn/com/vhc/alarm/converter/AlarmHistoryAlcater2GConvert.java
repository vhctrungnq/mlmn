package vn.com.vhc.alarm.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;  

// Su dung de convert file du lieu History Alcater 2G
public class AlarmHistoryAlcater2GConvert extends AL_BasicConverter {
	private String createDate = "";
	private static final Logger logger = Logger
			.getLogger(AlarmHistoryAlcater2GConvert.class.getName());

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 
		 
		SimpleDateFormat full_date = new SimpleDateFormat("yyyyMMddHHmmss");
    	SimpleDateFormat full_date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			wAlarmLog.write("#ATYPE;EVTTIME;VENDOR;ANETWORK;NE_TYPE;BSC;SITE;CELL;OBJ_TYPE;PORT;RACK;SUBRACK;SLOT;FRDNAME_INFO;SEV;EVTTYPE;PBCAUSE;SPECPB;ACKSTS;CLRSTS;CLRTIME;USRNOTE;CREATE_DATE;ALARM_TYPE\n");			
			
			String strLine = "";
			boolean alarmBlock = false;
			String line ="";
			String temp = "",alarm_info = "", strBsc = "",strSite="",strCell="",strSEV="";
			String strType = "", objType = "", port = "", rack = "", subrack = "", slot = ""; 
			String vendor = "ALCATEL";
			String network = "2G";
			String netype = "";
			
			while ((strLine = reader.readLine()) != null){
				if(strLine.contains("EVTTIME;FRDNAME")){
					alarmBlock = true;
				}else{
					if(alarmBlock == true){
						try {

							String[] str = strLine.split(";");  
							//EVTTIME 
							str[0] = full_date1.format(full_date.parse(str[0].substring(0,14)));
							alarm_info = 	str[1];	
							int num = Integer.parseInt(str[2].substring(str[2].indexOf("(")+1,str[2].indexOf(")")));//SEV
							if(num == 0){
								strSEV = "Indeterminate";
							}else {
								strSEV = "A"+num;
							}
							
						String str5 = str[5].substring(0, 3);
							if(str[5].contains("OMU") || str[5].contains("BTS") || str5.contains("ENV") || str5.contains("CLK") 
									|| str5.contains("RX-") || str[5].contains("audit failed") || str5.contains("SW-")){
								try {
									strBsc = str[1].substring(0,7);// Bsc
								} catch (Exception e) {
									System.out.println("error1:"+strLine);
									// TODO: handle exception
									logger.warn("Not value BSC :"+outFile.getParent()+"/"+outFile.getName()+":"+strLine);
								} 
								
								if(str[1].length() >= 13)
								{
									String[] str2 = str[1].split(" ");
									strSite = str2[1];
									for(int i=2;i<str2.length;i++){
										temp += str2[i]+ " ";
									} 
									netype = "BTS";
								} 
							}else if(str5.contains("CEL") || str[5].contains("Loss of Packet Service")){
								String[] str2 = str[1].split(" ");
								
								strBsc = str2[0].toString().trim();// Bsc
								strSite = str2[1].toString().trim();// Site
								strCell = str2[2].toString().trim();// Cell
								netype = "CELL";
								temp="";
							}else{
								try {
									strBsc = str[1].substring(0,7);// Bsc
								} catch (Exception e) {
									strBsc = str[1].trim();// Bsc
								}
								
								strSite = ""; strCell = "";// Site,Cell 
								netype = "BSC"; 
								try{
									temp= str[1].substring(8,str[1].length()).trim();
								}catch(Exception e){
									temp = "";
									System.out.println("error2:"+e.getMessage());
								} 
							}
							
							try{
								String[] block = temp.split(" "); 
								
								// Phan tich loi Abis Ater AterMux
								if(block[0].contains("Abis") 
										|| block[0].contains("Ater") || block[0].equalsIgnoreCase("A")){ 
									objType = block[0];
									port = block[2];
									if(port.contains("[")){
										port = port.substring(0,port.indexOf("["));
									}
								}
								
								// Phan tich loi TC chap chon
								if(block[0].equalsIgnoreCase("TC")){
									objType = block[0];
									if(block[1].contains("rack")){ 
										rack = block[1].substring(block[1].lastIndexOf("k")+1,block[1].length());
										subrack = block[2].substring(block[2].lastIndexOf("f")+1,block[2].length());
										slot = block[4];
									}else{
										port = block[block.length -1];
									}
								} 
								
								// Phan tich thong tin port card
								if(block[0].contains("rack")){
									rack = block[0].substring(block[0].lastIndexOf("k")+1,block[0].length());
									if(block[1].equals("Shelf")){
										subrack = block[2]; 
									}else{
										subrack = block[1].substring(block[1].lastIndexOf("f")+1,block[1].length());
									}
								}
							}catch(Exception e){ 
								System.out.println("error3:"+strLine);
							}  
							
							try {
								if(!str[12].equalsIgnoreCase("")){
									str[12]= full_date1.format(full_date.parse(str[12].substring(0,14)));
									strType = "ALARM CLEARING";
								}else{
									strType = "ALARM FROM";
								}
								
								line = strType+";"+str[0]+";"+vendor+";"+network+";"+netype+";"+strBsc+";"+strSite+";"+strCell+";"+objType+";"+port+";"+rack+";"+subrack+";"+slot+";"+alarm_info.trim()+";"+strSEV+";"+str[3].trim()+";"
										+str[4].trim()+";"+str[5].trim()+";"+str[10].substring(0,str[10].indexOf(" "))+";"+str[11].substring(0,str[11].indexOf(" "))+";"+str[12].trim()+";"+str[13].trim()+ ";" + createDate ;
								// Added by TrungNQ 12/9/2016 tim du lieu mat dien theo alarm_name;
								
								List<String> powerRegexList = new ArrayList<String>();
								powerRegexList.add("(.*)(POWERPROBLEM)(.*)|");
								powerRegexList.add("(.*)(\\[9\\])(.*)(AC FAILURE)(.*)(\\[(82|83)\\])(.*)|");
								powerRegexList.add("(.*)(\\[9\\])(.*)(VIBA AC FAILURE)(.*)(\\[(85|89)\\])(.*)|");
								powerRegexList.add("(.*)(\\[5\\])(.*)(POWER-SUPPLY-U)(.*)(\\[11\\])(.*)|");
								powerRegexList.add("(.*)(\\[9\\])(.*)(ALARMBOX AC FAILURE)(.*)(\\[91\\])(.*)");
								powerRegexList.add("(.*)(\\[9\\])(\\s*)(MAINS-POWER-FAILURE-2)(\\s*)(\\[2\\])(.*)|");
								powerRegexList.add("(.*)(\\[9\\])(.*)(BATTERY-DEEP-DISCHARGE-11)(.*)(\\[(11|84)\\])(.*)|");
								powerRegexList.add("(.*)(\\[9\\])(\\s*)(INVERTER-FAILURE-16)(\\s*)(\\[16\\])(.*)|");
								powerRegexList.add("(.*)(\\[5\\])(.*)(MAINS-AL)(.*)(\\[20\\])(.*)|");
								powerRegexList.add("(.*)(\\[9\\])(\\s*)(BATTERY-DEEP-DISCHARGE)(\\s*)(\\[84\\])(.*)");
								String powerRegex = "";
								
								for (String regex : powerRegexList) {
									powerRegex += regex;
								}
								
								if (str[5].matches(powerRegex)) {
									line += ";POWER";
								} else {
									line = line + ";";
								}
								wAlarmLog.write(line);
								wAlarmLog.newLine();
							} catch (Exception e) { 
								try {
									line = "ALARM FROM"+";"+str[0]+";"+vendor+";"+network+";"+netype+";"+strBsc+";"+strSite+";"+strCell+";"+objType+";"+port+";"+rack+";"+subrack+";"+slot+";"+alarm_info.trim()+";"+strSEV+";"+str[3].trim()+";"
											+str[4].trim()+";"+str[5].trim()+";;;;";
									wAlarmLog.write(line);
									wAlarmLog.newLine();
								} catch (Exception e2) {
									logger.warn(strLine); 
									System.out.println("error4:"+strLine);
								}
							}
							
							line ="";alarm_info = ""; temp = "";strBsc = "";strSite="";strCell="";strSEV="";objType="";port="";rack="";subrack="";slot="";netype="";
						
						} catch (Exception e) {
							System.out.println("error5:"+strLine);
						}
					}
					else{
						String[] tempArray = strLine.split(";");
						createDate = tempArray[0] + " " + tempArray[1];
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
