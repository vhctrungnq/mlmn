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

//Su dung de convert file du lieu Cell Ericsson 3G
public class IsoTRXEricsson3GConverter extends IN_BasicConverter {
	private static final Logger logger = Logger.getLogger(IsoTRXEricsson3GConverter.class.getName());

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
			wAlarmLog.write("#DAY;VENDOR;NETWORK;NE_TYPE;NE;SITE;CELL;PROXY;ADM;STATE_LOCK;OP;STATE_ABLED;MO\n");
			
			boolean block = false;
			int count = 0;
			int[] lengthArr = new int[5]; 
			
			String vendor = "ERICSSON";
			String network = "3G";
			String neType = "RNC"; 
			String day = "";
			String ne = outFile.getName().substring(outFile.getName().indexOf(".")+1, outFile.getName().indexOf(".")+8);
			String strLine = "";
			
			while ((strLine = reader.readLine()) != null){ 
				if(strLine.length() < 1){
					continue;
				}
				
				if(strLine.contains("st utrancell")){
					block = true;
					count = 0;
				}
				
				if(block){
					count ++;
					if(count == 2){
						day = strLine.substring(0, strLine.indexOf("-"));
					}
					
					if(count == 4){
						lengthArr = new int[5];
						lengthArr[0] = strLine.substring(0, strLine.indexOf("Adm")).length();
						lengthArr[1] = lengthArr[0] + strLine.substring(strLine.indexOf("Adm"), strLine.indexOf("State")).length();
						lengthArr[2] = lengthArr[1] + strLine.substring(strLine.indexOf("State"), strLine.indexOf("Op")).length();
						lengthArr[3] = lengthArr[2] + strLine.substring(strLine.indexOf("Op"), strLine.lastIndexOf("State")).length();
						lengthArr[4] = lengthArr[3] + strLine.substring(strLine.lastIndexOf("State"), strLine.indexOf("MO")).length();
					}
					
					if(count >= 6){
						if(strLine.contains("===")){
							block = false;
							continue;
						}else{
							String proxy = strLine.substring(0, lengthArr[0]).trim();
							String adm = strLine.substring(lengthArr[0], lengthArr[1]-2).trim();
							String stateLock = strLine.substring(lengthArr[1]-2, lengthArr[2]).trim();
							String op = strLine.substring(lengthArr[2], lengthArr[3]-2).trim();
							String stateAbled = strLine.substring(lengthArr[3]-2, lengthArr[4]).trim();
							String mo = strLine.substring(lengthArr[4]).trim();
							String cell = mo.substring(mo.lastIndexOf("=")+1);
							String site = cell.substring(0, cell.length()-1);
							//String info = proxy+" "+adm+" "+stateLock+" "+op+" "+stateAbled+" "+mo;
							
							String line = day+";"+vendor+";"+network+";"+neType+";"+ne+";"+site+";"+cell+";"+
											proxy+";"+adm+";"+stateLock+";"+op+";"+stateAbled+";"+mo;
							
							//System.out.println(line);
							wAlarmLog.write(line);
							wAlarmLog.newLine();
						} 
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
