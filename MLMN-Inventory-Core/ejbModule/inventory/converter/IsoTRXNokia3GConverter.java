package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.util.exceptions.IN_ConvertException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;

//Su dung de convert file du lieu Cell Nokia 3G
public class IsoTRXNokia3GConverter extends IN_BasicConverter {
	private static final Logger logger = Logger.getLogger(IsoTRXNokia3GConverter.class.getName());

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			wAlarmLog.write("#DAY;VENDOR;NETWORK;NE_TYPE;NE;SITE;CELL;C_ID;STATE;HSDPA;HSUPA;DC_HSDPA;HS_FACH;MIMO\n");
			
			boolean block = false;
			int count = 0;
			int[] lengthArr = new int[5]; 
			
			String vendor = "NOKIA SEIMENS";
			String network = "3G";
			String neType = "RNC";  
			String ne = outFile.getName().substring(outFile.getName().indexOf(".")+1, outFile.getName().indexOf(".")+8);
			String day = outFile.getName().substring(outFile.getName().indexOf("-")+1, outFile.getName().indexOf("-")+9);
			String strLine = "";
			
			while ((strLine = reader.readLine()) != null){ 
				if(strLine.length() < 1){
					continue;
				}
				
				if(strLine.contains("----") && count == 0){
					block = true;
				}else if(strLine.contains("----") && count != 0){
					block = false;
				}
				
				if(block){
					count++;
					try {
						if(count == 2){
							lengthArr = new int[8];
							lengthArr[0] = strLine.substring(0, strLine.indexOf("WCEL")).length();
							lengthArr[1] = lengthArr[0] + strLine.substring(strLine.indexOf("WCEL"), strLine.indexOf("C-ID")).length();
							lengthArr[2] = lengthArr[1] + strLine.substring(strLine.indexOf("C-ID"), strLine.indexOf("State")).length();
							lengthArr[3] = lengthArr[2] + strLine.substring(strLine.indexOf("State"), strLine.indexOf("HSDPA")).length();
							lengthArr[4] = lengthArr[3] + strLine.substring(strLine.indexOf("HSDPA"), strLine.indexOf("HSUPA")).length();
							lengthArr[5] = lengthArr[4] + strLine.substring(strLine.indexOf("HSUPA"), strLine.indexOf("DC-")).length();
							lengthArr[6] = lengthArr[5] + strLine.substring(strLine.indexOf("DC-"), strLine.indexOf("HS-")).length();
							lengthArr[7] = lengthArr[6] + strLine.substring(strLine.indexOf("HS-"), strLine.indexOf("MIMO")).length();
						}
					} catch (Exception e) {
						// TODO: handle exception
						logger.warn(strLine+ "---"+e);
					} 
					
					try {
						if(count >= 5){
							String wbts = strLine.substring(0, lengthArr[0]).trim();
							String wcel = strLine.substring(lengthArr[0], lengthArr[1]).trim();
							String cId = strLine.substring(lengthArr[1], lengthArr[2]).trim();
							String state = strLine.substring(lengthArr[2], lengthArr[3]).trim();
							String hsdpa = strLine.substring(lengthArr[3], lengthArr[4]).trim();
							String hsupa = strLine.substring(lengthArr[4], lengthArr[5]).trim();
							String dcHsdpa = strLine.substring(lengthArr[5], lengthArr[6] - 1).trim();
							String hsFach = strLine.substring(lengthArr[6] - 1, lengthArr[7]).trim();
							String mimo = strLine.substring(lengthArr[7]).trim();
							
							if(wbts.contains("WBTS")){
								String line =day+";"+vendor+";"+network+";"+neType+";"+ne+";"+wbts+";"+wcel+";"+
										cId+";"+state+";"+hsdpa+";"+hsupa+";"+dcHsdpa+";"+hsFach+";"+mimo;
						
								//System.out.println(line);
								wAlarmLog.write(line);
								wAlarmLog.newLine();
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						logger.warn(strLine+ "---"+e);
					} 
				}
			}
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMSC2-0306", e);
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
		logger.info("Convert file: " + file.getName() + " success");
	}
}
