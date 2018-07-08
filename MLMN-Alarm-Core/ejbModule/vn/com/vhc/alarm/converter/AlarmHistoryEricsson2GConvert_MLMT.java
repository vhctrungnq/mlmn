package vn.com.vhc.alarm.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class AlarmHistoryEricsson2GConvert_MLMT extends AL_BasicConverter {

	private static final Logger logger = Logger.getLogger(AlarmHistoryEricsson2GConvert_MLMT.class.getName());
	private static final String HEADER = "#VENDOR;TYPE;ALARM_TYPE;EVENT_TYPE;OBJECT_OF_REFERENCE;BSC;NETWORK;SITE;PERCEIVED_SEVERITY;PROBABLE_CAUSE;SPECIFIC_PROBLEM;PROBLEM_TEXT;LOG_RECORD_ID;SDATE;EDATE;CELL;MO;DIP";

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		BufferedReader reader = null;
		BufferedWriter out = null;
		
		// dung de lay cell va mo
		Pattern patt = Pattern.compile("([^\\s]+)(\\s+)(.*)");
		Matcher m;
		String[] tmp;
		StringBuffer problemText = new StringBuffer();
		String severity = "";
		boolean isClearing = false;
		try {
			String line;
			String bsc = "";
			String obj = "";
			String site = "";
			String logId = "";
			String sdate = "";
			String edate = "";
			String alarmName = "";
			String cell = "";
			String mo = "";
			String dip = "";
			reader = new BufferedReader(new FileReader(file));
			out = new BufferedWriter(new FileWriter(direcPath + "/" + file.getName()));
			out.write(HEADER + "\n");
			while ((line = reader.readLine()) != null) {			
				if (line.equals("")) {
					continue;
				}

				// fist line of block
				if (!line.contains("ALARM END") && line.contains("-------------")) {
					isClearing = false;
					out.write("ERICSSON;2G;");
					line = line.replaceAll("-+", "");
					if (line.substring(0, 20).equals("ALARM CLEARING FROM ")) {
						isClearing = true;
						out.write("ALARM CLEARING FROM;");
					} else {
						out.write("ALARM FROM;");
					}
					
				}
				
				if (line.contains("Log Record ID:") && !isClearing){
					logId = line.replaceAll("\\D+", "");				
				} 
				
				if (line.contains("Event Time:")) {
					sdate = "";
					edate = "";
					if (!isClearing) {
						sdate = line.replaceAll("Event Time:\\s+", "").replaceAll("\\s+", " ");
					} else {
						edate = line.replaceAll("Event Time:\\s+", "").replaceAll("\\s+", " ");
					}
				} 
				
				if (line.contains("Event Type:")) {
					out.write(line.replaceAll("Event Type:\\s*", "") + ";");				
				} 
				
				if (line.contains("Object of Reference")) {
					
					obj = line.replaceAll("Object of Reference:\\s*", "") ;
					out.write(obj + ";");
					tmp = obj.split(",");
					for (String str : tmp) {
						if (str.contains("ManagedElement")) {
							bsc = str.replaceAll("ManagedElement=\\s*", "");
						} else if (str.contains("BtsSiteMgr=")) {
							site = str.replaceAll("BtsSiteMgr=\\s*", "");
						}
					}
					out.write(bsc + ";");
					if (bsc.startsWith("B")) {
						out.write("2G;");
					} else if (bsc.startsWith("R")) {
						out.write("3G;");
					} else {
						out.write(";");
					}
					out.write(site + ";");
				} 
				
				if (line.contains("Perceived Severity")) {
					severity = line.replaceAll("Perceived Severity:\\s*", "");
					if (severity.equals("Critical")) {
						out.write("A1;");
					} else if (severity.equals("Major")|| severity.equals("Cleared")) {
						out.write("A2;");
					} else if (severity.equals("Warning")) {
						out.write("O2;");
					} else if (severity.equals("Minor")) {
						out.write("A3;");
					} else if (severity.equals("Indeterminate")) {
						out.write("Indeterminate;");
					}
				}
				
				if (line.contains("Probable Cause")) {
					out.write(line.replaceAll("Probable Cause:\\s*", "") + ";");
				} 
				
				if (line.contains("Specific Problem")) {
					alarmName = line.replaceAll("Specific Problem:\\s*", "");
					out.write(alarmName + ";");
				}
				
				if (line.contains("Problem Text")) {
					problemText.setLength(0);
					
					// neu la canh bao cell thi bo di 3 dong con canh bao radio thi 
					line = reader.readLine();
					
					// TRUNGNQ 27/9/2016 bo qua phan alarm name dong thoi lay them cell, mo, dip
					line = reader.readLine();
					while (alarmName.contains(line)) {
						line = reader.readLine();
					}
					
					while (line != null && !line.equals("") && !line.equals("END") && !line.contains("Correlated ID:")) {
						problemText.append(line + ">");
						if (alarmName.startsWith("CELL") && line.startsWith("CELL")) {
							line = reader.readLine();
							problemText.append(line + ">");
							m = patt.matcher(line);
							if (m.find()) {
								cell = m.group(1);
							}
							
							continue;
						} else if (alarmName.startsWith("RADIO") && line.startsWith("MO")) {
							line = reader.readLine();
							problemText.append(line + ">");
							m = patt.matcher(line);
							if (m.find()) {
								mo = m.group(1);
							}
							
							continue;
						} else if (alarmName.startsWith("DIGITAL") && line.startsWith("DIP")) {
							line = reader.readLine();
							problemText.append(line + ">");
							m = patt.matcher(line);
							if (m.find()) {
								dip = m.group(1);
							}
							
							continue;
						} else {
							line = reader.readLine();
						}
						
					}
					
					// xoa dau ">" cuoi cung neu co
					if (problemText.length() > 0) {
						problemText.deleteCharAt(problemText.length()-1);
					}
					
					problemText.append(";");
					out.write(problemText.toString().replaceAll(">>", ">").replaceAll("\\s+", " ").trim());
				}  
				
				if (line != null && line.contains("Correlated ID:") && isClearing) {
					logId = line.replaceAll("\\D+", "");
				}
				
				if (line.contains("ALARM END")) {
					out.write(logId + ";");
					out.write(sdate + ";");
					out.write(edate + ";");
					out.write(cell + ";");
					out.write(mo + ";");
					out.write(dip + "\n");
					
					bsc = "";
					obj = "";
					site = "";
					logId = "";
					sdate = "";
					edate = "";
					alarmName = "";
					cell = "";
					mo = "";
					dip = "";	
				}
				
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		logger.info("Convert file: " + file.getName() + " success");
	}

}
