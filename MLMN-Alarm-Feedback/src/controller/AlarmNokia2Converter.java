package controller;

import inventory.utils.IN_DateTools;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat; 
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger; 
import org.springframework.beans.factory.annotation.Autowired;

import dao.RAlarmLogTempDAO;
  
import vo.RAlarmLogTemp; 

public class AlarmNokia2Converter{
	private static final Logger logger = Logger
			.getLogger(AlarmNokia2Converter.class.getName()); 
	
	private DateFormat df_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	
	private String alarmId = "", severity = "", bts = "", ne = "", alarmName = "",
			sdate = "", edate = "", ackDate = "", ackUser = "", alarmInfo = "";
	
	List<RAlarmLogTemp> alarmLogList = new ArrayList<RAlarmLogTemp>();
	
	@Autowired
	private RAlarmLogTempDAO rAlarmLogTempDAO;
	
	public List<RAlarmLogTemp> convertFile(InputStream pathFile, String userName) throws NumberFormatException, IOException{  		
		
		/*try {*/
			//Tao mot con tro de connect den pathFile
			//FileInputStream fileInPutStream = new FileInputStream(pathFile);
			Reader reader = new InputStreamReader(pathFile);
			//Doc tung byte
			BufferedReader buffReader = new BufferedReader(reader); 
			
			String strLine = null;
			int count = 0; 
			boolean block = false;  
			//	int count1 = 0;
			System.out.println("userName: "+userName);
			while ((strLine = buffReader.readLine()) != null)
			{
				strLine = strLine.trim();
				
				if(strLine.contains("*") || IN_DateTools.isValidNumber(strLine.split(" ")[0])){
					block = true;
					count = 0; 
				} 
				
				if(block == true){
					count ++;
				}else{
					count = 0;
				}
				
				if(count == 1){
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
				}else 
				if(count == 2){
					strLine = unSpace(strLine);
					bts = strLine.split(" ")[0];
					continue;
				}
				if(count == 3){
					ne =  strLine.trim();
					continue;
				}
				if(count == 4){
					alarmName = strLine.trim();  
					continue;
				}
				if(count == 5){ 
					strLine = unSpace(strLine.trim());
					String[] str = strLine.split(" ");
					try { 
						sdate = str[1]+" "+str[2];
						edate = str[4]+" "+str[5];
					} catch (Exception e) { 
					}  
					continue;
				}
				if(count > 5){
					strLine = unSpace(strLine.trim());
					alarmInfo += strLine+">>>";
				}
				if(strLine.contains("Acked by")){
					strLine = strLine.trim();
					ackDate = strLine.substring(strLine.indexOf("Acked")+6, strLine.lastIndexOf("Acked") - 1);
					ackUser = strLine.substring(strLine.lastIndexOf(" ")+1);
					continue;
				}
				
				if(strLine.length() == 0 && block == true){
					block = false;
					RAlarmLogTemp record = new RAlarmLogTemp();
					
					record.setAlarmId(Integer.parseInt(alarmId));
					record.setSeverity(severity);
					record.setSite(bts);
					record.setNe(ne);
					record.setAlarmName(alarmName);
					record.setVendor("NOKIA SIEMENS");
					record.setUsername(userName);
					String network;
					
					if(ne.substring(0,1).equalsIgnoreCase("B")){
						network = "2G";
					}else if(ne.substring(0,1).equalsIgnoreCase("R")){
						network = "3G";
					}else if(ne.substring(0,1).equalsIgnoreCase("S")||ne.substring(0,1).equalsIgnoreCase("G")){
						network = "PS_CORE";
					}else{
						network = "CS_CORE";
					}
					record.setNetwork(network);
					
					try {
						record.setSdate(df_full.parse(sdate));  
					} catch (ParseException e) {
						// TODO Auto-generated catch block 
					}
					try {
						record.setEdate(df_full.parse(edate));
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					try {
						record.setAckTime(df_full.parse(ackDate)); 
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					record.setAckUser(ackUser);
					record.setAlarmInfo(alarmInfo);
					/*alarmLogList.add(record);
					System.out.println(alarmId+";"+severity+";"+bts+";"+alarmName+";"+sdate+";"+edate+";"
							+ackDate+";"+ackUser+";"+alarmInfo); 
					count1++;*/
					try {
						rAlarmLogTempDAO.insertNokiaTemp(record);
						System.out.println("Insert success");
						//System.out.println(alarmId+";"+severity+";"+bts+";"+alarmName+";"+sdate+";"+edate+";"+ackDate+";"+ackUser+";"+alarmInfo);
						
					} catch (Exception e) {
						// TODO: handle exception
						logger.warn(e);
						alarmLogList.add(record);
					}   
					refresh();
					continue;
				}
			}
			rAlarmLogTempDAO.insertNokia("NOKIA SIEMENS", userName);
			reader.close();
		/*}catch (Exception e) {
			logger.warn("Khong tim thay file " + e);
		} */
		System.out.println(alarmLogList.size()); 
		return alarmLogList;
	} 
	
	private void refresh(){ 
		alarmId = ""; 
		severity = ""; 
		bts = "";
		ne = ""; 
		alarmName = "";
		sdate = ""; 
		edate = "";
		ackDate = "";
		ackUser = "";
		alarmInfo = "";
	}
	
	private String unSpace(String strLine){
		while(strLine.indexOf("  ") > 0){
			strLine = strLine.replace("  ", " ");
		}
		return strLine;
	}
}
