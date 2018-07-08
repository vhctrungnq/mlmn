package vn.com.vhc.alarm.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; 
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.csvreader.CsvReader;

import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.model.StructAlarmAlcatel2G;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;
import vn.com.vhc.alarm.utils.DataTools;

/**
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Copyright: Copyright (c) by VHCSoft 2016
 * </p>
 * <p>
 * Company: VietNam High Technology Software JSC.
 * </p>
 * <p>
 * Create Date:Jan 13, 2017
 * </p>
 * 
 * @author VHC - Software
 * @version 1.0
 */

public class AlarmAlcatel2GConverter extends AL_BasicConverter{
	private static final Logger logger = Logger.getLogger(AlarmAlcatel2GConverter.class.getName());
	private static final SimpleDateFormat dff = new SimpleDateFormat("yyyyMMddHHmmss");

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 
		StructAlarmAlcatel2G structAlcatel =  new StructAlarmAlcatel2G();
		boolean block = false; 
		
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			
			CsvReader buffReader = new CsvReader(reader); 
			buffReader.setDelimiter(';');
			
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			wAlarmLog.write(structAlcatel.getHeader());
			
			while(buffReader.readRecord()){
				structAlcatel =  new StructAlarmAlcatel2G();
				if(!block){
					if(buffReader.getRawRecord().contains("EVTTIME;FRDNAME")){
						String[] header = buffReader.getValues();
						buffReader.setHeaders(header);
						block = true;
					}
				}else{
					String EVTTIME = buffReader.get("EVTTIME");
					String FRDNAME = buffReader.get("FRDNAME");
					String SEV = buffReader.get("SEV");
					String EVTTYPE = buffReader.get("EVTTYPE");
					String PBCAUSE = buffReader.get("PBCAUSE");
					String SPECPB = buffReader.get("SPECPB");
					String MONITOR = buffReader.get("MONITOR");
					String STATECHG = buffReader.get("STATECHG");
					String ADDINFO = buffReader.get("ADDINFO");
					
					int REPCOUNT = 0;
					try {
						REPCOUNT = Integer.valueOf(buffReader.get("REPCOUNT"));
					} catch (Exception e) {
						REPCOUNT = 0;
					}
					
					String ACKSTS = buffReader.get("ACKSTS");
					String CLRSTS = buffReader.get("CLRSTS");
					String CLRTIME = buffReader.get("CLRTIME");
					String USRNOTE = buffReader.get("USRNOTE");
					String MOC = buffReader.get("MOC");
					String MOI = buffReader.get("MOI");
					String ALARMNB = buffReader.get("ALARMNB");
					
					if (REPCOUNT > 0 && CLRTIME.equals("")) 
						continue;
					//Get vendor
					structAlcatel.vendor="ALCATEL";
					//Get network
					structAlcatel.network="2G";
					//Get start date
					structAlcatel.evttime=EVTTIME.substring(0, EVTTIME.indexOf("."));
					//Get alarm name
					structAlcatel.alarmName = SPECPB.split(",")[0]; 
					//Get alarm info
					structAlcatel.frdnameInfo = FRDNAME;
					//Get ne,site,cell
					if(check_in_hoa(FRDNAME)){
						if(FRDNAME.contains(" ")){
							structAlcatel.ne = FRDNAME.substring(0, FRDNAME.indexOf(" "));
							FRDNAME = FRDNAME.substring(FRDNAME.indexOf(" ")+1, FRDNAME.length());
							structAlcatel.netype="BSC";
							if (check_in_hoa(FRDNAME)) {
								if(FRDNAME.contains(" ")){
									structAlcatel.site = FRDNAME.substring(0, FRDNAME.indexOf(" "));
									FRDNAME = FRDNAME.substring(FRDNAME.indexOf(" ")+1, FRDNAME.length());
									structAlcatel.netype="BTS";
									if (check_in_hoa(FRDNAME)) {
										if(FRDNAME.contains(" ")){
											structAlcatel.cell = FRDNAME.substring(0, FRDNAME.indexOf(" "));
											structAlcatel.netype="CELL";
										}else{
											structAlcatel.cell = FRDNAME;
											structAlcatel.netype="CELL";
										}
									}
								}else{
									structAlcatel.site = FRDNAME;
									structAlcatel.netype="BTS";
								}
							}
						}else{
							structAlcatel.ne = FRDNAME;
							structAlcatel.netype="BSC";
						}
					} 
					//Get severity
					structAlcatel.severity= DataTools.formatSeverity(SEV.substring(0, SEV.indexOf(" ")));
					//Get evttype
					structAlcatel.evttype=EVTTYPE;
					//Get pbcause
					structAlcatel.pbcause=PBCAUSE;
					//Get end date
					if (CLRTIME != null && !CLRTIME.equals("")) {
						structAlcatel.clrtime=CLRTIME.substring(0, CLRTIME.indexOf("."));
						structAlcatel.atype="ALARM CLEARING";
					}else{
						structAlcatel.atype="ALARM FROM";
					}
					
					//Loai bo truong hop loi ten SITE < 6
					if(structAlcatel.site != null){
						if(structAlcatel.site.length() < 6) structAlcatel.site="";
					} 
					
					//Kiem tra thoi gian bat dau lon hon ngay hien tai hay ko?
					//Neu lon hon khong ghi du lieu ban ghi do vao file
					//java.util.Date a = new java.util.Date();
					//if(dff.parse(structAlcatel.evttime).compareTo(a) == -1){
						wAlarmLog.newLine();
						wAlarmLog.write(structAlcatel.toString());
					//}
				}
			}
		}catch (Exception e) { 
			throw new AL_ConvertException(e.getMessage(), "VMSC2-0306", e);
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
	} 
	
	private boolean check_in_hoa(String a){
		String a1 = null;
		try {
			a1 = a.substring(0,2);
		} catch (Exception e) {
			return false;
		}
		
		if(a1.matches("[A-Z,0-9]{2}")) return true;
		else return false;
	}
}

