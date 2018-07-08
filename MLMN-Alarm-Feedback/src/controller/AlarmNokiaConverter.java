package controller;

import java.io.BufferedReader; 
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat; 
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger; 
  
import vo.RAlarmLogTemp; 

public class AlarmNokiaConverter{
	private static final Logger logger = Logger
			.getLogger(AlarmNokiaConverter.class.getName()); 
	
	private DateFormat df_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	
	private String alarmId = "", severity = "", bts = "", ne = "", alarmName = "",
			sdate = "", edate = "";
	
	List<RAlarmLogTemp> alarmLogList = new ArrayList<RAlarmLogTemp>();
	
	public List<RAlarmLogTemp> convertFile(InputStream pathFile, String userName){  		
		
		try {
			//Tao mot con tro de connect den pathFile
			//FileInputStream fileInPutStream = new FileInputStream(pathFile);
			Reader reader = new InputStreamReader(pathFile);
			//Doc tung byte
			BufferedReader buffReader = new BufferedReader(reader); 
			
			String strLine = null;
			int count = 0; 
			boolean block = false;  
					
			while ((strLine = buffReader.readLine()) != null)
			{
				if(strLine.contains("*")){
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
				}else if(count == 2){
					bts = strLine.trim(); 
				}else if(count == 3){
					ne =  strLine.trim(); 
				}else if(count == 4){
					alarmName = strLine.trim(); 
				}else if(count == 5){
					block = false;
					strLine = unSpace(strLine.trim());
					String[] str = strLine.split(" ");
					try { 
						sdate = str[1]+" "+str[2];
						edate = str[4]+" "+str[5];
					} catch (Exception e) { 
					} 
					
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
						record.setEdate(df_full.parse(edate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					
					alarmLogList.add(record);
					//rAlarmLogTempDAO.insertSelective(record);  
					refresh();
				}
			}
			reader.close();
		}catch (Exception e) {
			logger.warn("Khong tim thay file " + e) ;
		}
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
	}
	
	private String unSpace(String strLine){
		while(strLine.indexOf("  ") > 0){
			strLine = strLine.replace("  ", " ");
		}
		return strLine;
	}
}
