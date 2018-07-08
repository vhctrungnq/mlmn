package controller;

import java.io.BufferedReader; 
import java.io.FileInputStream;
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

public class AlarmEricssonConverter{
	private static final Logger logger = Logger
			.getLogger(AlarmEricssonConverter.class.getName()); 
	
	private DateFormat df_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	private String  _Ne = "", _Bts = "";
	
	List<RAlarmLogTemp> alarmLogList = new ArrayList<RAlarmLogTemp>();
	
	public List<RAlarmLogTemp> convertFile(InputStream pathFile, String userName){ 
		try {
			//Tao mot con tro de connect den pathFile  
		//	FileInputStream pathFile1 = new FileInputStream(pathFile);
			
			Reader reader = new InputStreamReader(pathFile); 
			
			//Doc tung byte
			BufferedReader buffReader = new BufferedReader(reader); 
			
			String strLine = null; 
			boolean block = false; 
			String[] str = new String[30];
			String line = "";
			int i=0;
			int count = 0, count1 = 0;
			while ((strLine = buffReader.readLine()) != null)
			{
				//System.out.println("Dong "+i +":"+ strLine);
				count++;
				if(strLine.contains("=======")){  
					block = true;
					i = 0; 
					str = new String[30];
					continue;
				} 
				
				if(block == true){ 
					try {
						if(i == 12 || i == 17 || i == 19){
							String[] str1 = strLine.split(" ");
							str[i] = str1[1]+ " " +str1[2];
						}else if(i == 1){
							str[i] = strLine.split(":")[1].trim();
							String[] str2 = strLine.split(",");
							_Ne = str2[1].split("=")[1];
							_Bts = str2[2].split("=")[1];
							if(_Ne.equals(_Bts)){
								_Bts = "";
							}
						}else if(i == 2){
							String str3 = strLine.split(":")[1].trim();
							if(str3.equals("Major")|| str3.equals("Cleared")){
								str[i] = "A2"; 
							}else if(str3.equals("Critical")){
								str[i] = "A1"; 
							}else if(str3.equals("Warning")){
								str[i] = "O2"; 
							}else if(str3.equals("Minor")){
								str[i] = "A3"; 
							}else if(str3.equals("Indeterminate")){
								str[i] = "Indeterminate"; 
							}
						}else{
							str[i] = strLine.split(":")[1].trim();
						}
						
						if(i == 29){
							count1++;
							block = false; 
							RAlarmLogTemp record = new RAlarmLogTemp(); 
							
							record.setSdateStr(str[17]);
							record.setEdateStr(str[12]);
							record.setFmAlarmidStr(str[0]);
							
							try {
								record.setEdate(df_full.parse(str[12]));
							} catch (Exception e) {
								// TODO: handle exception
							}
							try {
								record.setSdate(df_full.parse(str[17])); 
							} catch (Exception e) {
								// TODO: handle exception
							}
							 
							record.setFmAlarmid(Integer.parseInt(str[0]));
							record.setObjectReference(str[1]);
							record.setSeverity(str[2]);
							record.setEvttype(str[18]);
							record.setCauseby(str[23]);
							record.setAlarmInfo(str[25]);
							record.setAlarmName(str[29]);
							record.setVendor("ERICSSON");
							record.setNe(_Ne);
							record.setSite(_Bts);
							record.setUsername(userName);
							String network;
							if(_Ne.substring(0,1).equalsIgnoreCase("B")){
								network = "2G";
							}else if(_Ne.substring(0,1).equalsIgnoreCase("R")){
								network = "3G";
							}else if(_Ne.substring(0,1).equalsIgnoreCase("S")||_Ne.substring(0,1).equalsIgnoreCase("G")){
								network = "PS_CORE";
							}else{
								network = "CS_CORE";
							}
							record.setNetwork(network);
							/*line += str[12]+";"+str[17]+";"+str[0]+";"+str[1]+";"+str[2]+";"+str[18]+";"
									+str[23]+";"+str[25]+";"+str[29]+";"+_Ne+";"+_Bts;
							System.out.println(line);*/
							//_Ne = ""; _Bts = ""; line = "";
							alarmLogList.add(record);
							//rAlarmLogTempDAO.insertSelective(record);
						}
						i++; 
					} catch (Exception e) {
						str[i] = ""; 
						i++;
					}  
				} 
			}
			System.out.println(count1 +"/"+ count);
			reader.close();
		}catch (Exception e) {
			logger.warn("Khong tim thay file " + e);
		}
		return alarmLogList;
	} 
}
