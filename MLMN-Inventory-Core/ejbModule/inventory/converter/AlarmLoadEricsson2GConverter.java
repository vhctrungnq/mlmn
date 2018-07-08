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

// Su dung de convert file du lieu tai ericsson 2G
public class AlarmLoadEricsson2GConverter extends IN_BasicConverter {
	private static final Logger logger = Logger.getLogger(AlarmLoadEricsson2GConverter.class.getName()); 
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
			_writer.write("#DATETIME;VENDOR;NETWORK;NE_TYPE;NE;INT;PLOAD;CALIM;OFFDO;OFFDI;FTCHDO;FTCHDI;OFFMPH;OFFMPL;FTCHMPH;FTCHMPL\n");
			
			String strLine = "";
			String fileName = outFile.getName();
			String ne = fileName.substring(fileName.indexOf(".")+1, fileName.indexOf(".")+8);
			String timeFull = fileName.substring(fileName.indexOf("-")+1, fileName.indexOf("-")+16);
			String vendor = "ERICSSON",neType="BSC",network="2G";  
			
			/*String day = timeFull.substring(0, timeFull.indexOf("-"));
			String hour = timeFull.substring(timeFull.indexOf("-")+1,timeFull.indexOf("-")+3); */
			
			int[] lengthArr = new int[10];
			boolean block = false;
			
			while ((strLine = reader.readLine()) != null){
				if(strLine.length() < 1){
					continue;
				}
				 
				try {
					if(strLine.contains("PLOAD")){
						lengthArr = new int[10];
						lengthArr[0] = strLine.substring(0, strLine.indexOf("PLOAD")).length();
						lengthArr[1] = lengthArr[0]+ strLine.substring(strLine.indexOf("PLOAD"), strLine.indexOf("CALIM")).length();
						lengthArr[2] = lengthArr[1]+ strLine.substring(strLine.indexOf("CALIM"), strLine.indexOf("OFFDO")).length();
						lengthArr[3] = lengthArr[2]+ strLine.substring(strLine.indexOf("OFFDO"), strLine.indexOf("OFFDI")).length();
						lengthArr[4] = lengthArr[3]+ strLine.substring(strLine.indexOf("OFFDI"), strLine.indexOf("FTCHDO")).length();
						lengthArr[5] = lengthArr[4]+ strLine.substring(strLine.indexOf("FTCHDO"), strLine.indexOf("FTCHDI")).length();
						lengthArr[6] = lengthArr[5]+ strLine.substring(strLine.indexOf("FTCHDI"), strLine.indexOf("OFFMPH")).length();
						lengthArr[7] = lengthArr[6]+ strLine.substring(strLine.indexOf("OFFMPH"), strLine.indexOf("OFFMPL")).length();
						lengthArr[8] = lengthArr[7]+ strLine.substring(strLine.indexOf("OFFMPL"), strLine.indexOf("FTCHMPH")).length();
						lengthArr[9] = lengthArr[8]+ strLine.substring(strLine.indexOf("FTCHMPH"), strLine.indexOf("FTCHMPL")).length();
						block = true;
						continue; 
					} 
					
					if(block){
						if(strLine.contains("OFFTCAP") && strLine.contains("FTDTCAP")){
							block = false;
							continue; 
						}
						
						String _int = strLine.substring(0, lengthArr[0]).trim();
						String _pload = strLine.substring(lengthArr[0], lengthArr[1]).trim();
						String _calim = strLine.substring(lengthArr[1], lengthArr[2]).trim();
						String _offdo = strLine.substring(lengthArr[2], lengthArr[3]).trim();
						String _offdi = strLine.substring(lengthArr[3], lengthArr[4]).trim();
						String _ftchdo = strLine.substring(lengthArr[4], lengthArr[5]).trim();
						String _ftchdi = strLine.substring(lengthArr[5], lengthArr[6]).trim();
						String _offmph = strLine.substring(lengthArr[6], lengthArr[7]).trim();
						String _offmpl = strLine.substring(lengthArr[7], lengthArr[8]).trim();
						String _ftchmph = strLine.substring(lengthArr[8], lengthArr[9]).trim();
						String _ftchmpl = strLine.substring(lengthArr[9]).trim();
						
						String line = timeFull+sep+vendor+sep+network+sep+neType+sep+ne+sep+
								_int+sep+_pload+sep+_calim+sep+_offdo+sep+_offdi+sep+_ftchdo+sep+
								_ftchdi+sep+_offmph+sep+_offmpl+sep+_ftchmph+sep+_ftchmpl;
						
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