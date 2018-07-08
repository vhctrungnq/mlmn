package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

/**
 * @author anhnt
 * @createDate 10:04:37 AM
 * TaiThietBiTruyenDanConverter.java
 *
 */
public class TaiThietBiTruyenDanConverter extends STS_BasicConverter {
	private static final Logger logger = Logger.getLogger(TaiThietBiTruyenDanConverter.class.getName());

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter wTaiThietBi = null;
		String strLine = "";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		String ne = "",slot = "";
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wTaiThietBi = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			wTaiThietBi.write("#time,ne,slot,cpuave,cpumax,cache,memory\n"); 
			
			String f = file.getName().substring(file.getName().indexOf(".")+1,file.getName().indexOf("-"));
			ne = f.substring(0, f.lastIndexOf(".")); 
			slot = f.substring(f.lastIndexOf("slot"), f.length()); 
			
			while ((strLine = reader.readLine()) != null){
				String time = "",cpuave = "",cpumax = "",cache = "",memory = ""; 
				strLine = strLine.trim();
				if(strLine.trim().length() == 0) 
					continue;
				
				if(strLine.contains("show cpu load")){
					continue;
				}else if(strLine.contains("#")){
					continue;
				}else if(strLine.contains("date")){
					continue;
				}else if(strLine.contains("enable")){
					continue;
				}else if(strLine.contains("ERROR")){
					continue;
				}else{
					while(strLine.contains("  ")){
						strLine = strLine.replace("  ", " ");
					}
					
					String[] str = strLine.split(" ");
					time = getDate7(str[0]+"/"+year+" "+str[1]);
					cpuave = str[2];
					cpumax = str[3];
					cache = str[4];
					memory = str[5];
				}
				
				String s = time+","+ne+","+slot+","+cpuave+","+cpumax+","+cache+","+memory;
				
				wTaiThietBi.write(s);
				wTaiThietBi.newLine();
			}
		}catch (Exception e) {
			logger.warn("Error Convert " + e + " strLine = "+ strLine);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					wTaiThietBi.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
		logger.info("Convert file: " + file.getName() + " success");
	}
	
	private String getDate7(String strDate){
		String str="";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			Date date = df.parse(strDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR, 7);
			str = df.format(cal.getTime());
		} catch (ParseException e) { 
			e.printStackTrace();
		}
		return str;
	}
}