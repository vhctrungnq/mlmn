package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.util.exceptions.IN_ConvertException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import org.apache.log4j.Logger;

// Su dung de convert file thong tin loai card Nokia 2G, 3G
public class AlarmCardTypeNokiaConverter extends IN_BasicConverter {
	private static final Logger logger = Logger.getLogger(AlarmCardTypeNokiaConverter.class.getName()); 
	DateFormat dfTime = new SimpleDateFormat("yyyyMMdd-HHmmss");
	private String sep = ";"; 
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter _writer = null; 	 
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			_writer = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			_writer.write("#DATETIME;VENDOR;NETWORK;NE_TYPE;NE;CARD_TYPE;PHYS;STATE;LOCATION;LOG;INFO\n");
			
			String strLine = "";
			String fileName = outFile.getName();
			String ne = fileName.substring(fileName.indexOf(".")+1, fileName.indexOf(".")+8);
			String timeFull = fileName.substring(fileName.indexOf("-")+1, fileName.indexOf("-")+16);
			String vendor = "NOKIA SEIMENS",neType="",network="";  
			
			String day = timeFull.substring(0, timeFull.indexOf("-"));
			String hour = timeFull.substring(timeFull.indexOf("-")+1,timeFull.indexOf("-")+3);
			
			if(ne.substring(0, 1).equals("B")){
				neType = "BSC";
				network = "2G";
			}else if(ne.substring(0, 1).equals("R")){
				neType = "RNC";
				network = "3G";
			} 
			
			int[] lengthArr = new int[4];
			boolean blockBsc = false;
			boolean blockRnc = false; 
			
			while ((strLine = reader.readLine()) != null){
				if(strLine.length() < 1){
					continue;
				}
				
				try {
					if(strLine.contains("UNIT") && strLine.contains("PHYS")){
						lengthArr = new int[4];
						if(neType.equals("BSC")){
							lengthArr[0] = strLine.substring(0, strLine.indexOf("PHYS")).length(); 
							lengthArr[1] = lengthArr[0] + strLine.substring(strLine.indexOf("PHYS"), strLine.indexOf("STATE")).length();
							lengthArr[2] = lengthArr[1] + strLine.substring(strLine.indexOf("STATE"), strLine.indexOf("LOCATION")).length();
							lengthArr[3] = lengthArr[2] + strLine.substring(strLine.indexOf("LOCATION"), strLine.indexOf("INFO")).length();	
							blockBsc = true;
							continue;
						} 
						
						if(neType.equals("RNC")){
							lengthArr[0] = strLine.substring(0, strLine.indexOf("PHYS")).length(); 
							lengthArr[1] = lengthArr[0] + strLine.substring(strLine.indexOf("PHYS"), strLine.indexOf("LOG")).length();
							lengthArr[2] = lengthArr[1] + strLine.substring(strLine.indexOf("LOG"), strLine.indexOf("STATE")).length();
							lengthArr[3] = lengthArr[2] + strLine.substring(strLine.indexOf("STATE"), strLine.indexOf("INFO")).length();
							blockRnc = true;
							continue;
						} 
					} 
					
					if(strLine.contains("TOTAL OF") || strLine.contains("NUMBER OF")){
						blockBsc = false;
						blockRnc = false;
					}
					
					if(blockBsc){
						String cardType = strLine.substring(0, lengthArr[0]).trim();
						String phys = strLine.substring(lengthArr[0], lengthArr[1]).trim();
						String state = strLine.substring(lengthArr[1], lengthArr[2]).trim();
						String location = strLine.substring(lengthArr[2], lengthArr[3]).trim();
						String info = strLine.substring(lengthArr[3]).trim();
						String log = "";
						
						String line = timeFull+sep+vendor+sep+network+sep+neType+sep+ne+sep+cardType+sep+
								phys+sep+state+sep+location+sep+log+sep+info;
						_writer.write(line);
						_writer.newLine();
					}
					
					if(blockRnc){
						String cardType = strLine.substring(0, lengthArr[0]).replace(">", "").trim();
						String phys = strLine.substring(lengthArr[0], lengthArr[1]).trim();
						String log = strLine.substring(lengthArr[1], lengthArr[2]).trim();
						String state = strLine.substring(lengthArr[2], lengthArr[3]).trim();
						String info = strLine.substring(lengthArr[3]).trim();
						String location = "";
						
						String line = timeFull+sep+vendor+sep+network+sep+neType+sep+ne+sep+cardType+sep+
								phys+sep+state+sep+location+sep+log+sep+info;
						_writer.write(line);
						_writer.newLine();
					}
				} catch (Exception e) {
					// TODO: handle exception
					logger.warn("Try line: "+strLine);
				}
			}
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					_writer.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
	}
} 