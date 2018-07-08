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

public class AlarmLogNokia2GConverter extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmLogNokia2GConverter.class.getName());

	private String str2 = "";
	private String str3 = "";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		 
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLogNokia2G = null; 	
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLogNokia2G = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			wAlarmLogNokia2G.write("#BSCID;SITEID;ALARM_DATA;CELLID;ALARM_CLASS;TYPE;SDATE;EDATE;DIP;STATUS;ALARM_NUMBER;ALARM_ID;ALARM_NAME");
			
			boolean blockAlarm = false;
			boolean blockTruyenDan = false;
			boolean blockGiamSat = false;
			boolean blockCauHinh = false;
			String strLine = "";
			int count = 0;
			
			while ((strLine = reader.readLine()) != null){
				while(strLine.indexOf("  ")>= 0)
				{
					strLine = strLine.replaceAll("  ", " ");
				}
				
				strLine = strLine.replaceAll(";", ".");
				
				if(strLine.contains("NR=7704")){
					blockTruyenDan = true;
					blockGiamSat = false;
					blockCauHinh = false;
				}
				if(strLine.contains("NR=7706")){
					blockTruyenDan = false;
					blockGiamSat = true;
					blockCauHinh = false;
				}
				if(strLine.contains("NR=7767")){
					blockTruyenDan = false;
					blockGiamSat = false;
					blockCauHinh = true;
				}
				if(strLine.contains("<HIST>")){
					count = 0;
					wAlarmLogNokia2G.newLine();
					blockAlarm = true;
				}
				
				if(blockAlarm == true){
					
					//Doc loi mat cau hinh
					if(blockCauHinh == true){
						try{
							count = count + 1;
							if(count == 1){
								String[] str = strLine.split(" ");
								str3 = str[4].trim().toString()+";";
								wAlarmLogNokia2G.write(str[2].trim().toString()+";"+str[3].trim().toString()+";"+str[5].trim().toString()+";");
								str2 = str[6].trim().toString()+" "+str[7].trim().toString().substring(0, str[7].trim().toString().indexOf("."))+";";
							}
							if(count == 2){
								String[] str = strLine.split(" ");
									wAlarmLogNokia2G.write(str[2]+";");
								if(str[0].equals("***")){
									wAlarmLogNokia2G.write("A1;");
								}else if(str[0].equals("**")){
									wAlarmLogNokia2G.write("A2;");
								}else if(str[0].equals("*")){
									wAlarmLogNokia2G.write("A3;");
								}else{
									wAlarmLogNokia2G.write(";");
								}
								if(strLine.contains("CANCEL")){
									wAlarmLogNokia2G.write("ALARM CLEARING;");
									wAlarmLogNokia2G.write(";"+str2+str3);//Thoi diem ket thuc canh bao			
								}
								else{
									wAlarmLogNokia2G.write("ALARM FROM;");
									wAlarmLogNokia2G.write(str2+";"+str3);// Thoi diem xuat hien canh bao
								}
							}
							if(count == 3){
								wAlarmLogNokia2G.write(strLine.toString().trim()+";");
							}
							if(count == 4){
								String[] str = strLine.split(" ");
								String str1 = str[1].trim().toString().substring(str[1].indexOf("(")+1,str[1].length()-1);
								wAlarmLogNokia2G.write(str1+";"+str[2].trim().toString()+";");
								for(int i=3; i<str.length-1; i++){
									wAlarmLogNokia2G.write(str[i].trim().toString()+" ");
								}
								wAlarmLogNokia2G.write(str[str.length-1].trim().toString());
							}
						}catch(Exception e){
							//logger.warn("Try alarm mat cau hinh 7767 : "+e);
						}	
					}
					
					// Doc loi mat truyen dan va loi mat giam sat tram
					if(blockTruyenDan == true || blockGiamSat == true){
						try{
							count = count + 1;
							if(count == 1){
								String[] str = strLine.split(" ");
								wAlarmLogNokia2G.write(str[2].trim().toString()+";"+str[3].trim().toString()+";"+str[4].trim().toString()+";");
								str2 = str[5].trim().toString()+" "+str[6].trim().toString().substring(0, str[6].trim().toString().indexOf("."))+";";
							}
							if(count == 2){
								String[] str = strLine.split(" ");
								wAlarmLogNokia2G.write(";");
								if(str[0].equals("***")){
									wAlarmLogNokia2G.write("A1;");
								}else if(str[0].equals("**")){
									wAlarmLogNokia2G.write("A2;");
								}else if(str[0].equals("*")){
									wAlarmLogNokia2G.write("A3;");
								}else{
									wAlarmLogNokia2G.write(";");
								}
								if(strLine.contains("CANCEL")){
									wAlarmLogNokia2G.write("ALARM CLEARING;");
									wAlarmLogNokia2G.write(";"+str2);//Thoi diem ket thuc canh bao			
								}
								else{
									wAlarmLogNokia2G.write("ALARM FROM;");
									wAlarmLogNokia2G.write(str2+";");// Thoi diem xuat hien canh bao
								}
							}
							if(count == 3){
									String[] str = strLine.split(" ");
									int strLength = str.length;
									if(strLength == 4){
										wAlarmLogNokia2G.write(str[1].trim().toString()+str[2].trim().toString()+";"+str[3].trim().toString()+";");
									}
									else if(strLength == 3){
										wAlarmLogNokia2G.write(str[1].trim().toString()+";"+str[2].trim().toString()+";");
									}
									else{
										wAlarmLogNokia2G.write(str[1].trim().toString()+";"+";");
									}
								}
							if(count == 4){
								String[] str = strLine.split(" ");
								String str1 = str[1].trim().toString().substring(str[1].indexOf("(")+1,str[1].length()-1);
								wAlarmLogNokia2G.write(str1+";"+str[2].trim().toString()+";");
								for(int i=3; i<str.length-1; i++){
									wAlarmLogNokia2G.write(str[i].trim().toString()+" ");
								}
								wAlarmLogNokia2G.write(str[str.length-1].trim().toString());
							}
						}catch(Exception e){
							//logger.warn("Try alarm mat truyen dan 7704, 7706 :"+e);
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
					wAlarmLogNokia2G.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
		//logger.info("Convert file: " + file.getName() + " success");
	}
}
